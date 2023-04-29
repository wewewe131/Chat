package com.example.firstdemo.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.firstdemo.Common.ApiResponse;
import com.example.firstdemo.Common.LocalUser;
import com.example.firstdemo.Entity.ChatUser;
import com.example.firstdemo.Entity.Group;
import com.example.firstdemo.Entity.UserAuthGroupRelation;
import com.example.firstdemo.Entity.Vo.GroupInfoVo;
import com.example.firstdemo.Entity.Vo.JoinGroupVo;
import com.example.firstdemo.Entity.Vo.UserGroupGrouping;
import com.example.firstdemo.Service.GroupService;
import com.example.firstdemo.Service.UserAuthGroupRelationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/group")
@Tag(name = "群聊相关接口", description = "群聊相关接口")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GroupController {

    private final GroupService groupService;

    private final UserAuthGroupRelationService userAuthGroupRelationService;

    @PostMapping
    @Operation(summary = "创建群聊")
    public ApiResponse<Group> createGroup(@RequestBody Group group) {

        groupService.createGroup(group);

        return ApiResponse.ok(group);
    }

    @PostMapping("/updateGroupInfo")
    @Operation(summary = "修改群聊信息")
    public ApiResponse<String> updateGroupInfo(@RequestBody Group group) {
        groupService.updateById(group);
        return ApiResponse.ok("修改成功");
    }



    @GetMapping
    @Operation(summary = "获取群聊信息")
    public ApiResponse<Group> getGroup(@RequestParam("groupId") String groupId) {
        Group group = groupService.getById(groupId);
        return ApiResponse.ok(group);
    }

    @GetMapping("/getAllGroupInfo")
    @Operation(summary = "获取用户群聊列表")
    public ApiResponse<List<UserGroupGrouping>> getAllGroupInfo() {
        return ApiResponse.ok(groupService.getAllGroupInfo());
    }

    @PostMapping("/joinGroup")
    @Operation(summary = "发送加群申请")
    public ApiResponse<String> joinGroup(@RequestBody JoinGroupVo vo) {
        String msg = groupService.joinGroup(vo.getGroupId(), vo.getMark());
        return "发送成功,请等待管理员通过".equals(msg) ? ApiResponse.ok(msg) : ApiResponse.fail(msg);
    }

    @PostMapping("/exitGroup")
    @Operation(summary = "退出群")
    public ApiResponse<String> exitGroup(@RequestBody JoinGroupVo vo) {
        String uid = LocalUser.getLocalUser().getUid();
        String msg = groupService.exitGroup(vo.getGroupId(), uid);
        return ApiResponse.ok(msg);
    }

    @PostMapping("/deleteGroup")
    @Operation(summary = "删除群")
    public ApiResponse<String> deleteGroup(@RequestBody JoinGroupVo vo) {
        String uid = LocalUser.getLocalUser().getUid();
        String msg = groupService.deleteGroup(vo.getGroupId());
        return ApiResponse.ok(msg);
    }

    @PostMapping("/updateGroupUserRelation")
    @Operation(summary = "修改群成员信息")
    public ApiResponse<String> updateGroupUserInfo(@RequestBody UserAuthGroupRelation vo) {

        boolean update = userAuthGroupRelationService.update(
                vo,
                new LambdaQueryWrapper<UserAuthGroupRelation>()
                        .eq(UserAuthGroupRelation::getGroupid, vo.getGroupid())
                        .eq(UserAuthGroupRelation::getUid, vo.getUid())
        );

        return update ? ApiResponse.ok("修改成功") : ApiResponse.fail("修改失败");
    }

    @GetMapping("/getGroupInfo/{groupId}")
    @Operation(summary = "获取群信息")
    @Parameter(name = "group", description = "群信息",schema = @Schema(implementation = Group.class))
    public ApiResponse<GroupInfoVo> getGroupInfo(@PathVariable String groupId) {
        GroupInfoVo groupInfoVo = groupService.getGroupInfo(groupId);
        return ApiResponse.ok(groupInfoVo);
    }

    @PostMapping("/removeGroupUser")
    @Operation(summary = "移除群成员")
    public ApiResponse<String> removeGroupUser(@RequestBody UserAuthGroupRelation userAuthGroupRelation) {
        String uid = LocalUser.getLocalUser().getUid();
        String msg = groupService.removeGroupUser(userAuthGroupRelation.getGroupid(), userAuthGroupRelation.getUid());
        return ApiResponse.ok(msg);
    }
}