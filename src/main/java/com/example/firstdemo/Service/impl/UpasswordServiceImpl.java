package com.example.firstdemo.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.firstdemo.Entity.Upassword;
import com.example.firstdemo.Service.UpasswordService;
import com.example.firstdemo.Mapper.UpasswordMapper;
import org.springframework.stereotype.Service;

/**
* @author 22832
* @description 针对表【upassword】的数据库操作Service实现
* @createDate 2023-03-10 00:00:49
*/
@Service
public class UpasswordServiceImpl extends ServiceImpl<UpasswordMapper, Upassword>
    implements UpasswordService{

}




