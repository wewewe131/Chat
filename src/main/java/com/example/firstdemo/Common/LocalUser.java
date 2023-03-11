package com.example.firstdemo.Common;

import com.example.firstdemo.Entity.ChatUser;

public class LocalUser {

    //新建一个ThreadLocal对象
    private static final ThreadLocal<ChatUser> localUser = new ThreadLocal<>();

    //获取当前线程的用户
    public static ChatUser getLocalUser() {
        return localUser.get();
    }

    //设置当前线程的用户
    public static void setLocalUser(ChatUser user) {
        localUser.set(user);
    }

}
