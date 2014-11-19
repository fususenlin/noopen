package com.matrixloop.weixin.pojo.customerMessage;

import java.util.List;


public class CustomerNewsMessage extends BaseCustomerMessage{
    // 多条图文消息信息，默认第一个item为大图  
    private CustomerArticles news;

	public CustomerArticles getNews() {
		return news;
	}

	public void setNews(CustomerArticles news) {
		this.news = news;
	}







    
    
}
