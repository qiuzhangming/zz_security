package com.zzdz.security.controller;

import com.zzdz.security.commom.entity.Result;
import com.zzdz.security.commom.entity.ResultCode;
import com.zzdz.security.commom.utils.IdWorker;
import com.zzdz.security.dao.SysPermissionDao;
import com.zzdz.security.entity.SysPermissionEntity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


/**
 * 
 *
 * @author joe
 * @email qiuzhangming@gmail.com
 * @date 2020-03-02 17:13:03
 */
@RestController
@RequestMapping("/sys")
public class SysPermissionController extends BaseController {
    @Autowired
    private IdWorker idWorker;

    @Autowired
    private SysPermissionDao sysPermissionDao;

    /**
     * 列表
     */
    @GetMapping("/permission")
    //@RequiresPermissions("sys:permission:list")
    public Result findAllByCompanyId(Pageable pageable) {
        Page<SysPermissionEntity> page = sysPermissionDao.findAllByCompanyId(companyId, pageable);

        return new Result(ResultCode.SUCCESS, page);
    }


    /**
     * 信息
     */
    @GetMapping("/permission/{id}")
    //@RequiresPermissions("sys:permission:info")
    public Result findById(@PathVariable("id") Long id) {
		SysPermissionEntity sysPermission = sysPermissionDao.findById(id).get();

        return new Result(ResultCode.SUCCESS, sysPermission);
    }

    /**
     * 保存
     */
    @PostMapping("/permission")
    //@RequiresPermissions("sys:permission:save")
    public Result save(@RequestBody SysPermissionEntity sysPermission) {
        sysPermission.setId(idWorker.nextId());
        sysPermission.setCreateTime(new Date());
		sysPermissionDao.save(sysPermission);

        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 修改
     */
    @PutMapping("/permission/{id}")
    //@RequiresPermissions("sys:permission:update")
    public Result update(@PathVariable("id") Long id, @RequestBody SysPermissionEntity sysPermission){
        SysPermissionEntity entity = sysPermissionDao.findById(id).get();

        entity.setName(sysPermission.getName());
        entity.setDescription(sysPermission.getDescription());
        sysPermissionDao.save(entity);

        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 删除
     */
    @DeleteMapping("/permission/{id}")
    //@RequiresPermissions("sys:permission:delete")
    public Result delete(@PathVariable("id") Long id) {
        sysPermissionDao.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }

}
