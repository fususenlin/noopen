package com.matrixloop.weixin.util;

import net.sf.json.JSONObject;

import com.matrixloop.weixin.pojo.menu.Button;
import com.matrixloop.weixin.pojo.menu.CommonButton;
import com.matrixloop.weixin.pojo.menu.ComplexButton;
import com.matrixloop.weixin.pojo.menu.Menu;
import com.matrixloop.weixin.pojo.menu.ViewButton;

public class MenuUtil {
	
	// 菜单创建（POST） 限100（次/天）  
    public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";  
    // 菜单删除（GET） 限100（次/天）
    public static String menu_delete_url ="https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
    /** 
     * 创建菜单 
     *  
     * @param menu 菜单实例 
     * @param accessToken 有效的access_token 
     * @return 0表示成功，其他值表示失败 
     */  
    public static int createMenu(Menu menu, String accessToken) {  
        int result = 0;  
      
        // 拼装创建菜单的url  
        String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);  
        // 将菜单对象转换成json字符串  
        String jsonMenu = JSONObject.fromObject(menu).toString();  
        // 调用接口创建菜单  
        JSONObject jsonObject = WeixinUtil.httpRequest(url, "POST", jsonMenu);  
      
        if (null != jsonObject) {  
            if (0 != jsonObject.getInt("errcode")) {  
                result = jsonObject.getInt("errcode");  
                System.out.println("创建菜单失败 errcode:{"+jsonObject.getInt("errcode")+"} errmsg:{"+jsonObject.getString("errmsg")+"}");
            }  
        }  
      
        return result;  
    } 
    
    /** 
     * 删除菜单 
     *  
     * @param accessToken 有效的access_token 
     * @return 0表示成功，其他值表示失败 
     */ 
    public static int deleteMenu(String accessToken) {  
        int result = 0;  
      
        // 拼装创建菜单的url  
        String url = menu_delete_url.replace("ACCESS_TOKEN", accessToken);  
        // 调用接口创建菜单  
        JSONObject jsonObject = WeixinUtil.httpRequest(url, "GET", null);  
      
        if (null != jsonObject) {  
            if (0 != jsonObject.getInt("errcode")) {  
                result = jsonObject.getInt("errcode");  
                System.out.println("删除菜单失败 errcode:{"+jsonObject.getInt("errcode")+"} errmsg:{"+jsonObject.getString("errmsg")+"}");
            }  
        }  
      
        return result;  
    } 
    
	/** 
     * 组装菜单数据 
     *  
     * @return 
     */  
    public static Menu getMenu() {  
        CommonButton btn11 = new CommonButton();  
        btn11.setName("个人信息");  
        btn11.setType("click");  
        btn11.setKey("11");  
  
        ViewButton btn12 = new ViewButton();  
        btn12.setName("绑定账号");  
        btn12.setType("view");  
        btn12.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc6e2e5d7fa3681cd&redirect_uri=http%3A%2F%2Fcarpweixin.duapp.com%2FOauthServlet&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");  
  
        CommonButton btn13 = new CommonButton();  
        btn13.setName("填写车保时间");  
        btn13.setType("click");  
        btn13.setKey("13");
        
        
  
  
        CommonButton btn21 = new CommonButton();  
        btn21.setName("查看险种");  
        btn21.setType("click");  
        btn21.setKey("21");  
  
        CommonButton btn22 = new CommonButton();  
        btn22.setName("查看报价");  
        btn22.setType("click");  
        btn22.setKey("22");  
  
        CommonButton btn23 = new CommonButton();  
        btn23.setName("查看感兴趣报价");  
        btn23.setType("click");  
        btn23.setKey("23");  
  
        CommonButton btn24 = new CommonButton();  
        btn24.setName("查看成交记录");  
        btn24.setType("click");  
        btn24.setKey("24");  
  
  
        CommonButton btn31 = new CommonButton();  
        btn31.setName("测试click");  
        btn31.setType("click");  
        btn31.setKey("31");  
  
        ViewButton btn32 = new ViewButton();  
        btn32.setName("测试view");  
        btn32.setType("view");  
        btn32.setUrl("http://www.baidu.com/");
  
  
        ComplexButton mainBtn1 = new ComplexButton();  
        mainBtn1.setName("关于我");  
        mainBtn1.setSub_button(new Button[] { btn11, btn12, btn13 });  
  
        ComplexButton mainBtn2 = new ComplexButton();  
        mainBtn2.setName("车险业务");  
        mainBtn2.setSub_button(new Button[] { btn21, btn22, btn23, btn24 });  
  
        ComplexButton mainBtn3 = new ComplexButton();  
        mainBtn3.setName("carp测试");  
        mainBtn3.setSub_button(new Button[] { btn31, btn32 }); 
        
  
        Menu menu = new Menu();  
        menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });  
  
        return menu;  
    } 
}
