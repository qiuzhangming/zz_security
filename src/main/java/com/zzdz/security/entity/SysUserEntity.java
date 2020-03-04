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
 * 
 * 
 * @author joe
 * @email qiuzhangming@gmail.com
 * @date 2020-03-02 17:13:03
 */
@Getter
@Setter
@Entity
@Table(name = "sys_user")
public class SysUserEntity implements Serializable {

	/**
	 * 
	 */
	@Id
	private Long id;
	/**
	 * 用户名
	 */
	private String name;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 状态  0：禁用   1：正常
	 */
	private Integer status;
	/**
	 * 企业ID
	 */
	private Long companyId;
	/**
	 * 部门ID
	 */
	private Long departmentId;
	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 *  用户-角色关系表
	 *  JsonIgnore: 忽略json转化
	 */
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name="sys_user_role",
			joinColumns={@JoinColumn(name="user_id",referencedColumnName="id")},
			inverseJoinColumns={@JoinColumn(name="role_id",referencedColumnName="id")}
	)
	private Set<SysRoleEntity> roles = new HashSet<>();//用户与角色   多对多
}
