package com.zzdz.security.controller;


import com.zzdz.security.commom.entity.Result;
import com.zzdz.security.commom.entity.ResultCode;
import com.zzdz.security.commom.utils.IdWorker;
import com.zzdz.security.dao.SysCompanyDao;
import com.zzdz.security.entity.SysCompanyEntity;
import com.zzdz.security.entity.SysUserEntity;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresRoles;
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
@Api(tags="公司管理")
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
    @RequiresRoles({"role1"})
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
    @ApiOperation(value="用户注册",notes="手机号、密码都是必输项，年龄随边填，但必须是数字")
    @ApiImplicitParams({
            @ApiImplicitParam(name="mobile",value="手机号",required=true,paramType="form"),
            @ApiImplicitParam(name="password",value="密码",required=true,paramType="form"),
            @ApiImplicitParam(name="age",value="年龄",required=true,paramType="form",dataType="Integer")
    })
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
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
