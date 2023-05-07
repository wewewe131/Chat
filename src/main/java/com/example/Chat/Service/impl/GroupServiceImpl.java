package com.example.Chat.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.Chat.Common.ConfigKeyEnum;
import com.example.Chat.Common.LocalUser;
import com.example.Chat.Entity.*;
import com.example.Chat.Entity.Vo.ApplyVo;
import com.example.Chat.Entity.Vo.GroupInfoVo;
import com.example.Chat.Entity.Vo.GroupUserInfoVo;
import com.example.Chat.Entity.Vo.UserGroupGrouping;
import com.example.Chat.Service.AuthService;
import com.example.Chat.Service.ChatUserService;
import com.example.Chat.Service.GroupService;
import com.example.Chat.Mapper.GroupMapper;
import com.example.Chat.Service.RequestApplyService;
import com.example.Chat.Socket.UserGroupSocket;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 22832
 * @description 针对表【group】的数据库操作Service实现
 * @createDate 2023-04-05 02:30:11
 */
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group>
        implements GroupService {

    private final UserAuthGroupRelationServiceImpl userAuthGroupRelationService;

    private final RequestApplyService requestApplyService;

    private final ChatUserService chatUserService;

    private final AuthService authService;

    @Override
    public void createGroup(Group group) {
        ChatUser localUser = LocalUser.getLocalUser();
        this.save(group);

        UserAuthGroupRelation relation = new UserAuthGroupRelation();
        relation.setAuthId(ConfigKeyEnum.group_host.getKey());
        relation.setGroupid(group.getGroupId());
        relation.setUid(localUser.getUid());
        userAuthGroupRelationService.save(relation);
    }

    @Override
    public List<UserGroupGrouping> getAllGroupInfo() {
        ChatUser localUser = LocalUser.getLocalUser();
        String uid = localUser.getUid();

        List<Group> createList = userAuthGroupRelationService.list(
                new QueryWrapper<UserAuthGroupRelation>()
                        .eq("uid", uid)
                        .eq("auth_id", ConfigKeyEnum.group_host.getKey())
                        .or()
                        .eq("uid", uid)
                        .eq("auth_id", ConfigKeyEnum.group_admin.getKey())
        ).stream().map(UserAuthGroupRelation::getGroupid).map(this::getById).collect(Collectors.toList());

        List<Group> joinList = userAuthGroupRelationService.list(
                new QueryWrapper<UserAuthGroupRelation>()
                        .eq("uid", uid)
                        .eq("auth_id", ConfigKeyEnum.group_member.getKey())

        ).stream().map(UserAuthGroupRelation::getGroupid).map(this::getById).collect(Collectors.toList());

        List<UserGroupGrouping> userGroupGroupings = new ArrayList<>();

        userGroupGroupings.add(UserGroupGrouping.builder().GroupGroupingName("我管理的群聊").groupList(createList).build());
        userGroupGroupings.add(UserGroupGrouping.builder().GroupGroupingName("我加入的群聊").groupList(joinList).build());

        return userGroupGroupings;
    }

    @Override
    public String joinGroup(String groupId, String mark) {
        ChatUser localUser = LocalUser.getLocalUser();
        String uid = localUser.getUid();

        Group byId = this.getById(groupId);

        if (byId == null)
            return "群聊不存在";

        List<RequestApply> list = requestApplyService.list(
                new LambdaQueryWrapper<RequestApply>()
                        .eq(RequestApply::getReceiveId, groupId)
                        .eq(RequestApply::getUserIdRequest, uid)
                        .eq(RequestApply::getIsAccept, 0)
                        .eq(RequestApply::getType, ConfigKeyEnum.request_apply_group.getKey())
        );

        if (list.size() > 0)
            return "已经申请过了";

        List<UserAuthGroupRelation> userAuthGroupRelations = userAuthGroupRelationService.list(
                new LambdaQueryWrapper<UserAuthGroupRelation>()
                        .eq(UserAuthGroupRelation::getGroupid, groupId)
                        .eq(UserAuthGroupRelation::getUid, uid)
        );

        if (userAuthGroupRelations.size() > 0)
            return "已在群聊中";


        RequestApply build = RequestApply.builder()
                .receiveId(groupId)
                .userIdRequest(uid)
                .type(ConfigKeyEnum.request_apply_group.getKey())
                .mark(mark)
                .isAccept(0)
                .build();

        boolean save = requestApplyService.save(build);

        List adminIds = userAuthGroupRelationService.list(new QueryWrapper<UserAuthGroupRelation>()
                .eq("groupid", groupId)
                .eq("auth_id", ConfigKeyEnum.group_host.getKey())
                .or()
                .eq("auth_id", ConfigKeyEnum.group_admin.getKey())
        ).stream().map(UserAuthGroupRelation::getUid).collect(Collectors.toList());

        ApplyVo joinGroup = ApplyVo.builder()
                .userName(localUser.getUname())
                .id(build.getId())
                .uid(build.getUserIdRequest())
                .applyId(build.getReceiveId())
                .mark(build.getMark())
                .type(build.getType()).build();

        try {
            UserGroupSocket.sendArrowUsers(joinGroup, adminIds);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return save ? "发送成功,请等待管理员通过" : "发送失败";
    }

    @Override
    public String exitGroup(String groupId, String uid) {

        UserAuthGroupRelation one = userAuthGroupRelationService.getOne(
                new LambdaQueryWrapper<UserAuthGroupRelation>()
                        .eq(UserAuthGroupRelation::getGroupid, groupId)
                        .eq(UserAuthGroupRelation::getUid, uid)
        );

        if (one.getAuthId().equals(ConfigKeyEnum.group_host.getKey()))
            return "群主不能退出群聊";

        boolean remove = userAuthGroupRelationService.remove(
                new LambdaQueryWrapper<UserAuthGroupRelation>()
                        .eq(UserAuthGroupRelation::getGroupid, groupId)
                        .eq(UserAuthGroupRelation::getUid, uid)
        );
        return remove ? "退出成功" : "退出失败";
    }

    @Override
    public String deleteGroup(String groupId) {
        boolean remove = userAuthGroupRelationService.remove(
                new LambdaQueryWrapper<UserAuthGroupRelation>()
                        .eq(UserAuthGroupRelation::getGroupid, groupId)
        );

        boolean b = this.removeById(groupId);
        return b && remove ? "删除成功" : "删除失败";
    }

    @Override
    public GroupInfoVo getGroupInfo(String groupId) {

        List<UserAuthGroupRelation> relationList = userAuthGroupRelationService.list(
                new LambdaQueryWrapper<UserAuthGroupRelation>()
                        .eq(UserAuthGroupRelation::getGroupid, groupId)
        );

        List<GroupUserInfoVo> collect = relationList.stream().map(relation -> {
            ChatUser chatUser = chatUserService.getById(relation.getUid());
            return GroupUserInfoVo.builder()
                    .Auth(relation.getAuthId())
                    .chatUser(chatUser)
                    .build();
        }).collect(Collectors.toList());

        return GroupInfoVo.builder()
                .groupUserInfoVos(collect)
                .group(this.getById(groupId))
                .build();
    }

    @Override
    public String removeGroupUser(String groupid, String uid) {

        boolean remove = userAuthGroupRelationService.remove(
                new LambdaQueryWrapper<UserAuthGroupRelation>()
                        .eq(UserAuthGroupRelation::getGroupid, groupid)
                        .eq(UserAuthGroupRelation::getUid, uid)
        );

        return remove ? "删除成功" : "删除失败";
    }
}




