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
	/*
	 *    <select id="recipeListData" resultType="com.sist.vo.RecipeVO" parameterType="hashmap">
		    SELECT no, title, chef, poster, hit, likecount, replycount, num
		    FROM (SELECT no, title, chef, poster, hit, likecount, replycount, rownum as num
		    FROM (SELECT no, title, chef, poster, hit, likecount, replycount
		    FROM recipe ORDER BY no ASC))
		    WHERE num BETWEEN #{start} AND #{end}
		  </select>
		  <select id="recipleTotalPage" resultType="int">
		    SELECT CEIL(COUNT(*)/12.0)
		    FROM recipe
		  </select>
	 * 
	 */
	public static List<RecipeVO> recipeListData(Map map) {
		SqlSession session = ssf.openSession();
		List<RecipeVO> list = session.selectList("recipeListData", map);
		session.close();
		return list;
	}
	
	public static int recipleTotalPage() {
		SqlSession session = ssf.openSession();
		int total = session.selectOne("recipleTotalPage");
		session.close();
		return total;
	}
	
	/*
	      <select id="recipeCount" resultType="int">
		    SELECT COUNT(*)
		    FROM recipe
		  </select>
	 */
	public static int recipeCount() {
		SqlSession session = ssf.openSession();
		int count = session.selectOne("recipeCount");
		session.close();
		return count;
	}
	// 쉐프 상세보기
	// 레시피 상세보기
	/*
		  <update id="recipeHitIncrement" parameterType="int">
		    UPDATE recipe SET
		    hit=hit+1
		    WHERE no=#{no}
		  </update>
		  
	      <select id="recipeDetailData" resultType="com.sist.vo.RecipeDetailVO" parameterType="int">
		    SELECT * FROM recipedetail
		    WHERE no=#{no}
		  </select>

	 */
	public static RecipeDetailVO recipeDetailData(int no) {
		RecipeDetailVO vo = null;
		try {
			SqlSession session = ssf.openSession();
			session.update("recipeHitIncrement", no);
			session.commit();
			vo = session.selectOne("recipeDetailData", no);
			session.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return vo;
	}
	// 레시피 등록 ****
	// 레시피 검색
}
