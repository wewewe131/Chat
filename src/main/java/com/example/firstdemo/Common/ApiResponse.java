package com.example.firstdemo.Common;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Tag(name = "统一返回结果", description = "统一返回结果")
public class ApiResponse<T> {
    @Schema(description = "返回码")
    private int code;
    @Schema(description = "返回数据")
    private T data;
    @Schema(description = "返回信息")
    private String msg;
    @Schema(description = "是否成功")
    private boolean success;
    @Schema(description = "时间戳")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date timestamp;

    // ok
    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponseBuilder<T>().data(data).code(ResEnums.SUCCESS.getCode()).success(true).timestamp(new Date()).msg(ResEnums.SUCCESS.getMsg()).build();
    }

    public static <T> ApiResponse<T> ok() {
        return new ApiResponseBuilder<T>().code(ResEnums.SUCCESS.getCode()).success(true).timestamp(new Date()).msg(ResEnums.SUCCESS.getMsg()).build();
    }

    //fail
    public static <T> ApiResponse<T> fail(String msg, ResEnums resEnums) {
        return new ApiResponseBuilder<T>().code(resEnums.getCode()).success(false).timestamp(new Date()).msg(msg).build();
    }
    public static <T> ApiResponse<T> fail(String msg) {
        return new ApiResponseBuilder<T>().code(ResEnums.UNAUTHORIZED.getCode()).success(false).timestamp(new Date()).msg(msg).build();
    }

}
