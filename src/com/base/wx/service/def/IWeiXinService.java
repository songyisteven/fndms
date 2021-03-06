package com.base.wx.service.def;

import java.util.List;

/**
 * 微信用户相关的service
 * 
 * @author Administrator
 * @version 1.0
 * @since cupid 1.0
 */
public interface IWeiXinService {
	
	/**
	 * 添加微信消息监听器
	 * @param weiXinListener
	 */
	public void addWeiXinListener(WeiXinListener weiXinListener);
	
	/**
	 * 删除微信消息监听器
	 * @param weiXinListener
	 */
	public void removeWeiXinListener(WeiXinListener weiXinListener);
	
	
	
	public List<WeiXinListener> getAllWeiXinListeners();

	/**
	 * 解析普通消息，返回的是微信平台的消息格式XML
	 * 
	 * @param content
	 * @param content2
	 * @param toUserName
	 */
	public String intercept(String fromUserName, String toUserName, String content);

}
