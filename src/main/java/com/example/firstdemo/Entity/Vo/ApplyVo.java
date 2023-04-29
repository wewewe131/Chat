package com.example.firstdemo.Entity.Vo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ApplyVo implements Serializable {
    @Schema(description = "申请id")
    private String id;
    @Schema(description = "申请类型")
    private String type;
    @Schema(description = "申请目标id")
    private String applyId;
    @Schema(description = "备注")
    private String mark;
    @Schema(description = "发起申请的用户名")
    private String userName;
    @Schema(description = "发起申请的用户id")
    private String uid;
}
