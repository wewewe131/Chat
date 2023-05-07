package com.example.Chat.Socket;

import com.example.Chat.Common.ApiResponse;
import com.example.Chat.Common.LocalUser;
import com.example.Chat.Common.ResEnums;
import com.example.Chat.Entity.Message;
import com.example.Chat.utils.JWTProcess;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;



import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint(value = "/socket/mainLink")
@Slf4j
public class UserGroupSocket {

    private static CopyOnWriteArraySet<UserGroupSocket> onlineUserMuster;

    private static RedisTemplate redisTemplate;


    private static MessageSocket messageSocket;
    @Autowired
    public void setMessageSocket(MessageSocket messageSocket) {
        UserGroupSocket.messageSocket = messageSocket;
    }

    private Boolean isOnline = false;

    private Session session;

    private String JWT;

    private String uid;

    public static void setOnlineUserMuster(CopyOnWriteArraySet<UserGroupSocket> onlineUserMuster) {
        UserGroupSocket.onlineUserMuster = onlineUserMuster;
    }

    public UserGroupSocket() {

    }

    public static void setRedisTemplate(RedisTemplate redisTemplate) {
        UserGroupSocket.redisTemplate = redisTemplate;
    }

    public void setJWT(String JWT) {
        this.JWT = JWT;
        this.uid = JWTProcess.verifyJWTGetId(JWT);
    }

    public String getUid() {
        return uid;
    }


    @OnOpen
    public void onOpen(Session session) throws IOException {
        this.JWT = LocalUser.getLocalUserToken();
        this.uid = JWTProcess.verifyJWTGetId(JWT);
        log.info("socket线程ID:{}", Thread.currentThread().getId());
        log.info("sessionId:{}", session.getId());
        log.info("JWT:{}", JWT);
        ObjectMapper objectMapper = new ObjectMapper();
        Object o = redisTemplate.opsForValue().get("onlineUser" + uid);
        isOnline = Objects.nonNull(o);
        if (!isOnline) {
            session.getBasicRemote().sendText(objectMapper.writeValueAsString(ApiResponse.ok("用户未登录")));
            session.close();
        } else {
            onlineUserMuster.add(this);
            this.session = session;
            session.getBasicRemote().sendText(objectMapper.writeValueAsString(ApiResponse.ok("连接成功")));
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        log.info("收到消息:{}", message);
        ObjectMapper objectMapper = new ObjectMapper();

        HashMap hashMap = objectMapper.readValue(message, HashMap.class);

        if (hashMap == null) {
            session.getBasicRemote().sendText(ResEnums.FAIL.getMsg());
            return;
        }

        String mode = (String) hashMap.get("mode");

        if ("message".equals(mode)){
            String msg1 = objectMapper.writeValueAsString(hashMap.get("msg"));
            Message msg = objectMapper.readValue((msg1) , Message.class);
            messageSocket.newMessage(msg);
        }

        System.out.println("收到消息");
    }

    @OnError
    public void onError(Throwable error, Session session) {
        error.printStackTrace();
        System.out.println("发生错误");
    }

    @OnClose
    public void onClose() {
        onlineUserMuster.remove(this);
        System.out.println("连接关闭");
    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    public static void sendToAllUser(String message) throws IOException {
        for (UserGroupSocket user : onlineUserMuster) {
            user.sendMessage(message);
        }
    }

    public static void sendArrowUsers(Object message, List userIds) throws IOException {
        for (UserGroupSocket socket : UserGroupSocket.onlineUserMuster) {
            String uid1 = socket.getUid();
            if (userIds.contains(uid1)) {
                ObjectMapper objectMapper = new ObjectMapper();
                // 注册时间模块
                objectMapper.registerModule(new JavaTimeModule());
                String text = objectMapper.writeValueAsString(message);
                socket.session.getBasicRemote().sendText(text);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserGroupSocket socket = (UserGroupSocket) o;

        return Objects.equals(uid, socket.uid);
    }

    @Override
    public int hashCode() {
        return uid != null ? uid.hashCode() : 0;
    }
}

