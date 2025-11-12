package com.sist.temp;

import java.util.*;
import java.sql.*;

public class ReserveDAO {
	private Connection conn;
	private PreparedStatement ps;
	private String url = "jdbc:oracle:thin:@localhost:1521:XE";
	private static ReserveDAO dao;
	
	public ReserveDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void getConnection() {
		try {
			conn = DriverManager.getConnection(url, "hr", "happy");
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void disConnection() {
		try {
			if(conn!=null) {
				conn.close();
			}
			if(ps!=null) {
				ps.close();
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static ReserveDAO newInstance() {
		if(dao==null) {
			dao = new ReserveDAO();
		}
		return dao;
	}
	
	public void insert(int no, String time) {
		try {
			getConnection();
			String sql = "INSERT INTO reserve_date VALUES(?, ?)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, no);
			ps.setString(2, time);
			ps.executeUpdate();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		finally {
			disConnection();
		}
	}
}
