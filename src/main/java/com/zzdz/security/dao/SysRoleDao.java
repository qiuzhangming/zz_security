package com.zzdz.security.dao;

import com.zzdz.security.entity.SysRoleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 角色
 * 
 * @author joe
 * @email qiuzhangming@gmail.com
 * @date 2020-03-02 17:13:03
 */
public interface SysRoleDao extends JpaRepository<SysRoleEntity,Long>, JpaSpecificationExecutor<SysRoleEntity> {
    Page<SysRoleEntity> findAllByCompanyId(Long companyId, Pageable pageable);
}
