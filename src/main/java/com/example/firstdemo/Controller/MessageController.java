package com.example.firstdemo.Controller;

import com.example.firstdemo.Service.MessageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "消息相关接口", description = "消息相关接口")
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MessageController {

    private MessageService messageService;


}
