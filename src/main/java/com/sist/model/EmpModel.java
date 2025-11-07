package com.sist.model;

import java.util.*;

import com.sist.controller.Controller;
import com.sist.controller.RequestMapping;
import com.sist.dao.*;
import com.sist.vo.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class EmpModel {
	@RequestMapping("emp/list.do")
	public String emp_list(HttpServletRequest request, HttpServletResponse response) {
		List<EmpVO> list = EmpDAO.empAllData();
		request.setAttribute("list", list);
		
		return "../emp/list.jsp";
	}
	// 동적 연습 => IN사용법
	// <trim> <if> <foreach> <where>
	
	@RequestMapping("emp/names.do")
	public String emp_names(HttpServletRequest request, HttpServletResponse response) {
		List<String> list = EmpDAO.empAllNames();
		request.setAttribute("list", list);
		
		return "../emp/names.jsp";
	}
	
	@RequestMapping("emp/info.do")
	public String emp_info(HttpServletRequest request, HttpServletResponse response) {
		String[] nameArr = request.getParameterValues("names");
		Map map = new HashMap();
		map.put("nameArr", nameArr);
		
		// DB연동
		List<EmpVO> list = EmpDAO.empFindData(map);
		request.setAttribute("list", list);
		
		return "../emp/info.jsp";
	}
}
