package com.sist.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.*;

import com.sist.commons.*;
import com.sist.vo.*;

public class NoticeDAO {
	private static SqlSessionFactory ssf;
	
	static {
		ssf = CreateSqlSessionFactory.getSsf();
	}
	
	/*
      <insert id="noticeInsert" parameterType="NoticeVO">
	    INSERT INTO notice
	    VALUES(notice_no_seq.nextval, #{state}, #{name}, #{subject}, #{content}, SYSDATE, 0, #{filename}, #{filesize})
	  </insert>
	  
	  <select id="noticeListData" resultType="NoticeVO" parameterType="int">
	    SELECT no, state, name, subject, TO_CHAR(regdate, 'YYYY-MM-DD') AS dbday, hit
	    FROM notice
	    ORDER BY no DESC
	    OFFSET #{start} ROWS FETCH NEXT 10 ROWS ONLY
	  </select>
	  
	  <select id="noticeTotalPage" resultType="int">
	    SELECT CEIL(COUNT(*)/10.0)
	    FROM notice
	  </select>
	 */
	public static void noticeInsert(NoticeVO vo) {
		try {
			SqlSession session = ssf.openSession(true);
			session.insert("noticeInsert", vo);
			session.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static List<NoticeVO> noticeListData(int start) {
		List<NoticeVO> list = null;
		try {
			SqlSession session = ssf.openSession();
			list = session.selectList("noticeListData", start);
			session.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}
	
	public static int noticeTotalPage() {
		int total = 0;
		try {
			SqlSession session = ssf.openSession();
			total = session.selectOne("noticeTotalPage");
			session.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return total;
	}
	
	/*
       <update id="noticeHitIncrement" parameterType="int">
	     UPDATE notice
		 SET hit = hit+1
	     WHERE no = #{no}
	   </update>
	   
	   <select id="noticeDetailData" resultType="NoticeVO" parameterType="int">
	     SELECT *
	     FROM notice
	     WHERE no = #{no}
	   </select>
	 */
	public static NoticeVO noticeDetailData(int no) {
		NoticeVO vo = null;
		try {
			SqlSession session = ssf.openSession();
			session.update("noticeHitIncrement", no);
			session.commit();
			vo = session.selectOne("noticeDetailData", no);
			session.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return vo;
	}
}
