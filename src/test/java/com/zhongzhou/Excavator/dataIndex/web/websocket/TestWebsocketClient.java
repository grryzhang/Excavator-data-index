package com.zhongzhou.Excavator.dataIndex.web.websocket;

import java.net.URI;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.apache.tomcat.websocket.WsWebSocketContainer;

import org.junit.Test;

public class TestWebsocketClient {


	@Test
	public void f1() {
	     try {
	         WebSocketContainer container = ContainerProvider.getWebSocketContainer(); // 获取WebSocket连接器，其中具体实现可以参照websocket-api.jar的源码,Class.forName("org.apache.tomcat.websocket.WsWebSocketContainer");
	         String uri = "ws://localhost:8080/DataIndex/log";
	         Session session = container.connectToServer(Client.class, new URI(uri)); // 连接会话
	         session.getBasicRemote().sendText("123132132131"); // 发送文本消息
	         session.getBasicRemote().sendText("4564546");
	     } catch (Exception e) {
	         e.printStackTrace();
	     }
	 }
}
