package com.example.firstdemo.Security;

import com.example.firstdemo.Common.LocalUser;
import com.example.firstdemo.Entity.ChatUser;
import com.example.firstdemo.Service.ChatUserService;
import com.example.firstdemo.utils.JWTProcess;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtAuthenticationTokenFilter implements Filter {

    private ChatUserService chatUserService;

    public JwtAuthenticationTokenFilter(ChatUserService chatUserService) {

        this.chatUserService = chatUserService;
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
