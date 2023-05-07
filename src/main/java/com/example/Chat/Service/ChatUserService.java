package com.example.Chat.Service;

import com.example.Chat.Entity.ChatUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.Chat.Entity.Vo.FriendListItemVo;
import com.example.Chat.Entity.Vo.RegisterVo;
import com.example.Chat.Entity.Vo.UserInfoVo;

import java.util.List;

/**
* @author 22832
* @description 针对表【chat_user】的数据库操作Service
* @createDate 2023-03-12 02:17:46
*/
public interface ChatUserService extends IService<ChatUser> {

    ChatUser register(RegisterVo chatUser);

    String sendFriendApply(String uid, String mark);

    List<FriendListItemVo> getFriendList(String uid);

    String removeFriendGroup(String uid, String groupName);

    String reFriendGroupName(String userIdFriend, String friendGroup);

    String addFriendGroup(Integer uid, String friendGroup);

    List<String> getFriendGroupList(String uid);

    UserInfoVo getFriendInfo(String uid);

    String changeFriendGroup(String userIdFriend, String friendGroup);

    String removeFriend(String userIdFriend);
}
