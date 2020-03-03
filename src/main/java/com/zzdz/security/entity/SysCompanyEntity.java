package com.zzdz.security.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "sys_company")
@Data
@NoArgsConstructor
public class SysCompanyEntity implements Serializable {

    @Id
    private Long id;

    /**
     * 公司名称
     */
    private String name;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;
}
