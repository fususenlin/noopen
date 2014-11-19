package com.matrixloop.weixin.pojo;

import java.util.Date;

public class AccessToken {
	 // 获取到的凭证  
    private String token;  
    // 凭证有效时间，单位：秒  
    private int expiresIn; 
    
    private Date expiresTime;
    
    
    public Date getExpiresTime() {
		return expiresTime;
	}

	public void setExpiresTime(Date expiresTime) {
		this.expiresTime = expiresTime;
	}

	public String getToken() {  
        return token;  
    }  
  
    public void setToken(String token) {  
        this.token = token;  
    }  
  
    public int getExpiresIn() {  
        return expiresIn;  
    }  
  
    public void setExpiresIn(int expiresIn) {  
        this.expiresIn = expiresIn;  
    }  
}
