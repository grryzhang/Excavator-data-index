package com.zhongzhou.Excavator.dataIndex.web.websocket.handler;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * 
 * @author Zhang Huanping
 */
public class IndexWebSocketHandler extends TextWebSocketHandler {

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String text = message.getPayload(); // 获取提交过来的消息
        System.out.println("handMessage:" + text);
        //template.convertAndSend("/topic/getLog", "call back"); // 这里用于广播
        session.sendMessage(message);
    }
}