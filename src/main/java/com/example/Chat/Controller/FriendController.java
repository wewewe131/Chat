package com.example.Chat.Controller;

import com.example.Chat.Common.ApiResponse;
import com.example.Chat.Common.LocalUser;
import com.example.Chat.Entity.ChatUser;
import com.example.Chat.Entity.FriendRelation;
import com.example.Chat.Entity.Vo.ApplyVo;
import com.example.Chat.Entity.Vo.FriendListItemVo;
import com.example.Chat.Entity.Vo.ReFriendGroupNameVo;
import com.example.Chat.Entity.Vo.UserInfoVo;
import com.example.Chat.Service.ChatUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friend")
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class FriendController {
    private final ChatUserService chatUserService;

    @PostMapping("/send")
    public ApiResponse<String> sendFriendApply(@RequestBody ApplyVo applyVo) {
        String s = chatUserService.sendFriendApply(applyVo.getApplyId(), applyVo.getMark());
        return "发送成功".equals(s) ? ApiResponse.ok(s) : ApiResponse.fail(s);
    }

    @GetMapping("/list")
    public ApiResponse<List> listFriend() {
        ChatUser localUser = LocalUser.getLocalUser();
        String uid = localUser.getUid();
        List<FriendListItemVo> friendList = chatUserService.getFriendList(uid);
        return ApiResponse.ok(friendList);
    }

    @DeleteMapping("/remove")
    public ApiResponse<String> removeFriend(@RequestBody FriendRelation friendRelation) {
        String s = chatUserService.removeFriend(friendRelation.getUserIdFriend());
        return "删除成功".equals(s) ? ApiResponse.ok(s) : ApiResponse.fail(s);
    }

    @GetMapping("/info")
    public ApiResponse<UserInfoVo> getFriendInfo(@RequestParam String uid) {
        UserInfoVo friendInfo = chatUserService.getFriendInfo(uid);
        return ApiResponse.ok(friendInfo);
    }

    @DeleteMapping("/group/removeGroup")
    public ApiResponse<String> removeGroup(@RequestBody FriendRelation groupName) {
        ChatUser localUser = LocalUser.getLocalUser();
        String uid = localUser.getUid();
        String s = chatUserService.removeFriendGroup(uid, groupName.getFriendGroup());
        return "删除成功".equals(s) ? ApiResponse.ok(s) : ApiResponse.fail(s);
    }

    @PutMapping("/group/reGroupName")
    public ApiResponse<String> reFriendGroupName(@RequestBody ReFriendGroupNameVo reFriendGroupNameVo) {
        String s = chatUserService.reFriendGroupName(reFriendGroupNameVo.getOldFriendGroupName(),reFriendGroupNameVo.getNewFriendGroupName());
        return "修改成功".equals(s) ? ApiResponse.ok(s) : ApiResponse.fail(s);
    }

    @PostMapping("/group/add")
    public ApiResponse<String> addFriendGroup(@RequestBody FriendRelation groupName) {
        ChatUser localUser = LocalUser.getLocalUser();
        String uid = localUser.getUid();
        String s = chatUserService.addFriendGroup(Integer.valueOf(uid), groupName.getFriendGroup());
        return "添加成功".equals(s) ? ApiResponse.ok(s) : ApiResponse.fail(s);
    }

    @GetMapping("/group/list")
    public ApiResponse<List> listFriendGroup() {
        ChatUser localUser = LocalUser.getLocalUser();
        String uid = localUser.getUid();
        List<String> friendGroupList = chatUserService.getFriendGroupList(uid);
        return ApiResponse.ok(friendGroupList);
    }

    @PutMapping("/group/changeGroup")
    public ApiResponse<String> changeFriendGroup(@RequestBody FriendRelation friendRelation) {
        String s = chatUserService.changeFriendGroup(friendRelation.getUserIdFriend(), friendRelation.getFriendGroup());
        return "修改成功".equals(s) ? ApiResponse.ok(s) : ApiResponse.fail(s);
    }
}
