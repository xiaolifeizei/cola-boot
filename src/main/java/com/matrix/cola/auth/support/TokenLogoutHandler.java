package com.matrix.cola.auth.support;

import com.matrix.cola.auth.util.ResponseUtil;
import com.matrix.cola.common.Result;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 推出登陆处理器
 *
 * @author : cui_feng
 * @since : 2022-04-21 17:24
 */
public class TokenLogoutHandler implements LogoutHandler {
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        ResponseUtil.out(response, Result.ok("退出登陆成功"));
    }
}
