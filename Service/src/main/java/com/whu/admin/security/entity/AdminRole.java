package com.whu.admin.security.entity;

import java.util.Date;

import com.whu.framework.base.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

public class AdminRole extends BaseEntity {
	
	@Getter @Setter private String name;

    @Getter @Setter private Date createTime;

    @Getter @Setter private Date updateTime;

}