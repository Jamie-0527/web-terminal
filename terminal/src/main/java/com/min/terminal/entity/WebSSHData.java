package com.min.terminal.entity;

public class WebSSHData {

    /**
     * 操作
     */
    private String operate;

    /**
     * ip地址
     */
    private String host;

    /**
     * 端口号
     */
    private Integer port;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 指令
     */
    private String command;

    public WebSSHData() {}

    public WebSSHData(String operate, String host, Integer port, String username, String password, String command) {
        this.operate = operate;
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.command = command;
    }

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return "WebSSHData{" +
                "operate='" + operate + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", command='" + command + '\'' +
                '}';
    }

}
