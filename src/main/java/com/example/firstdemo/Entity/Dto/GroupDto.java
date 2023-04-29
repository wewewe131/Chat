package com.example.firstdemo.Entity.Dto;

import com.example.firstdemo.Entity.ChatUser;
import com.example.firstdemo.Entity.Group;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class GroupDto extends Group {
    private List<ChatUser> chatUsers;
}
