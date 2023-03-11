package com.example.firstdemo.Controller;

import com.example.firstdemo.Common.LocalUser;
import com.example.firstdemo.Entity.ChatUser;
import com.example.firstdemo.Entity.Room;
import com.example.firstdemo.Service.ChatUserService;
import com.example.firstdemo.Service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class V1 {

    private final ChatUserService chatUserService;

    private final RoomService roomService;
    @RequestMapping("/user")
    class User {

        @Cacheable("NowUser")
        @GetMapping
        public ChatUser getUser() {
            ChatUser localUser = LocalUser.getLocalUser();
            return localUser;
        }

    }

    @RequestMapping("/room")
    class RoomController {

        @Cacheable("NowRoom")
        @GetMapping
        public Room getRoom(String roomId) {
            Room room = roomService.getById(roomId);
            return room;
        }

        @PutMapping
        public Room updateRoom(Room room) {
            roomService.updateById(room);
            return room;
        }

    }




}
