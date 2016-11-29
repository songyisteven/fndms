/**
 * 
 */
package com.base.wx.service.impl;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.base.wx.constants.WeixinConstants;
import com.base.wx.service.def.WeiXinListener;

/**
 * @author yangtao
 *
 *处理微信多客服的Listener
 */
public class WxKfListener implements WeiXinListener {


	@Override
	public void onMessage(Map<String, String> message, HttpServletResponse servletResponse) {

	}

	
	@Override
	public boolean isValid(Map<String, String> message) {
		String msgType = message.get(WeixinConstants.WX_MSG_MsgType);
		if (msgType == null)
			return false;
		if (!msgType.equals("event"))
			return false;
		String event=message.get("Event");
		if (event.equals(WeixinConstants.MSG_SUBSCRIBE)||event.equals(WeixinConstants.MSG_UNSUBSCRIBE))
			return true;
		return false;
	}

}
