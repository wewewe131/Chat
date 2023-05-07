package com.example.Chat.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.Chat.Entity.Session;
import com.example.Chat.Service.SessionService;
import com.example.Chat.Mapper.SessionMapper;
import org.springframework.stereotype.Service;

/**
* @author 22832
* @description 针对表【session】的数据库操作Service实现
* @createDate 2023-04-30 11:58:32
*/
@Service
public class SessionServiceImpl extends ServiceImpl<SessionMapper, Session>
    implements SessionService{

}




