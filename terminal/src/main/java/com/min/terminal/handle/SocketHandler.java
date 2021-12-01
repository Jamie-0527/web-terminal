package com.min.terminal.handle;

import com.min.terminal.service.WebSocketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

@Component
public class SocketHandler extends DefaultHandshakeHandler implements WebSocketHandler {

    @Autowired
    private WebSocketService webSocketService;
    private Logger logger = LoggerFactory.getLogger(SocketHandler.class);

    @Override
    public void afterConnectionEstablished(WebSocketSession session){
        logger.info("连接WebSSH");
        //调用初始化连接
        webSocketService.initConnection(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message){

        if (message instanceof TextMessage) {
            logger.info("发送命令:{}", message.getPayload());

            webSocketService.recvHandle((String) message.getPayload(), session);
        } else if (message instanceof BinaryMessage) {

        } else if (message instanceof PongMessage) {

        } else {
            System.out.println("Unexpected WebSocket message type: " + message);
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception){
        logger.error("数据传输错误");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus){
        logger.info("断开WebSSH连接");
        //调用service关闭连接
        webSocketService.close(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
