package com.shopping.controller;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.shopping.domain.CategoryVO;
import com.shopping.domain.GoodsVO;
import com.shopping.domain.GoodsViewVO;
import com.shopping.service.AdminService;
import com.shopping.utils.UploadFileUtils;

import net.sf.json.JSONArray;

@Controller
@RequestMapping(value="/admin/*")
public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	@Inject
	AdminService adminService;
	
	@Resource(name="uploadPath")
	private String uploadPath;
	
	// 관리자 화면
	@RequestMapping(value="/index", method = RequestMethod.GET)
	public void getIndex() throws Exception {
		logger.info("getIndex()");
	}
	
	// 상품 등록
	@RequestMapping(value="/goods/register", method = RequestMethod.GET)
	public void getGoodsRegister(Model model) throws Exception {
		logger.info("getGoodsRegister()");
		
		List<CategoryVO> category = null;
		category = adminService.category();
		model.addAttribute("category", JSONArray.fromObject(category));
	}
	
	// 상품 등록 POST
	@RequestMapping(value="/goods/register", method = RequestMethod.POST)
	public String postGoodsRegister(GoodsVO vo, MultipartFile file) throws Exception {
		
		// 파일용 input박스에 등록된 파일의 정보를 가져오고, UploadFileUtils.java를 통해 폴더를
		// 생성한 후 원본 파일과 썸네일을 저장한 뒤, 이 경로를 데이터베이스에 전달
		String imgUploadPath = uploadPath + File.separator + "imgUpload";
		String ymdPath = UploadFileUtils.calcPath(imgUploadPath);
		String fileName = null;

		if(file.getOriginalFilename() != null && file.getOriginalFilename() != "") {
		 fileName = UploadFileUtils.fileUpload(imgUploadPath, file.getOriginalFilename(), file.getBytes(), ymdPath); 
		} else {
		 fileName = uploadPath + File.separator + "images" + File.separator + "none.png";
		}

		vo.setGdsImg(File.separator + "imgUpload" + ymdPath + File.separator + fileName);
		vo.setGdsThumbImg(File.separator + "imgUpload" + ymdPath + File.separator + "s" + File.separator + "s_" + fileName);
		
		adminService.register(vo);
		
		return "redirect:/admin/index";
	}
	
	// 상품 목록 GET
	@RequestMapping(value="/goods/list", method = RequestMethod.GET)
	public void getGoodsList(Model model) throws Exception {
		logger.info("getGoodsList()");
		List<GoodsVO> goodsList = adminService.goodsList();
		model.addAttribute("goodsList", goodsList);
	}
	
	// 상품 조회
	@RequestMapping(value="/goods/view", method = RequestMethod.GET)
	public void getGoodsView(@RequestParam("n") int gdsNum, Model model) throws Exception {
		logger.info("get goods view");
		
//		GoodsVO goods = adminService.goodsView(gdsNum);
		GoodsViewVO goods = adminService.goodsView(gdsNum);
		
		model.addAttribute("goods", goods);
	}
	
	// 상품 수정 GET
	@RequestMapping(value="/goods/modify", method = RequestMethod.GET)
	public void getGoodsModify(@RequestParam("n") int gdsNum, Model model) throws Exception {
		logger.info("getGoodsModify()");
		
		
//		GoodsVO goods = adminService.goodsView(gdsNum);
		GoodsViewVO goods = adminService.goodsView(gdsNum);
		model.addAttribute("goods", goods);
		
		List<CategoryVO> category = null;
		category = adminService.category();
		model.addAttribute("category", JSONArray.fromObject(category));
	}
	
	// 상품 수정 POST
	@RequestMapping(value = "/goods/modify", method = RequestMethod.POST)
	public String postGoodsModify(GoodsVO vo, MultipartFile file, HttpServletRequest req) throws Exception {
	 logger.info("post goods modify");
	 
	// 새로운 파일이 등록되었는지 확인 <---------에.........러.........나.........ㅅㅂ........
	 if(file.getOriginalFilename() != null && file.getOriginalFilename() != "" && !file.isEmpty()) {
	 
		 // 기존 파일을 삭제
	  new File(uploadPath + req.getParameter("gdsImg")).delete();
	  new File(uploadPath + req.getParameter("gdsThumbImg")).delete();
	  
	  // 새로 첨부한 파일을 등록
	  String imgUploadPath = uploadPath + File.separator + "imgUpload";
	  String ymdPath = UploadFileUtils.calcPath(imgUploadPath);
	  String fileName = UploadFileUtils.fileUpload(imgUploadPath, file.getOriginalFilename(), file.getBytes(), ymdPath);
	  
	  vo.setGdsImg(File.separator + "imgUpload" + ymdPath + File.separator + fileName);
	  vo.setGdsThumbImg(File.separator + "imgUpload" + ymdPath + File.separator + "s" + File.separator + "s_" + fileName);
	  
	 } else {  // 새로운 파일이 등록되지 않았다면
	  // 기존 이미지를 그대로 사용
	  vo.setGdsImg(req.getParameter("gdsImg"));
	  vo.setGdsThumbImg(req.getParameter("gdsThumbImg"));
	  
	 }

	 adminService.goodsModify(vo);
	 
	 return "redirect:/admin/index";
	}
	
	// 상품 삭제
	@RequestMapping(value="/goods/delete", method = RequestMethod.POST)
	public String postGoodsDelete(@RequestParam("n") int gdsNum) throws Exception {
		
		adminService.goodsDelete(gdsNum);
		
		return "redirect:/admin/index";
	}
}
