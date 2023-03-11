package com.example.firstdemo.Common;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "统一返回结果", description = "统一返回结果")
public class ApiResponse<T> {
    @ApiModelProperty(value = "状态码")
    private int code;
    @ApiModelProperty(value = "返回数据")
    private T data;
    @ApiModelProperty(value = "操作信息")
    private String msg;
    @ApiModelProperty(value = "是否成功")
    private boolean success;
    @ApiModelProperty(value = "时间戳")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date timestamp;

    // ok
    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponseBuilder<T>().data(data).code(ResEnums.SUCCESS.getCode()).success(true).timestamp(new Date()).msg(ResEnums.SUCCESS.getMsg()).build();
    }
    //fail
    public static <T> ApiResponse<T> fail(String msg,ResEnums resEnums) {
        return new ApiResponseBuilder<T>().code(resEnums.getCode()).success(false).timestamp(new Date()).msg(msg).build();
    }

}
