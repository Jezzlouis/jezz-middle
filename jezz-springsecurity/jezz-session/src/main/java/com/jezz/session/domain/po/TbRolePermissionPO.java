package com.jezz.session.domain.po;

import java.io.Serializable;

public class TbRolePermissionPO implements Serializable {
    //ID
    private Integer id;

    //角色id
    private Integer roleId;

    //权限ID
    private Integer permissionId;

    //参数
    private String parameters;

    //将弃用
    private Integer status;

    static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}