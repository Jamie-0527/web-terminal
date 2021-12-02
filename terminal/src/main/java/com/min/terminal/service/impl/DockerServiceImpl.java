package com.min.terminal.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.min.terminal.service.DockerService;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @author zhangjiamin
 * @date 2021/12/2 16:28
 */
@Service
public class DockerServiceImpl implements DockerService {

//    @Override
//    public String createExec(String ip, String containerId) throws Exception {
//        return DockerUtil.query(ip, docker->{
//            ExecCreation execCreation=docker.execCreate(containerId,new String[]{"/bin/bash"},
//                    DockerClient.ExecCreateParam.attachStdin(), DockerClient.ExecCreateParam.attachStdout(), DockerClient.ExecCreateParam.attachStderr(),
//                    DockerClient.ExecCreateParam.tty(true));
//            return execCreation.id();
//        });
//    }

    @Override
    public Socket connectExec(String ip, String execId) throws IOException {
        Socket socket=new Socket(ip,2375);
        socket.setKeepAlive(true);
        OutputStream out = socket.getOutputStream();
        StringBuffer pw = new StringBuffer();
        pw.append("POST /exec/"+execId+"/start HTTP/1.1\r\n");
        pw.append("Host: "+ip+":2375\r\n");
        pw.append("User-Agent: Docker-Client\r\n");
        pw.append("Content-Type: application/json\r\n");
        pw.append("Connection: Upgrade\r\n");
        JSONObject obj = new JSONObject();
        obj.put("Detach",false);
        obj.put("Tty",true);
        String json=obj.toJSONString();
        pw.append("Content-Length: "+json.length()+"\r\n");
        pw.append("Upgrade: tcp\r\n");
        pw.append("\r\n");
        pw.append(json);
        out.write(pw.toString().getBytes(StandardCharsets.UTF_8));
        out.flush();
        return socket;
    }

    @Override
    public void getExecMessage(WebSocketSession session, String ip, String containerId, Socket socket) throws IOException {
        InputStream inputStream=socket.getInputStream();
        byte[] bytes=new byte[1024];
        StringBuffer returnMsg=new StringBuffer();
        while(true){
            int n = inputStream.read(bytes);
            String msg=new String(bytes,0,n);
            returnMsg.append(msg);
            bytes=new byte[10240];
            if(returnMsg.indexOf("\r\n\r\n")!=-1){
                session.sendMessage(new TextMessage(returnMsg.substring(returnMsg.indexOf("\r\n\r\n")+4,returnMsg.length())));
                break;
            }
        }
//        OutPutThread outPutThread=new OutPutThread(inputStream,session);
//        outPutThread.start();
//        execSessionMap.put(containerId,new ExecSession(ip,containerId,socket,outPutThread));
    }

    /**
     * 修改tty大小.
     * @param ip
     * @param width
     * @param height
     * @param execId
     * @throws Exception
     */
//    private void resizeTty(String ip, String width, String height, String execId) throws Exception {
//        DockerUtil.execute(ip, docker->{
//            docker.execResizeTty(execId,Integer.parseInt(height),Integer.parseInt(width));
//        });
//    }
}
