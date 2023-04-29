package com.example.firstdemo.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.firstdemo.Entity.Auth;
import com.example.firstdemo.Mapper.AuthMapper;
import com.example.firstdemo.Service.AuthService;
import org.springframework.stereotype.Service;

/**
* @author 22832
* @description 针对表【auth】的数据库操作Service实现
* @createDate 2023-03-05 04:44:08
*/
@Service
public class AuthServiceImpl extends ServiceImpl<AuthMapper, Auth>
    implements AuthService {

}




