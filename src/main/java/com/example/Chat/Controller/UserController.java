package com.example.Chat.Controller;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.Chat.Common.ApiResponse;
import com.example.Chat.Common.LocalUser;
import com.example.Chat.Entity.ChatUser;
import com.example.Chat.Entity.Upassword;
import com.example.Chat.Entity.Vo.RePasswordVo;
import com.example.Chat.Service.ChatUserService;
import com.example.Chat.Service.UpasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public ApiResponse<String> updateUser(@RequestBody ChatUser chatUser) {
        ChatUser localUser = LocalUser.getLocalUser();
        String uid = localUser.getUid();
        chatUser.setUid(uid);
        boolean b = chatUserService.updateById(chatUser);
        return b ? ApiResponse.ok("修改成功"): ApiResponse.fail("修改失败");
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
