package com.shopping.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shopping.domain.GoodsViewVO;
import com.shopping.domain.MemberVO;
import com.shopping.domain.ReplyListVO;
import com.shopping.domain.ReplyVO;
import com.shopping.service.ShopService;

@Controller
@RequestMapping(value="/shop/*")
public class ShopController {
	
	private static final Logger logger = LoggerFactory.getLogger(ShopController.class);

	@Inject
	ShopService service;
	
	// 카테고리별 상품 리스트
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public void getList(@RequestParam("c") int cateCode,
						@RequestParam("l") int level, Model model) throws Exception{
		logger.info("getList() 실행");
		
		List<GoodsViewVO> list = null;
		list = service.list(cateCode, level);
		
		model.addAttribute("list", list);
	}
	
	// 상품조회 GET
	@RequestMapping(value="/view", method=RequestMethod.GET)
	public void getView(@RequestParam("n") int gdsNum, Model model) throws Exception {
		logger.info("getView() 실행");
		
		GoodsViewVO view = service.goodsView(gdsNum);
		model.addAttribute("view", view);
		
		/*
		List<ReplyListVO> reply = service.replyList(gdsNum);
		model.addAttribute("reply", reply);
		*/
	}
	
	/*
	// 상품조회 - 댓글작성 POST
	@RequestMapping(value="/view", method=RequestMethod.POST)
	public String registReply(ReplyVO reply, HttpSession session) throws Exception {
		logger.info("registReply() post 실행");
		
		MemberVO member = (MemberVO)session.getAttribute("member");
		reply.setUserId(member.getUserId());
		
		service.registReply(reply);
		
		return "redirect:/shop/view?n=" + reply.getGdsNum();
	}
	*/
	
	// 상품조회 - 댓글 작성 POST
	@ResponseBody
	@RequestMapping(value="/view/registReply", method = RequestMethod.POST)
	public void registReply(ReplyVO reply, HttpSession session) throws Exception {
		logger.info("registReply() 실행");
		
		MemberVO member = (MemberVO)session.getAttribute("member");
		reply.setUserId(member.getUserId());
		
		service.registReply(reply);
	}

	// 댓글 목록
	@ResponseBody
	@RequestMapping(value="/view/replyList", method=RequestMethod.GET)
	public List <ReplyListVO> getReplyList(@RequestParam("n") int gdsNum) throws Exception {
		logger.info("getReplyList() 실행");
		
		List<ReplyListVO> reply = service.replyList(gdsNum);
		
		System.out.println(reply.get(0).getUserId());
		
		return reply;
	}
}
