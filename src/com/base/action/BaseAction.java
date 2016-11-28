/**
 * 
 */
package com.base.action;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author tao
 *
 */
abstract public class BaseAction extends ActionSupport implements SessionAware, ServletRequestAware, ServletResponseAware{

	private static final long serialVersionUID = -4062103460710868545L;

	public HttpServletRequest request;
	
	public HttpServletResponse response;
	
	public Map<String, Object> session = new ConcurrentHashMap<String, Object>();
	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response=response;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;	
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session.clear();
		this.session.putAll(session);
		
	}

}
