package com.sist.model;

import com.sist.controller.Controller;
import com.sist.controller.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 *       JSP (Java Server pages)
 *        JSP
 *        1) 자바기반의 서버사이드 웹 기술
 *        2) HTML 내부에 자바코드를 첨부해서 동적페이지 생성
 *        3) Servelet을 쉽게 작성하기 위한 표현 언어
 *        4) JSP 파일은 _jspService()메소드 영역
 *      1. 구성 요소
 *         1) 선언문
 *            <%! %> : 멤버 변수 , 메소드 선언
 *           2) 스크립트릿
 *              <%  %> : 실행 코드 => _jspService(){여기에 첨부}
 *           3) 표현식
 *              <%= %> : 데이터를 화면에 출력
 *              => 변경 : out.println(<%=%>) => ; 을 사용할 수 없다
 *           4) 주석 : <%-- --%> 사라진다 <!-- -->는 남아 있다
 *                     | 자바 포함        | HTML만 주석
 *       2. JSP의 지시자
 *          <%@ page %> : JSP파일에 대한 정보
 *                     속성
 *                     = contentType
 *                     = import
 *                     = errorPage : 에러발생시 이동하는 파일을 설정
 *                      errorPage="error.jsp"
 *                      => 기본 : forward를 사용하고 있다
 *                      *** URL주소 (request를 가지고 있다)
 *                      
 */
@Controller
public class MainModel {
	@RequestMapping("main/main.do")
	  public String main_main(HttpServletRequest request, HttpServletResponse response) {
		  
		  request.setAttribute("main_jsp", "../main/home.jsp");
		  return "../main/main.jsp";
	  }
}
