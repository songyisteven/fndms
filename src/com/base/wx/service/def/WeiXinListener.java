/**
 * 
 */
package com.base.wx.service.def;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

/**
 * @author yangtao
 *
 */
public interface WeiXinListener {
	
	public void onMessage(Map<String,String> message,HttpServletResponse servletResponse);
	
	public boolean isValid(Map<String,String> message);

}
