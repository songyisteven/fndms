/**
 * 
 */
package com.base.wx.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.base.ServerBeanFactory;
import com.base.log.LogUtil;
import com.base.util.WeiXinUtil;
import com.base.util.WeixinMessageDigestUtil;
import com.base.wx.constants.WeixinConstants;
import com.base.wx.service.def.IWeiXinService;

/**
 * @author tao
 *
 */
public class WxServlet extends HttpServlet {
	private static final long serialVersionUID = 2148447682092966772L;
	private static final String TOKEN = "Lvze2015";
	private Logger logger = LogUtil.getLogger(WxServlet.class.getSimpleName());

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/**
		 * 接收微信服务器发送请求时传递过来的4个参数
		 */
		String signature = request.getParameter("signature");// 微信加密签名signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
		String timestamp = request.getParameter("timestamp");// 时间戳
		String nonce = request.getParameter("nonce");// 随机数
		String echostr = request.getParameter("echostr");// 随机字符串
		if (StringUtils.isNotEmpty(echostr) && checkSignature(signature, timestamp, nonce, echostr)) {// toke验证
			response.getWriter().write(echostr);
			return;
		}

		logger.info("接收到微信消息...");
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			// line = new String(line.getBytes("utf-8"));
			sb.append(line);
		}
		logger.info("data:" + sb.toString());
		if (sb.toString().equals("")) {
			response.getWriter().write("");
			return;
		}

		Document msgdoc = null;
		try {
			msgdoc = DocumentHelper.parseText(sb.toString());
		} catch (DocumentException e) {
			logger.info("" + e);
			return;
		}
		Element root = msgdoc.getRootElement();
		Element node = root.element("MsgType");
		String MsgType = node.getTextTrim();
		node = root.element("ToUserName");// 开发者账号/公众号-----------
		String toUserName = node.getTextTrim();
		node = root.element("FromUserName");// 发送者openId
		// 即：openId
		String fromUserName = node.getTextTrim();
		String responseXml = "";
		if (MsgType != null && MsgType.equals("event")) {
			node = root.element("Event");
			// 事件名称
			String event = node.getTextTrim();
			String eventkey = root.element("EventKey").getTextTrim();
			logger.info("MsgType==" + MsgType + "  event=" + event);

			if (WeixinConstants.MSG_SUBSCRIBE.equals(event)) {

			}
			if (WeixinConstants.KF_CLOSE_SESSION.equals(event)) {

			}

			if (WeixinConstants.MSG_UNSUBSCRIBE.equals(event)) {

			}

			if (event.equalsIgnoreCase("CLICK")) {

			} else if (event.equalsIgnoreCase("VIEW")) {

			}
		} else if (StringUtils.isEmpty(MsgType) || !"text".equals(MsgType)) {
			logger.info("不支持的消息类型是：" + MsgType);
			responseXml = WeiXinUtil.getResponseXml(fromUserName, toUserName, "本系统目前暂不受理文本以外的消息类型！");
			response.getWriter().write(responseXml);
			return;
		} else {
			node = root.element("CreateTime");
			String CreateTime = node.getTextTrim();
			node = root.element("Content");
			String Content = node.getTextTrim();
			node = root.element("MsgId");
			String MsgId = node.getTextTrim();
			logger.info("content=" + Content);
			// // 以#开头的是命令行
			if (Content.startsWith("#")) {
				logger.info("接收到指令消息..");

			} else {
				IWeiXinService iWeixinService = (IWeiXinService) ServerBeanFactory.getBean("");
				responseXml = iWeixinService.intercept(fromUserName, toUserName, Content);
				logger.info("responseXml==" + responseXml);
				response.getWriter().write(responseXml);
			}
		}
	}

	private boolean checkSignature(String signature, String timestamp, String nonce, String echostr) {
		logger.info("signature:" + signature);
		logger.info("timestamp" + timestamp);
		logger.info("nonce" + nonce);
		logger.info("echostr" + echostr);
		// 由于这里是所有公众号账号公众号的统一校验入口，但是校验服务器是同一个， 所以token是一样的、
		String arrtemp[] = { TOKEN, timestamp, nonce };
		Arrays.sort(arrtemp); // 排序
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arrtemp.length; i++) {
			sb.append(arrtemp[i]);
		}
		String pwd = WeixinMessageDigestUtil.getInstance().encipher(sb.toString());
		if (pwd != null && pwd.equals(signature)) {
			logger.info("校验成功!返回:" + echostr);
			return true;
		} else {
			logger.equals("校验失败！");
		}
		return false;
	}
}
