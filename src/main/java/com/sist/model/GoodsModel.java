package com.sist.model;

import com.sist.controller.Controller;
import com.sist.controller.RequestMapping;
import com.sist.dao.*;
import com.sist.vo.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.*;

@Controller
public class GoodsModel {
	private String[] table_name = {
		"",
		"goods_all",
		"goods_best",
		"goods_new",
		"goods_special"
	};
	
	private String[] titles = {
		"",
		"상품 전체 목록",
		"베스트 상품 목록",
		"신상품 목록",
		"특가 상품 목록"
	};
			
    @RequestMapping("goods/list.do")
    public String goods_list(HttpServletRequest request,HttpServletResponse response) {
	   String page = request.getParameter("page");
	   if(page==null)
		   page = "1";
	   int curpage = Integer.parseInt(page);
	   String cno = request.getParameter("cno");
	   Map map = new HashMap();
	   int rowSize = 12;
	   int start = (curpage*rowSize)-(rowSize-1);
	   int end = curpage*rowSize;
	   map.put("goods", table_name[Integer.parseInt(cno)]);
	   map.put("start", start);
	   map.put("end", end);
	   List<GoodsVO> list = GoodsDAO.goodsListData(map);
	   int totalpage = GoodsDAO.goodsTotalPage(map);
	   
	   // 블록별 처리
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
	   request.setAttribute("cno", cno);
	   request.setAttribute("title", titles[Integer.parseInt(cno)]);
	   
	   request.setAttribute("main_jsp", "../goods/list.jsp");
	   return "../main/main.jsp";
    }
    
    @RequestMapping("goods/detail_before.do")
    public String goods_detail_before(HttpServletRequest request, HttpServletResponse response) {
    	String no = request.getParameter("no");
    	String page = request.getParameter("page");
    	String cno = request.getParameter("cno");

    	return "redirect:../goods/detail.do?no="+no+"&page="+page+"&cno="+cno;
    }
    
    @RequestMapping("goods/detail.do")
    public String goods_detail(HttpServletRequest request, HttpServletResponse response) {
    	String no = request.getParameter("no");
    	String page = request.getParameter("page");
    	String cno = request.getParameter("cno");
    	
    	request.setAttribute("main_jsp", "../goods/detail.jsp");
    	return "../main/main.jsp";
    }
}
