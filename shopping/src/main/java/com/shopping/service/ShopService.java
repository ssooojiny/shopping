package com.shopping.service;

import java.util.List;

import com.shopping.domain.GoodsViewVO;
import com.shopping.domain.ReplyListVO;
import com.shopping.domain.ReplyVO;

public interface ShopService {
	
	// 카테고리별 상품 리스트 
	public List<GoodsViewVO> list(int cateCode, int level)  throws Exception;
		
	// 상품조회
	public GoodsViewVO goodsView(int gdsNum) throws Exception;
	
	// 댓글 작성
	public void registReply(ReplyVO reply) throws Exception;
	
	// 댓글 목록
	public List<ReplyListVO> replyList(int gdsNum) throws Exception;

	// 댓글 삭제
	public void deleteReply(ReplyVO reply) throws Exception;
	
	// 아이디 체크
	public String idCheck(int repNum) throws Exception;
}

