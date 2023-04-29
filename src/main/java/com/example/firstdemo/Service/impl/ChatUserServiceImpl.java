package com.example.firstdemo.Service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.firstdemo.Common.ConfigKeyEnum;
import com.example.firstdemo.Common.LocalUser;
import com.example.firstdemo.Entity.*;
import com.example.firstdemo.Entity.Vo.ApplyVo;
import com.example.firstdemo.Entity.Vo.FriendListItemVo;
import com.example.firstdemo.Entity.Vo.RegisterVo;
import com.example.firstdemo.Entity.Vo.UserInfoVo;
import com.example.firstdemo.Exception.UserException;
import com.example.firstdemo.Service.*;
import com.example.firstdemo.Mapper.ChatUserMapper;
import com.example.firstdemo.Socket.UserGroupSocket;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 22832
 * @description 针对表【chat_user】的数据库操作Service实现
 * @createDate 2023-03-12 00:44:27
 */
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class ChatUserServiceImpl extends ServiceImpl<ChatUserMapper, ChatUser>
        implements ChatUserService {

    private final UpasswordService upasswordService;

    private final FriendRelationService friendRelation;

    private final RequestApplyService requestApplyService;

    private final ConfigService configService;

    private final FriendGroupService friendGroupService;

    @Override
    @Transactional(noRollbackFor = UserException.class)
    public ChatUser register(RegisterVo chatUser) {
        // 注册
        chatUser.setUavatar(configService.getConfigValue(ConfigKeyEnum.default_avatar.getKey()));
        this.save(chatUser);
        // 注册密码
        //MD5加密
        String password = DigestUtil.md5Hex(chatUser.getPassword());
        Upassword upassword = Upassword.builder()
                .uid(chatUser.getUid())
                .upassword(password)
                .build();
        upasswordService.save(upassword);
        ChatUser byId = this.getById(chatUser.getUid());
        return byId;
    }

    @Override
    public String sendFriendApply(String uid, String mark) {

        ChatUser byId = this.getById(uid);

        if (byId == null) {
            return "用户不存在";
        }

        RequestApply one = requestApplyService.getOne(
                new LambdaQueryWrapper<RequestApply>()
                        .eq(RequestApply::getUserIdRequest, LocalUser.getLocalUser().getUid())
                        .eq(RequestApply::getReceiveId, uid)
                        .eq(RequestApply::getType, ConfigKeyEnum.request_apply_friend.getKey())
                        .eq(RequestApply::getIsAccept, 0)
        );
        if (one != null) {
            return "已经申请过了";
        }

        List<FriendRelation> list = friendRelation.list(
                new LambdaQueryWrapper<FriendRelation>()
                        .eq(FriendRelation::getUserIdFriend, LocalUser.getLocalUser().getUid())
                        .eq(FriendRelation::getUserIdMaster, uid)
        );

        if (list.size() > 0) {
            return "已经是好友了";
        }

        RequestApply requestApply = RequestApply.builder()
                .isAccept(0)
                .mark(mark)
                .userIdRequest(LocalUser.getLocalUser().getUid())
                .type(ConfigKeyEnum.request_apply_friend.getKey())
                .receiveId(uid)
                .build();

        boolean save = requestApplyService.save(requestApply);

        ApplyVo applyVo = ApplyVo.builder()
                .userName(LocalUser.getLocalUser().getUname())
                .id(requestApply.getId())
                .uid(requestApply.getUserIdRequest())
                .applyId(requestApply.getReceiveId())
                .mark(requestApply.getMark())
                .type(requestApply.getType()).build();

        try {
            UserGroupSocket.sendArrowUsers(applyVo, Arrays.asList(uid));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return save ? "发送成功" : "发送失败";
    }

    @Override
    public List<FriendListItemVo> getFriendList(String uid) {
        List<FriendRelation> friendRelationList = friendRelation.list(
                new LambdaQueryWrapper<FriendRelation>()
                        .eq(FriendRelation::getUserIdMaster, uid)
        );

        List<FriendGroup> groupList = friendGroupService.list(
                new LambdaQueryWrapper<FriendGroup>()
                        .eq(FriendGroup::getUid, uid)
        );

        List<FriendListItemVo> friendListItemVos = new ArrayList<>();

        FriendListItemVo allFriendList = FriendListItemVo.builder()
                .friendList(
                        friendRelationList.stream()
                                .map(friendRelation -> this.getById(friendRelation.getUserIdFriend()))
                                .collect(Collectors.toList())
                )
                .groupName("我的好友")
                .build();
        friendListItemVos.add(allFriendList);

        groupList.stream().forEach(
                v -> {
                    FriendListItemVo build = FriendListItemVo.builder()
                            .friendList(new ArrayList<>())
                            .groupName(v.getGroupname())
                            .build();
                    friendListItemVos.add(build);
                }
        );

        friendRelationList.forEach(friendRelation -> {
            if (friendRelation.getFriendGroup().equals(ConfigKeyEnum.friend_default_group.getKey()))
                return;


            int index = friendListItemVos.stream()
                    .filter(friendListItemVo -> friendRelation.getFriendGroup().equals(friendListItemVo.getGroupName()))
                    .map(friendListItemVos::indexOf).findFirst().orElse(-1);

            friendListItemVos.get(index).getFriendList().add(this.getById(friendRelation.getUserIdFriend()));

        });
        return friendListItemVos;
    }

    @Override
    public String removeFriendGroup(String uid, String groupName) {

        boolean update = friendRelation.update(FriendRelation.builder().friendGroup("").build(),
                new LambdaQueryWrapper<FriendRelation>()
                        .eq(FriendRelation::getUserIdMaster, uid)
                        .eq(FriendRelation::getFriendGroup, groupName)
        );

        friendGroupService.remove(
                new LambdaQueryWrapper<FriendGroup>()
                        .eq(FriendGroup::getUid, uid)
                        .eq(FriendGroup::getGroupname, groupName)
        );

        return update ? "移除成功" : "移除失败";
    }

    @Override
    public String reFriendGroupName(String oldFriendGroupName, String newFriendGroupName) {

        boolean updateGroupTable = friendGroupService.update(
                FriendGroup.builder()
                        .groupname(newFriendGroupName)
                        .build(),
                new LambdaQueryWrapper<FriendGroup>()
                        .eq(FriendGroup::getGroupname, oldFriendGroupName)
                        .eq(FriendGroup::getUid, LocalUser.getLocalUser().getUid())
        );
        if(!updateGroupTable)
            return "修改失败";

        boolean updateRelationTable = friendRelation.update(
                FriendRelation.builder()
                        .friendGroup(newFriendGroupName)
                        .build(),
                new LambdaQueryWrapper<FriendRelation>()
                        .eq(FriendRelation::getFriendGroup, oldFriendGroupName)
                        .eq(FriendRelation::getUserIdMaster, LocalUser.getLocalUser().getUid())
        );
        return updateRelationTable ? "修改成功" : "修改失败";
    }

    @Override
    public String addFriendGroup(Integer uid, String friendGroup) {

        boolean save = friendGroupService.save(FriendGroup.builder()
                .groupname(friendGroup)
                .uid(uid)
                .build());
        return save ? "添加成功" : "添加失败";
    }

    @Override
    public List<String> getFriendGroupList(String uid) {
        List<String> collect = friendGroupService.list(new LambdaQueryWrapper<FriendGroup>().eq(FriendGroup::getUid, uid))
                .stream().map(FriendGroup::getGroupname).collect(Collectors.toList());
        collect.add(0, "我的好友");
        log.info("collect:{}", collect);
        return collect;
    }

    @Override
    public UserInfoVo getFriendInfo(String uid) {

        ChatUser byId = this.getById(uid);

        FriendRelation one = friendRelation.getOne(
                new LambdaQueryWrapper<FriendRelation>()
                        .eq(FriendRelation::getUserIdMaster, LocalUser.getLocalUser().getUid())
                        .eq(FriendRelation::getUserIdFriend, uid)
        );
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(byId, userInfoVo);
        userInfoVo.setFriendGroup(one.getFriendGroup().equals(ConfigKeyEnum.friend_default_group.getKey())? "我的好友" : one.getFriendGroup());
        return userInfoVo;
    }

    @Override
    public String changeFriendGroup(String userIdFriend, String friendGroup) {
            if (friendGroup.equals("我的好友"))
                friendGroup = ConfigKeyEnum.friend_default_group.getKey();
            boolean update = friendRelation.update(
                    FriendRelation.builder()
                            .friendGroup(friendGroup)
                            .build(),
                    new LambdaQueryWrapper<FriendRelation>()
                            .eq(FriendRelation::getUserIdMaster, LocalUser.getLocalUser().getUid())
                            .eq(FriendRelation::getUserIdFriend, userIdFriend)
            );

            return update ? "修改成功" : "修改失败";
    }

    @Override
    public String removeFriend(String userIdFriend) {
        boolean remove = friendRelation.remove(
                new LambdaQueryWrapper<FriendRelation>()
                        .eq(FriendRelation::getUserIdMaster, LocalUser.getLocalUser().getUid())
                        .eq(FriendRelation::getUserIdFriend, userIdFriend)
                        .or()
                        .eq(FriendRelation::getUserIdMaster, userIdFriend)
                        .eq(FriendRelation::getUserIdFriend, LocalUser.getLocalUser().getUid())
        );

        return remove ? "删除成功" : "删除失败";
    }
}




