package com.zzdz.security.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 角色
 * 
 * @author joe
 * @email qiuzhangming@gmail.com
 * @date 2020-03-02 17:13:03
 */
@Getter
@Setter
@Entity
@Table(name = "sys_role")
public class SysRoleEntity implements Serializable {

	/**
	 * 
	 */
	@Id
	private Long id;
	/**
	 * 角色名称
	 */
	private String name;
	/**
	 * 企业id
	 */
	private Long companyId;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 说明
	 */
	private String description;

	/**
	 * 角色与用户
	 * cascade = CascadeType.REFRESH, fetch = FetchType.EAGER
	 */
	@JsonIgnore
	@ManyToMany(mappedBy="roles")  //不维护中间表
	private Set<SysUserEntity> users = new HashSet<>(0);//角色与用户   多对多

	/**
	 * 角色与权限
	 */
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name="sys_role_permission",
			joinColumns={@JoinColumn(name="role_id",referencedColumnName="id")},
			inverseJoinColumns={@JoinColumn(name="permission_id",referencedColumnName="id")})
	private Set<SysPermissionEntity> permissions = new HashSet<>();//角色与模块  多对多

}
