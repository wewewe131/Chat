package com.example.firstdemo.Security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.kotlin.KtQueryChainWrapper;
import com.example.firstdemo.Entity.ChatUser;
import com.example.firstdemo.Service.ChatUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
@Component
public class MyUserDetailService implements UserDetailsService {
    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Autowired
    ChatUserService chatUserService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ChatUser uname = chatUserService.getOne(
                new QueryWrapper<ChatUser>().eq("uname", username)
        );

        if (uname.getUid().isBlank())
            throw new UsernameNotFoundException("用户不存在");

        UserDetail userDetail = new UserDetail();
        BeanUtils.copyProperties(uname,userDetail);
        return userDetail;
    }
}
