package com.example.Chat.Common;

import com.example.Chat.Entity.ChatUser;

public class LocalUser {

    //新建一个ThreadLocal对象
    private static final ThreadLocal<ChatUser> localUser = new ThreadLocal<>();

    private static final ThreadLocal<String> localUserToken = new ThreadLocal<>();

    //获取当前线程的用户
    public static ChatUser getLocalUser() {
        return localUser.get();
    }

    //设置当前线程的用户
    public static void setLocalUser(ChatUser user) {
        localUser.set(user);
    }


    public static String getLocalUserToken() {
        return localUserToken.get();
    }

    public static void setLocalUserToken(String token) {
        localUserToken.set(token);
    }



}
