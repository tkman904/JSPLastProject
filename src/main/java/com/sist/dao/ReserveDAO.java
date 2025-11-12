package com.sist.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.*;
import java.sql.*;

import com.sist.vo.*;
import com.sist.commons.*;

public class ReserveDAO {
	private static SqlSessionFactory ssf;
	
	static {
		ssf = CreateSqlSessionFactory.getSsf();
	}
	
	/*
	      <select id="reserveFoodListData" resultType="FoodVO" parameterType="string">
		    SELECT fno, name, poster, type, rownum
		    FROM (SELECT fno, name, poster, type
		    FROM menupan_food ORDER BY fno)
		    WHERE rownum&lt;=100
		    AND type LIKE '%'||#{type}||'%'
		  </select>
	 */
	public static List<FoodVO> reserveFoodListData(String type) {
		List<FoodVO> list = null;
		try {
			SqlSession session = ssf.openSession();
			list = session.selectList("reserveFoodListData", type);
			session.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}
}
