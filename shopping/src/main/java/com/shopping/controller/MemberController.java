package com.shopping.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopping.domain.MemberVO;
import com.shopping.service.MemberService;

@Controller
@RequestMapping(value="/member/*")
public class MemberController {

	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Inject
	MemberService service;
	
	@Bean
	BCryptPasswordEncoder passEncoder() {
		return new BCryptPasswordEncoder();
	}; // 암호화
	
	// 회원가입 get
	@RequestMapping(value="/signup", method = RequestMethod.GET)
	public void getSignup() throws Exception {
		logger.info("getSignup()");
	}
	
	// 회원가입 post
	@RequestMapping(value="/signup", method = RequestMethod.POST)
	public String postSignup(MemberVO vo) throws Exception {
		logger.info("postSignup()");
		
		String inputPass = vo.getUserPass();
		String pass = passEncoder().encode(inputPass);
		vo.setUserPass(pass); // 암호화 시켜서 vo에 넘겨줌
		
		logger.info(vo.getUserId() + vo.getUserPass() + vo.getUserName() + vo.getUserPhone());
		
		service.signup(vo);
		
		return "redirect:/";
	}
	
	// 로그인  get
	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	public void getSignin() throws Exception {
	 logger.info("get signin");
	}

	// 로그인 post
	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public String postSignin(MemberVO vo, HttpServletRequest req, RedirectAttributes rttr) throws Exception {
	 logger.info("post signin");
	   
	 MemberVO login = service.signin(vo);  
	 HttpSession session = req.getSession();

	 boolean passMatch = passEncoder().matches(vo.getUserPass(), login.getUserPass());
	 // ^ 사용자가 입력한 패스워드와 데이터베이스에 저장된 패스워드를 비교 => 동일 true, 동일x false
	 logger.error(passMatch+"");
	 
	 
	 if(login != null && passMatch) { 
	  session.setAttribute("member", login); //아이디나 패스워드가 모두 맞아야실행
	 } else {
	  session.setAttribute("member", null);	// 세션값 제거
	  rttr.addFlashAttribute("msg", false);	// 다른 페이지로 이동될때 msg의 값 false (다른 페이지: redirect:/mebmer/signin)
	  
	  return "redirect:/member/signin";
	 }  
	 
	 return "redirect:/";
	}
	  
	// 로그아웃
	@RequestMapping(value = "/signout", method = RequestMethod.GET)
	public String signout(HttpSession session) throws Exception {
	 logger.info("get logout");
	 
	 service.signout(session);
	   
	 return "redirect:/";
	}
}
