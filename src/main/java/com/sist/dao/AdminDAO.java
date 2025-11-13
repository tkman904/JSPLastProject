package com.sist.dao;

import java.util.*;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.sist.commons.*;
import com.sist.vo.*;

public class AdminDAO {
	private static SqlSessionFactory ssf;
	
	static {
		ssf = CreateSqlSessionFactory.getSsf();
	}
	
	/*
	      <!-- admin -->
		  <select id="reserveAdminListData">
		    SELECT no, ri.fno, id, rday, ri.time, inwon, ok, name, phone, poster
		    FROM reserve_info ri, menupan_food mf
		    WHERE ri.fno=mf.fno
		    ORDER BY no DESC 
		  </select>
	 */
	public static List<ReserveVO> reserveAdminListData() {
		List<ReserveVO> list = null;
		try {
			SqlSession session = ssf.openSession();
			list = session.selectList("reserveAdminListData");
			session.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}
	
	/*
	      <update id="reserveOk" parameterType="int">
		    UPDATE reserve_info SET
		    ok=1
		    WHERE no=#{no}
		  </update>
	 */
	public static void reserveOk(int no) {
		try {
			SqlSession session = ssf.openSession(true);
			session.update("reserveOk", no);
			session.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
