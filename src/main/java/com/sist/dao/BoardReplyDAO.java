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
	
	/*
	      <insert id="replyInsert" parameterType="BoardReplyVO">
		    INSERT INTO mvcBoardReply(no, bno, id, name, msg, group_id)
			VALUES(mr_no_seq.nextval, #{bno}, #{id}, #{name}, #{msg}, 
				  (SELECT NVL(MAX(group_id)+1, 1)
				   FROM mvcBoardReply))
		  </insert>
	 */
	public static void replyInsert(BoardReplyVO vo) {
		try {
			SqlSession session = ssf.openSession(true);
			session.insert("replyInsert", vo);
			session.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/*
	      <update id="replyUpdate" parameterType="BoardReplyVO">
		    UPDATE mvcBoardReply SET
		    msg=#{msg}
		    WHERE no=#{no}
		  </update>
	 */
	public static void replyUpdate(BoardReplyVO vo) {
		try {
			SqlSession session = ssf.openSession(true);
			session.update("replyUpdate", vo);
			session.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/*
	      <!-- 대댓글 -->
		  <!-- 1. group_id, group_step, group_tab -->
		  <select id="replyParentInfoData" resultType="BoardReplyVO" parameterType="int">
		    SELECT group_id, group_step, group_tab
		    FROM mvcBoardReply
		    WHERE no=#{no}
		  </select>
		  
		  <!-- 2. 답변의 순서 지정 -->
		  <update id="replyGroupStepIncrement" parameterType="BoardReplyVO">
		    UPDATE mvcBoardReply SET
		    group_step=group_step+1
		    WHERE group_id=#{group_id} AND group_step>#{group_step}
		  </update>
		  <!-- 
		  							gi	gs	gt
		  		AAAAAA				1	1	1
		  		  => BBBBBB			1	2	2
		  		  => EEEEEE			1	3	2
		  		CCCCCC				2	1	1
		  		  => DDDDDD			2	2	2 
		  		  	   => FFFFFF	2	3	3	
		  		  	   
		  		group_id : 답변을 모아서 관리
		  		group_step : 답변 출력 = 순서
		  		group_tab : 간격
		   -->
		   
		  <!-- 3. INSERT -->
		  <insert id="replyReplyInsert" parameterType="BoardReplyVO">
		    INSERT INTO mvcBoardReply(no, bno, id, name, msg, group_id, group_step, group_tab, root)
		    VALUES(mr_no_seq.nextval, #{bno}, #{id}, #{name}, #{msg}, #{group_id}, #{group_step}, #{group_tab}, #{root})
		  </insert>
		   
		  <!-- 4. depth 증가 -->
		  <update id="replyDepthIncrement" parameterType="int">
		    UPDATE mvcBoardReply SET
		    depth=depth+1
		    WHERE no=#{no}
		  </update> 
	 */
	public static void replyReplyInsert(int pno, BoardReplyVO vo) {
		try {
			SqlSession session = ssf.openSession();
			BoardReplyVO pvo = session.selectOne("replyParentInfoData", pno);
			session.update("replyGroupStepIncrement", pvo);
			vo.setGroup_id(pvo.getGroup_id());
			vo.setGroup_step(pvo.getGroup_step()+1);
			vo.setGroup_tab(pvo.getGroup_tab()+1);
			vo.setRoot(pno);
			
			session.insert("replyReplyInsert", vo);
			session.update("replyDepthIncrement", pno);
			session.commit();
			session.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/*
	      <!-- 삭제 -->
		  <!-- 1. depth, root -->
		  <select id="replyInfoData" resultType="BoardReplyVO" parameterType="int">
		    SELECT depth, root
		    FROM mvcBoardReply
		    WHERE no=#{no}
		  </select>
		  
		  <!-- 2. 삭제 -->
		  <delete id="replyDelete" parameterType="int">
		    DELETE FROM mvcBoardReply
		    WHERE no=#{no}
		  </delete>
		  
		  <update id="replyMsgUpdate" parameterType="int">
		    UPDATE mvcBoardReply SET
		    msg='삭제된 댓글입니다'
		    WHERE no=#{no}
		  </update>
		  
		  <!-- root = depth를 감소 -->
		  <update id="replyDepthDecrement" parameterType="int">
		    UPDATE mvcBoardReply SET
		    depth=depth-1
		    WHERE no=#{no}
		  </update>
	 */
	public static void replyDelete(int no) {
		try {
			SqlSession session = ssf.openSession();
			BoardReplyVO vo = session.selectOne("replyInfoData", no);
			if(vo.getDepth()==0) {
				session.delete("replyDelete", no);
			} else {
				session.update("replyMsgUpdate", no);
			}
			session.update("replyDepthDecrement", vo.getRoot());
			session.commit();
			session.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
