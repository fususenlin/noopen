package com.matrixloop.weixin.test;

import com.matrixloop.weixin.pojo.WeixinUserInfo;
import com.matrixloop.weixin.util.WeixinUtil;

public class UseInfoTest {

	public static void main(String[] args) {
		
		WeixinUserInfo weixinUserInfo = WeixinUtil.getUserInfo("zqVjhneAOosgn5w9w7EfTZh2lgWEnjpIg51O7JtlxPWAFnr-qv-2nNDL9pqS9A3hFpoOSDe8yoNNOEf9gLavPYow2db6wokud3Y2kO3UWP0", "oHedIt3JHJ7NgGO_5QFqrPmY8Dgc");
		System.out.println(weixinUserInfo.getNickname());

	}

}
