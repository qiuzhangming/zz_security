package com.zzdz.security.dao;

import com.zzdz.security.entity.SysPermissionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 
 * 
 * @author joe
 * @email qiuzhangming@gmail.com
 * @date 2020-03-02 17:13:03
 */
public interface SysPermissionDao extends JpaRepository<SysPermissionEntity, Long>, JpaSpecificationExecutor<SysPermissionEntity> {

    Page<SysPermissionEntity> findAllByCompanyId(Long companyId, Pageable pageable);

}
