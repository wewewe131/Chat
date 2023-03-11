package com.example.firstdemo.Controller;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userAuth")
@Slf4j
@Api(tags = "用户:用户地址")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserAuthController {

    private final AuthenticationManager authenticationManager;

    private final RedisTemplate<String,Object> redisTemplate;

    private final ChatUserService chatUserService;

    private final UpasswordService upasswordService;

    @PostMapping
    @ApiModelProperty(value = "登录", notes = "登录")
    @ApiImplicitParam(name = "loginVo", value = "登录信息", required = true, dataType = "LoginVo")
    public ApiResponse<String> booleanApiResponse(@RequestBody LoginVo loginVo) {
        String username = loginVo.getUsername();
        String password = loginVo.getPassword();

        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        if (authenticate == null) {
            return ApiResponse.fail("登录失败", ResEnums.UNAUTHORIZED);
        }
        //获取用户
        UserDetail userDetail = (UserDetail) authenticate.getPrincipal();
        String jwt = JWTProcess.createJWT(userDetail.getUid(), userDetail.getUsername());

        redisTemplate.opsForSet().add("loginUserList", userDetail.getUid());

        return ApiResponse.ok(jwt);
    }

    //注册
    @PostMapping("/register")
    @ApiModelProperty(value = "注册", notes = "注册")
    @ApiImplicitParam(name = "loginVo", value = "注册信息", required = true, dataType = "LoginVo")
    public ApiResponse<ChatUser> register(@RequestBody RegisterVo registerVo) {
        // 注册
        ChatUser register = chatUserService.register(registerVo);

        return ApiResponse.ok(register);
    }

    @RequestMapping(value = "/loginFail")
    public ApiResponse<String> loginFailPost() {
        return ApiResponse.ok("loginFail");
    }

}
