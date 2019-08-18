package com.jezz.session.domain.po;

import java.io.Serializable;

public class TbAdminRolePO implements Serializable {
    private Integer id;

    //管理员ID
    private Integer adminId;

    //角色ID
    private Integer roleId;

    static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}