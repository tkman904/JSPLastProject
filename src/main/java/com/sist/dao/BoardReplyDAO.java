package com.sist.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.sist.commons.CreateSqlSessionFactory;
import com.sist.commons.*;
import com.sist.vo.*;

import java.util.*;

public class BoardReplyDAO {
	private static SqlSessionFactory ssf;
	
	static {
		ssf = CreateSqlSessionFactory.getSsf();
	}
	
	/*
	      <select id="replyListData" resultType="BoardReplyVO" parameterType="int">
		    SELECT no, bno, id, name, msg, TO_CHAR(regdate, 'YYYY-MM-DD HH24:MI:SS') as dbday
		    FROM mvcBoardReply
		    WHERE bno=#{bno}
		  </select>
		  
		  <select id="replyCount" resultType="int" parameterType="int">
		    SELECT COUNT(*)
		    FROM mvcBoardReply
		    WHERE bno=#{bno}
		  </select>
	 */
	public static List<BoardReplyVO> replyListData(int bno) {
		List<BoardReplyVO> list = null;
		try {
			SqlSession session = ssf.openSession();
			list = session.selectList("replyListData", bno);
			session.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}
	
	public static int replyCount(int bno) {
		int count = 0;
		try {
			SqlSession session = ssf.openSession();
			count = session.selectOne("replyCount", bno);
			session.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return count;
	}
}
