package com.fanuo.actions;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.base.action.BaseAction;
import com.base.constants.CupidStrutsConstants;
import com.base.log.LogUtil;
import com.fanuo.model.User;
import com.fanuo.service.def.UserService;

/**
 * 后台管理,维护会员信息,系统信息
 * 
 * @author Administrator
 * @version 1.0
 * @since cupid 1.0
 */
@Controller("AdminAction")
@Scope("prototype")
public class AdminAction extends BaseAction {

	private static final long serialVersionUID = -6840817897299260353L;

	private Logger logger = LogUtil.getLogger(LogUtil.SYSTEM_LOG_ID);

	@Autowired
	private UserService userService;


	private User user=null;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * 到管理员登陆界面
	 * 
	 * @return
	 */
	public String gologin() {
		if (session.get(CupidStrutsConstants.SESSION_ADMIN) != null)
			return gotoAdminIndex();
		return "login";
	}

	public String gotoAdminIndex() {
		return SUCCESS;
	}

	public String login() {
		User userDb=userService.getUser(user.getUsername());
		return SUCCESS;
		/*
		String CODE_IMAGE = this.session.get(CupidStrutsConstants.CODE_IMAGE_ADMIN) == null ? "" : this.session.get(
				CupidStrutsConstants.CODE_IMAGE_ADMIN).toString();
		String verifyCode = this.request.getParameter("verifyCode");
		if (!CODE_IMAGE.equalsIgnoreCase(verifyCode)) {
			request.setAttribute("errorMsg", "验证码错误");
			return "login";
		}
		String encrptPassword = new BASE64Encoder().encode(admin.getPassword().getBytes());
		Admin user = securityService.getUserWithPermissionByName(admin.getUsername());
		if (user != null) {
			if (user.getPassword().trim().equals(encrptPassword.trim())) {
				if (user.getStatus() == 0) {
					request.setAttribute("errorMsg", "该账号已被禁用");
					return "login";
				}
				session.put(CupidStrutsConstants.SESSION_ADMIN, user);
				session.put(CupidStrutsConstants.SESSION_ADMIN_ROLE, user.getRoleString());// 角色名
				session.put(CupidStrutsConstants.SESSION_SUPER_ADMIN, user.isSuperAdmin());// 是否是超级管理员
				if (user.isSuperAdmin()) {
					request.getSession().setAttribute("orgCode", "0");
					request.getSession().setAttribute("adminLevel", "超级管理员");
				} else if (user.isAdmin()) {//平台管理员
					// 根据admin Id去查找管辖的分销机构，通过机构代码从而确定是什么角色
					Org org = adminService.getOrgByUserId(user.getId());
					if (org != null) {
						request.getSession().setAttribute("adminOrg", org);
						request.getSession().setAttribute("orgCode", org.getCode());
					}
					request.getSession().setAttribute("adminLevel", "平台管理员");
				} else {
					// 根据admin Id去查找管辖的分销机构，通过机构代码从而确定是什么角色
					Org org = adminService.getOrgByUserId(user.getId());
					if (org != null) {
						request.getSession().setAttribute("adminOrg", org);
						request.getSession().setAttribute("orgCode", org.getCode());
						request.getSession().setAttribute("adminLevel", org.getLevel());
					} else {
						// 根据角色配置
						if (user.getRoleString().indexOf("门店管理员") != -1) {
							request.getSession().setAttribute("adminLevel", "门店");
						} else if (user.getRoleString().indexOf("代理商") != -1) {
							request.getSession().setAttribute("adminLevel", "代理商");
						} else {
							// 显示默认菜单
							request.setAttribute("errorMsg", "此用户未授权，无法登陆。");
							return "login";
						}
					}
				}
				user.setOnline(true);
				user.setLastLoginDate(new Date());
				user.setLogCount(user.getLogCount() + 1);
				securityService.updateAdminUser(user);
				return SUCCESS;
			} else {
				request.setAttribute("errorMsg", "密码错误");
				return "login";
			}
		} else {
			request.setAttribute("errorMsg", "用户名错误");
			return "login";
		}
		*/
	}

	public String index() {
		return SUCCESS;
	}

	
}
