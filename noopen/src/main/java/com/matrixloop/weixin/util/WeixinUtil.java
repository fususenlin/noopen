package com.matrixloop.weixin.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.matrixloop.weixin.dao.TokenDao;
import com.matrixloop.weixin.pojo.AccessToken;
import com.matrixloop.weixin.pojo.WeixinUserInfo;
import com.matrixloop.weixin.pojo.customerMessage.CustomerNewsMessage;
import com.matrixloop.weixin.pojo.menu.Menu;
import com.matrixloop.weixin.service.CoreService;
import com.matrixloop.weixin.test.CoreServlet;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class WeixinUtil {
	static SimpleDateFormat dateFormate = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpRequest(String requestUrl,
			String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			// 设置代理
			/*String proxy = "10.167.251.83";
			int port = 8080;
			System.setProperty("proxyPort", Integer.toString(port));
			System.setProperty("proxyHost", proxy);
			System.setProperty("proxySet", "true");*/
			

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url
					.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (ConnectException ce) {
			System.out.println("Weixin server connection timed out.");
		} catch (Exception e) {
			System.out.println("https request error:{" + e + "}");
		}

		System.out.println(jsonObject);
		return jsonObject;

	}

	// 获取access_token的接口地址（GET） 限200（次/天）
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

	/**
	 * 获取access_token
	 * 
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密钥
	 * @return
	 * @throws Exception
	 */
	public static AccessToken getAccessToken(String appid, String appsecret)
			throws Exception {
		/*AccessToken accessToken = null;

		// 从数据库获取
		// TokenDao tokenDao = new TokenDao();
		// accessToken = tokenDao.get();
		// if(dateFormate.parse(dateFormate.format(accessToken.getExpiresTime())).getTime()>new
		// Date().getTime()){
		// 调用接口获取

		TokenDao tokenDao = new TokenDao();
		String requestUrl = access_token_url.replace("APPID", appid).replace(
				"APPSECRET", appsecret);
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				accessToken = new AccessToken();
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
				tokenDao.update(jsonObject.getString("access_token"),
						new Date());
			} catch (JSONException e) {
				accessToken = null;
				// 获取token失败
				System.out.println("获取token失败 errcode:{"
						+ jsonObject.getInt("errcode") + "} errmsg:{"
						+ jsonObject.getString("errmsg") + "}");

			}
		}
		// }

		return accessToken;*/
		System.out.println("static:"+CoreServlet.accessToken);
		if(CoreServlet.accessToken==null){
			String requestUrl = access_token_url.replace("APPID", appid).replace(
					"APPSECRET", appsecret);
			JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
			
			// 如果请求成功
			if (null != jsonObject) {
				try {
					CoreServlet.accessToken = new AccessToken();
					CoreServlet.accessToken.setToken(jsonObject.getString("access_token"));
					CoreServlet.accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
					CoreServlet.accessToken.setExpiresTime(new Date(new Date().getTime()+7200));
				} catch (JSONException e) {
					CoreServlet.accessToken = null;
					// 获取token失败
					System.out.println("获取token失败 errcode:{"
							+ jsonObject.getInt("errcode") + "} errmsg:{"
							+ jsonObject.getString("errmsg") + "}");

				}
			}
		}else if(dateFormate.parse(dateFormate.format(CoreServlet.accessToken.getExpiresTime())).getTime()>new Date().getTime()){
			System.out.println("expiresTime:"+dateFormate.parse(dateFormate.format(CoreServlet.accessToken.getExpiresTime())).getTime());
			System.out.println("now:"+new Date().getTime());
			String requestUrl = access_token_url.replace("APPID", appid).replace(
					"APPSECRET", appsecret);
			JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
			
			// 如果请求成功
			if (null != jsonObject) {
				try {
					CoreServlet.accessToken.setToken(jsonObject.getString("access_token"));
					CoreServlet.accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
					CoreServlet.accessToken.setExpiresTime(new Date(new Date().getTime()+7200));
				} catch (JSONException e) {
					CoreServlet.accessToken = null;
					// 获取token失败
					System.out.println("获取token失败 errcode:{"
							+ jsonObject.getInt("errcode") + "} errmsg:{"
							+ jsonObject.getString("errmsg") + "}");

				}
			}
		}

		System.out.println("token:"+CoreServlet.accessToken);
		return CoreServlet.accessToken;
	}
	
	
	/*public static AccessToken getAccessToken()
			throws Exception {
		if(CoreService.accessToken==null){
			TokenDao tokenDao = new TokenDao();
			String requestUrl = access_token_url.replace("APPID", appid).replace(
					"APPSECRET", appsecret);
			JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
			// 如果请求成功
			if (null != jsonObject) {
				try {
					accessToken = new AccessToken();
					accessToken.setToken(jsonObject.getString("access_token"));
					accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
					tokenDao.update(jsonObject.getString("access_token"),
							new Date());
				} catch (JSONException e) {
					accessToken = null;
					// 获取token失败
					System.out.println("获取token失败 errcode:{"
							+ jsonObject.getInt("errcode") + "} errmsg:{"
							+ jsonObject.getString("errmsg") + "}");

				}
			}
		}

		// 从数据库获取
		// TokenDao tokenDao = new TokenDao();
		// accessToken = tokenDao.get();
		// if(dateFormate.parse(dateFormate.format(accessToken.getExpiresTime())).getTime()>new
		// Date().getTime()){
		// 调用接口获取

		
		// }

		return accessToken;
	}*/

	public static WeixinUserInfo getUserInfo(String accessToken, String openid) {

		WeixinUserInfo weixinUserInfo = null;
		String userInfoUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
		userInfoUrl = userInfoUrl.replace("ACCESS_TOKEN", accessToken).replace(
				"OPENID", openid);
		JSONObject jsonObject = httpRequest(userInfoUrl, "GET", null);
		if (null != jsonObject) {
			try {
				weixinUserInfo = new WeixinUserInfo();
				weixinUserInfo.setCity(jsonObject.getString("city"));
				weixinUserInfo.setCountry(jsonObject.getString("country"));
				weixinUserInfo.setHeadImageUrl(jsonObject
						.getString("headimgurl"));
				weixinUserInfo.setLanguage(jsonObject.getString("language"));
				weixinUserInfo.setNickname(jsonObject.getString("nickname"));
				weixinUserInfo.setOpenid(jsonObject.getString("openid"));
				weixinUserInfo.setProvice(jsonObject.getString("province"));
				weixinUserInfo.setSex(jsonObject.getInt("sex"));
				weixinUserInfo.setSubscribe(jsonObject.getInt("subscribe"));
				weixinUserInfo.setSubscribeTime(jsonObject
						.getString("subscribe_time"));
			} catch (Exception e) {
				e.printStackTrace();
				if ("0".equals(weixinUserInfo.getSubscribe())) {
					System.out.println("用户" + weixinUserInfo.getOpenid()
							+ "未关注。");
				} else {
					int errorCode = jsonObject.getInt("errcode");
					String errorMessage = jsonObject.getString("errmsg");
					System.out.println("获取用户消息失败，errorcode:" + errorCode
							+ ",errmsg:" + errorMessage);
				}

			}
		}

		return weixinUserInfo;
	}
	
	
	
	

}
