package com.example.firstdemo.Service;

import com.example.firstdemo.Entity.Config;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 22832
* @description 针对表【config】的数据库操作Service
* @createDate 2023-04-03 12:32:27
*/
public interface ConfigService extends IService<Config> {
    String getConfigValue(String key);
}
