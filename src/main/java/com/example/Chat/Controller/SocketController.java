package com.example.Chat.Controller;

import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@ServerEndpoint("/socketTest")
@Slf4j
public class SocketController {

    public static final ArrayList<SocketController> SOCKET_CONTROLLER_ARRAY_LIST = new ArrayList<SocketController>();

    private String uid = null;

    @OnOpen
    public void open(@PathParam("/ids") String uid) {
        this.uid = uid;
        SOCKET_CONTROLLER_ARRAY_LIST.add(this);
    }

    @OnMessage
    public void onMessage(String message) {

    }

}
