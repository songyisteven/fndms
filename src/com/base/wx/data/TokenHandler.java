package com.base.wx.data;

import org.apache.log4j.Logger;

import com.base.log.LogUtil;
import com.base.util.HttpUtils;
import com.base.wx.cache.WxParamCache;
import com.base.wx.constants.WeixinConstants;

import net.sf.json.JSONObject;

public class TokenHandler implements WxDataHandler {
	Logger logger = LogUtil.getLogger(LogUtil.SERVER);
	@Override
	public String getValueAndUpdate2Cache(String orgCode) {
		String APPID = WeixinConstants.getAppParam(orgCode).get("AppId");
		String APPSECRET = WeixinConstants.getAppParam(orgCode).get("APPSECRET");
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+APPID + "&secret="+ APPSECRET	;
		logger.info("tokenurl---------" + url);
		String doGet = HttpUtils.doGet(url, null);
		if("".equals(doGet)){
			return null;
		}
		JSONObject fromObject = JSONObject.fromObject(doGet);
		if(fromObject.get("access_token") != null){
			String token = (String) fromObject.get("access_token");
			logger.info("final token get----------------" + token);
			WxParamCache.getInstance().updateParam(orgCode, WxParamCache.TOKEN, token, System.currentTimeMillis());
			return token;
		}else{
			return null;
		}
	}
}
