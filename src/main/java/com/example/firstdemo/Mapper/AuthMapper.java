package com.example.firstdemo.Mapper;

import com.example.firstdemo.Entity.Auth;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
* @author 22832
* @description 针对表【auth】的数据库操作Mapper
* @createDate 2023-03-05 04:44:08
* @Entity com.example.firstdemo.Entity.Auth
*/
public interface AuthMapper extends BaseMapper<Auth> {
    @Select("select * from auth where auth_id = #{authId}")
    Auth selectByAuthId(String authId);
}




