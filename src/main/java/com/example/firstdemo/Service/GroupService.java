package com.example.firstdemo.Service;

import com.example.firstdemo.Entity.Group;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.firstdemo.Entity.Vo.GroupInfoVo;
import com.example.firstdemo.Entity.Vo.UserGroupGrouping;

import java.util.List;

/**
* @author 22832
* @description 针对表【group】的数据库操作Service
* @createDate 2023-04-05 02:30:11
*/
public interface GroupService extends IService<Group> {

    void createGroup(Group group);

    List<UserGroupGrouping> getAllGroupInfo();
    String joinGroup(String groupId, String mark);

    String exitGroup(String groupId, String uid);

    String deleteGroup(String groupId);

    GroupInfoVo getGroupInfo(String groupId);

    String removeGroupUser(String groupid, String uid);
}
