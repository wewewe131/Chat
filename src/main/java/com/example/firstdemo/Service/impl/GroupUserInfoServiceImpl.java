package com.example.firstdemo.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.firstdemo.Entity.GroupUserInfo;
import com.example.firstdemo.Service.GroupUserInfoService;
import com.example.firstdemo.Mapper.GroupUserInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author 22832
* @description 针对表【group_user_info】的数据库操作Service实现
* @createDate 2023-04-25 23:13:31
*/
@Service
public class GroupUserInfoServiceImpl extends ServiceImpl<GroupUserInfoMapper, GroupUserInfo>
    implements GroupUserInfoService{

}




