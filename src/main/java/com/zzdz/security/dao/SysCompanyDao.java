package com.zzdz.security.dao;

import com.zzdz.security.entity.SysCompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


/**
 * 
 * 
 * @author joe
 * @email qiuzhangming@gmail.com
 * @date 2020-03-02 17:13:03
 */

public interface SysCompanyDao extends JpaRepository<SysCompanyEntity,Long>, JpaSpecificationExecutor<SysCompanyEntity> {

}
