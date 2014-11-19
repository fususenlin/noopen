package com.matrixloop.weixin.test;

import com.matrixloop.weixin.dao.TokenDao;
import com.matrixloop.weixin.pojo.AccessToken;
import com.matrixloop.weixin.util.WeixinUtil;

public class TokenTest {

	public static void main(String[] args) throws Exception {
		TokenDao tokenDao = new TokenDao();
		AccessToken at = tokenDao.get();
		System.out.println("yes!"+at.getToken());

	}

}
