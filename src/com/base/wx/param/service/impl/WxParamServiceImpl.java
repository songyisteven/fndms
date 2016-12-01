/**
 * 
 */
package com.base.wx.param.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.base.log.LogUtil;
import com.base.wx.model.WxParamObj;
import com.base.wx.param.handler.WxDataHandler;
import com.base.wx.param.service.def.WxParamService;

/**
 * @author yangtao
 *
 */
@Service("wxParamService")
public class WxParamServiceImpl implements WxParamService {

	private final Map<String,WxDataHandler> wxDataHandlers=new ConcurrentHashMap<String,WxDataHandler>();
	
	private final Map<String, Map<String, WxParamObj>> allCache = new ConcurrentHashMap<String, Map<String,WxParamObj>>();
	
	private final Logger logger=LogUtil.getLogger(WxParamServiceImpl.class.getName());
	

	@Override
	public void addWxDataHandler(String key, WxDataHandler wxDataHandler) {
		wxDataHandlers.put(key, wxDataHandler);
	}


	@Override
	public void removeWxDataHandler(String key) {
		wxDataHandlers.remove(key);
	}
	
	@Override
	public WxDataHandler getWxDataHandler(String key) {
		return wxDataHandlers.get(key);
	}


	@Override
	public List<WxDataHandler> getAllWxDataHandlers() {
		return new ArrayList<WxDataHandler>(wxDataHandlers.values());
	}

	
	@Override
	public void updateWxParam(String orgCode, String key, String value, Long newTime) {
		//先从大缓存里取出某个共众号的缓存map
		Map<String, WxParamObj> paramCache = allCache.get(orgCode);
		if(paramCache != null){
			WxParamObj WxParamObj = paramCache.get(key);
			if (WxParamObj != null) {
				WxParamObj.setParamvalue(value);
				WxParamObj.setTimestamp(System.currentTimeMillis());
				logger.info("参数" + key + "更新完成...");
			} else {
				logger.info("无此参数["+ key +"]，新增, value=" + value);
				WxParamObj = new WxParamObj(value, newTime);
				paramCache.put(key, WxParamObj);
			}
		}else{
			//缓存里不存在， 先增加公众号缓存 
			paramCache = new ConcurrentHashMap<String, WxParamObj>();
			allCache.put(orgCode, paramCache);
			logger.info("无此参数["+ key +"]，新增, value=" + value);
			WxParamObj WxParamObj = new WxParamObj(value, newTime);
			paramCache.put(key, WxParamObj);
		}
	}

	
	@Override
	public String getWxParam(String orgCode, String key) {
		try{
			logger.info("param-:" + orgCode + "|" + key);
			Map<String, WxParamObj> map = this.allCache.get(orgCode);
			if(map == null){//还没有该分销商的缓存
				Map<String, WxParamObj> tm = new ConcurrentHashMap<String, WxParamObj>();
				allCache.put(orgCode, tm);
				map = tm;
			}
			WxParamObj WxParamObj = map.get(key);
			if (WxParamObj != null) {
				return WxParamObj.getParamvalue();
			} else {
				logger.info(key + "in getWxJsParam--重新获取token--" + orgCode);
				//重新获取
				WxDataHandler handler =wxDataHandlers.get(key);
				return handler.getValueAndUpdate2Cache(orgCode);
			}
		}catch(Exception e){
		    logger.error("", e);
			return null;
		}
	}


	@Override
	public Map<String, Map<String, WxParamObj>> getFullCache() {
		return new HashMap<String,Map<String,WxParamObj>>(allCache);
	}


	@Override
	public String getJSApiTicket(String orgCode) {
		return getWxParam(orgCode,WxParamService.JS_TICKET);
	}


	@Override
	public String getAccessToken(String orgCode) {
		return  getWxParam(orgCode,WxParamService.TOKEN);
	}





}
