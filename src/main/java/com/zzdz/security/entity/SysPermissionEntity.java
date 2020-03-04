package com.zzdz.security.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * 
 * @author joe
 * @email qiuzhangming@gmail.com
 * @date 2020-03-02 17:13:03
 */
@Data
@Entity
@Table(name = "sys_permission")
public class SysPermissionEntity implements Serializable {

	/**
	 * 主键
	 */
	@Id
	private Long id;
	/**
	 * 权限名称
	 */
	private String name;
	/**
	 * 权限描述
	 */
	private String description;
	/**
	 * 权限类型 1为菜单 2为功能 3为API
	 */
	private Integer type;
	/**
	 * 企业id
	 */
	private Long companyId;
	/**
	 * 
	 */
	private String code;
	/**
	 * 企业可见性 0：不可见，1：可见
	 */
	private Integer enVisible;

	/**
	 * 创建时间
	 */
	private Date createTime;

	@JsonIgnore
	@ManyToMany(mappedBy="permissions")  //不维护中间表
	private Set<SysRoleEntity> roles = new HashSet<>(0);//角色与权限   多对多

}
