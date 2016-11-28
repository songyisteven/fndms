package com.base.wx.data;

import java.util.ArrayList;
import java.util.List;

import com.base.wx.cache.WxParamCache;

public class WxDataCommands {
	
	public static List<WxDataHandler> handlers = new ArrayList<WxDataHandler>();
	
	private static WxDataCommands instance;
	
	private WxDataCommands(){
		handlers.add(new TokenHandler());
		handlers.add(new JsApiTickedHandler());
	}
	
	
	public static WxDataCommands getInstance(){
		if(instance == null){
			instance = new WxDataCommands();
		}
		return instance;
	}
	
	
	public List<WxDataHandler> getAllCommands(){
		return new ArrayList<WxDataHandler>(handlers);
	}


	public WxDataHandler getDataHandler(String key) {
		if(WxParamCache.JS_TICKET.equals(key)){
			return new JsApiTickedHandler();
		}else if(WxParamCache.TOKEN.equals(key)){
			return new TokenHandler();
		}
		return null;
	}
	
}
