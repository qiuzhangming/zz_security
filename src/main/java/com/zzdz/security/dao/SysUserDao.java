package com.zzdz.security.dao;

import com.zzdz.security.entity.SysUserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

/**
 * 
 * 
 * @author joe
 * @email qiuzhangming@gmail.com
 * @date 2020-03-02 17:13:03
 */

public interface SysUserDao extends JpaRepository<SysUserEntity,Long>, JpaSpecificationExecutor<SysUserEntity> {

    Page<SysUserEntity> findAllByCompanyId(Long commanyId, Pageable pageable);

    Optional<SysUserEntity> findByName(String name);

    Optional<SysUserEntity> findByNameAndPassword(String name, String password);

    Optional<SysUserEntity> findByNameAndPasswordAndCompanyId(String name, String password, Long companyId);
}
