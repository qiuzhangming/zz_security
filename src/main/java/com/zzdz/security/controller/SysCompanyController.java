package com.zzdz.security.controller;


import com.zzdz.security.commom.entity.Result;
import com.zzdz.security.commom.entity.ResultCode;
import com.zzdz.security.commom.utils.IdWorker;
import com.zzdz.security.dao.SysCompanyDao;
import com.zzdz.security.entity.SysCompanyEntity;
import com.zzdz.security.entity.SysUserEntity;
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
public class SysCompanyController extends BaseController {
    @Autowired
    private IdWorker idWorker;

    @Autowired
    private SysCompanyDao sysCompanyDao;

    /**
     * 查询所有
     */
    @GetMapping("/company")
    public Result findAll(Pageable pageable) {
        Page<SysCompanyEntity> page = sysCompanyDao.findAll(pageable);
        return new Result(ResultCode.SUCCESS, page);
    }


    /**
     * 根据id查询
     */
    @GetMapping("/company/{id}")
    public Result findById(@PathVariable("id") Long id) {
        SysCompanyEntity sysCompany = sysCompanyDao.findById(id).get();
        return new Result(ResultCode.SUCCESS, sysCompany);
    }

    /**
     * 保存
     */
    @PostMapping("/company")
    public Result save(@RequestBody SysCompanyEntity sysCompany) {
        sysCompany.setId(idWorker.nextId());
        sysCompany.setCreateTime(new Date());
        sysCompanyDao.save(sysCompany);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 修改
     */
    @PutMapping("/company/{id}")
    public Result update(@PathVariable("id") Long id, @RequestBody SysCompanyEntity sysCompany){
        SysCompanyEntity entity = sysCompanyDao.findById(id).get();

        entity.setName(sysCompany.getName());
        entity.setRemark(sysCompany.getRemark());
        sysCompanyDao.save(entity);

        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 删除
     */
    @DeleteMapping("/company/{id}")
    public Result delete(@PathVariable("id") Long id) {
        sysCompanyDao.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }

}
