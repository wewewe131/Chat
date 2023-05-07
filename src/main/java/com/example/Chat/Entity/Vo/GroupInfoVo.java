package com.example.Chat.Entity.Vo;

import com.example.Chat.Entity.Group;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupInfoVo implements Serializable {
    private Group group;
    private List<GroupUserInfoVo> groupUserInfoVos;
}
