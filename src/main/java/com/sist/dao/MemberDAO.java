package com.sist.dao;

import java.util.*;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.sist.commons.*;
import com.sist.vo.*;

public class MemberDAO {
	private static SqlSessionFactory ssf;
	
	static {
		ssf = CreateSqlSessionFactory.getSsf();
	}
	
	/*
	      <select id="memberIdCheck" resultType="int" parameterType="string">
		    SELECT COUNT(*)
		    FROM mvcMember
		    WHERE id=#{id}
		  </select>
	 */
	public static int memberIdCheck(String id) {
		int count = 0;
		try {
			SqlSession session = ssf.openSession();
			count = session.selectOne("memberIdCheck", id);
			session.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return count;
	}
	
	// 회원 가입
	/*
	      <insert id="memberInsert" parameterType="MemberVO">
		    INSERT INTO mvcMember VALUES(
		    	#{id},
		    	#{pwd},
		    	#{name},
		    	#{sex},
		    	#{birthday},
		    	#{email},
		    	#{post},
		    	#{addr1},
		    	#{addr2},
		    	#{phone},
		    	#{content},
		    	'n',
		    	SYSDATE
		    )
		  </insert>
	 */
	public static void memberInsert(MemberVO vo) {
		try {
			SqlSession session = ssf.openSession(true);
			session.insert("memberInsert", vo);
			session.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/*
	     <select id="memberIdCheck" resultType="int" parameterType="string">
		   SELECT COUNT(*)
		   FROM mvcMember
		   WHERE id=#{id}
		 </select>
		 
		 <select id="memberInfoData" resultType="MemberVO" parameterType="string">
		   SELECT id, name, sex, pwd, admin, post, addr1, addr2, phone
		   FROM mvcMember
		   WHERE id=#{id}
		 </select>
	 */
	public static MemberVO memberLogin(String id, String pwd) {
		MemberVO vo = new MemberVO();
		try {
			SqlSession session = ssf.openSession();
			int count = session.selectOne("memberIdCheck", id);
			if(count==0) {
				vo.setMsg("NOID");
			} else {
				MemberVO dbVO = session.selectOne("memberInfoData", id);
				if(pwd.equals(dbVO.getPwd())) {
					vo.setMsg("OK");
					vo.setId(dbVO.getId());
					vo.setName(dbVO.getName());
					vo.setSex(dbVO.getSex());
					vo.setPost(dbVO.getPost());
					vo.setAddr1(dbVO.getAddr1());
					vo.setAddr2(dbVO.getAddr2());
					vo.setAdmin(dbVO.getAdmin());
					vo.setPhone(dbVO.getPhone());
				} else {
					vo.setMsg("NOPWD");
				}
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return vo;
	}
}
