package com.min.terminal.entity;

import com.min.terminal.utils.OutPutThread;

import java.net.Socket;

/**
 * @author zhangjiamin
 * @date 2021/12/2 18:55
 */
public class ExecInfo {

    /**
     * ip 地址
     */
    private String ip;

    /**
     * 容器 id
     */
    private String containerId;

    /**
     * socket 连接
     */
    private Socket socket;

    /**
     * 输出对象
     */
    private OutPutThread outPutThread;

    public ExecInfo(String ip, String containerId, Socket socket, OutPutThread outPutThread) {
        this.ip = ip;
        this.containerId = containerId;
        this.socket = socket;
        this.outPutThread = outPutThread;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public OutPutThread getOutPutThread() {
        return outPutThread;
    }

    public void setOutPutThread(OutPutThread outPutThread) {
        this.outPutThread = outPutThread;
    }
}
