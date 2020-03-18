package com.shopping.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.shopping.domain.MemberVO;

public class AdminInterceptor extends HandlerInterceptorAdapter{

	// 컨트롤러 실행 전에 실행되는 preHandle 메서드
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		HttpSession session = request.getSession();
		MemberVO member = (MemberVO)session.getAttribute("member");
		
		if(member == null) {
			response.sendRedirect("/member/signin");
			return false;
		}
		
		if(member.getVerify() != 9) {
			response.sendRedirect("/");
			return false;
		}
		
		return true;
	}
	
}
