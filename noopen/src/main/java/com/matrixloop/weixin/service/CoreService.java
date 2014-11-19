package com.matrixloop.weixin.service;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.matrixloop.weixin.dao.TokenDao;
import com.matrixloop.weixin.pojo.AccessToken;
import com.matrixloop.weixin.pojo.WeixinUserInfo;
import com.matrixloop.weixin.pojo.message.Article;
import com.matrixloop.weixin.pojo.message.LinkMessage;
import com.matrixloop.weixin.pojo.message.NewsMessage;
import com.matrixloop.weixin.pojo.message.TextMessage;
import com.matrixloop.weixin.test.CustomerMessageTest;
import com.matrixloop.weixin.util.CustomerMessageUtil;
import com.matrixloop.weixin.util.MessageUtil;
import com.matrixloop.weixin.util.WeixinUtil;

public class CoreService {
	
	
	
	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return
	 */
	public static String processRequest(HttpServletRequest request,HttpServletResponse response) {
		String respMessage = null;
		try {
			// 默认返回的文本消息内容
			String respContent = "请求处理异常，请稍候尝试！";
			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			System.out.println("requestMap:"+requestMap);
			// 消息类型
			String msgType = requestMap.get("MsgType");
			String fromUserName = requestMap.get("ToUserName");
			String toUserName = requestMap.get("FromUserName");
			System.out.println("from:"+toUserName+"==to:"+fromUserName);
			
			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				respContent = "您发送的是文本消息！";
				TextMessage textMessage = createTextMessage(fromUserName,toUserName,
						respContent);
				respMessage = MessageUtil.textMessageToXml(textMessage);
			}
			// 图片消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				respContent = "您发送的是图片消息！";
				TextMessage textMessage = createTextMessage(fromUserName,toUserName,
						respContent);
				respMessage = MessageUtil.textMessageToXml(textMessage);
			}
			// 地理位置消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				respContent = "您发送的是地理位置消息！";
				TextMessage textMessage = createTextMessage(fromUserName,toUserName,
						respContent);
				respMessage = MessageUtil.textMessageToXml(textMessage);
			}
			// 链接消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				respContent = "您发送的是链接消息！";
				TextMessage textMessage = createTextMessage(fromUserName,toUserName,
						respContent);
				respMessage = MessageUtil.textMessageToXml(textMessage);
			}
			// 音频消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				
				respContent = "您发送的是音频消息！";
				TextMessage textMessage = createTextMessage(fromUserName,toUserName,
						respContent);
				respMessage = MessageUtil.textMessageToXml(textMessage);
			}
			// 事件推送
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				// 订阅
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					respContent = "谢谢您的关注！";
					System.out.println("谢谢您的关注！");
					TextMessage textMessage = createTextMessage(fromUserName,toUserName,
							respContent);
					respMessage = MessageUtil.textMessageToXml(textMessage);
				}
				// 取消订阅
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
				}
				// 自定义菜单点击事件
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {

					// 事件KEY值，与创建自定义菜单时指定的KEY值对应
					String eventKey = requestMap.get("EventKey");

					if (eventKey.equals("11")) {
						
						
						
						
						NewsMessage newsMessage = createNewsMessage(fromUserName,toUserName);
						newsMessage.setArticleCount(1);
						
						AccessToken at = WeixinUtil.getAccessToken("wxc6e2e5d7fa3681cd", "89a3cfd333f9c3e11c446e8aa40d4031");
						
//						WeixinUserInfo weinxinUserInfo = WeixinUtil.getUserInfo(at.getToken(), newsMessage.getToUserName());
						
						/*Article article = new Article();
						article.setDescription("description info");
						article.setTitle("news test");
//						article.setUrl("http://carpweixin.duapp.com/?openid="
//								+ newsMessage.getToUserName()+"&nickname="+weinxinUserInfo.getNickname()+"&city="+weinxinUserInfo.getCity()+"&headImageUrl="+weinxinUserInfo.getHeadImageUrl());
						article.setUrl("http://carpweixin.duapp.com/?openid="+ newsMessage.getToUserName());
						ArrayList<Article> articles = new ArrayList<Article>();
						articles.add(article);
						newsMessage.setArticles(articles);

						respMessage = MessageUtil.newsMessageToXml(newsMessage);*/
						
						/*LinkMessage linkMessage = createLinkMessage(fromUserName,toUserName);
						linkMessage.setDescription("关注我");
						linkMessage.setTitle("关注");
						linkMessage.setUrl("weixin://contacts/profile/gh_8b3646b245e8");
						respMessage = MessageUtil.linkMessageToXml(linkMessage);*/
						
						
						// 响应消息  
				        PrintWriter out = response.getWriter();  
				        out.print("");  
				        out.close(); 
						
						
						
						CustomerMessageTest.sendCustomerNewsMessage(CustomerMessageUtil.getCustomerNewsMessage(toUserName,at.getToken()), at.getToken());
					} else if (eventKey.equals("12")) {
						respContent = "12点击！";
						TextMessage textMessage = createTextMessage(fromUserName,toUserName,
								respContent);
						respMessage = MessageUtil.textMessageToXml(textMessage);
					} else if (eventKey.equals("13")) {
						System.out.println("13:");
						respContent = "填写车保时间被点击！";
						TextMessage textMessage = createTextMessage(fromUserName,toUserName,
								respContent);
						respMessage = MessageUtil.textMessageToXml(textMessage);
					} else if (eventKey.equals("21")) {
						respContent = "查看险种被点击！";
						TextMessage textMessage = createTextMessage(fromUserName,toUserName,
								respContent);
						respMessage = MessageUtil.textMessageToXml(textMessage);
					} else if (eventKey.equals("22")) {
						respContent = "查看报价被点击！";
						TextMessage textMessage = createTextMessage(fromUserName,toUserName,
								respContent);
						respMessage = MessageUtil.textMessageToXml(textMessage);
					} else if (eventKey.equals("23")) {
						respContent = "查看感兴趣报价被点击！";
						TextMessage textMessage = createTextMessage(fromUserName,toUserName,
								respContent);
						respMessage = MessageUtil.textMessageToXml(textMessage);
					} else if (eventKey.equals("24")) {
						respContent = "查看成交记录被点击！";
						TextMessage textMessage = createTextMessage(fromUserName,toUserName,
								respContent);
						respMessage = MessageUtil.textMessageToXml(textMessage);
					} else if (eventKey.equals("31")) {
//						TokenDao tokenDao = new TokenDao();
						AccessToken at = WeixinUtil.getAccessToken("wxc6e2e5d7fa3681cd", "89a3cfd333f9c3e11c446e8aa40d4031");
						System.out.println("yes!"+at.getToken());
						respContent = "测试click被点击！";
						TextMessage textMessage = createTextMessage(fromUserName,toUserName,
								respContent);
						respMessage = MessageUtil.textMessageToXml(textMessage);
					} else if (eventKey.equals("32")) {
						respContent = "32被点击！";
						TextMessage textMessage = createTextMessage(fromUserName,toUserName,
								respContent);
						respMessage = MessageUtil.textMessageToXml(textMessage);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return respMessage;
	}
	
	
	public static LinkMessage createLinkMessage(String fromUserName,String toUserName) throws Exception {
		LinkMessage linkMessage = new LinkMessage();
		linkMessage.setFromUserName(fromUserName);
		linkMessage.setToUserName(toUserName);
		linkMessage.setCreateTime(new Date().getTime());
		linkMessage.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_LINK);
		linkMessage.setFuncFlag(0);
		return linkMessage;
	}
	
	public static TextMessage createTextMessage(String fromUserName,String toUserName,
			String content) throws Exception {
		TextMessage textMessage = new TextMessage();
		textMessage.setToUserName(toUserName);
		textMessage.setFromUserName(fromUserName);
		textMessage.setCreateTime(new Date().getTime());
		textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
		textMessage.setFuncFlag(0);
		textMessage.setContent(content);
		return textMessage;
	}

	public static NewsMessage createNewsMessage(String fromUserName,String toUserName)
			throws Exception {
		
		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setFromUserName(fromUserName);
		newsMessage.setToUserName(toUserName);
		newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		newsMessage.setCreateTime(new Date().getTime());
		return newsMessage;
	}
}
