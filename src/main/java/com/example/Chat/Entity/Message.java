package com.example.Chat.Entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 
 * @TableName message
 */
@TableName(value ="message")
@Data
public class Message implements Serializable {
    /**
     * 消息id
     */
    @TableId(value = "message_id")
    private String messageId;

    /**
     * 发出消息用户的头像
     */
    @TableField(value = "send_user_avatar")
    private String sendUserAvatar;

    /**
     * 消息内容
     */
    @TableField(value = "message")
    private String message;

    /**
     * 发出消息用户
     */
    @TableField(value = "send_user")
    private String sendUser;

    /**
     * 发出消息的用户Id
     */
    @TableField(value = "send_user_id")
    private Integer sendUserId;

    /**
     * 收到消息的会话
     */
    @TableField(value = "session_id")
    private String sessionId;

    /**
     * 发出消息的时间
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

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
        Message other = (Message) that;
        return (this.getMessageId() == null ? other.getMessageId() == null : this.getMessageId().equals(other.getMessageId()))
            && (this.getSendUserAvatar() == null ? other.getSendUserAvatar() == null : this.getSendUserAvatar().equals(other.getSendUserAvatar()))
            && (this.getMessage() == null ? other.getMessage() == null : this.getMessage().equals(other.getMessage()))
            && (this.getSendUser() == null ? other.getSendUser() == null : this.getSendUser().equals(other.getSendUser()))
            && (this.getSendUserId() == null ? other.getSendUserId() == null : this.getSendUserId().equals(other.getSendUserId()))
            && (this.getSessionId() == null ? other.getSessionId() == null : this.getSessionId().equals(other.getSessionId()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getMessageId() == null) ? 0 : getMessageId().hashCode());
        result = prime * result + ((getSendUserAvatar() == null) ? 0 : getSendUserAvatar().hashCode());
        result = prime * result + ((getMessage() == null) ? 0 : getMessage().hashCode());
        result = prime * result + ((getSendUser() == null) ? 0 : getSendUser().hashCode());
        result = prime * result + ((getSendUserId() == null) ? 0 : getSendUserId().hashCode());
        result = prime * result + ((getSessionId() == null) ? 0 : getSessionId().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", messageId=").append(messageId);
        sb.append(", sendUserAvatar=").append(sendUserAvatar);
        sb.append(", message=").append(message);
        sb.append(", sendUser=").append(sendUser);
        sb.append(", sendUserId=").append(sendUserId);
        sb.append(", sessionId=").append(sessionId);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}