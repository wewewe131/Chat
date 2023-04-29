package com.example.firstdemo.Controller;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.firstdemo.AOP.PrintLog;
import com.example.firstdemo.Common.ApiResponse;
import com.example.firstdemo.Common.ResEnums;
import com.example.firstdemo.Entity.ChatUser;
import com.example.firstdemo.Entity.Upassword;
import com.example.firstdemo.Entity.Vo.LoginVo;
import com.example.firstdemo.Entity.Vo.RegisterVo;
import com.example.firstdemo.Security.UserDetail;
import com.example.firstdemo.Service.ChatUserService;
import com.example.firstdemo.Service.UpasswordService;
import com.example.firstdemo.utils.JWTProcess;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;

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
        log.info("http线程id:{}",Thread.currentThread().getId());
        log.info("sessionId:{}",httpServletRequest.getSession().getId());

        String userId = loginVo.getUserId();
        String password = loginVo.getPassword();
        Authentication authenticate;
        try {
            authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userId, password));
        }catch (Exception e){
            return ApiResponse.fail("登录失败", ResEnums.UNAUTHORIZED);
        }
        //获取用户
        UserDetail userDetail = (UserDetail) authenticate.getPrincipal();
        String jwt = JWTProcess.createJWT(userDetail.getUid(), userDetail.getUsername());
        redisTemplate.opsForSet().add("onlineUserMuster", userDetail.getUid());
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

    @Operation(summary = "授权失败", description = "授权失败")
    @RequestMapping(value = "/loginFail")
    public ApiResponse<String> loginFailPost() {
        return ApiResponse.fail("loginFail");
    }

}
