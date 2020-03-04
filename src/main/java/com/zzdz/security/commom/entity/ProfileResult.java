package com.zzdz.security.commom.entity;


import com.zzdz.security.entity.SysPermissionEntity;
import com.zzdz.security.entity.SysRoleEntity;
import com.zzdz.security.entity.SysUserEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.*;


/**
 * 精简用户认证信息
 */
@Setter
@Getter
public class ProfileResult implements Serializable {
    private Long id;
    private String name;
    private Long companyId;
    private Set<String> roles = new HashSet<>();
    private Set<String> perms = new HashSet<>();


    public ProfileResult(SysUserEntity userEntity) {
        this.id = userEntity.getId();
        this.name = userEntity.getName();
        this.companyId = userEntity.getCompanyId();
        this.companyId = userEntity.getCompanyId();

        for (SysRoleEntity sysRoleEntity : userEntity.getRoles()) {
            roles.add(sysRoleEntity.getName());
            for (SysPermissionEntity permission : sysRoleEntity.getPermissions()) {
                perms.add(permission.getName());
            }
        }
    }
}
