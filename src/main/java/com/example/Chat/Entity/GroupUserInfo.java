package com.example.Chat.Entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 
 * @TableName group_user_info
 */
@TableName(value ="group_user_info")
@Data
public class GroupUserInfo implements Serializable {
    /**
     * 房间id
     */
    @TableField(value = "groupid")
    private Long groupid;

    /**
     * 房间名
     */
    @TableField(value = "group_name")
    private String groupName;

    /**
     * 用户id
     */
    @TableField(value = "uid")
    private Long uid;

    /**
     * 用户名
     */
    @TableField(value = "uname")
    private String uname;

    /**
     * 用户性别
     */
    @TableField(value = "usex")
    private String usex;

    /**
     * 用户头像
     */
    @TableField(value = "uavatar")
    private String uavatar;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    /**
     * 权限名
     */
    @TableField(value = "auth_name")
    private String authName;

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
        GroupUserInfo other = (GroupUserInfo) that;
        return (this.getGroupid() == null ? other.getGroupid() == null : this.getGroupid().equals(other.getGroupid()))
            && (this.getGroupName() == null ? other.getGroupName() == null : this.getGroupName().equals(other.getGroupName()))
            && (this.getUid() == null ? other.getUid() == null : this.getUid().equals(other.getUid()))
            && (this.getUname() == null ? other.getUname() == null : this.getUname().equals(other.getUname()))
            && (this.getUsex() == null ? other.getUsex() == null : this.getUsex().equals(other.getUsex()))
            && (this.getUavatar() == null ? other.getUavatar() == null : this.getUavatar().equals(other.getUavatar()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getAuthName() == null ? other.getAuthName() == null : this.getAuthName().equals(other.getAuthName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getGroupid() == null) ? 0 : getGroupid().hashCode());
        result = prime * result + ((getGroupName() == null) ? 0 : getGroupName().hashCode());
        result = prime * result + ((getUid() == null) ? 0 : getUid().hashCode());
        result = prime * result + ((getUname() == null) ? 0 : getUname().hashCode());
        result = prime * result + ((getUsex() == null) ? 0 : getUsex().hashCode());
        result = prime * result + ((getUavatar() == null) ? 0 : getUavatar().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getAuthName() == null) ? 0 : getAuthName().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", groupid=").append(groupid);
        sb.append(", groupName=").append(groupName);
        sb.append(", uid=").append(uid);
        sb.append(", uname=").append(uname);
        sb.append(", usex=").append(usex);
        sb.append(", uavatar=").append(uavatar);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", authName=").append(authName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}