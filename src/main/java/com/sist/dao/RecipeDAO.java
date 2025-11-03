package com.sist.dao;

import java.util.*;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.sist.commons.*;
import com.sist.vo.*;

public class RecipeDAO {
	private static SqlSessionFactory ssf;
	
	static {
		ssf = CreateSqlSessionFactory.getSsf();
	}
	
	// 쉐프 목록
	/*
	      <select id="chefListData" resultType="ChefVO" parameterType="hashmap">
		    SELECT no, chef, poster, mem_cont1, mem_cont2, mem_cont7, mem_cont3, num
		    FROM (SELECT no, chef, poster, mem_cont1, mem_cont2, mem_cont7, mem_cont3, rownum as num
		    FROM (SELECT no, chef, poster, mem_cont1, mem_cont2, mem_cont7, mem_cont3
		    FROM chef ORDER BY no ASC))
		    WHERE num BETWEEN #{start} AND #{end}
		  </select>
		  <select id="chefTotalPage" resultType="int">
		    SELECT CEIL(COUNT(*)/30.0) FROM chef
		  </select>
	 */
	public static List<ChefVO> chefListData(Map map) {
		SqlSession session = ssf.openSession();
		List<ChefVO> list = session.selectList("chefListData", map);
		session.close();
		return list;
	}
	
	public static int chefTotalPage() {
		SqlSession session = ssf.openSession();
		int total = session.selectOne("chefTotalPage");
		session.close();
		return total;
	}
	
	// 레시피 목록
	// 쉐프 상세보기
	// 레시피 상세보기
	// 레시피 등록 ****
	// 레시피 검색
}
