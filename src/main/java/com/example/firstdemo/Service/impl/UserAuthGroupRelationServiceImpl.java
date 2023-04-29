package com.example.firstdemo.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.firstdemo.Entity.UserAuthGroupRelation;
import com.example.firstdemo.Service.UserAuthGroupRelationService;
import com.example.firstdemo.Mapper.UserAuthGroupRelationMapper;
import org.springframework.stereotype.Service;

/**
* @author 22832
* @description 针对表【user_auth_group_relation】的数据库操作Service实现
* @createDate 2023-04-05 03:19:50
*/
@Service
public class UserAuthGroupRelationServiceImpl extends ServiceImpl<UserAuthGroupRelationMapper, UserAuthGroupRelation>
    implements UserAuthGroupRelationService{

}




