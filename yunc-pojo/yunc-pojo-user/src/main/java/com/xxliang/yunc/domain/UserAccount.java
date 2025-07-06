package com.xxliang.yunc.domain;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 用户账户
 * </p>
 *
 * @author xxliang
 * @since 2025-05-30
 */
@TableName("t_user_account")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAccount extends Model<UserAccount> {

    private static final long serialVersionUID = 1L;

    private Long id;
    @TableField("usable_amount")
    private BigDecimal usableAmount = BigDecimal.ZERO;
    @TableField("frozen_amount")
    private BigDecimal frozenAmount  = BigDecimal.ZERO;
    @TableField("create_time")
    private Long createTime;
    @TableField("update_time")
    private Long updateTime;
    /**
     * 支付密码
     */
    private String password;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getUsableAmount() {
        return usableAmount;
    }

    public void setUsableAmount(BigDecimal usableAmount) {
        this.usableAmount = usableAmount;
    }

    public BigDecimal getFrozenAmount() {
        return frozenAmount;
    }

    public void setFrozenAmount(BigDecimal frozenAmount) {
        this.frozenAmount = frozenAmount;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
        ", id=" + id +
        ", usableAmount=" + usableAmount +
        ", frozenAmount=" + frozenAmount +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", password=" + password +
        "}";
    }
}
