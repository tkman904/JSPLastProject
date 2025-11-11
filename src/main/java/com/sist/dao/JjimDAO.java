package com.sist.dao;

import java.util.*;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.sist.commons.*;
import com.sist.vo.*;

/*
 * 	package
 *  import
 *  @어노테이션
 *  class className
 *  
 *  => 스프링은 모든 클래스에 어노테이션을 올려서 사용
 *     ----- 부트
 *     | => 스프링은 클래스 관리자 (생성 ~ 소멸)
 *                             | 싱글턴
 */
public class JjimDAO {
	private static SqlSessionFactory ssf;
	
	static {
		ssf = CreateSqlSessionFactory.getSsf();
	}
	
	/*
	      <select id="jjimCheckCount" resultType="int" parameterType="JjimVO">
		    SELECT COUNT(*)
		    FROM all_jjim
		    WHERE id=#{id} AND rno=#{rno} AND type=#{type}
		  </select>
		  
		  <insert id="jjimInsert" parameterType="JjimVO">
		    INSERT INTO all_jjim
		    VALUES(aj_jno_seq.nextval, #{type}, #{rno}, #{id}, SYSDATE)
		  </insert>
	 */
	public static int jjimCheckCount(JjimVO vo) {
		int count = 0;
		try {
			SqlSession session = ssf.openSession();
			count = session.selectOne("jjimCheckCount", vo);
			session.close();
			/*
			 * 	 {} => getter
			 * 	 {name} => getName()
			 *     | vo => 변수명
			 *     
			 *   {start} => map.get("start")
			 *     | map => key
			 *     
			 *     => ?에 들어가는 값 => parameterType
			 *     => SQL문장 실행 결과 => resultType
			 */
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return count;
	}
	
	// 찜하기 => 추가
	public static void jjimInsert(JjimVO vo) {
		try {
			SqlSession session = ssf.openSession(true);
			session.insert("jjimInsert", vo);
			//            => SQL문장 찾기 , ?에 값을 채운다
			session.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	// 마이페이지에 출력
	/*
	      <select id="jjimFoodListData" resultMap="jjimMap" parameterType="string">
		    SELECT jno, aj.type, rno, name, poster
		    FROM all_jjim aj, menupan_food mf
		    WHERE aj.rno=mf.fno AND id=#{id} AND aj.type=1
		    ORDER BY jno DESC
		  </select>
	 */
	public static List<JjimVO> jjimFoodListData(String id) {
		List<JjimVO> list = null;
		try {
			SqlSession session = ssf.openSession();
			list = session.selectList("jjimFoodListData", id);
			session.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}
	
	/*
	      <delete id="jjimCancel" parameterType="int">
		    DELETE FROM all_jjim
		    WHERE jno=#{jno}
		  </delete>
	 */
	public static void jjimCancel(int jno) {
		try {
			SqlSession session = ssf.openSession(true);
			session.delete("jjimCancel", jno);
			session.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/*
	      <select id="foodDetailData" resultType="FoodVO" parameterType="int">
		    SELECT * FROM menupan_food
		    <include refid="where-fno"/>
		  </select>
	 */
	public static FoodVO foodDetailData(int fno) {
		FoodVO vo = null;
		try {
			SqlSession session = ssf.openSession();
			vo = session.selectOne("foodDetailData", fno);
			session.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return vo;
	}
}
