package com.base.wx.action;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.base.action.BaseAction;
import com.base.log.LogUtil;
import com.base.util.WeiXinUtil;
import com.base.util.WeixinMessageDigestUtil;
import com.base.wx.constants.WeixinConstants;
import com.base.wx.service.def.IWeiXinService;
@Controller("WeixinAction")
@Scope("prototype")
public class WeiXinAction extends BaseAction {

	private static final long serialVersionUID = 4622027781423053486L;

	private static final Logger logger = LogUtil.getLogger(LogUtil.SERVER);

	private static final String TOKEN = "Lvze2015";
	@Autowired
	private IWeiXinService weiXinService;
	
	
	/**
	 * 客服标记map，用来记录某个用户当前是进行何种咨询
	 * key-openId
	 * value- 根据KF_K_PRODUCT获取的客服号
	 */
	public static Map<String, String> kf_flag_map = new ConcurrentHashMap<String, String>();
	
	private String url;

	/**
	 * 接入和转发 -------------------------------- 被添加：
	 * 初次进入需要登陆（即绑定），需要提供身份证号码进行绑定
	 * 一档绑定，下次再进入，只要检查数据库该openId已经绑定了身份证号码，就不用再登陆了。
	 * @returnorg.apache.coyote.ajp.AjpMessage
	 * @throws Exception
	 */
	public String verify() throws Exception {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		logger.info("进入微信消息处理方法...");
		response.setCharacterEncoding("utf-8");
		if (StringUtils.isNotEmpty(echostr)) {
			logger.info("  开始校验...");
			if (this.checkSignature(signature, timestamp, nonce, echostr)) {
				response.getWriter().write(echostr);
			}
			logger.info("结束校验");
		} else {
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
				return null;
			}
			// String xml =
			// "<xml><ToUserName><![CDATA[gh_649e8c686a80]]></ToUserName><FromUserName><![CDATA[o1PuujpRyno87Ja2YaPWCBpRbE0c]]></FromUserName><CreateTime>1376371547</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[哈哈哈哈哈]]></Content><MsgId>5911470781509926936</MsgId></xml>";
			// Document msgdoc = DocumentHelper.parseText(xml);
			Document msgdoc = DocumentHelper.parseText(sb.toString());
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
				// ----------->>>>>>>>>>>>>>>>>>>> 订阅消息自动回复 <<<<<<<<<<<<<<<<<<<<<<-------------
				if (WeixinConstants.MSG_SUBSCRIBE.equals(event)) {
					logger.info("新关注用户..." + fromUserName);
					//用户关注后反馈的msg
					String msg = "";
					if(eventkey.startsWith("qrscene_")){
						
					}else{
						
					}
					return null;
				}
				if (WeixinConstants.KF_CLOSE_SESSION.equals(event)) {
					
				}
				
				if(WeixinConstants.MSG_UNSUBSCRIBE.equals(event)){
					
				}

				if (event.equalsIgnoreCase("CLICK")) {
					node = root.element("EventKey");
					String key = node.getTextTrim();
					
					logger.info("key==" + key);
					if(key.equalsIgnoreCase("DKF_M")){
						
					}else{
						
					}
				}else if(event.equalsIgnoreCase("VIEW")){
					
				}
			} else if (StringUtils.isEmpty(MsgType) || !"text".equals(MsgType)) {
				logger.info("不支持的消息类型是：" + MsgType);
				responseXml = WeiXinUtil.getResponseXml(fromUserName, toUserName, "本系统目前暂不受理文本以外的消息类型！");
				response.getWriter().write(responseXml);
				return null;
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
					responseXml = this.weiXinService.intercept(fromUserName, toUserName, Content);
					logger.info("responseXml==" + responseXml);
					response.getWriter().write(responseXml);
				}
			}
		}
		return null;
	}


	/**
	 * 校验方法
	 * 
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param echostr
	 * @return
	 */
	private boolean checkSignature(String signature, String timestamp, String nonce, String echostr) {
		logger.info("signature:" + signature);
		logger.info("timestamp" + timestamp);
		logger.info("nonce" + nonce);
		logger.info("echostr" + echostr);
		//由于这里是所有公众号账号公众号的统一校验入口，但是校验服务器是同一个， 所以token是一样的、
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
	
	

	
	

	public static void main(String[] args) {
		String xml = "<xml><ToUserName><![CDATA[gh_649e8c686a80]]></ToUserName><FromUserName><![CDATA[o1PuujpRyno87Ja2YaPWCBpRbE0c]]></FromUserName><CreateTime>1376371547</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[哈哈哈哈哈]]></Content><MsgId>5911470781509926936</MsgId></xml>";
		 Document msgdoc;
		try {
			msgdoc = DocumentHelper.parseText(xml);
			Element root = msgdoc.getRootElement();
			Element node = root.element("MsgType");
			String MsgType = node.getTextTrim();
			node = root.element("ToUserName");// 开发者账号/公众号----------- 
			String toUserName = node.getTextTrim();
			node = root.element("FromUserName");// 发送者openId
			// 即：openId
			String fromUserName = node.getTextTrim();
			System.out.print(fromUserName);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		Document msgdoc = DocumentHelper.parseText(sb.toString());
	}

	public void setWeiXinService(IWeiXinService weiXinService) {
		this.weiXinService = weiXinService;
	}
	

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
