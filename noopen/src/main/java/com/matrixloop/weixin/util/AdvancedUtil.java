package com.matrixloop.weixin.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.matrixloop.weixin.pojo.SNSUserInfo;
import com.matrixloop.weixin.pojo.WeixinOauth2Token;
import com.matrixloop.weixin.pojo.WeixinUserInfo;

/**
 * �߼��ӿڹ�����
 * 
 * @author liufeng
 * @date 2013-11-9
 */
public class AdvancedUtil {
	private static Logger log = LoggerFactory.getLogger(AdvancedUtil.class);

	public static boolean sendCustomMessage(String accessToken, String jsonMsg) {
		log.info("��Ϣ���ݣ�{}", jsonMsg);
		boolean result = false;
		// ƴ�������ַ
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		// ���Ϳͷ���Ϣ
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", jsonMsg);

		if (null != jsonObject) {
			int errorCode = jsonObject.getInt("errcode");
			String errorMsg = jsonObject.getString("errmsg");
			if (0 == errorCode) {
				result = true;
				log.info("�ͷ���Ϣ���ͳɹ� errcode:{} errmsg:{}", errorCode, errorMsg);
			} else {
				log.error("�ͷ���Ϣ����ʧ�� errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}

		return result;
	}
	
	static String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	/**
	 * ��ȡ��ҳ��Ȩƾ֤
	 * 
	 * @param appId �����˺ŵ�Ψһ��ʶ
	 * @param appSecret �����˺ŵ���Կ
	 * @param code
	 * @return WeixinAouth2Token
	 */
	public static WeixinOauth2Token getOauth2AccessToken(String appId, String appSecret, String code) {
		WeixinOauth2Token wat = null;
		// ƴ�������ַ
//		String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
		requestUrl = requestUrl.replace("APPID", appId);
		requestUrl = requestUrl.replace("SECRET", appSecret);
		requestUrl = requestUrl.replace("CODE", code);
		// ��ȡ��ҳ��Ȩƾ֤
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
		if (null != jsonObject) {
			try {
				wat = new WeixinOauth2Token();
				wat.setAccessToken(jsonObject.getString("access_token"));
				wat.setExpiresIn(jsonObject.getInt("expires_in"));
				wat.setRefreshToken(jsonObject.getString("refresh_token"));
				wat.setOpenId(jsonObject.getString("openid"));
				wat.setScope(jsonObject.getString("scope"));
			} catch (Exception e) {
				wat = null;
				int errorCode = jsonObject.getInt("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("��ȡ��ҳ��Ȩƾ֤ʧ�� errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		return wat;
	}

	/**
	 * ˢ����ҳ��Ȩƾ֤
	 * 
	 * @param appId �����˺ŵ�Ψһ��ʶ
	 * @param refreshToken
	 * @return WeixinAouth2Token
	 */
	public static WeixinOauth2Token refreshOauth2AccessToken(String appId, String refreshToken) {
		WeixinOauth2Token wat = null;
		// ƴ�������ַ
		String requestUrl = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
		requestUrl = requestUrl.replace("APPID", appId);
		requestUrl = requestUrl.replace("REFRESH_TOKEN", refreshToken);
		// ˢ����ҳ��Ȩƾ֤
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
		if (null != jsonObject) {
			try {
				wat = new WeixinOauth2Token();
				wat.setAccessToken(jsonObject.getString("access_token"));
				wat.setExpiresIn(jsonObject.getInt("expires_in"));
				wat.setRefreshToken(jsonObject.getString("refresh_token"));
				wat.setOpenId(jsonObject.getString("openid"));
				wat.setScope(jsonObject.getString("scope"));
			} catch (Exception e) {
				wat = null;
				int errorCode = jsonObject.getInt("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("ˢ����ҳ��Ȩƾ֤ʧ�� errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		return wat;
	}

	/**
	 * ͨ����ҳ��Ȩ��ȡ�û���Ϣ
	 * 
	 * @param accessToken ��ҳ��Ȩ�ӿڵ���ƾ֤
	 * @param openId �û���ʶ
	 * @return SNSUserInfo
	 */
	@SuppressWarnings( { "deprecation", "unchecked" })
	public static SNSUserInfo getSNSUserInfo(String accessToken, String openId) {
		SNSUserInfo snsUserInfo = null;
		// ƴ�������ַ
		String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
		// ͨ����ҳ��Ȩ��ȡ�û���Ϣ
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);

		if (null != jsonObject) {
			try {
				snsUserInfo = new SNSUserInfo();
				// �û��ı�ʶ
				snsUserInfo.setOpenId(jsonObject.getString("openid"));
				// �ǳ�
				snsUserInfo.setNickname(jsonObject.getString("nickname"));
				// �Ա�1�����ԣ�2��Ů�ԣ�0��δ֪��
				snsUserInfo.setSex(jsonObject.getInt("sex"));
				// �û����ڹ��
				snsUserInfo.setCountry(jsonObject.getString("country"));
				// �û�����ʡ��
				snsUserInfo.setProvince(jsonObject.getString("province"));
				// �û����ڳ���
				snsUserInfo.setCity(jsonObject.getString("city"));
				// �û�ͷ��
				snsUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
				// �û���Ȩ��Ϣ
				snsUserInfo.setPrivilegeList(JSONArray.toList(jsonObject.getJSONArray("privilege"), List.class));
			} catch (Exception e) {
				snsUserInfo = null;
				int errorCode = jsonObject.getInt("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("��ȡ�û���Ϣʧ�� errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		return snsUserInfo;
	}


	/**
	 * ��ȡ�û���Ϣ
	 * 
	 * @param accessToken �ӿڷ���ƾ֤
	 * @param openId �û���ʶ
	 * @return WeixinUserInfo
	 */
	public static WeixinUserInfo getUserInfo(String accessToken, String openId) {
		WeixinUserInfo weixinUserInfo = null;
		// ƴ�������ַ
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
		// ��ȡ�û���Ϣ
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);

		if (null != jsonObject) {
			try {
				weixinUserInfo = new WeixinUserInfo();
				// �û��ı�ʶ
				weixinUserInfo.setOpenid(jsonObject.getString("openid"));
				// ��ע״̬��1�ǹ�ע��0��δ��ע����δ��עʱ��ȡ����������Ϣ
				weixinUserInfo.setSubscribe(jsonObject.getInt("subscribe"));
				// �û���עʱ��
				weixinUserInfo.setSubscribeTime(jsonObject.getString("subscribe_time"));
				// �ǳ�
				weixinUserInfo.setNickname(jsonObject.getString("nickname"));
				// �û����Ա�1�����ԣ�2��Ů�ԣ�0��δ֪��
				weixinUserInfo.setSex(jsonObject.getInt("sex"));
				// �û����ڹ��
				weixinUserInfo.setCountry(jsonObject.getString("country"));
				// �û�����ʡ��
				weixinUserInfo.setProvice(jsonObject.getString("province")); 
				// �û����ڳ���
				weixinUserInfo.setCity(jsonObject.getString("city"));
				// �û������ԣ���������Ϊzh_CN
				weixinUserInfo.setLanguage(jsonObject.getString("language"));
				// �û�ͷ��
				weixinUserInfo.setHeadImageUrl(jsonObject.getString("headimgurl")); 
			} catch (Exception e) {
				if (0 == weixinUserInfo.getSubscribe()) {
					log.error("�û�{}��ȡ���ע", weixinUserInfo.getOpenid());
				} else {
					int errorCode = jsonObject.getInt("errcode");
					String errorMsg = jsonObject.getString("errmsg");
					log.error("��ȡ�û���Ϣʧ�� errcode:{} errmsg:{}", errorCode, errorMsg);
				}
			}
		}
		return weixinUserInfo;
	}

}
