package com.matrixloop.weixin.test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;

public class CheShouYeApiTest {
	 public static String URL ="http://www.cheshouye.com";
	 
		/**  
		 * title:测试方法
		 * 
		 * @param args
		 * @throws Exception 
		 */
		public static void main(String[] args) throws Exception {
			/*String enChar = URLEncoder.encode("皖LF222J", "utf-8");
			System.out.println(enChar);
			String deChar = URLDecoder.decode(enChar,"utf-8");
			System.out.println(deChar);*/
			
			// 设置代理
						String proxy = "10.167.251.83";
						int port = 8080;
						System.setProperty("proxyPort", Integer.toString(port));
						System.setProperty("proxyHost", proxy);
						System.setProperty("proxySet", "true");
			
			// TODO Auto-generated method stub

			try {
				String carInfo = "{hphm=粤YM5610&classno=LBEPCCAK45X116238&engineno=G4GA-5B257630&city_id=658&car_type=02}";
//				String carInfo ="{hphm=皖LF222J&classno=LZWACAGA3A7234056&engineno=8AC0111367&city_id=681&car_type=02}";
//				String carInfo = "{hphm=皖LF222J&classno=LZWACAGA3A7234056&engineno=8AC0111367&city_id=681&car_type=02}";
				String appId="140";    //联系车首页获取
				String appKey="4e0060a1927d16fff17993fe603b0bd3";//联系车首页获取
				
				System.out.println("请稍后...");
				String sb = getWeizhangInfoPost(carInfo, appId, appKey);
				System.out.println("返回违章结果：" + sb);
				
				
				//获取省份城市字典配置
				System.out.println("附录一   获取省份城市参数接口参数="+getConfig());

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		/**
		 * title:获取违章信息
		 * 
		 * @param carInfo
		 * @return
		 */
		public static String getWeizhangInfoPost(String carInfo, String appId, String appKey) {
			long timestamp = System.currentTimeMillis();

			String line = null;
			String signStr = appId + carInfo + timestamp + appKey;
			String sign = md5(signStr);
			try {
				URL postUrl = new URL(URL + "/api/weizhang/query_task?");
				String content = "car_info=" + URLEncoder.encode(carInfo, "utf-8") + "&sign=" + sign + "&timestamp=" + timestamp + "&app_id=" + appId;
			
				System.out.println("请求URL="+postUrl+content);
				
				line = post(postUrl, content);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return line;
		}

		/**
		 * title:获取省份城市对应ID配置
		 * 
		 * @return
		 * @throws IOException
		 */
		public static String getConfig() throws IOException {
			String line = null;
			try {
				URL postUrl = new URL(URL + "/api/weizhang/get_all_config?");
				String content = "";
				line = post(postUrl, content);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return line;

		}

		/**
		 * title:post请求
		 * 
		 * @param postUrl
		 * @param content
		 * @return
		 */
		private static String post(URL postUrl, String content) {
			String line = null;
			try {
				HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
				connection.setDoOutput(true);
				connection.setDoInput(true);
				connection.setRequestMethod("POST");
				connection.setUseCaches(false);
				connection.setInstanceFollowRedirects(true);
				connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				connection.connect();
				DataOutputStream out = new DataOutputStream(connection.getOutputStream());
				out.writeBytes(content);
				out.flush();
				out.close();
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));

				while ((line = reader.readLine()) != null) {
					return line;
				}
				reader.close();
				connection.disconnect();

			} catch (Exception e) {
				e.printStackTrace();
			}
			return line;

		}

		/**
		 * title:md5加密,应与 (http://tool.chinaz.com/Tools/MD5.aspx) 上32加密结果一致
		 * 
		 * @param password
		 * @return
		 */
		private static String md5(String msg) {
			try {
				MessageDigest instance = MessageDigest.getInstance("MD5");
				instance.update(msg.getBytes("UTF-8"));
				byte[] md = instance.digest();
				return byteArrayToHex(md);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		private static String byteArrayToHex(byte[] a) {
			StringBuilder sb = new StringBuilder();
			for (byte b : a) {
				sb.append(String.format("%02x", b & 0xff));
			}
			return sb.toString();
		}
}
