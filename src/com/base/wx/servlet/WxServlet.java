/**
 * 
 */
package com.base.wx.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.base.ServerBeanFactory;
import com.base.log.LogUtil;
import com.base.util.WeixinMessageDigestUtil;
import com.base.wx.service.def.IWeiXinService;
import com.base.wx.service.def.WeiXinListener;

/**
 * @author tao
 *
 */
public class WxServlet extends HttpServlet {
	private static final long serialVersionUID = 2148447682092966772L;
	private static final String TOKEN = "Lvze2015";
	private static final Logger logger = LogUtil.getLogger(WxServlet.class.getName());

	private IWeiXinService iWeixinService;

	@Override
	public void init() throws ServletException {
		super.init();
		iWeixinService = (IWeiXinService) ServerBeanFactory.getBean("weixinService");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		// String xml =
		//<xml>
		//	<ToUserName><![CDATA[gh_649e8c686a80]]></ToUserName>
		//	<FromUserName><![CDATA[o1PuujpRyno87Ja2YaPWCBpRbE0c]]></FromUserName>
		//	<CreateTime>1376371547</CreateTime>
		//	<MsgType><![CDATA[text]]></MsgType>
		//	<Content><![CDATA[哈哈哈哈哈]]></Content>
		//	<MsgId>5911470781509926936</MsgId>
		//</xml>";
		// Document msgdoc = DocumentHelper.parseText(xml);
		try {
			Map<String, String> map = parseXml(request);
			List<WeiXinListener> weiXinListeners = iWeixinService.getAllWeiXinListeners();
			for (WeiXinListener weiXinListener : weiXinListeners) {
				try {
					if (weiXinListener.isValid(map)) {
						weiXinListener.onMessage(map,response);
					}
				} catch (Exception ex) {
					logger.error("", ex);
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String signature = request.getParameter("signature");// 微信加密签名signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
		String timestamp = request.getParameter("timestamp");// 时间戳
		String nonce = request.getParameter("nonce");// 随机数
		String echostr = request.getParameter("echostr");// 随机字符串
		if (StringUtils.isNotEmpty(echostr) && checkSignature(signature, timestamp, nonce, echostr)) {// toke验证
			response.getWriter().write(echostr);
			return;
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

	private static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
		// 将解析结果存储在HashMap中
		Map<String, String> map = new HashMap<String, String>();
		// 从request中取得输入流
		InputStream inputStream = request.getInputStream();
		try {
			// 读取输入流
			SAXReader reader = new SAXReader();
			Document document = reader.read(inputStream);
			// 得到xml根元素
			Element root = document.getRootElement();
			// 得到根元素的所有子节点
			List<Element> elementList = root.elements();
			// 遍历所有子节点
			for (Element e : elementList) {
				map.put(e.getName(), e.getText());
			}
			logger.info(map);
		} finally {
			inputStream.close();
			inputStream = null;
		}

		return map;
	}
}
