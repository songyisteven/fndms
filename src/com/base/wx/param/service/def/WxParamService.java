/**
 * 
 */
package com.base.wx.param.service.def;

import java.util.List;
import java.util.Map;

import com.base.wx.model.WxParamObj;
import com.base.wx.param.handler.WxDataHandler;

/**
 * @author yangtao
 *
 */
public interface WxParamService {
	
	public static final String TOKEN = "access_token";

	public static final String JS_TICKET = "jsapi_ticket";

	public void addWxDataHandler(String key,WxDataHandler wxDataHandler);
	
	public void removeWxDataHandler(String key);
	
	public WxDataHandler getWxDataHandler(String key);
	
	public List<WxDataHandler> getAllWxDataHandlers();
	
	public void updateWxParam(String orgCode, String key, String value, Long newTime);
	
	public String getWxParam(String orgCode, String key);
	
	public Map<String, Map<String, WxParamObj>>  getFullCache();
	
	public String getJSApiTicket(String orgCode);
	
	public String getAccessToken(String orgCode);
	
}
