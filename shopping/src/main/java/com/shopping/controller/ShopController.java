package com.shopping.controller;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.shopping.domain.GoodsViewVO;
import com.shopping.service.ShopService;

@Controller
@RequestMapping(value="/shop/*")
public class ShopController {
	
	private static final Logger logger = LoggerFactory.getLogger(ShopController.class);

	@Inject
	ShopService service;
	
	// 카테고리별 상품 리스트
	@RequestMapping(value="/list", method = RequestMethod.GET)
	public void getList(@RequestParam("c") int cateCode, @RequestParam("1") int level, Model model) throws Exception {
		logger.info("getList() 실행");
		
		List<GoodsViewVO> list = null;
		
	}
	
	// 상품 조회
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public void getView(@RequestParam("n") int gdsNum, Model model) throws Exception {
	 logger.info("get view");
	 
	 GoodsViewVO view = service.goodsView(gdsNum);
	 model.addAttribute("view", view);
	}
}
