package com.example.Chat.Entity.Vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReFriendGroupNameVo implements Serializable {

    private String oldFriendGroupName;
    private String newFriendGroupName;

}
