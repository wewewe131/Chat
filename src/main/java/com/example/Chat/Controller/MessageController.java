package com.example.Chat.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.Chat.Common.ApiResponse;
import com.example.Chat.Entity.Message;
import com.example.Chat.Service.MessageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "消息相关接口", description = "消息相关接口")
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/message")
public class MessageController {

    private MessageService messageService;

    @GetMapping("/getMessageList")
    public ApiResponse<List<Message>> getMessageList(String sessionId){
        List<Message> list = messageService.list(
                new LambdaQueryWrapper<Message>()
                        .eq(Message::getSessionId, sessionId)
                        .orderByAsc(Message::getCreateTime)
        );
        return ApiResponse.ok(list);
    }

}
