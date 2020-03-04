package com.zzdz.security.controller;


import com.zzdz.security.commom.entity.Result;
import com.zzdz.security.commom.entity.ResultCode;
import com.zzdz.security.commom.utils.IdWorker;
import com.zzdz.security.dao.SysRoleDao;
import com.zzdz.security.dao.SysUserDao;
import com.zzdz.security.entity.SysRoleEntity;
import com.zzdz.security.entity.SysUserEntity;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 
 *
 * @author joe
 * @email qiuzhangming@gmail.com
 * @date 2020-03-02 17:13:03
 */
@RestController
@RequestMapping("/sys")
public class SysUserController extends BaseController {
    @Autowired
    private IdWorker idWorker;

    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private SysRoleDao sysRoleDao;

    @PutMapping("/user/assignRoles")
    public Result assignRoles(@RequestBody Map<String,Object> map) {
        //1.获取被分配的用户id
        Long userId = (Long) map.get("id");
        //2.获取到角色的id列表
        List<Long> roleIds = (List<Long>) map.get("roleIds");
        Set<SysRoleEntity> roles = new HashSet<>();
        for (Long roleId : roleIds) {
            SysRoleEntity sysRole = sysRoleDao.findById(roleId).get();
            roles.add(sysRole);
        }

        SysUserEntity sysUser = sysUserDao.findById(userId).get();
        sysUser.setRoles(roles);
        sysUserDao.save(sysUser);

        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 查询所有
     */
    @GetMapping("/user")
    @RequiresPermissions(value = {"sys:user:list", "or"}, logical = Logical.OR)
    public Result findAllByCompanyId(Pageable pageable) {
        Page<SysUserEntity> page = sysUserDao.findAllByCompanyId(companyId, pageable);
        return new Result(ResultCode.SUCCESS, page);
    }


    /**
     * 根据id查询
     */
    @GetMapping("/user/{id}")
    //@RequiresPermissions("sys:user:info")
    public Result findById(@PathVariable("id") Long id) {
		SysUserEntity sysUser = sysUserDao.findById(id).get();
        return new Result(ResultCode.SUCCESS, sysUser);
    }

    /**
     * 保存
     */
    @PostMapping("/user")
    //@RequiresPermissions("sys:user:save")
    public Result save(@RequestBody SysUserEntity sysUser) {
        sysUser.setId(idWorker.nextId());
        sysUser.setCreateTime(new Date());
        String password = new Md5Hash(sysUser.getPassword(),sysUser.getName(),3).toString();
        sysUser.setPassword(password);

        sysUserDao.save(sysUser);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 修改
     */
    @PutMapping("/user/{id}")
    //@RequiresPermissions("sys:user:update")
    public Result update(@PathVariable("id") Long id, @RequestBody SysUserEntity sysUser){
        SysUserEntity entity = sysUserDao.findById(id).get();

        entity.setStatus(sysUser.getStatus());
        entity.setDepartmentId(sysUser.getDepartmentId());
        sysUserDao.save(entity);

        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 删除
     */
    @DeleteMapping("/user/{id}")
    //@RequiresPermissions("sys:user:delete")
    public Result delete(@PathVariable("id") Long id){
        sysUserDao.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }

}
