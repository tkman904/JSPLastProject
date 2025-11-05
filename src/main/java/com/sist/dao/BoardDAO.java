package com.sist.dao;

import java.util.*;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.sist.commons.*;
import com.sist.vo.*;

public class BoardDAO {
	private static SqlSessionFactory ssf;
	
	static {
		ssf = CreateSqlSessionFactory.getSsf();
		// getConnection(), disConnection()
	}
	
	/*
	      <select id="boardListData" resultType="BoardVO" parameterType="int">
		    SELECT no, subject, name, TO_CHAR(regdate, 'YYYY-MM-DD') as dbday, hit, replycount
		    FROM mvcBoard
		    ORDER BY no DESC
		    OFFSET #{start} ROWS FETCH NEXT 10 ROWS ONLY
		  </select>
		  <select id="boardTotalPage" resultType="int">
		    SELECT CEIL(COUNT(*)/10.0)
		    FROM mvcBoard
		  </select>
		  
		  @Select("
		  	SELECT no, subject, name, TO_CHAR(regdate, 'YYYY-MM-DD') as dbday, hit, replycount
		    FROM mvcBoard
		    ORDER BY no DESC
		    OFFSET #{start} ROWS FETCH NEXT 10 ROWS ONLY
		  ");
		  public List<BoardVO> boardListData(int start)
		  
		  public List<BoardVO> boardListData(int start)
	 */
	public static List<BoardVO> boardListData(int start) {
		List<BoardVO> list = null;
		try {
			SqlSession session = ssf.openSession();
			list = session.selectList("boardListData", start);
			session.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}
	
	public static int boardTotalPage() {
		int total = 0;
		try {
			SqlSession session = ssf.openSession();
			total = session.selectOne("boardTotalPage");
			session.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return total;
	}
	
	/*
	      <insert id="boardInsert" parameterType="BoardVO">
		    INSERT INTO mvcBoard(no, name, subject, content, pwd)
		    VALUES(mb_no_seq.nextval, #{name}, #{subject}, #{content}, #{pwd})
		  </insert>
	 */
	public static void boardInsert(BoardVO vo) {
		try {
			SqlSession session = ssf.openSession(true);
			session.insert("boardInsert", vo);
			session.close(); // 생략이 되면 => 8번 수행 후 종료
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/*
	      <update id="boardHitIncrement" parameterType="int">
		    UPDATE mvcBoard SET
		    hit=hit+1
		    WHERE no=#{no}
		  </update>
		  
		  <select id="boardDetailData" resultType="BoardVO" parameterType="int">
		    SELECT no, name, subject, content, hit, TO_CHAR(regdate, 'YYYY-MM-DD HH24:MI:SS') as dbday, replycount
		    FROM mvcBoard
		    WHERE no=#{no} 
		  </select>
	 */
	public static BoardVO boardDetailData(int no, int type) {
		BoardVO vo = null;
		try {
			SqlSession session = ssf.openSession();
			if(type==0) {
				session.update("boardHitIncrement", no);
				session.commit();
			}
			vo = session.selectOne("boardDetailData", no);
			session.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return vo;
	}
	
	/*
	  <select id="boardGetPassword" resultType="string" parameterType="int">
	    SELECT pwd FROM mvcBoard
	    WHERE no=#{no}
	  </select>
	  
	  <delete id="boardDelete" parameterType="int">
	    DELETE FROM mvcBoard
	    WHERE no=#{no}
	  </delete>
	 */
	public static String boardDelete(int no, String pwd) {
		String res = "no";
		try {
			SqlSession session = ssf.openSession();
			String db_pwd = session.selectOne("boardGetPassword", no);
			if(db_pwd.equals(pwd)) {
				// 댓글 => 삭제
				session.delete("boardDelete", no);
				session.commit();
				res = "yes";
			}
			session.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return res;
	}
	
	public static BoardVO boardUpdateData(int no) {
		BoardVO vo = null;
		try {
			SqlSession session = ssf.openSession();
			vo = session.selectOne("boardDetailData", no);
			session.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return vo;
	}
	/*
	 * 		Map map = new HashMap();
	 * 		map.put("boardGetPassword", "SELECT~");
	 * 		map.put("boardListData", "SELECT~");
	 * 		map.put("boardInsert", "INSERT~");
	 */
	public static String boardPwdCheck(int no, String pwd) {
		String res = "no";
		try {
			SqlSession session = ssf.openSession();
			String db_pwd = session.selectOne("boardGetPassword", no);
			if(db_pwd.equals(pwd)) {
				res = "yes";
			}
			session.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return res;
	}
	
	/*
	      <update id="boardUpdate" parameterType="BoardVO">
		    UPDATE mvcBoard SET
		    name=#{name}, subject=#{subject}, content=#{content}
		    WHERE no=#{no}
		  </update>
	 */
	public static String boardUpdate(BoardVO vo) {
		String res = "";
		try {
			SqlSession session = ssf.openSession();
			session.update("boardUpdate", vo);
			session.commit();
			session.close();
			res = "yes";
		} catch(Exception ex) {
			ex.printStackTrace();
			res = ex.getMessage();
		}
		return res;
	}
}
