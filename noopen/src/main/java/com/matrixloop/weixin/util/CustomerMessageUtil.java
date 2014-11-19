package com.matrixloop.weixin.util;

import net.sf.json.JSONObject;

import com.matrixloop.weixin.pojo.WeixinUserInfo;
import com.matrixloop.weixin.pojo.customerMessage.CustomerArticle;
import com.matrixloop.weixin.pojo.customerMessage.CustomerArticles;
import com.matrixloop.weixin.pojo.customerMessage.CustomerNewsMessage;
import com.matrixloop.weixin.pojo.menu.Button;
import com.matrixloop.weixin.pojo.menu.CommonButton;
import com.matrixloop.weixin.pojo.menu.ComplexButton;
import com.matrixloop.weixin.pojo.menu.Menu;
import com.matrixloop.weixin.pojo.menu.ViewButton;

public class CustomerMessageUtil {
	

	
	public static CustomerNewsMessage getCustomerNewsMessage(String openid,String accesstoken) { 
		WeixinUserInfo weinxinUserInfo = WeixinUtil.getUserInfo(accesstoken, openid);
		
        CustomerArticle customerArticle = new CustomerArticle();
        customerArticle.setDescription("customerMesssage info");
        customerArticle.setTitle("customer title");
        customerArticle.setUrl("http://carpweixin.duapp.com/?openid="
				+ openid+"&nickname="+weinxinUserInfo.getNickname()+"&city="+weinxinUserInfo.getCity()+"&headImageUrl="+weinxinUserInfo.getHeadImageUrl());
//        customerArticle.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc6e2e5d7fa3681cd&redirect_uri=http%3A%2F%2Fcarpweixin.duapp.com%2FOauthServlet&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");
        CustomerArticles customerArticles =new CustomerArticles();
        customerArticles.setArticles(new CustomerArticle[]{customerArticle});
        
        CustomerNewsMessage customerNewsMessage = new CustomerNewsMessage();
        customerNewsMessage.setMsgtype("news");
//        customerNewsMessage.setTouser("oHedIt3pUEHtRSa47RUVPwJMVJHU");
        customerNewsMessage.setTouser(openid);
        customerNewsMessage.setNews(customerArticles);
  
        return customerNewsMessage;  
    } 
}
