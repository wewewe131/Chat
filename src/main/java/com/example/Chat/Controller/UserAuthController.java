package com.example.Chat.Controller;

import com.example.Chat.Common.ApiResponse;
import com.example.Chat.Common.LocalUser;
import com.example.Chat.Common.ResEnums;
import com.example.Chat.Entity.ChatUser;
import com.example.Chat.Entity.Vo.LoginVo;
import com.example.Chat.Entity.Vo.RegisterVo;
import com.example.Chat.Security.UserDetail;
import com.example.Chat.Service.ChatUserService;
import com.example.Chat.utils.JWTProcess;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/userAuth")
@Slf4j
@Tag(name = "用户登录注册", description = "用户登录注册")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserAuthController {

    private final AuthenticationManager authenticationManager;

    private final RedisTemplate<String, Object> redisTemplate;

    private final ChatUserService chatUserService;

    @PostMapping
    @Operation(summary = "登录", description = "登录")
    public ApiResponse<String> login(@RequestBody LoginVo loginVo, HttpServletRequest httpServletRequest) {
        log.info("http线程id:{}", Thread.currentThread().getId());
        log.info("sessionId:{}", httpServletRequest.getSession().getId());
        String userId = loginVo.getUserId();
        String password = loginVo.getPassword();
        Authentication authenticate;
        try {
            authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userId, password));
        } catch (Exception e) {
            return ApiResponse.fail("登录失败", ResEnums.UNAUTHORIZED);
        }
        //获取用户
        UserDetail userDetail = (UserDetail) authenticate.getPrincipal();
        userDetail.setIsOnline(1);
        chatUserService.updateById(userDetail);
        String jwt = JWTProcess.createJWT(userDetail.getUid(), userDetail.getUsername());
        redisTemplate.opsForValue().set("onlineUser"+userDetail.getUid(), userDetail.getUid(),30, TimeUnit.MINUTES);
        return ApiResponse.ok(jwt);
    }

    //注册
    @PostMapping("/register")
    @Operation(summary = "注册", description = "注册", tags = {"用户登录注册"}, operationId = "register")
    @Parameters(
            @Parameter(name = "registerVo", description = "注册信息", required = true, schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = RegisterVo.class))
    )
    public ApiResponse<ChatUser> register(@RequestBody RegisterVo registerVo) {
        // 注册
        ChatUser register = chatUserService.register(registerVo);
        return ApiResponse.ok(register);
    }

    @PostMapping("/logout")
    @Operation(summary = "登出", description = "登出")
    public ApiResponse<String> logout() {
        ChatUser localUser = LocalUser.getLocalUser();
        String uid = localUser.getUid();
        ChatUser chatUser = chatUserService.getById(uid);
        chatUser.setIsOnline(0);
        chatUserService.updateById(chatUser);
        redisTemplate.opsForSet().remove("onlineUserMuster", uid);
        return ApiResponse.ok("登出成功");
    }

    @Operation(summary = "授权失败", description = "授权失败")
    @RequestMapping(value = "/loginFail")
    public ApiResponse<String> loginFailPost() {
        return ApiResponse.fail("loginFail");
    }

}
