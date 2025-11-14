package com.sist.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.sist.commons.*;
import com.sist.vo.*;

public class CartDAO {
	private static SqlSessionFactory ssf;
	
	static {
		ssf = CreateSqlSessionFactory.getSsf();
	}
	
	/*
	      <insert id="cartInsert" parameterType="CartVO">
		    INSERT INTO cart
		    VALUES(cart_no_seq.nextval, #{gno}, #{id}, #{account}, 0, SYSDATE)
		  </insert>
	 */
	public static void cartInsert(CartVO vo) {
		try {
			SqlSession session = ssf.openSession(true);
			session.insert("cartInsert", vo);
			session.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
