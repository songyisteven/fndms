/**
 * 
 */
package com.base.wx.app.service.def;

import java.util.Map;

/**
 * @author yangtao
 * 
 * 用于管理微信公众号参数的服务接口
 *
 */
public interface WxAppService {
	
	
	public final static String APPID="AppId";
	public final static String APPSecret="APPSECRET";
	
	public Map<String,String> getAppParams(String orgCode);
	
	public String getAppId(String orgCode);
	
	public String getAppSecret(String orgCode);

}
