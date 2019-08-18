package com.jezz.session.domain.po;

import java.io.Serializable;
import java.util.Date;

public class TbAdminPO implements Serializable {
    private Integer id;

    //管理员名称
    private String loginName;

    //管理员密码
    private String loginPassword;

    //管理员真实名称
    private String reallyName;

    //禅道账号
    private String zentaoAccount;

    //该管理员是否禁用(0不禁用1禁用)
    private Byte status;

    //电话号码
    private String phone;

    //公司名
    private String company;

    //区域ID（对应表tb_area的id）
    private Integer areaId;

    //国内外管理员表示(1国内2国外)
    private Integer zone;

    //管理员邮箱
    private String email;

    //管理员QQ
    private String qq;

    //web平台切换免登陆认证码
    private String uuid;

    //备注
    private String remark;

    //职位id
    private Integer positionId;

    //组织id
    private Integer organizationId;

    private Date createDate;

    private Date updateDate;

    //最近一次登录时间
    private Date latestLoginTime;

    static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String getReallyName() {
        return reallyName;
    }

    public void setReallyName(String reallyName) {
        this.reallyName = reallyName;
    }

    public String getZentaoAccount() {
        return zentaoAccount;
    }

    public void setZentaoAccount(String zentaoAccount) {
        this.zentaoAccount = zentaoAccount;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getZone() {
        return zone;
    }

    public void setZone(Integer zone) {
        this.zone = zone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
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

    public Date getLatestLoginTime() {
        return latestLoginTime;
    }

    public void setLatestLoginTime(Date latestLoginTime) {
        this.latestLoginTime = latestLoginTime;
    }
}