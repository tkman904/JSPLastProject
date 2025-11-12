package com.sist.model;

import java.util.*;

import com.sist.controller.Controller;
import com.sist.controller.RequestMapping;
import com.sist.vo.*;
import com.sist.dao.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ReserveModel {
   @RequestMapping("reserve/reserve_main.do")
   public String reserve_main(HttpServletRequest request, HttpServletResponse response) {
	   request.setAttribute("main_jsp", "../reserve/reserve_main.jsp");
	   return "../main/main.jsp";
   }
   
   @RequestMapping("reserve/reserve_food.do")
   public String reserve_food(HttpServletRequest request, HttpServletResponse response) {
	   String type = request.getParameter("type");
	   List<FoodVO> list = ReserveDAO.reserveFoodListData(type);
	   
	   request.setAttribute("list", list);
	   
	   return "../reserve/reserve_food.jsp";
   }
}