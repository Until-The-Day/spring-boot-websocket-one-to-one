package com.lee.app.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class CallHandler extends TextWebSocketHandler {

    private static final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    private static final Gson gson = new GsonBuilder().create();


    // 链接成功会调用此方法
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        log.info("链接创建成功：sessionId==" + session.getId());

    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        log.info("收到来自（" + session.getId() + "）send的请求");

        log.info(gson.toJson(message.getPayload()));

        JsonObject jsonObject = gson.fromJson(message.getPayload(), JsonObject.class);

        switch (jsonObject.get("id").getAsString()) {
            case "sign-in":
                signIn(jsonObject.get("name").getAsString(), session);
                break;
            case "call":
                call(jsonObject.get("callName").getAsString(), jsonObject.get("content").toString());
                break;
            default:
                break;
        }
    }

    /**
     *
     * @param name 注册名
     * @param session 当前session
     * @throws IOException
     */
    private void signIn(String name, WebSocketSession session) throws IOException {
        sessions.put(name, session);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("code", 200);
        jsonObject.addProperty("msg", "success");
        jsonObject.addProperty("id", "signIn");

        log.info(String.valueOf(sessions.size()));
        session.sendMessage(new TextMessage(jsonObject.toString()));
    }

    /**
     *
     * @param callName 发送人的name
     * @param content 发送内容
     */
    private void call(String callName, String content) throws IOException {
        WebSocketSession webSocketSession = sessions.get(callName);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("code", 200);
        jsonObject.addProperty("msg", content);
        jsonObject.addProperty("id", "call");

        webSocketSession.sendMessage(new TextMessage(jsonObject.toString()));
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
    }

}
