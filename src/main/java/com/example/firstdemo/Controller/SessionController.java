package com.example.firstdemo.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.firstdemo.Common.ApiResponse;
import com.example.firstdemo.Common.ConfigKeyEnum;
import com.example.firstdemo.Common.LocalUser;
import com.example.firstdemo.Entity.ChatUser;
import com.example.firstdemo.Entity.Session;
import com.example.firstdemo.Service.ChatUserService;
import com.example.firstdemo.Service.GroupService;
import com.example.firstdemo.Service.MessageService;
import com.example.firstdemo.Service.SessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@Tag(name = "会话相关接口", description = "会话相关接口")
@RequestMapping("/session")
public class SessionController {

    @Autowired
    private ChatUserService chatUserService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    public MessageService messageService;

    @Autowired
    public GroupService groupService;


    @PostMapping("/createSession")
    @Operation(summary = "打开会话")
    public ApiResponse<Session> createSession(@RequestBody Session session) {
        Integer receiveId = session.getReceiveId();
        String sessionType = session.getSessionType();
        Integer userId = session.getUserId();

        List<Session> list = sessionService.list(
                new LambdaQueryWrapper<Session>()
                        .eq(Session::getUserId, userId)
                        .eq(Session::getReceiveId, receiveId)
                        .eq(Session::getSessionType, sessionType)
                        .or()
                        .eq(Session::getUserId, receiveId)
                        .eq(Session::getReceiveId, userId)
                        .eq(Session::getSessionType, sessionType)

        );
        if (list.size() == 0)
            sessionService.save(session);

        return ApiResponse.ok(session);

    }

    @Operation(summary = "获取会话列表",responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",description = "获取成功",content = @io.swagger.v3.oas.annotations.media.Content(schema = @Schema(implementation = Session.class)))
    })
    @GetMapping("/getSessionList")
    public ApiResponse<List> getSessionList() {
        ChatUser localUser = LocalUser.getLocalUser();
        String uid = localUser.getUid();
        List<Session> list = sessionService.list(
                new LambdaQueryWrapper<Session>()
                        .eq(Session::getUserId, uid)
                        .or()
                        .eq(Session::getReceiveId, uid)
        );

        if(list.size() == 0)
            return ApiResponse.ok(list);
        List<Session> collect = list.stream().map(v -> {
            String receiveName = v.getReceiveName();
            if (localUser.getUname().equals(receiveName)) {
                ChatUser byId = chatUserService.getById(v.getUserId());
                v.setReceiveName(byId.getUname());
            }
            if(v.getSessionType().equals(ConfigKeyEnum.request_apply_group.getKey())){
                v.setReceiveName(groupService.getById(v.getReceiveId()).getGroupName());
                sessionService.updateById(v);
            }
            return v;
        }).collect(Collectors.toList());

        return ApiResponse.ok(collect);
    }

}
