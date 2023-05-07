package com.example.Chat.Entity.Dto;

import com.example.Chat.Entity.ChatUser;
import com.example.Chat.Entity.Group;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class GroupDto extends Group {
    private List<ChatUser> chatUsers;
}
