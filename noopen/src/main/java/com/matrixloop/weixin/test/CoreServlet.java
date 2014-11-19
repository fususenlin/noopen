package com.matrixloop.weixin.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.matrixloop.weixin.pojo.AccessToken;
import com.matrixloop.weixin.service.CoreService;
import com.matrixloop.weixin.util.SignUtil;

/**
 * Servlet implementation class CoreServlet
 */
public class CoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static AccessToken accessToken = null;
    /**
     * Default constructor. 
     */
    public CoreServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 微信加密签名  
        String signature = request.getParameter("signature");  
        // 时间戮  
        String timestamp = request.getParameter("timestamp");  
        // 随机数  
        String nonce = request.getParameter("nonce");  
        // 随机字符串  
        String echostr = request.getParameter("echostr");   
          
        PrintWriter out = response.getWriter();  
        // 通过检验 signature 对请求进行校验，若校验成功则原样返回 echostr，表示接入成功，否则接入失败  
       if(SignUtil.checkSignature(signature, timestamp, nonce)){  
           out.print(echostr);  
       }  
		
		 
       out.close();  
       out = null; 
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 将请求、响应的编码均设置为UTF-8（防止中文乱码）  
        request.setCharacterEncoding("UTF-8");  
        response.setCharacterEncoding("UTF-8");  
  
        // 调用核心业务类接收消息、处理消息  
        String respMessage = CoreService.processRequest(request,response);  
          
        // 响应消息  
        PrintWriter out = response.getWriter();  
        out.print(respMessage);  
        out.close(); 
	}

}
