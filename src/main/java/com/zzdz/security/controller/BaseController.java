package com.zzdz.security.controller;

import com.zzdz.security.entity.SysUserEntity;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseController {
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected Long companyId;

    /**
     * 使用shiro获取
     * @param request
     * @param response
     */
    @ModelAttribute
    public void setResAnReq(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;

        //获取session中的安全数据
        Subject subject = SecurityUtils.getSubject();
        //1.subject获取所有的安全数据集合
        PrincipalCollection principals = subject.getPrincipals();
        if(principals != null && !principals.isEmpty()){
            //2.获取安全数据
            SysUserEntity user = (SysUserEntity) principals.getPrimaryPrincipal();
            this.companyId = user.getCompanyId();
        }
    }
}
