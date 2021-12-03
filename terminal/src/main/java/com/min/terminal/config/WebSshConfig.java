package com.min.terminal.config;

import com.min.terminal.filter.WebSocketHandshakeInterceptor;
import com.min.terminal.handle.SocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @author zhangjiamin
 * @date 2021/11/28 14:54
 */
@Configuration
@EnableWebSocket
public class WebSshConfig implements WebSocketConfigurer {

    @Autowired
    private SocketHandler socketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(socketHandler, "/stomp/websocketJS")
                .addInterceptors(new WebSocketHandshakeInterceptor())
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }
}
