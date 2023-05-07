package com.example.Chat.Controller;

import com.example.Chat.Common.ApiResponse;
import com.example.Chat.Common.LocalUser;
import com.example.Chat.Entity.ChatUser;
import com.example.Chat.Entity.Group;
import com.example.Chat.Service.ChatUserService;
import com.example.Chat.Service.GroupService;
import com.example.Chat.utils.GetFileSuffix;
import com.luciad.imageio.webp.WebPWriteParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.UUID;

@Controller
@RequestMapping
public class UpdateController {
    @Autowired
    private ChatUserService chatUserService;
    @Autowired
    private GroupService groupService;
    @Value("${filepath}")
    private String filePath;
    @ResponseBody
    @RequestMapping(value = "/reAvatar",method = RequestMethod.POST)
    public ApiResponse<String> reAvatar(@RequestBody HashMap<String,Object> map) throws IOException {
        // 读取文件
        Base64.Decoder decoder = Base64.getDecoder();
        String fileBase64 = (String) map.get("file");

        String[] split = fileBase64.split(",");
        fileBase64 = split[1];
        String type = split[0].split(";")[0].split(":")[1];

        String suffix = Arrays.stream(GetFileSuffix.values()).filter(item -> item.getMime().equals(type)).toList().get(0).getSuffix();
        byte[] files = decoder.decode(fileBase64);
        String randomName = UUID.randomUUID().toString();
        String fileName = UUID.randomUUID().toString() + suffix;
        // 生成文件名
        FileOutputStream outputStream = new FileOutputStream(filePath+"avatar/"+fileName);
        outputStream.write(files);
        outputStream.close();

        File file = new File(filePath + "avatar/" + fileName);

        BufferedImage image = ImageIO.read(file);

        ImageWriter writer = ImageIO.getImageWritersByMIMEType("image/webp").next();

        WebPWriteParam webPWriteParam = new WebPWriteParam(writer.getLocale());

        webPWriteParam.setCompressionMode(WebPWriteParam.MODE_EXPLICIT);
        String webpName = randomName + ".webp";
        writer.setOutput(new FileImageOutputStream(new File(filePath+"avatar/"+randomName + ".webp")));

        writer.write(null,new IIOImage(image,null,null),webPWriteParam);


        ChatUser localUser = LocalUser.getLocalUser();

        localUser.setUavatar(webpName);

        chatUserService.updateById(localUser);

        return ApiResponse.ok(fileName);
    }
    @ResponseBody
    @RequestMapping(value = "/updateGroupAvatar",method = RequestMethod.POST)
    public ApiResponse<String> updateGroupAvatar(@RequestBody HashMap<String,Object> map) throws IOException {
        // 读取文件
        Base64.Decoder decoder = Base64.getDecoder();
        String fileBase64 = (String) map.get("file");

        String[] split = fileBase64.split(",");
        fileBase64 = split[1];
        String type = split[0].split(";")[0].split(":")[1];

        System.out.println(type);

        String suffix = Arrays.stream(GetFileSuffix.values()).filter(item -> item.getMime().equals(type)).toList().get(0).getSuffix();
        byte[] files = decoder.decode(fileBase64);
        String randomName = UUID.randomUUID().toString();
        String fileName = randomName + suffix;
        // 生成文件名
        FileOutputStream outputStream = new FileOutputStream(filePath+"avatar/"+fileName);
        outputStream.write(files);
        outputStream.close();
        File file = new File(filePath + "avatar/" + fileName);

        BufferedImage image = ImageIO.read(file);

        ImageWriter writer = ImageIO.getImageWritersByMIMEType("image/webp").next();

        WebPWriteParam webPWriteParam = new WebPWriteParam(writer.getLocale());

        webPWriteParam.setCompressionMode(WebPWriteParam.MODE_EXPLICIT);
        String webpName = randomName + ".webp";
        writer.setOutput(new FileImageOutputStream(new File(filePath+"avatar/"+randomName + ".webp")));

        writer.write(null,new IIOImage(image,null,null),webPWriteParam);


        Group groupId = groupService.getById((String) map.get("groupId"));

        groupId.setGroupAvatar(webpName);

        groupService.updateById(groupId);

        return ApiResponse.ok(fileName);
    }
}
