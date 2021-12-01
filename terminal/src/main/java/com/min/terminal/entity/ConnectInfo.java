package com.min.terminal.entity;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

/**
 * @author zhangjiamin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConnectInfo {

    private WebSocketSession webSocketSession;
    private JSch jsch;
    private Channel channel;
}
