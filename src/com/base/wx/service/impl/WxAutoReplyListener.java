/**
 * 
 */
package com.base.wx.service.impl;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.log.LogUtil;
import com.base.wx.constants.WeixinConstants;
import com.base.wx.service.def.IWeiXinService;
import com.base.wx.service.def.WeiXinListener;

/**
 * @author yangtao
 *
 *处理微信自动回复功能的Listener
 */
@Service("wxAutoReplyListener")
public class WxAutoReplyListener implements WeiXinListener {

	private static Logger logger = LogUtil.getLogger(WxAutoReplyListener.class.getName());
	@Autowired
	private IWeiXinService weixinService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.base.wx.service.def.WeiXinListener#onMessage(java.util.Map)
	 */
	@Override
	public void onMessage(Map<String, String> message, HttpServletResponse servletResponse) {
		// String createTime = message.get(WeixinConstants.WX_MSG_CreateTime);
		String content = message.get(WeixinConstants.WX_MSG_Content);
		// String msgId =message.get(WeixinConstants.WX_MSG_MSGID);
		// String msgType=message.get(WeixinConstants.WX_MSG_MsgType);
		String toUserName = message.get(WeixinConstants.WX_MSG_ToUserName);
		String fromUserName = message.get(WeixinConstants.WX_MSG_FromUserName);
		logger.info("content=" + content);
		// // 以#开头的是命令行
		if (content.startsWith("#")) {
			logger.info("接收到指令消息:" + message.toString());
		} else {
			String responseXml = this.weixinService.intercept(fromUserName, toUserName, content);
			logger.info("responseXml==" + responseXml);
			try {
				servletResponse.getWriter().write(responseXml);
			} catch (IOException e) {
				logger.error("", e);
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.base.wx.service.def.WeiXinListener#isValid(java.util.Map)
	 */
	@Override
	public boolean isValid(Map<String, String> message) {
		String msgType = message.get(WeixinConstants.WX_MSG_MsgType);
		if (msgType == null)
			return false;
		if (msgType.equals("event"))
			return false;
		if (msgType.equals("text"))
			return true;
		return false;
	}

}
