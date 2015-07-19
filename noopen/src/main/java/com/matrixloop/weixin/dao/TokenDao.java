package com.matrixloop.weixin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.matrixloop.weixin.pojo.AccessToken;
import com.matrixloop.weixin.util.MySqlDBUtil;
import com.matrixloop.weixin.util.WeixinUtil;


public class TokenDao {
	private Connection conn;
	static SimpleDateFormat dateFormate = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public TokenDao() throws Exception {
		conn = MySqlDBUtil.getConnection();
		Ssssss
	}
	
	public void insert(AccessToken at) throws Exception{
		PreparedStatement ps = null;
		ps = conn.prepareStatement("insert into token_tb(value,expirestime) values(?)");
		ps.setString(1, at.getToken());
		ps.executeUpdate();
		closeResource(null, ps);
	}
	
	public AccessToken get() throws Exception{
		AccessToken accessToken = null;
		
		Date now = new Date();
		
		
		PreparedStatement ps = null;
		ps = conn.prepareStatement("select * from  token_tb where id=1");
		ResultSet resultSet = ps.executeQuery();
		if(resultSet.first()){
			System.out.println(resultSet.getTimestamp("expirestime").getTime());
			System.out.println(now.getTime());
//			if(dateFormate.parse(dateFormate.format(resultSet.getTimestamp("expirestime"))).getTime()>now.getTime()){
				accessToken = new AccessToken();
				accessToken.setToken(resultSet.getString("value"));
				accessToken.setExpiresTime(resultSet.getTimestamp("expiresTime"));
			/*}else{
				System.out.println("over time!1");
		        String appId = "wxc6e2e5d7fa3681cd";  
		        String appSecret = "89a3cfd333f9c3e11c446e8aa40d4031"; 
		        if(null!=conn){
					conn.close();
					closeResource(resultSet, ps);
				}
//		        accessToken = WeixinUtil.getAccessToken(appId, appSecret);
		        accessToken = new AccessToken();
		        accessToken.setToken("nice");
		        update(accessToken.getToken(),now);
		        
			}*/
		}else{
			System.out.println("数据不存在！");
		}
			closeResource(resultSet, ps);
		
		return accessToken;
	}
	
	public void update(String value,Date date) throws Exception{
		
		PreparedStatement nps = null;
		nps = conn.prepareStatement("update token_tb set value=?,expirestime=? where id=1");
		
		nps.setString(1, value);
		nps.setObject(2, dateFormate.format(date));
		nps.executeUpdate();
		System.out.println("update!");
		closeResource(null, nps);
	}
	
	public AccessToken toAccessToken(ResultSet resultSet) throws SQLException {
		AccessToken accessToken = null;
		if (resultSet.next()) {
			accessToken = new AccessToken();
			accessToken.setToken(resultSet.getString("value"));
			System.out.println("tokenValue:"+resultSet.getString("value"));
		}
		return accessToken;
	}
	
	public void closeResource(ResultSet rs, PreparedStatement ps) throws SQLException {
		if (null != rs) {
			rs.close();
		}
		if (null != ps) {
			ps.close();
		}
	}
	
}
