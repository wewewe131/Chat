package com.example.Chat.Controller;

import com.example.Chat.Common.ApiResponse;
import com.example.Chat.Common.LocalUser;
import com.example.Chat.Entity.ChatUser;
import com.example.Chat.Entity.RequestApply;
import com.example.Chat.Entity.Vo.ApplyAgreeParam;
import com.example.Chat.Entity.Vo.ApplyVo;
import com.example.Chat.Service.ChatUserService;
import com.example.Chat.Service.RequestApplyService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/apply")
public class ApplyController {
    @Lazy
    private final RequestApplyService requestApplyService;

    private final ChatUserService chatUserService;

    @GetMapping
    public ApiResponse<List> listApiResponse() {
        ChatUser localUser = LocalUser.getLocalUser();
        String uid = localUser.getUid();
        List<RequestApply> userApply = requestApplyService.getUserApply(uid);
        List<ApplyVo> applyVos = userApply.stream().map(v ->
                ApplyVo.builder()
                        .applyId(v.getReceiveId())
                        .id(v.getId())
                        .mark(v.getMark())
                        .type(v.getType())
                        .userName(chatUserService.getById(v.getUserIdRequest()).getUname())
                        .uid(v.getUserIdRequest())
                        .build()
        ).toList();
        return ApiResponse.ok(applyVos);
    }

    @PostMapping("/agree")
    public ApiResponse<Boolean> agree(@RequestBody ApplyAgreeParam applyAgreeParam) {
        boolean b = requestApplyService.agree(applyAgreeParam.getApplyId());
        return ApiResponse.ok(b);
    }

    @PostMapping("/refuse")
    public ApiResponse<Boolean> refuse(@RequestBody ApplyAgreeParam applyAgreeParam) {
        boolean b = requestApplyService.refuse(applyAgreeParam.getApplyId());
        return ApiResponse.ok(b);
    }
}
