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
 * @TableName friden_relation
 */
@TableName(value ="friend_relation")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FriendRelation implements Serializable {
    /**
     * 好友关系表
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    @TableField(value = "user_id_master")
    private String userIdMaster;

    /**
     * 好友id
     */
    @TableField(value = "user_id_friend")
    private String userIdFriend;

    /**
     * 添加时间
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 好友分组
     */
    @TableField(value = "friend_group")
    private String friendGroup;

    /**
     * 好友备注
     */
    @TableField(value = "friend_remarks")
    private String friendRemarks;

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
        FriendRelation other = (FriendRelation) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserIdMaster() == null ? other.getUserIdMaster() == null : this.getUserIdMaster().equals(other.getUserIdMaster()))
            && (this.getUserIdFriend() == null ? other.getUserIdFriend() == null : this.getUserIdFriend().equals(other.getUserIdFriend()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getFriendGroup() == null ? other.getFriendGroup() == null : this.getFriendGroup().equals(other.getFriendGroup()))
            && (this.getFriendRemarks() == null ? other.getFriendRemarks() == null : this.getFriendRemarks().equals(other.getFriendRemarks()))
            && (this.getIsDel() == null ? other.getIsDel() == null : this.getIsDel().equals(other.getIsDel()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserIdMaster() == null) ? 0 : getUserIdMaster().hashCode());
        result = prime * result + ((getUserIdFriend() == null) ? 0 : getUserIdFriend().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getFriendGroup() == null) ? 0 : getFriendGroup().hashCode());
        result = prime * result + ((getFriendRemarks() == null) ? 0 : getFriendRemarks().hashCode());
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
        sb.append(", userIdMaster=").append(userIdMaster);
        sb.append(", userIdFriend=").append(userIdFriend);
        sb.append(", createTime=").append(createTime);
        sb.append(", friendGroup=").append(friendGroup);
        sb.append(", friendRemarks=").append(friendRemarks);
        sb.append(", isDel=").append(isDel);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}