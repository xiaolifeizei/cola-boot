package com.matrix.cola.auth.support;

import com.matrix.cola.auth.util.ResponseUtil;
import com.matrix.cola.common.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 匿名用户访问需要权限才能访问的资源时的异常
 *
 * @author : cui_feng
 * @since : 2022-04-21 17:17
 */
public class TokenUnAuthEntryPint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ResponseUtil.out(response, Result.err(508,"您未认证，无法访问本资源"));
    }
}
