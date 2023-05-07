package com.example.Chat.Socket;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.Chat.Entity.Message;
import com.example.Chat.Entity.Session;
import com.example.Chat.Entity.UserAuthGroupRelation;
import com.example.Chat.Service.MessageService;
import com.example.Chat.Service.SessionService;
import com.example.Chat.Service.UserAuthGroupRelationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MessageSocket {

    private MessageService messageService;

    private UserAuthGroupRelationService userAuthGroupRelationService;

    private SessionService sessionService;

    public void newMessage(Message message) throws IOException {
        messageService.save(message);
        Session session = sessionService.getById(message.getSessionId());
        session.setLastMessage(message.getMessage());
        sessionService.updateById(session);
        if ("group".equals(session.getSessionType())) {
            List<String> ids = userAuthGroupRelationService.list(
                    new LambdaQueryWrapper<UserAuthGroupRelation>()
                            .eq(UserAuthGroupRelation::getGroupid, session.getReceiveId())
            ).stream().map(UserAuthGroupRelation::getUid).collect(Collectors.toList());

            UserGroupSocket.sendArrowUsers(SocketRes.builder().data(message).type("message").build(), ids);
        }
        if ("friend".equals(session.getSessionType())) {
            UserGroupSocket.sendArrowUsers(SocketRes.builder().data(message).type("message").build(), Arrays.asList(session.getReceiveId().toString(), session.getUserId().toString()));
        }
    }




}
