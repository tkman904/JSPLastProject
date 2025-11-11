package com.sist.model;

import java.util.*;

import com.sist.vo.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.sist.controller.Controller;
import com.sist.controller.RequestMapping;
import com.sist.dao.*;

@Controller
public class JjimModel {
	private String[] urls = {"", "../food/detail.do?fno=", "../recipe/detail.do?no=", "../goods/detail.do?no="};
	@RequestMapping("jjim/jjim_insert.do")
	public String jjim_insert(HttpServletRequest request, HttpServletResponse response) {
		String rno = request.getParameter("rno");
		String type = request.getParameter("type");
		String page = request.getParameter("page");
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		
		return "redirect:"+urls[Integer.parseInt(type)]+rno+"&page="+page;
	}
}
