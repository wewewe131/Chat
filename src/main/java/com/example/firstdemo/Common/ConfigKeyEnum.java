package com.example.firstdemo.Common;

import lombok.Getter;


@Getter
public enum ConfigKeyEnum {
    default_avatar("defaultAvatar"),
    group_host("1"),
    group_admin("2"),
    group_member("3"),
    request_apply_group("group"),

    request_apply_friend("friend"),

    friend_default_group("defaultGroup"),
    ;
    private String key;

    ConfigKeyEnum(String key) {
        this.key = key;
    }

    public static ConfigKeyEnum getEnumByKey(String key){
        for(ConfigKeyEnum configKeyEnum : ConfigKeyEnum.values()){
            if(configKeyEnum.getKey().equals(key)){
                return configKeyEnum;
            }
        }
        return null;
    }
}
