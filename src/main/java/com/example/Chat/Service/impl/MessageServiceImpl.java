package com.example.Chat.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.Chat.Entity.Message;
import com.example.Chat.Service.MessageService;
import com.example.Chat.Mapper.MessageMapper;
import org.springframework.stereotype.Service;

/**
* @author 22832
* @description 针对表【message】的数据库操作Service实现
* @createDate 2023-04-30 11:17:32
*/
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message>
    implements MessageService{

}




