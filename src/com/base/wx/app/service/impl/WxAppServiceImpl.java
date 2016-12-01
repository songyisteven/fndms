/**
 * 
 */
package com.base.wx.app.service.impl;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.base.util.FileUtils;
import com.base.wx.app.service.def.WxAppService;

/**
 * @author yangtao
 *
 */
@Service("wxAppService")
public class WxAppServiceImpl implements WxAppService {

	private  final Map<String,Map<String,String>> appParams=new ConcurrentHashMap<String,Map<String,String>>();
	
	
	@PostConstruct
	public void init(){
		InputStream in=this.getClass().getClassLoader().getResourceAsStream("app.properties");
		Properties properties=FileUtils.getProperties(in);
		for(Entry<Object,Object> entry:properties.entrySet()){
			String orgCode=entry.getKey().toString();
			String value=entry.getValue().toString();
			String temp[]=value.split(",");
			if(temp.length!=2)
				continue;
			String appId=temp[0];
			String appSecret=temp[1];
			Map<String,String> fnds=new HashMap<String,String>();
			fnds.put(WxAppService.APPID, appId);
			fnds.put(WxAppService.APPSecret, appSecret);
			appParams.put(orgCode, fnds);	
		}
		
	}
	
	@Override
	public Map<String, String> getAppParams(String orgCode) {
		return new HashMap<String,String>(appParams.get(orgCode));
	}

	@Override
	public String getAppId(String orgCode) {
		if(!appParams.containsKey(orgCode))
			return null;
		return appParams.get(orgCode).get(WxAppService.APPID);
	}

	@Override
	public String getAppSecret(String orgCode) {
		if(!appParams.containsKey(orgCode))
			return null;
		return appParams.get(orgCode).get(WxAppService.APPSecret);
	}

}
