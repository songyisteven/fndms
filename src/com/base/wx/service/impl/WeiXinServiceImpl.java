package com.base.wx.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.log.LogUtil;
import com.base.util.WeiXinUtil;
import com.base.wx.dao.WxRuleDao;
import com.base.wx.model.WxRules;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("weixinService")
public class WeiXinServiceImpl implements com.base.wx.service.def.IWeiXinService {

	private Logger logger = LogUtil.getLogger(LogUtil.SERVER);

	@Autowired
	private WxRuleDao wxRuleDao;

	public void setWxRuleDao(WxRuleDao wxRuleDao) {
		this.wxRuleDao = wxRuleDao;
	}

	@Override
	public String intercept(String toUserName, String fromUserName, String content) {
		List<WxRules> allEntities = wxRuleDao.getAllEntities();
		boolean isReturn = false;
		logger.info("关键字规则：" + allEntities.size());
		WxRules fitRule = new WxRules();
		for (WxRules wxRules : allEntities) {
			logger.info("关键字---" + wxRules.getKw());
			String[] split = StringUtils.split(wxRules.getKw(), "|");
			// 判断是否需要响应
			for (String keyword : split) {
				if (content.equalsIgnoreCase(keyword.trim())) {// 包含某个关键字
					logger.info(content + "-包含关键字.....:" + keyword);
					isReturn = true;
					break;
				}
			}
			if (isReturn) {
				fitRule.setRespType(wxRules.getRespType());
				fitRule.setTitle(wxRules.getTitle());
				fitRule.setTwdesc(wxRules.getTwdesc());
				fitRule.setPicUrl(wxRules.getPicUrl());
				fitRule.setRespContent(wxRules.getRespContent());
				break;
			}
		}
		// 通过上述的遍历，已经找到反馈规则
		if (isReturn) {
			// 判断回复类型
			if (fitRule.getRespType().equals(WxRules.NEWS)) {// 图文
				logger.info("需要响应.....图文");
				logger.info("title=" + fitRule.getTitle());
				logger.info("desc=" + fitRule.getTwdesc());
				logger.info("picUrl=" + fitRule.getPicUrl());
				logger.info("url=" + fitRule.getRespContent());
				JSONObject fromObject = JSONObject.fromObject(fitRule.getRespContent());
				List<Map<String, String>> picTexts = new ArrayList<Map<String, String>>();
				Map<String, String> m1 = new HashMap<String, String>();
				// Version.getInstance().getNewProperty("wx_cmdList")
				m1.put("title", fitRule.getTitle());
				m1.put("desc", fitRule.getTwdesc());
				m1.put("picUrl", fitRule.getPicUrl());
				m1.put("url", fromObject.getString("twUrl"));
				picTexts.add(m1);
				return WeiXinUtil.getResponsePicTextXml(toUserName, fromUserName, picTexts);
			} else if (fitRule.getRespType().equals(WxRules.MULTI)) {// 多图文
				logger.info("需要响应.....多图文");
				JSONArray fromObject = JSONArray.fromObject(fitRule.getRespContent());
				Object[] array = fromObject.toArray();
				List<Map<String, String>> picTexts = new ArrayList<Map<String, String>>();
				for (Object object : array) {
					JSONObject jo = (JSONObject) object;
					Map<String, String> m1 = new HashMap<String, String>();
					// Version.getInstance().getNewProperty("wx_cmdList")
					m1.put("title", jo.getString("title"));
					m1.put("desc", jo.getString("twdesc"));
					m1.put("picUrl", jo.getString("picUrl"));
					m1.put("url", jo.getString("twUrl"));
					picTexts.add(m1);
				}
				return WeiXinUtil.getResponsePicTextXml(toUserName, fromUserName, picTexts);
			} else if (fitRule.getRespType().equals(WxRules.TEXT)) {// 文本消息
				logger.info("需要响应.....文本");
				return WeiXinUtil.getResponseXml(toUserName, fromUserName, fitRule.getRespContent());
			}
		} else {
			// 自动回复
			// logger.info("普通的自动回复....." + content);
			// return WeiXinUtil.getResponseXml(toUserName, fromUserName, Version.getInstance().getNewProperty("welcome"));
		}
		return "";
	}

}
