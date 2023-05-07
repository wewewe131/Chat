package com.example.Chat.Config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class MybatisMetaConfiguration implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("insert填充");
        if (metaObject.hasGetter("createTime"))
            metaObject.setValue("createTime", LocalDateTime.now());
        if (metaObject.hasGetter("updateTime"))
            metaObject.setValue("updateTime", LocalDateTime.now());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (metaObject.hasGetter("updateTime"))
            metaObject.setValue("updateTime", LocalDateTime.now());
    }
}
