package com.example.Chat.Scheduled;

import com.example.Chat.Service.ChatUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class onlineState {
    private final ChatUserService chatUserService;

    private RedisTemplate<String,Object> redisTemplate;

    @Scheduled(cron = "30 * * * * *")
    public void onlineState() {
        System.out.println("定时任务");
        chatUserService.list().forEach(chatUser -> {
            Boolean onlineUserMuster = redisTemplate.opsForValue().get("onlineUser" + chatUser.getUid()) != null;
            if (onlineUserMuster) {
                chatUser.setIsOnline(1);
                chatUserService.updateById(chatUser);
            }
            else {
                chatUser.setIsOnline(0);
                chatUserService.updateById(chatUser);
            }
        });
    }
}
