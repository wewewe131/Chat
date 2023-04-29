package com.example.firstdemo.Controller;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.firstdemo.Common.ApiResponse;
import com.example.firstdemo.Common.LocalUser;
import com.example.firstdemo.Entity.ChatUser;
import com.example.firstdemo.Entity.Upassword;
import com.example.firstdemo.Entity.Vo.RePasswordVo;
import com.example.firstdemo.Service.ChatUserService;
import com.example.firstdemo.Service.UpasswordService;
import com.example.firstdemo.utils.UploadHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private ChatUserService chatUserService;
    @Autowired
    private UpasswordService upasswordService;

    @GetMapping
    public ApiResponse<ChatUser> getUser() {
        ChatUser localUser = LocalUser.getLocalUser();
        return ApiResponse.ok(localUser);
    }

    @PutMapping
    public ApiResponse<ChatUser> updateUser(@RequestBody ChatUser chatUser) {
        ChatUser localUser = LocalUser.getLocalUser();
        String uid = localUser.getUid();
        chatUser.setUid(uid);
        chatUserService.updateById(chatUser);
        return ApiResponse.ok(chatUser);
    }

    @PutMapping("/rePassword")
    public ApiResponse<String> rePassword(@RequestBody RePasswordVo rePasswordVo) {
        ChatUser localUser = LocalUser.getLocalUser();
        String uid = localUser.getUid();
        Upassword oldPassword = upasswordService.getOne(new LambdaQueryWrapper<Upassword>()
                .eq(Upassword::getUid, uid)
                .eq(Upassword::getUpassword, DigestUtil.md5Hex(rePasswordVo.getOldPassword()))
        );
        if(oldPassword == null) {
            return ApiResponse.fail("原密码错误");
        }
        String password = DigestUtil.md5Hex(rePasswordVo.getNewPassword());
        oldPassword.setUpassword(password);
        upasswordService.updateById(oldPassword);
        return ApiResponse.ok("修改成功");
    }

    @GetMapping("/info")
    public ApiResponse<ChatUser> chatUserApiResponse(@RequestParam("uid") String uid) {
        ChatUser chatUser = chatUserService.getById(uid);
        return ApiResponse.ok(chatUser);
    }

}
