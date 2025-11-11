package com.sist.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.sist.commons.CreateSqlSessionFactory;
import com.sist.commons.*;
import com.sist.vo.*;

import java.util.*;

public class LikeDAO {
	private static SqlSessionFactory ssf;
	
	static {
		ssf = CreateSqlSessionFactory.getSsf();
	}
	
	/*
      <insert id="likeOn" parameterType="hashmap">
	    INSERT INTO all_like
	    VALUES(
	    	al_lno_seq.nextval,
	    	#{type},
	    	#{rno},
	    	#{id}
	    )
	  </insert>
	  
	  <update id="likeCountIncrement" parameterType="hashmap">
	    UPDATE ${table} SET
	    likecount=likecount+1
	    WHERE ${checks}=#{rno}
	  </update>
	  
	  <select id="likeCount" resultType="int" parameterType="hashmap">
	    SELECT NVL(likecount, 0) AS likecount
	    FROM ${table}
	    WHERE ${checks}=#{rno}
	  </select>
	 */
	public static int likeOn(Map map) {
		int count = 0;
		SqlSession session = null;
		try {
			session = ssf.openSession();
			session.insert("likeOn", map);
			session.update("likeCountIncrement", map);
			session.commit();
			count = session.selectOne("likeCount", map);
			session.close();
		} catch(Exception ex) {
			session.rollback();
			ex.printStackTrace();
		}
		return count;
	}
	
	/*
	  <delete id="likeOff" parameterType="hashmap">
	    DELETE FROM all_like
	    WHERE rno=#{rno} AND id=#{id} AND type=#{type}
	  </delete>
	  
	  <update id="likeCountDecrement" parameterType="hashmap">
	    UPDATE ${table} SET
	    likecount=likecount-1
	    WHERE ${checks}=#{rno}
	  </update>
	 */
	public static int likeOff(Map map) {
		int count = 0;
		SqlSession session = null;
		try {
			session = ssf.openSession();
			session.delete("likeOff", map);
			session.update("likeCountDecrement", map);
			session.commit();
			count = session.selectOne("likeCount", map);
			session.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return count;
	}
	
	/*
		  <select id="likeCheck" resultType="int" parameterType="hashmap">
		    SELECT COUNT(*) FROM all_like
		    WHERE rno=#{rno} AND type=#{type} AND id=#{id}
		  </select>
	 */
	public static int likeCheck(Map map) {
		int count = 0;
		try {
			SqlSession session = ssf.openSession();
			count = session.selectOne("likeCheck", map);
			session.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return count;
	}
}
