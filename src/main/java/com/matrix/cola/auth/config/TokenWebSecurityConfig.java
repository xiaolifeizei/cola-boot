package com.matrix.cola.auth.config;


import com.matrix.cola.auth.filter.TokenAuthFilter;
import com.matrix.cola.auth.filter.TokenLoginFilter;
import com.matrix.cola.auth.support.ColaPasswordEncoderFactories;
import com.matrix.cola.auth.support.TokenAccessDeniedHandler;
import com.matrix.cola.auth.support.TokenLogoutHandler;
import com.matrix.cola.auth.support.TokenUnAuthEntryPint;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.time.Duration;
import java.util.Collections;

/**
 * Web安全配置
 *
 * @author : cui_feng
 * @since : 2022-04-20 14:18
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class TokenWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenAuthFilter tokenAuthFilter;


    @Override
    @SneakyThrows
    protected void configure(HttpSecurity http) {
        http
                .httpBasic()
            .and()
                .exceptionHandling()
                .authenticationEntryPoint(new TokenUnAuthEntryPint())// 未认证
                .accessDeniedHandler(new TokenAccessDeniedHandler()) // 权限不足
            .and()
                .cors()
                .configurationSource(corsConfigurationSource())
            .and()
                .csrf().disable()//关闭csrf
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//关闭Session
            .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/auth/**").permitAll()// 允许所有人访问
                .anyRequest().authenticated() // 其他所有访问需要鉴权认证
            .and()
                .logout().logoutUrl("/auth/logout")
                .addLogoutHandler(new TokenLogoutHandler())//退出登陆处理器
            .and()
                .addFilterAt(new TokenLoginFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(tokenAuthFilter, UsernamePasswordAuthenticationFilter.class);

    }

    /**
     * 配置用户信息实现类和密码加密处理器
     *
     * @param auth 鉴权管理器
     * @throws Exception 异常信息
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * 配置放行路径,这里只放静态资源
     * @param web webSecurity对象
     * @throws Exception 异常信息
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/public/**","/static/**");
    }

    @Bean
    @SneakyThrows
    public AuthenticationManager authenticationManagerBean() {
        return super.authenticationManagerBean();
    }

    public PasswordEncoder passwordEncoder() {
        return ColaPasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * 跨域配置
     * @return 跨域配置对象
     */
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOriginPatterns(Collections.singletonList("*"));
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setMaxAge(Duration.ofHours(1));
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
