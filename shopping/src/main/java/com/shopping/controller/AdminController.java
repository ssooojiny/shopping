package com.shopping.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		String fileName = null; // 기본 경로와 별개로 작성되는 경로 + 파일 이름

		if(file.getOriginalFilename() != null && file.getOriginalFilename() != "") {
			// 파일 인풋박스에 첨부된 파일이 없으면 (=첨부된 파일 이름이 없으면)
			fileName = UploadFileUtils.fileUpload(imgUploadPath, file.getOriginalFilename(), file.getBytes(), ymdPath); 
			
			// gdsImg에 원본 파일 경로 + 파일명 저장
			vo.setGdsImg(File.separator + "imgUpload" + ymdPath + File.separator + fileName);
			// gdsThumbImg에 썸네일 파일 경로 + 썸네일 파일명 저장
			vo.setGdsThumbImg(File.separator + "imgUpload" + ymdPath + File.separator + "s" + File.separator + "s_" + fileName);
			  
		} else {
		 fileName = uploadPath + File.separator + "images" + File.separator + "none.png";
		}

		// gdsImg에 원본파이 경로 + 파일명 저장
		vo.setGdsImg(File.separator + "imgUpload" + ymdPath + File.separator + fileName);
		// gdsThumbImg에 썸네일 파일 경로 + 썸네일 파일명 저장
		vo.setGdsThumbImg(File.separator + "imgUpload" + ymdPath + File.separator + "s" + File.separator + "s_" + fileName);
	
			adminService.register(vo);
			
			return "redirect:/admin/goods/list";
	}
	
	// 상품 목록 GET
	@RequestMapping(value="/goods/list", method = RequestMethod.GET)
	public void getGoodsList(Model model) throws Exception {
		logger.info("getGoodsList()");
		List<GoodsViewVO> goodsList = adminService.goodsList();
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

		// 새로운 파일이 등록되었는지 확인 
		 if(file.getOriginalFilename() != null && !file.getOriginalFilename().equals("")) {
		 
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
		
		return "redirect:/admin/goods/list";
	}
	
	// ck 에디터에서 파일 업로드
	@RequestMapping(value = "/goods/ckUpload", method = RequestMethod.POST)
	public void postCKEditorImgUpload(HttpServletRequest req, HttpServletResponse res, @RequestParam MultipartFile upload) throws Exception {
		logger.info("post CKEditor img upload");
		
		// 랜덤 문자 생성
		UUID uid = UUID.randomUUID();
		
		OutputStream out = null;
		PrintWriter printWriter = null;
		
		
		// 인코딩
		res.setCharacterEncoding("utf-8");
		res.setContentType("text/html;charset=utf-8");
		
		try {
			String fileName = upload.getOriginalFilename(); // 파일 이름 가져오기
			byte[] bytes = upload.getBytes();
			
			logger.info(fileName);
			
			// 업로드 경로
			String ckUploadPath = uploadPath + File.separator + "ckUpload" + File.separator + uid + "_" + fileName;
			  
			  out = new FileOutputStream(new File(ckUploadPath));
			  out.write(bytes);
			  out.flush();  // out에 저장된 데이터를 전송하고 초기화
			  
			  logger.info(ckUploadPath);
			  
//			  String callback = req.getParameter("CKEditorFuncNum");
			  printWriter = res.getWriter();
			  String fileUrl = "/ckUpload/" + uid + "_" + fileName;  // 작성화면
			  
			  logger.info(fileUrl);
			  
			
			  // 업로드시 메시지 출력 
//			  printWriter.println("<script type='text/javascript'>" 
//					  + "window.parent.CKEDITOR.tools.callFunction(" 
//					  + callback+",'"
//					  + fileName+",'"
//					  + fileUrl+"','이미지를 업로드하였습니다.')" 
//					  +"</script>");

			  printWriter.println("{\"filename\" : \""+fileName+"\", \"uploaded\" : 1, \"url\":\""+fileUrl+"\"}");
			  // 업그레이드 된 이후에는 이렇게 제이슨으로 데이터를 보내야 한다고 함... 위의 printWriter는 옛버전.....
			 
			  			  
			  printWriter.flush();
			  
			 } catch (IOException e) { 
				 e.printStackTrace();
			 } finally {
			  try {
			   if(out != null) { 
				   out.close();
				   }
			   if(printWriter != null) {
				   printWriter.close(); 
				   }
			  } catch(IOException e) { 
				  e.printStackTrace();
				  }
			 }
			 
			 return; 
			}
	}
