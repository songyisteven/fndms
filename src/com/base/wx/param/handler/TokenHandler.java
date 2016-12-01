package com.base.wx.param.handler;

import javax.annotation.PostConstruct;

import org.apache.cxf.common.util.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.log.LogUtil;
import com.base.util.HttpUtils;
import com.base.wx.app.service.def.WxAppService;
import com.base.wx.param.service.def.WxParamService;

import net.sf.json.JSONObject;

@Service("tokenHandler")
public class TokenHandler implements WxDataHandler {
	Logger logger = LogUtil.getLogger(LogUtil.SERVER);
	
	@Autowired
	private WxParamService wxParamService;
	@Autowired
	private WxAppService wxAppService;
	
	@PostConstruct
	public void init(){
		wxParamService.addWxDataHandler(WxParamService.TOKEN, this);
	}
	
	@Override
	public String getValueAndUpdate2Cache(String orgCode) {
		String APPID = wxAppService.getAppId(orgCode);
		String APPSECRET = wxAppService.getAppSecret(orgCode);
		if(StringUtils.isEmpty(APPID)||StringUtils.isEmpty(APPSECRET)){
			return null;
		}
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
			wxParamService.updateWxParam(orgCode, WxParamService.TOKEN, token, System.currentTimeMillis());
			return token;
		}else{
			return null;
		}
	}
}
