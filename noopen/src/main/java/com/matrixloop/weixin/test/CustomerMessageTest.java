package com.matrixloop.weixin.test;

import net.sf.json.JSONObject;

import com.matrixloop.weixin.pojo.AccessToken;
import com.matrixloop.weixin.pojo.customerMessage.CustomerNewsMessage;
import com.matrixloop.weixin.util.CustomerMessageUtil;
import com.matrixloop.weixin.util.WeixinUtil;

public class CustomerMessageTest {

	public static void main(String[] args) {
		 AccessToken at = new AccessToken();
	        at.setToken("fvuqgFPqvH4wfhT381w67wVGLSW61xLuy4Isd45z6OujHt6BetOyuXEROKOeZ_YtNKkltuBxoWmKjg0wEcfphlJwR1fKT21AW4EtyINjDs8");
	        at.setExpiresIn(7200);
	        if (null != at) {  
	            int result = sendCustomerNewsMessage(CustomerMessageUtil.getCustomerNewsMessage("oHedIt3pUEHtRSa47RUVPwJMVJHU",at.getToken()), at.getToken());
	            
	  
	            if (0 == result)  {
	            	System.out.println("发送成功！");
	            }
	            	 
	            else  {
	            	System.out.println("发送失败"+result);
	            }
	            	  
	        }

	}
	
	
public final static String customerMessage_create_url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
	
	public static int sendCustomerNewsMessage(CustomerNewsMessage customerNewsMessage, String accessToken) {  
        int result = 0;  
      
        // 拼装创建菜单的url  
        String url = customerMessage_create_url.replace("ACCESS_TOKEN", accessToken);  
        // 将菜单对象转换成json字符串  
        String jsonCustomerNewsMessage = JSONObject.fromObject(customerNewsMessage).toString(); 
        System.out.println("jsonCustomerNewsMessage:"+jsonCustomerNewsMessage);
        // 调用接口创建菜单  
        JSONObject jsonObject = WeixinUtil.httpRequest(url, "POST", jsonCustomerNewsMessage);  
      
        if (null != jsonObject) {  
            if (0 != jsonObject.getInt("errcode")) {  
                result = jsonObject.getInt("errcode");  
                System.out.println("客服消息发送失败 errcode:{"+jsonObject.getInt("errcode")+"} errmsg:{"+jsonObject.getString("errmsg")+"}");
            }  
        }  
      
        return result;  
    }

}
