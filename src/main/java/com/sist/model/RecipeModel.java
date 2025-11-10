package com.sist.model;

import java.util.*;

import com.sist.controller.Controller;
import com.sist.controller.RequestMapping;
import com.sist.dao.*;
import com.sist.vo.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class RecipeModel {
	// 쉐프 출력
	@RequestMapping("recipe/chef_list.do")
	public String recipe_chef_list(HttpServletRequest request, HttpServletResponse response) {
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
		   
		List<ChefVO> list=RecipeDAO.chefListData(map);
		int totalpage = RecipeDAO.chefTotalPage();
		 
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
		request.setAttribute("main_jsp", "../recipe/chef_list.jsp");
		return "../main/main.jsp";
	}
	
	// 레시피 목록
	@RequestMapping("recipe/list.do")
	public String recipe_list(HttpServletRequest request, HttpServletResponse response) {
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
		   
		List<RecipeVO> list=RecipeDAO.recipeListData(map);
		int totalpage = RecipeDAO.recipleTotalPage();
		 
		// 블록별 페이지 처리 
		final int BLOCK=10;
		int startPage=((curpage-1)/BLOCK*BLOCK)+1;
		// curpage => 1~10 : 1 , 11~20 : 11
		int endPage=((curpage-1)/BLOCK*BLOCK)+BLOCK;
		
		int count = RecipeDAO.recipeCount();
		
		if(endPage>totalpage)
			endPage=totalpage;
		// 브라우저 전송 
		request.setAttribute("list", list);
		request.setAttribute("count", count);
		request.setAttribute("curpage", curpage);
		request.setAttribute("totalpage", totalpage);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		  
		// 보여주는 화면 
		request.setAttribute("main_jsp", "../recipe/list.jsp");
		
		return "../main/main.jsp";
	}
	
	// 상세보기
	@RequestMapping("recipe/detail.do")
	public String recipe_detail(HttpServletRequest request, HttpServletResponse response) {
		// 사용자가 보내준 값 받기
		String no = request.getParameter("no");
		
		// 데이터베이스 읽기
		RecipeDetailVO vo = RecipeDAO.recipeDetailData(Integer.parseInt(no));
		
		List<String> mList = new ArrayList<String>();
		List<String> iList = new ArrayList<String>();
		String[] datas = vo.getFoodmake().split("\n");
		for(String s : datas) {
			StringTokenizer st = new StringTokenizer(s, "^");
			mList.add(st.nextToken());
			iList.add(st.nextToken());
		}
		
		request.setAttribute("mList", mList);
		request.setAttribute("iList", iList);
		request.setAttribute("vo", vo);
		
		request.setAttribute("main_jsp", "../recipe/detail.jsp");
		return "../main/main.jsp";
	}
}
