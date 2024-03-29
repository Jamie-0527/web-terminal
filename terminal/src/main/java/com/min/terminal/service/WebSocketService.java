package com.min.terminal.service;

import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;


public interface WebSocketService {

    /**
     * 初始化ssh连接
     * @param session
     */
    void initConnection(WebSocketSession session);

    /**
     * 处理客户段发的数据
     * @param buffer
     * @param session
     */
    void recvHandle(String buffer, WebSocketSession session);

    /**
     * 数据写回前端 for websocket
     * @param session
     * @param buffer
     * @throws IOException
     */
    void sendMessage(WebSocketSession session, byte[] buffer) throws IOException;

    /**
     * 关闭连接
     * @param session
     */
    void close(WebSocketSession session);
}
