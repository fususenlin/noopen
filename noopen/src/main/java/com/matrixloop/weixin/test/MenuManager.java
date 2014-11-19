package com.matrixloop.weixin.test;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.matrixloop.weixin.pojo.AccessToken;
import com.matrixloop.weixin.pojo.menu.Button;
import com.matrixloop.weixin.pojo.menu.CommonButton;
import com.matrixloop.weixin.pojo.menu.ComplexButton;
import com.matrixloop.weixin.pojo.menu.Menu;
import com.matrixloop.weixin.pojo.menu.ViewButton;
import com.matrixloop.weixin.util.MenuUtil;
import com.matrixloop.weixin.util.WeixinUtil;

public class MenuManager {
	private static Logger log = LoggerFactory.getLogger(MenuManager.class);  
	  
    public static void main(String[] args) throws Exception {  
        // 第三方用户唯一凭证  
        String appId = "wxc6e2e5d7fa3681cd";  
        // 第三方用户唯一凭证密钥  
        String appSecret = "89a3cfd333f9c3e11c446e8aa40d4031";  
  
        // 调用接口获取access_token  
//        AccessToken at = WeixinUtil.getAccessToken(appId, appSecret); 
        AccessToken at = new AccessToken();
        at.setToken("fvuqgFPqvH4wfhT381w67wVGLSW61xLuy4Isd45z6OujHt6BetOyuXEROKOeZ_YtNKkltuBxoWmKjg0wEcfphlJwR1fKT21AW4EtyINjDs8");
        at.setExpiresIn(7200);
        if (null != at) {  
            // 调用接口创建菜单  
            int result = MenuUtil.createMenu(MenuUtil.getMenu(), at.getToken());
            
         // 调用接口创建菜单  
//            int result = MenuUtil.deleteMenu(at.getToken());
  
            // 判断菜单创建结果  
            if (0 == result)  {
            	System.out.println("菜单创建成功！");
            	log.info("菜单创建成功！"); 
            }
            	 
            else  {
            	System.out.println("菜单创建失败"+result);
                log.info("菜单创建失败，错误码：" + result);
            }
            	  
        } 
    	
    	
    }
    
    
    public static String urlEncodeUTF8(String source) throws Exception{
    	String result = source;
    	result = java.net.URLEncoder.encode(source, "utf-8");
    	return result;
    }
  
    
}
