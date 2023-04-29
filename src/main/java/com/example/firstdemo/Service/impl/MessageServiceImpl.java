package com.example.firstdemo.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.firstdemo.Entity.Message;
import com.example.firstdemo.Service.MessageService;
import com.example.firstdemo.Mapper.MessageMapper;
import org.springframework.stereotype.Service;

/**
* @author 22832
* @description 针对表【message】的数据库操作Service实现
* @createDate 2023-04-29 06:40:56
*/
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message>
    implements MessageService{

}




