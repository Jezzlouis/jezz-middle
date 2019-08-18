package com.jezz.session.domain.po;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Date;

public class TbPermissionPO implements GrantedAuthority, Serializable {
    //主键id
    private Integer id;

    //权限模块
    private String module;

    //权限代码
    private String authority;

    //父级权限id
    private Integer pid;

    //是否有效 1为有效(页面初次添加默认无效)，0无效
    private Integer flag;

    //对应URL
    private String url;

    //备注
    private String remark;

    //创建时间
    private Date createDate;

    //更新时间
    private Date updateDate;

    //类型empty,text,number,select
    private String type;

    //正则校验规则
    private String verify;

    //正则错误返回信息
    private String error;

    //如果为1 则不论管理员是否有权限 都可以展示进行勾选
    private Integer display;

    static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Integer getDisplay() {
        return display;
    }

    public void setDisplay(Integer display) {
        this.display = display;
    }
}