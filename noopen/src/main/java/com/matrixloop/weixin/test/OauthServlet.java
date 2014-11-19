package com.matrixloop.weixin.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.matrixloop.weixin.pojo.SNSUserInfo;
import com.matrixloop.weixin.pojo.WeixinOauth2Token;
import com.matrixloop.weixin.util.WeixinOauth2Util;

import net.sf.json.JSONObject;


/**
 * Servlet implementation class OauthServlet
 */
public class OauthServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	static String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OauthServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		String code = request.getParameter("code");
		System.out.println("code"+code);
		
		
		// 响应消息  
        PrintWriter out = response.getWriter();  
        out.print("");  
        out.close();
		
		if(!"authdeny".equals(code)){
			System.out.println("authdeny");
			WeixinOauth2Token weixinOauth2Token = WeixinOauth2Util.getOauth2AccessToken("wxc6e2e5d7fa3681cd", "89a3cfd333f9c3e11c446e8aa40d4031", code);
			System.out.println("oauthToken:"+weixinOauth2Token.getAccessToken());
			String accessToken = weixinOauth2Token.getAccessToken();
			String openId = weixinOauth2Token.getOpenId();
			SNSUserInfo snsUserInfo = WeixinOauth2Util.getSNSUserInfo(accessToken, openId);
			
			request.setAttribute("snsUserInfo", snsUserInfo);
			System.out.println("snsUserInfo:"+snsUserInfo);
			
			/*WeixinOauth2Token wat = null;
			
			
//			String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
			requestUrl = requestUrl.replace("APPID", "wxc6e2e5d7fa3681cd");
			requestUrl = requestUrl.replace("SECRET", "89a3cfd333f9c3e11c446e8aa40d4031");
			requestUrl = requestUrl.replace("CODE", code);
			JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl, "GET", null);
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
				}
			}
			System.out.println(wat.getAccessToken());*/
			
			
		}
		
		request.getRequestDispatcher("oauth.jsp").forward(request, response);
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
