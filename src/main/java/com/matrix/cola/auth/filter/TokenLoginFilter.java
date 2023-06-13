package com.matrix.cola.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matrix.cola.auth.service.SecurityUser;
import com.matrix.cola.auth.util.JwtTokenUtil;
import com.matrix.cola.auth.util.ResponseUtil;
import com.matrix.cola.common.Result;
import com.matrix.cola.system.user.entity.UserEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 认证过滤器
 *
 * @author cui_feng
 * @since : 2022-04-20 14:18
 */
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {


    public TokenLoginFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        super.setPostOnly(false);
        super.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/auth/token", "POST"));
    }

    /**
     * 登陆认证
     * @param request request对象
     * @param response response对象
     * @return 认证对象
     * @throws AuthenticationException 认证异常
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        UserEntity userPO = new UserEntity();
        try {
            userPO = new ObjectMapper().readValue(request.getInputStream(), UserEntity.class);
        } catch (IOException e) {
            // throw new RuntimeException("没有获取到用户信息");
        }
        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(userPO.getLoginName(),userPO.getPassword(),new ArrayList<>()));
    }

    /**
     * 认证成功后
     * @param request request对象
     * @param response response对象
     * @param chain 过滤器链
     * @param authResult 认证请求
     * @throws IOException IO异常
     * @throws ServletException Web异常
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityUser securityUser = (SecurityUser) authResult.getPrincipal();
        // 认证成功返回Jwt Token
        Result result = Result.ok();
        result
                .put("token", JwtTokenUtil.createToken(securityUser.getCurrentUser()))
                .put("userId",securityUser.getCurrentUser().getId())
                .put("userName",securityUser.getCurrentUser().getName())
                .put("loginName",securityUser.getUsername())
                .put("groupId",securityUser.getCurrentUser().getGroupId());
        ResponseUtil.out(response,result);
    }

    /**
     * 登陆失败后
     * @param request request对象
     * @param response response对象
     * @param failed 认证失败对象
     * @throws IOException IO异常
     * @throws ServletException Web异常
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        ResponseUtil.out(response,Result.err("认证失败!用户名或密码错误"));
    }
}
