package com.min.terminal.service;

import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.net.Socket;

/**
 * @author zhangjiamin
 * @date 2021/12/2 16:25
 */
public interface DockerService {

    /**
     * 创建与 docker 容器建立 exec 的连接
     * @param ip ip地址
     * @param containerId 容器id
     * @return execId
     */
//    String createExec(String ip, String containerId) throws Exception;

    /**
     * 以 /bin/bash 方式建立连接
     * @param ip ip地址
     * @param execId 命令id
     * @return socket连接
     */
    Socket connectExec(String ip, String execId) throws IOException;

    /**
     * 获得输出
     * @param session
     * @param ip
     * @param containerId
     * @param socket
     */
    void getExecMessage(WebSocketSession session, String ip, String containerId, Socket socket) throws IOException;

}
