package com.example.firstdemo.Entity.Vo;

import com.example.firstdemo.Entity.ChatUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GroupUserInfoVo implements Serializable {

    private ChatUser chatUser;
    @Schema(name = "Auth",description = "权限号",allowableValues={"1","2","3"})
    private String Auth;

}
