package com.zzdz.security.shiro;

import com.alibaba.fastjson.JSON;
import com.zzdz.security.dao.SysUserDao;
import com.zzdz.security.entity.SysPermissionEntity;
import com.zzdz.security.entity.SysRoleEntity;
import com.zzdz.security.entity.SysUserEntity;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * 自定义Realm 处理登录 权限
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private SysUserDao sysUserDao;

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        //1.获取安全数据
        SysUserEntity userEntity = (SysUserEntity) principalCollection.getPrimaryPrincipal();

        //2.获取权限信息
        Set<String> roles = new HashSet<>();
        Set<String> perms = new HashSet<>();

        for (SysRoleEntity sysRoleEntity : userEntity.getRoles()) {
            roles.add(sysRoleEntity.getName());
            for (SysPermissionEntity permission : sysRoleEntity.getPermissions()) {
                perms.add(permission.getName());
            }
        }

        System.out.println("roles:" + JSON.toJSONString(roles));
        System.out.println("perms" + JSON.toJSONString(perms));

        //3.构造权限数据，返回值
        SimpleAuthorizationInfo info = new  SimpleAuthorizationInfo();
        info.setRoles(roles);
        info.setStringPermissions(perms);
        return info;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;

        String username = token.getUsername();
        String password = new String(token.getPassword());
        //查询用户信息
        SysUserEntity user = sysUserDao.findByNameAndPassword(username,password).get();
        //账号不存在
        if(user == null) {
            throw new UnknownAccountException("账号或密码不正确");
        }

        //账号锁定
        if(user.getStatus() == 0) {
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        }

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
        return info;
    }

}
