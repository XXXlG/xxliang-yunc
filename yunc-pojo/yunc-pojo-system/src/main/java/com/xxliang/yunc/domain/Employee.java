package com.xxliang.yunc.domain;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.xxliang.yunc.constant.ValidateConstant;
import net.sf.jsqlparser.statement.alter.ValidateConstraint;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author xxliang
 * @since 2025-05-29
 */
@TableName("t_employee")
public class Employee extends Model<Employee> {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "员工id不能为空")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 姓名
     */
    @TableField("real_name")
    @NotEmpty(message = "员工姓名不能为空")
    private String realName;
    /**
     * 电话
     */
    @Pattern(regexp = ValidateConstant.TEL_REGEX, message = "手机号码格式不正确")//正则表达式
    private String tel;
    /**
     * 邮箱
     */
    @Email(message = "邮箱格式不正确")
    private String email;
    /**
     * 创建时间
     */
    @TableField("input_time")
    private Date inputTime;
    /**
     * 状态：0正常，1锁定，2注销
     */
    private Integer state;
    /**
     * 部门id
     */
    @TableField("dept_id")
    private Long deptId;
    /**
     * 员工类型 ， 1平台普通员工 ，2平台客服人员，3平台管理员，4机构员工，5,机构管理员或其他
     */
    private Integer type;
    @TableField("login_id")
    private Long loginId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getInputTime() {
        return inputTime;
    }

    public void setInputTime(Date inputTime) {
        this.inputTime = inputTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getLoginId() {
        return loginId;
    }

    public void setLoginId(Long loginId) {
        this.loginId = loginId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Employee{" +
                ", id=" + id +
                ", realName=" + realName +
                ", tel=" + tel +
                ", email=" + email +
                ", inputTime=" + inputTime +
                ", state=" + state +
                ", deptId=" + deptId +
                ", type=" + type +
                ", loginId=" + loginId +
                "}";
    }
}
