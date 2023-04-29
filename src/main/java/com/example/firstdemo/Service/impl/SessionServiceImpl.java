package com.example.firstdemo.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.firstdemo.Entity.Session;
import com.example.firstdemo.Service.SessionService;
import com.example.firstdemo.Mapper.SessionMapper;
import org.springframework.stereotype.Service;

/**
* @author 22832
* @description 针对表【session】的数据库操作Service实现
* @createDate 2023-04-29 06:40:37
*/
@Service
public class SessionServiceImpl extends ServiceImpl<SessionMapper, Session>
    implements SessionService{

}




