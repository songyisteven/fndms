package com.base.wx.constants;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WeixinConstants {

	/** 订阅消息 */
	public static final String MSG_SUBSCRIBE = "subscribe";

	/** 取消订阅消息 */
	public static final String MSG_UNSUBSCRIBE = "unsubscribe";
	
	/** 多客服关闭会话 */
	public static final String KF_CLOSE_SESSION = "kf_close_session";
	
	public static final Map<String,Map<String,String>> appParams=new ConcurrentHashMap<String,Map<String,String>>();
	
	static{
		Map<String,String> fnds=new HashMap<String,String>();
		fnds.put("APPid", "12311111");//添加分销系统Appid及AppSecret
		fnds.put("APPSECRET", "12311111");
		appParams.put("fnd", fnds);
		
		
		
	}
	
	
	public static Map<String,String> getAppParam(String org){
		return appParams.get(org);
	}
	

	

}
