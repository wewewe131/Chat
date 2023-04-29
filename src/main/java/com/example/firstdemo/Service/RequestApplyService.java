package com.example.firstdemo.Service;

import com.example.firstdemo.Entity.RequestApply;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 22832
* @description 针对表【request_apply】的数据库操作Service
* @createDate 2023-04-11 02:21:21
*/
public interface RequestApplyService extends IService<RequestApply> {

    List<RequestApply> getUserApply(String uid);

    boolean agree(String applyId);

    boolean refuse(String applyId);
}
