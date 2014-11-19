package com.matrixloop.weixin.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlDBUtil {
	private static Connection conn;
	public static Connection connect() throws ClassNotFoundException, SQLException {
		if (conn == null) {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://sqld.duapp.com:4050/rzUowhpAxCAvlnfNPBqx", "N1iOGk7wiI5mecGukBwtwP8D", "VXSsFTyN1NQF3f13qtte29EWKsc2Kucu");
		}
		return conn;
	}
	
	/*public static Connection connect() throws ClassNotFoundException, SQLException {
		if (conn == null) {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/weixin_db", "root", "");
		}
		return conn;
	}*/
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		if (null == conn) {
			connect();
		}
		return conn;
	}
	
}
