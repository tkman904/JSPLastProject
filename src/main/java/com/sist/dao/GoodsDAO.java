package com.sist.dao;

import java.util.*;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.sist.commons.*;
import com.sist.vo.*;

public class GoodsDAO {
	private static SqlSessionFactory ssf;
	
	static {
		ssf = CreateSqlSessionFactory.getSsf();
	}
	
	/*
	 *   <select id="goodsListData" resultType="GoodsVO" parameterType="hashmap">
		    SELECT no, goods_name, goods_sub, goods_price, goods_discount, goods_poster, hit, num
		    FROM (SELECT no, goods_name, goods_sub, goods_price, goods_discount, goods_poster, hit, rownum as num
		    FROM (SELECT no, goods_name, goods_sub, goods_price, goods_discount, goods_poster, hit
		    FROM ${goods} ORDER BY no ASC))
		    WHERE num BETWEEN #{start} AND #{end}
		 </select>
		 <select id="goodsTotalPage" resultType="int" parameterType="hashmap">
		    SELECT CEIL(COUNT(*)/12.0)
		    FROM ${goods}
		 </select>
	 */
	public static List<GoodsVO> goodsListData(Map map) {
		SqlSession session = ssf.openSession();
		List<GoodsVO> list = session.selectList("goodsListData", map);
		session.close();
		return list;
	}
	
	public static int goodsTotalPage(Map map) {
		SqlSession session = ssf.openSession();
		int total = session.selectOne("goodsTotalPage", map);
		session.close();
		return total;
	}
	
	/*
	      <update id="goodsHitIncrement" parameterType="hashmap">
		    UPDATE ${goods} SET
		    hit=hit+1
		    WHERE no=#{no}
		  </update>
		  
		  <select id="goodsDetailData" resultType="GoodsVO" parameterType="hashmap">
		    SELECT * FROM ${goods}
		    WHERE no=#{no}
		  </select>
	 */
	public static GoodsVO goodsDetailData(Map map) {
		SqlSession session = ssf.openSession();
		session.update("goodsHitIncrement", map);
		session.commit();
		GoodsVO vo = session.selectOne("goodsDetailData", map);
		session.close();
		return vo;
	}
	
	public static GoodsVO goodsCookieData(Map map) {
		SqlSession session = ssf.openSession();
		GoodsVO vo = session.selectOne("goodsDetailData", map);
		session.close();
		return vo;
	}
	
	/*
	      <insert id="orderInsert" parameterType="OrdersVO">
		    INSERT INTO mvcOrders
		    VALUES(order_no_seq.nextval, #{gno}, #{id}, #{name}, #{post}, #{addr1}, #{addr2}, #{msg}, #{account}, SYSDATE)
		  </insert>
		  
		  => JSP (링크 => <a> <form>, ajax) : 요청 사항
		  => mapper.xml => SQL
		                    | 연습
		  => DAO
		  => MODEL
		  => 해당 JSP에 값을 출력
		  => 화면 유지 : ajax / vue / react
		  => JSP => 서버 => JSP
		      |             |
		      --------------- 다른 JSP new className()
		                              ---------------
		      => Spring Framework
		         Spring-Boot
		         ----------------------- VueJS
		      => Spring-Boot / VSCode
		            |            |
		           서버         클라이언트 (Vue / React)
		            |            |
		            --------------
		                   |
		                  JSON
	 */
	public static void orderInsert(OrdersVO vo) {
		try {
			SqlSession session = ssf.openSession(true);
			session.insert("orderInsert", vo);
			session.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
