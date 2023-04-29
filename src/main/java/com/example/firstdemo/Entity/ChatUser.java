package com.example.firstdemo.Entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 
 * @TableName chat_user
 */
@TableName(value ="chat_user")
@Data
public class ChatUser implements Serializable {
    /**
     * 用户id
     */
    @TableId(value = "uid",type = IdType.AUTO)
    private String uid;

    /**
     * 用户名
     */
    @TableField(value = "uname")
    private String uname;

    /**
     * 用户头像
     */
    @TableField(value = "uavatar")
    private String uavatar;

    /**
     * 用户性别
     */
    @TableField(value = "usex")
    private String usex;

    /**
     * 用户签名
     */
    @TableField(value="signature")
    private String signature;

    /**
     * 注册时间
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 用户状态:1用户正常状态 0用户被封禁
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * ????
     */
    @TableField(value = "is_del")
    private Integer isDel;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ChatUser other = (ChatUser) that;
        return (this.getUid() == null ? other.getUid() == null : this.getUid().equals(other.getUid()))
            && (this.getUname() == null ? other.getUname() == null : this.getUname().equals(other.getUname()))
            && (this.getUavatar() == null ? other.getUavatar() == null : this.getUavatar().equals(other.getUavatar()))
            && (this.getUsex() == null ? other.getUsex() == null : this.getUsex().equals(other.getUsex()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getIsDel() == null ? other.getIsDel() == null : this.getIsDel().equals(other.getIsDel()))
            && (this.getSignature() == null ? other.getSignature() == null : this.getSignature().equals(other.getSignature()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUid() == null) ? 0 : getUid().hashCode());
        result = prime * result + ((getUname() == null) ? 0 : getUname().hashCode());
        result = prime * result + ((getUavatar() == null) ? 0 : getUavatar().hashCode());
        result = prime * result + ((getUsex() == null) ? 0 : getUsex().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getIsDel() == null) ? 0 : getIsDel().hashCode());
        result = prime * result + ((getSignature() == null) ? 0 : getSignature().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", uid=").append(uid);
        sb.append(", uname=").append(uname);
        sb.append(", uavatar=").append(uavatar);
        sb.append(", usex=").append(usex);
        sb.append(", createTime=").append(createTime);
        sb.append(", signature=").append(signature);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", status=").append(status);
        sb.append(", isDel=").append(isDel);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}