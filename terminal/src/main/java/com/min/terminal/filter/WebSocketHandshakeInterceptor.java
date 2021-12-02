package com.min.terminal.filter;

import com.min.terminal.common.Constants;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author zhangjiamin
 * @date 2021/11/26 20:27
 */
public class WebSocketHandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    private final Logger logger = LoggerFactory.getLogger(WebSocketHandshakeInterceptor.class);

    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
                                   ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        // websocket握手建立前调用，获取httpsession
        if(request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servlet = (ServletServerHttpRequest) request;

            // 这里从request中获取session,获取不到不创建，可以根据业务处理此段
            HttpSession httpSession = servlet.getServletRequest().getSession();
            logger.info("httpSession key：" + httpSession.getId());
            httpSession.setAttribute(Constants.USER_KEY, httpSession.getId());

            // 获取到httpsession后，可以根据自身业务，操作其中的信息，这里只是单纯的和websocket进行关联
            attributes.put("HTTP_SESSION", httpSession);
//
//            if (request.getHeaders().containsKey("Sec-WebSocket-Extensions")) {
//                request.getHeaders().set("Sec-WebSocket-Extensions", "permessage-deflate");
//            }
            String ip = ((ServletServerHttpRequest) request).getServletRequest().getParameter("ip");
            String containerId = ((ServletServerHttpRequest) request).getServletRequest().getParameter("containerId");
            String width = ((ServletServerHttpRequest) request).getServletRequest().getParameter("width");
            String height = ((ServletServerHttpRequest) request).getServletRequest().getParameter("height");
            if (StringUtils.isNotBlank(ip) && StringUtils.isNotBlank(containerId)){
                attributes.put(Constants.IP, ip);
                attributes.put(Constants.CONTAINER_ID, containerId);
                attributes.put(Constants.WIDTH, width);
                attributes.put(Constants.HEIGHT, height);
            }
        }

        return super.beforeHandshake(request,response,wsHandler,attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler webSocketHandler, Exception e) {
        // websocket握手建立后调用
        logger.info("websocket连接握手成功");
    }
}

