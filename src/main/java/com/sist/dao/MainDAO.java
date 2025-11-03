package com.sist.dao;

import java.util.*;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.sist.commons.*;
import com.sist.vo.*;

public class MainDAO {
	private static SqlSessionFactory ssf;
	
	static {
		ssf = CreateSqlSessionFactory.getSsf();
	}
	
	/*
	 *   <select id="mainTopData" resultType="FoodVO">
		   SELECT *
		   FROM (SELECT * FROM menupan_food 
		    	ORDER BY DBMS_RANDOM.VALUES
		   )
		   WHERE ROWNUM=1
		 </select>
	 */
	public static FoodVO mainTopData() {
		SqlSession session = ssf.openSession();
		FoodVO vo = session.selectOne("mainTopData");
		session.close();
		return vo;
	}
	
	/*
	 *   <select id="mainHitTop4" resultType="FoodVO">
		    SELECT fno, name, theme, hit, likecount, replycount, type, address, rownum
		    FROM (SELECT fno, name, theme, hit, likecount, replycount, type, address
		    FROM menupan_food
		    ORDER BY hit DESC)
		    WHERE ROWNUM&lt;=4;
		 </select>
		 <select id="mainLikeTop4" resultType="FoodVO">
		    SELECT fno, name, theme, hit, likecount, replycount, type, address, rownum
		    FROM (SELECT fno, name, theme, hit, likecount, replycount, type, address
		    FROM menupan_food
		    ORDER BY likecount DESC)
		    WHERE ROWNUM&lt;=4;
		 </select>
	 */
	public static List<FoodVO> mainHitTop4() {
		SqlSession session = ssf.openSession();
		List<FoodVO> list = session.selectList("mainHitTop4");
		session.close();
		return list;
	}
	
	public static List<FoodVO> mainLikeTop4() {
		SqlSession session = ssf.openSession();
		List<FoodVO> list = session.selectList("mainLikeTop4");
		session.close();
		return list;
	}
	// Cookie
	// 뉴스, 실시간 동영상(레시피)
}
