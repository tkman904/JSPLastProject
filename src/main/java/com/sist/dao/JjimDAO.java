package com.sist.dao;

import java.util.*;

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
}
