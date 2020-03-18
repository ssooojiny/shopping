package com.shopping.controller;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.shopping.domain.CategoryVO;
import com.shopping.service.AdminService;

import net.sf.json.JSONArray;

@Controller
@RequestMapping(value="/admin/*")
public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	@Inject
	AdminService adminService;
	
	// 관리자 화면
	@RequestMapping(value="/index", method = RequestMethod.GET)
	public void getIndex() throws Exception {
		logger.info("getIndex()");
	}
	
	// 상품 등록
	@RequestMapping(value="/goods/register", method = RequestMethod.GET)
	public void getGoodsRegister(Model model) throws Exception {
		logger.info("getGoodsRegister()");
		
		List<CategoryVO> categoryList = null;
		categoryList = adminService.category();
		model.addAttribute("categoryList", JSONArray.fromObject(categoryList));
	}
}
