package com.sist.model;
import java.util.*;

import com.sist.controller.Controller;
import com.sist.controller.RequestMapping;
import com.sist.dao.*;
import com.sist.vo.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
/*
 *    화면 변경
 *      => request 전송 : forward 
 *         return "파일명";
 *      => request 초기화  : sendRedirect
 *         return "redirect:~.do"
 *    데이터 전송 
 *      => request/session이용 => request.setAttribute()
 *         : String
 *      => json 
 *         : void
 *           => out.write(데이터) 
 */
@Controller
public class FoodModel {
   @RequestMapping("food/list.do")
   public String food_list(HttpServletRequest request,
		   HttpServletResponse response)
   {
	   // 사용자가 요청 (페이지를 보여달라)
	   
	   
	   
	   String page=request.getParameter("page");
	   if(page==null)
		   page="1";
	   int curpage=Integer.parseInt(page);
	   Map map=new HashMap();
	   int rowSize=12;
	   int start=(rowSize*curpage)-(rowSize-1);
	   int end=rowSize*curpage;
	   
	   map.put("start", start);
	   map.put("end", end);
	   
	   List<FoodVO> list=FoodDAO.foodListData(map);
	   for(FoodVO vo:list)
	   {
		   String addr=vo.getAddress();
		   addr=addr.substring(0,addr.indexOf(" "));
		   vo.setAddress(addr.trim());
	   }
	   // 총페이지 
	   int totalpage=FoodDAO.foodTotalPage();
	   
	   // 블록별 페이지 처리 
	   final int BLOCK=10;
	   int startPage=((curpage-1)/BLOCK*BLOCK)+1;
	   // curpage => 1~10 : 1 , 11~20 : 11
	   int endPage=((curpage-1)/BLOCK*BLOCK)+BLOCK;
	   
	   if(endPage>totalpage)
		   endPage=totalpage;
	   
	   // 브라우저 전송 
	   request.setAttribute("list", list);
	   
	   request.setAttribute("curpage", curpage);
	   request.setAttribute("totalpage", totalpage);
	   request.setAttribute("startPage", startPage);
	   request.setAttribute("endPage", endPage);
	   
	   // 보여주는 화면 
	   request.setAttribute("main_jsp", "../food/list.jsp");
	   return "../main/main.jsp";
   }
   
   /*
    *   Spring => JSP
    *   | include / forward => request를 공유
    *   | sendRedirect => request가 초기화
    *   | MVC => 모든 화면 DispatcherServlet이다
    *                    -----------------
    *                     => .do
    *                     => 화면 변경 => HTML만 덮어쓰고 있다
    */
   @RequestMapping("food/detail.do")
   public String food_detail(HttpServletRequest request, HttpServletResponse response) {
	   String fno = request.getParameter("fno");
	   String page = request.getParameter("page");
	   FoodVO vo = FoodDAO.foodDetailData(Integer.parseInt(fno));
	   request.setAttribute("vo", vo);
	   request.setAttribute("page", page);
	   // String food_detail(int fno)
	   // String food_detail(FoodVO vo)
	   // => 스프링 : 전송 객체 / 사용자 요청값 Model
	   // => request는 사용하지 않는다
	   request.setAttribute("main_jsp", "../food/detail.jsp");
	   return "../main/main.jsp";
   }
   
   // food_ // goods_ // recipe_
   @RequestMapping("food/detail_before.do")
   public String food_detail_before(HttpServletRequest request, HttpServletResponse response) {
	   String fno = request.getParameter("fno");
	   String page = request.getParameter("page");
	   
	   // cookie
	   // 1. 로그인된 상태
	   // 2. 쿠키 저장
	   
	   return "redirect:../food/detail.do?fno="+fno+"&page="+page;
   }
}