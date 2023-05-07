package com.example.Chat.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class UploadHandler {

    public static String upload(MultipartFile file, String uploadPath) {
        //收到请求后会生成一个临时文件,在请求中需要将文件转存，否则在请求结束后临时文件会删除

        //获取原文件名
        String fileOriginName = file.getOriginalFilename();

        //查找最后一个"."出现的位置,substring截取从最后出现的位置到末尾的字符串，此处是为了取出文件后缀
        String suffix = fileOriginName.substring(fileOriginName.lastIndexOf("."));

        //随机生成UUID将文件重命名,避免出现文件同名的情况
        String newFileName = UUID.randomUUID().toString() + suffix;

        //根据路径新建一个对象
        File dir = new File(uploadPath);

        //判断路径是否存在
        if (!dir.exists())
            dir.mkdir();//如果不存在就新建一个目录

        try {
            //将文件转存到指定的目录下
            file.transferTo(new File(uploadPath + newFileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return newFileName;
    }
}
