package com.example.Chat.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.Chat.Common.ConfigKeyEnum;
import com.example.Chat.Entity.FriendRelation;
import com.example.Chat.Entity.RequestApply;
import com.example.Chat.Entity.UserAuthGroupRelation;
import com.example.Chat.Service.FriendRelationService;
import com.example.Chat.Service.RequestApplyService;
import com.example.Chat.Mapper.RequestApplyMapper;
import com.example.Chat.Service.UserAuthGroupRelationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author 22832
 * @description 针对表【request_apply】的数据库操作Service实现
 * @createDate 2023-04-11 02:21:21
 */
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RequestApplyServiceImpl extends ServiceImpl<RequestApplyMapper, RequestApply>
        implements RequestApplyService {

    private final UserAuthGroupRelationService userAuthGroupRelationService;
    @Lazy
    private final FriendRelationService friendRelationService;

    @Override
    public List<RequestApply> getUserApply(String id) {
        // TODO 获取单个用户的申请列表
        // TODO 查找当前用户是群主或管理的群聊
        // TODO 在申请列表中查找receiveId等于群ID并且type为群聊的申请或者receiveId等于用户ID并且type为好友的申请
        List<UserAuthGroupRelation> userAuthGroupRelationList = userAuthGroupRelationService.list(
                new LambdaQueryWrapper<UserAuthGroupRelation>()
                        .eq(UserAuthGroupRelation::getUid, id)
                        .eq(UserAuthGroupRelation::getAuthId, ConfigKeyEnum.group_host.getKey())
                        .or()
                        .eq(UserAuthGroupRelation::getAuthId, ConfigKeyEnum.group_admin.getKey())
        );
        List<String> groupIdList = userAuthGroupRelationList.stream().map(UserAuthGroupRelation::getGroupid).toList();

        LambdaQueryWrapper<RequestApply> requestApplyLambdaQueryWrapper = new LambdaQueryWrapper<>();

        requestApplyLambdaQueryWrapper
                .eq(RequestApply::getIsAccept, 0)
                .eq(RequestApply::getType, ConfigKeyEnum.request_apply_group.getKey())
                .in(RequestApply::getReceiveId, groupIdList)
                .or()
                .eq(RequestApply::getIsAccept, 0)
                .eq(RequestApply::getType, ConfigKeyEnum.request_apply_friend.getKey())
                .eq(RequestApply::getReceiveId, id);

        List<RequestApply> list = this.list(requestApplyLambdaQueryWrapper);
        return list;
    }

    @Override
    public boolean agree(String applyId) {
        final String group = ConfigKeyEnum.request_apply_group.getKey();
        RequestApply byId = this.getById(applyId);
        if (byId == null) return false;
        return byId.getType().equals(group) ? this.addUserToGroup(applyId) : this.addFriend(applyId);
    }

    private boolean addFriend(String applyId) {
        RequestApply byId = this.getById(applyId);
        byId.setIsAccept(1);
        this.updateById(byId);

        boolean save = friendRelationService.saveBatch(
                Arrays.asList(
                        FriendRelation.builder()
                                .friendGroup(ConfigKeyEnum.friend_default_group.getKey())
                                .userIdFriend(byId.getReceiveId())
                                .userIdMaster(byId.getUserIdRequest())
                                .build(),
                        FriendRelation.builder()
                                .friendGroup(ConfigKeyEnum.friend_default_group.getKey())
                                .userIdFriend(byId.getUserIdRequest())
                                .userIdMaster(byId.getReceiveId())
                                .build()
                )
        );
        return save;
    }

    private boolean addUserToGroup(String applyId) {
        RequestApply byId = this.getById(applyId);
        byId.setIsAccept(1);
        this.updateById(byId);
        boolean save = userAuthGroupRelationService.save(
                UserAuthGroupRelation.builder()
                        .authId(ConfigKeyEnum.group_member.getKey())
                        .groupid(byId.getReceiveId())
                        .uid(byId.getUserIdRequest())
                        .build()
        );
        return save;

    }

    @Override
    public boolean refuse(String applyId) {
        RequestApply byId = this.getById(applyId);
        byId.setIsAccept(-1);
        boolean b = this.updateById(byId);
        return b;
    }
}




