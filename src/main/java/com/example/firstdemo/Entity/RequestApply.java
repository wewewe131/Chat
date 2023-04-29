package com.example.firstdemo.Entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName request_apply
 */
@TableName(value ="request_apply")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestApply implements Serializable {
    /**
     * 好友
     */
    @TableId(value = "id",type = IdType.AUTO)
    private String id;

    /**
     * 发起好友申请的用户ID
     */
    @TableField(value = "user_id_request")
    private String userIdRequest;

    /**
     * 收到请求的用户/群聊
     */
    @TableField(value = "receive_id")
    private String receiveId;

    /**
     * 请求的类型"friend"or"group"
     */
    @TableField(value = "type")
    private String type;

    /**
     * 好友申请备注
     */
    @TableField(value = "mark")
    private String mark;

    /**
     * 是否同意好友请求1同意/0未同意/-1拒绝
     */
    @TableField(value = "is_accept")
    private Integer isAccept;

    /**
     * 请求时间
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 是否删除
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
        RequestApply other = (RequestApply) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserIdRequest() == null ? other.getUserIdRequest() == null : this.getUserIdRequest().equals(other.getUserIdRequest()))
            && (this.getReceiveId() == null ? other.getReceiveId() == null : this.getReceiveId().equals(other.getReceiveId()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getMark() == null ? other.getMark() == null : this.getMark().equals(other.getMark()))
            && (this.getIsAccept() == null ? other.getIsAccept() == null : this.getIsAccept().equals(other.getIsAccept()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getIsDel() == null ? other.getIsDel() == null : this.getIsDel().equals(other.getIsDel()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserIdRequest() == null) ? 0 : getUserIdRequest().hashCode());
        result = prime * result + ((getReceiveId() == null) ? 0 : getReceiveId().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getMark() == null) ? 0 : getMark().hashCode());
        result = prime * result + ((getIsAccept() == null) ? 0 : getIsAccept().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getIsDel() == null) ? 0 : getIsDel().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userIdRequest=").append(userIdRequest);
        sb.append(", receiveId=").append(receiveId);
        sb.append(", type=").append(type);
        sb.append(", mark=").append(mark);
        sb.append(", isAccept=").append(isAccept);
        sb.append(", createTime=").append(createTime);
        sb.append(", isDel=").append(isDel);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}