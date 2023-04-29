package com.example.firstdemo.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.firstdemo.Entity.FriendGroup;
import com.example.firstdemo.Service.FriendGroupService;
import com.example.firstdemo.Mapper.FriendGroupMapper;
import org.springframework.stereotype.Service;

/**
* @author 22832
* @description 针对表【friend_group】的数据库操作Service实现
* @createDate 2023-04-26 23:11:07
*/
@Service
public class FriendGroupServiceImpl extends ServiceImpl<FriendGroupMapper, FriendGroup>
    implements FriendGroupService{

}




