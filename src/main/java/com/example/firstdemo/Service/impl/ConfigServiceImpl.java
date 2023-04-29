package com.example.firstdemo.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.firstdemo.Entity.Config;
import com.example.firstdemo.Service.ConfigService;
import com.example.firstdemo.Mapper.ConfigMapper;
import org.springframework.stereotype.Service;

/**
* @author 22832
* @description 针对表【config】的数据库操作Service实现
* @createDate 2023-04-03 12:32:27
*/
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config>
    implements ConfigService {

    @Override
    public String getConfigValue(String key) {
        Config configKey = this.getOne(new QueryWrapper<Config>().eq("configKey", key));
        return configKey.getConfigvalue();
    }
}




