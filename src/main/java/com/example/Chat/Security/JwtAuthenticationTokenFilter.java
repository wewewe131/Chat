package com.example.Chat.Security;

import com.example.Chat.Common.LocalUser;
import com.example.Chat.Entity.ChatUser;
import com.example.Chat.Service.ChatUserService;
import com.example.Chat.utils.JWTProcess;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
public class JwtAuthenticationTokenFilter implements Filter {

    private ChatUserService chatUserService;

    private RedisTemplate<String, Object> redisTemplate;

    public JwtAuthenticationTokenFilter(ChatUserService chatUserService, RedisTemplate<String, Object> redisTemplate) {

        this.chatUserService = chatUserService;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        log.info("请求经过了JwtAuthenticationTokenFilter：{}", request.getRequestURI());
        String authorization = request.getHeader("Authorization");
        authorization = authorization.replace("Bearer ", "");
        if (Strings.isBlank(authorization)) {
            filterChain.doFilter(request, response);
            return;
        }

        String userId;
        try {
            userId = JWTProcess.verifyJWTGetId(authorization);
        } catch (Exception e) {
            filterChain.doFilter(request, response);
            return;
        }

        redisTemplate.opsForValue().set("onlineUser"+userId,userId, 30L, TimeUnit.MINUTES);

        ChatUser loginUser = chatUserService.getById(userId);

        LocalUser.setLocalUser(loginUser);

        UserDetail userDetail = new UserDetail();

        BeanUtils.copyProperties(loginUser, userDetail);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());

        SecurityContext securityContext = SecurityContextHolder.getContext();

        securityContext.setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
