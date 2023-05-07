package com.example.Chat.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName config
 */
@TableName(value ="config")
@Data
public class Config implements Serializable {
    /**
     * 配置id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 配置key
     */
    @TableField(value = "configKey")
    private String configkey;

    /**
     * 配置value
     */
    @TableField(value = "configValue")
    private String configvalue;

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
        Config other = (Config) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getConfigkey() == null ? other.getConfigkey() == null : this.getConfigkey().equals(other.getConfigkey()))
            && (this.getConfigvalue() == null ? other.getConfigvalue() == null : this.getConfigvalue().equals(other.getConfigvalue()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getConfigkey() == null) ? 0 : getConfigkey().hashCode());
        result = prime * result + ((getConfigvalue() == null) ? 0 : getConfigvalue().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", configkey=").append(configkey);
        sb.append(", configvalue=").append(configvalue);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}