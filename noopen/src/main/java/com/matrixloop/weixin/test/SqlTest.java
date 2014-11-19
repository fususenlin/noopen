package com.matrixloop.weixin.test;

import java.util.Date;

import com.matrixloop.weixin.dao.TokenDao;
import com.matrixloop.weixin.pojo.AccessToken;
import com.matrixloop.weixin.util.WeixinUtil;

public class SqlTest {

	public static void main(String[] args) throws Exception {
		
		AccessToken at = WeixinUtil.getAccessToken("wx590826358ff577f1", "3b5b36bad40309e3128aa4cce32097f9");
		
		System.out.println("ok2");

	}

}
