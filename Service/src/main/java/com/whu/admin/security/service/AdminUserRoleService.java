package com.whu.admin.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whu.admin.security.dao.AdminUserRoleDAO;
import com.whu.admin.security.entity.AdminUserRole;

/**
 * AdminUserRoleService
 */
@Service
public class AdminUserRoleService {

	@Autowired private AdminUserRoleDAO adminUserRoleDAO;

	/**
	 * 批量保存
	 * @param adminUserRoles
     */
	public void saveBatchAdminUserRole(List<AdminUserRole> adminUserRoles) {
		if (null != adminUserRoles && !adminUserRoles.isEmpty()) {
			this.adminUserRoleDAO.deleteByUserId(adminUserRoles.get(0).getAdminUserId());
			this.adminUserRoleDAO.saveBatch(adminUserRoles);
		}
	}

	/**
	 * 根据用户ID删除
	 * @param adminUserId
     */
	public void deleteAdminUserRoleByUserId(Long adminUserId) {
		this.adminUserRoleDAO.deleteByUserId(adminUserId);
	}
}
