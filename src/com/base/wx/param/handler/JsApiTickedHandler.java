package com.base.wx.param.handler;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.log.LogUtil;
import com.base.util.HttpUtils;
import com.base.util.WeiXinUtil;
import com.base.wx.param.service.def.WxParamService;

@Service("jsApiTickedHandler")
public class JsApiTickedHandler implements WxDataHandler {
	private static final Logger logger = LogUtil.getLogger(LogUtil.SERVER);
	@Autowired
	private WxParamService wxParamService;
	
	@PostConstruct
	public void init(){
		wxParamService.addWxDataHandler(WxParamService.JS_TICKET, this);
	}
	@Override
	public String getValueAndUpdate2Cache(String orgCode) {
		String token =wxParamService.getWxParam(orgCode, WxParamService.TOKEN);
		logger.info("in JsApiTickedHandler--" + token);
		String jsapiUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+ token +"&type=jsapi";
		String doGet = HttpUtils.doGet(jsapiUrl, null);
		String ticket = WeiXinUtil.getValueFromJson(doGet, "ticket");
		//更新到缓存
		wxParamService.updateWxParam(orgCode, WxParamService.JS_TICKET, ticket, System.currentTimeMillis());
		return ticket;
	}
}
