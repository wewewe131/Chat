package com.example.firstdemo.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.firstdemo.Entity.FriendRelation;
import com.example.firstdemo.Service.FriendRelationService;
import com.example.firstdemo.Mapper.FriendRelationMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author 22832
* @description 针对表【friend_relation】的数据库操作Service实现
* @createDate 2023-03-15 22:15:37
*/
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class FriendRelationServiceImpl extends ServiceImpl<FriendRelationMapper, FriendRelation>
    implements FriendRelationService{


}




