package com.sist.dao;
import java.util.*;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.sist.commons.*;
import com.sist.vo.*;
// 오라클만 연동
public class FoodDAO {
   private static SqlSessionFactory ssf;
   static
   {
	   ssf=CreateSqlSessionFactory.getSsf();
   }
   /*
    *   <select id="foodListData" resultType="FoodVO"
		   parameterType="hashmap"
		  >
		    SELECT fno,name,type,address,poster,likecount,replycount,num
		    FROM (SELECT fno,name,type,address,poster,likecount,replycount,rownum as num
		    FROM (SELECT fno,name,type,address,poster,likecount,replycount 
		    FROM menupan_food ORDER BY fno ASC))
		    WHERE num BETWEEN #{start} AND #{end}
		  </select>
		  <select id="foodTotalPage" resultType="int">
		    SELECT CEIL(COUNT(*)/20.0) FROM menupan_food
		  </select>
    */
   
   
   // 목록 읽기 
   public static List<FoodVO> foodListData(Map map)
   {
	   List<FoodVO> list=null;
	   SqlSession session=null;
	   try
	   {
		   session=ssf.openSession();
		   list=session.selectList("foodListData",map);
	   }catch(Exception ex)
	   {
		   ex.printStackTrace();
	   }
	   finally
	   {
		   if(session!=null)
			   session.close();
	   }
	   return list;
   }
   // 총페이지 
   public static int foodTotalPage()
   {
	   int total=0;
	   SqlSession session=null;
	   try
	   {
		   session=ssf.openSession();
		   total=session.selectOne("foodTotalPage");
	   }catch(Exception ex)
	   {
		   ex.printStackTrace();
	   }
	   finally
	   {
		   if(session!=null)
			   session.close();
	   }
	   return total;
   }
}