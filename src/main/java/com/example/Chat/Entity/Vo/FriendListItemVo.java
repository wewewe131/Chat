package com.example.Chat.Entity.Vo;

import com.example.Chat.Entity.ChatUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FriendListItemVo {
    private String groupName;
    private List<ChatUser> friendList;
}
