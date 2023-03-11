package com.example.firstdemo.Entity.Vo;

import com.example.firstdemo.Entity.ChatUser;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterVo extends ChatUser {
    @NotBlank(message = "密码不能为空")
    private String password;
    //父类不能为空
    @NotBlank(message = "用户名不能为空")
    private String uname;
    @NotBlank(message = "邮箱不能为空")
    private String uemail;
    @NotBlank(message = "性别不能为空")
    private String usex;

}
