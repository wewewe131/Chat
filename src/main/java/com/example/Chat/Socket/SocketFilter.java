package com.example.Chat.Socket;

import com.example.Chat.Common.LocalUser;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.firewall.FirewalledRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SocketFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 给websocket传递jwt
        FirewalledRequest request1 = (FirewalledRequest) request;
        String header = request1.getHeader("sec-websocket-protocol");
        // : Sent non-empty 'Sec-WebSocket-Protocol' header but no response was received
        ((HttpServletResponse) response).setHeader("sec-websocket-protocol", header);
        LocalUser.setLocalUserToken(header);
        chain.doFilter(request, response);

    }
}
