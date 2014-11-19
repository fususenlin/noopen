package com.matrixloop.weixin.util;

import com.matrixloop.weixin.pojo.SNSUserInfo;
import com.matrixloop.weixin.pojo.WeixinOauth2Token;

import net.sf.json.JSONObject;


public class WeixinOauth2Util {
	private static String code = null;
	public static WeixinOauth2Token getOauth2AccessToken(String appId,
			String appSecret, String code) {
		WeixinOauth2Token woToken = null;
		
		if(WeixinOauth2Util.code==null||!WeixinOauth2Util.code.equals(code)){
			WeixinOauth2Util.code = code;
			
			String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
			requestUrl = requestUrl.replace("APPID", appId);
			requestUrl = requestUrl.replace("SECRET", appSecret);
			requestUrl = requestUrl.replace("CODE", code);

			JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl, "GET", null);
			if (null != jsonObject) {
				try {
					woToken = new WeixinOauth2Token();
					woToken.setAccessToken(jsonObject.getString("access_token"));
					woToken.setExpiresIn(jsonObject.getInt("expires_in"));
					woToken.setOpenId(jsonObject.getString("openid"));
					woToken.setRefreshToken(jsonObject.getString("refresh_token"));
					woToken.setScope(jsonObject.getString("scope"));
				} catch (Exception e) {
					woToken = null;
					int errorCode = jsonObject.getInt("errcode");
					String errorMsg = jsonObject.getString("errmsg");
					System.out.println("获取授权凭证失败errorcode:" + errorCode
							+ ",errmsg:" + errorMsg);
				}
			}
		}
		

		return woToken;
	}

	public static WeixinOauth2Token refreshOauth2AccessToken(String appId,
			String refreshToken) {
		WeixinOauth2Token woToken = null;

		String requestUrl = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
		requestUrl = requestUrl.replace("APPID", appId);
		requestUrl = requestUrl.replace("REFRESH_TOKEN", refreshToken);

		JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl, "GET", null);
		if (null != jsonObject) {
			try {
				woToken = new WeixinOauth2Token();
				woToken.setAccessToken(jsonObject.getString("access_token"));
				woToken.setExpiresIn(jsonObject.getInt("expires_in"));
				woToken.setOpenId(jsonObject.getString("openid"));
				woToken.setRefreshToken(jsonObject.getString("refresh_token"));
				woToken.setScope(jsonObject.getString("scope"));
			} catch (Exception e) {
				woToken = null;
				int errorCode = jsonObject.getInt("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				System.out.println("刷新授权凭证失败errorcode:" + errorCode
						+ ",errmsg:" + errorMsg);
			}
		}

		return woToken;
	}

	public static SNSUserInfo getSNSUserInfo(String accessToken, String openId) {
		SNSUserInfo snsUserInfo = null;

		String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		requestUrl = requestUrl.replace("OPENID", openId);

		JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl, "GET", null);
		if (null != jsonObject) {
			try {
				snsUserInfo = new SNSUserInfo();
				snsUserInfo.setCity(jsonObject.getString("city"));
				snsUserInfo.setCountry(jsonObject.getString("country"));
				snsUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
				snsUserInfo.setNickname(jsonObject.getString("nickname"));
				snsUserInfo.setOpenId(jsonObject.getString("openid"));
				snsUserInfo.setPrivilegeList(null);
				snsUserInfo.setProvince(jsonObject.getString("province"));
				snsUserInfo.setSex(jsonObject.getInt("sex"));
			} catch (Exception e) {
				snsUserInfo = null;
				int errorCode = jsonObject.getInt("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				System.out.println("获取用户信息失败errorcode:" + errorCode
						+ ",errmsg:" + errorMsg);
			}

		}
		return snsUserInfo;
	}
}
