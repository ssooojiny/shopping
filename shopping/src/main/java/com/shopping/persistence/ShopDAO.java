package com.shopping.persistence;

import java.util.List;

import com.shopping.domain.GoodsViewVO;
import com.shopping.domain.ReplyListVO;
import com.shopping.domain.ReplyVO;

public interface ShopDAO {
	
	// 카테고리별 상품 리스트 : 1차분류
	public List<GoodsViewVO> list (int cateCode, int cateCodeRef) throws Exception;
	
	// 카테고리별 상품 리스트 : 2차분류
	public List<GoodsViewVO> list (int cateCode) throws Exception;

	// 상품 조회
	public GoodsViewVO goodView(int gdsNum) throws Exception;
	
	// 댓글 작성
	public void registReply(ReplyVO reply) throws Exception;
	
	// 댓글 리스트
	public List<ReplyListVO> replyList(int gdsNum) throws Exception;
	

}
