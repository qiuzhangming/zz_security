package com.zzdz.security.controller;

import com.zzdz.security.commom.entity.Result;
import com.zzdz.security.commom.entity.ResultCode;
import com.zzdz.security.commom.utils.IdWorker;
import com.zzdz.security.dao.SysPermissionDao;
import com.zzdz.security.dao.SysRoleDao;
import com.zzdz.security.entity.SysPermissionEntity;
import com.zzdz.security.entity.SysRoleEntity;
import com.zzdz.security.entity.SysUserEntity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.*;


/**
 * 角色
 *
 * @author joe
 * @email qiuzhangming@gmail.com
 * @date 2020-03-02 17:13:03
 */
@RestController
@RequestMapping("/sys")
public class SysRoleController extends BaseController {
    @Autowired
    private IdWorker idWorker;

    @Autowired
    private SysRoleDao sysRoleDao;

    @Autowired
    private SysPermissionDao sysPermissionDao;

    @PutMapping("/role/assignPermissions")
    public Result assignPermissions(@RequestBody Map<String,Object> map) {
        //1.获取被分配的角色id
        Long roleId = (Long) map.get("id");
        //2.获取到角色的id列表
        List<Long> permissionIds = (List<Long>) map.get("permissionIds");
        Set<SysPermissionEntity> permissions = new HashSet<>();
        for (Long permissionId : permissionIds) {
            SysPermissionEntity sysPermission = sysPermissionDao.findById(permissionId).get();
            permissions.add(sysPermission);
        }

        SysRoleEntity sysRole = sysRoleDao.findById(roleId).get();
        sysRole.setPermissions(permissions);
        sysRoleDao.save(sysRole);

        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 查询所有
     */
    @GetMapping("/role")
    //@RequiresPermissions("sys:role:list")
    public Result findAllByCompanyId(Pageable pageable){
        Page<SysRoleEntity> page = sysRoleDao.findAllByCompanyId(companyId, pageable);
        return new Result(ResultCode.SUCCESS, page);
    }


    /**
     * 信息
     */
    @GetMapping("/role/{id}")
    //@RequiresPermissions("sys:role:info")
    public Result findById(@PathVariable("id") Long id){
		SysRoleEntity sysRole = sysRoleDao.findById(id).get();
        return new Result(ResultCode.SUCCESS, sysRole);
    }

    /**
     * 保存
     */
    @PostMapping("/role")
    //@RequiresPermissions("sys:role:save")
    public Result save(@RequestBody SysRoleEntity sysRole){
        sysRole.setId(idWorker.nextId());
        sysRole.setCreateTime(new Date());
        sysRoleDao.save(sysRole);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 修改
     */
    @PutMapping("/role/{id}")
    //@RequiresPermissions("sys:role:update")
    public Result update(@PathVariable("id") Long id, @RequestBody SysRoleEntity sysRole){
        SysRoleEntity entity = sysRoleDao.findById(id).get();

        entity.setName(sysRole.getName());
        entity.setDescription(sysRole.getDescription());
        sysRoleDao.save(entity);

        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    //@RequiresPermissions("sys:role:delete")
    public Result delete(@PathVariable("id") Long id) {
        sysRoleDao.deleteById(id);

        return new Result(ResultCode.SUCCESS);
    }

}
