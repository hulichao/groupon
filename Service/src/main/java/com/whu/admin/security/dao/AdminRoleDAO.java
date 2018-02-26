package com.whu.admin.security.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.whu.admin.security.entity.AdminRole;
import com.whu.framework.common.page.PagingResult;
import com.whu.framework.common.persistence.BaseMybatisDAO;
import com.whu.framework.common.search.Search;

/**
 * AdminRoleDAO
 */
@Repository
public class AdminRoleDAO extends BaseMybatisDAO {
	
	private final String MAPPER_NAMESPACE = "com.whu.admin.security.entity.AdminRoleMapper";

	/**
	 * 分页查询角色信息
	 * @param search
	 * @return
     */
	public PagingResult<AdminRole> findPage(Search search) {
		return super.findForPage(MAPPER_NAMESPACE + ".countPageAdminRoles",
				MAPPER_NAMESPACE + ".findPage", search);
	}

	/**
	 * 根据ID列表查询对应角色信息
	 * @param params
	 * @return
     */
	public List<AdminRole> findByIds(List<Long> params) {
		return super.findAll(MAPPER_NAMESPACE + ".findByIds", params);
	}

	/**
	 * 新建角色
	 * @param adminRole
     */
	public void save(AdminRole adminRole) {
		super.save(MAPPER_NAMESPACE + ".insertSelective", adminRole);
	}

	/**
	 * 更新角色
	 * @param adminRole
     */
	public void update(AdminRole adminRole) {
		super.update(MAPPER_NAMESPACE + ".updateByPrimaryKeySelective", adminRole);
	}
	
}
