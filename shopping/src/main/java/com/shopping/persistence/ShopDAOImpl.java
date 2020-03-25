package com.shopping.persistence;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.shopping.domain.GoodsViewVO;
import com.shopping.domain.ReplyListVO;
import com.shopping.domain.ReplyVO;


@Repository
public class ShopDAOImpl implements ShopDAO {
	
	private static String namespace = "com.shopping.mappers.shopMapper";
	
	@Inject
	private SqlSession sql;

	//// 카테고리별 상품 리스트 : 1차분류
	@Override
	public List<GoodsViewVO> list(int cateCode, int cateCodeRef) throws Exception {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("cateCode", cateCode);
		map.put("cateCodeRef", cateCodeRef);
		
		return sql.selectList(namespace+".list_1", map);
	}
	
	// 카테고리별 상품 리스트 : 2차분류
	@Override
	public List<GoodsViewVO> list(int cateCode) throws Exception {
		return sql.selectList(namespace+".list_2", cateCode);
	}

	// 상품조회
	@Override
	public GoodsViewVO goodView(int gdsNum) throws Exception {
		return sql.selectOne("com.shopping.mappers.adminMapper.goodsView", gdsNum);
	}

	// 댓글작성
	@Override
	public void registReply(ReplyVO reply) throws Exception {
		sql.insert(namespace+".registReply", reply);
	}

	// 댓글 목록
	@Override
	public List<ReplyListVO> replyList(int gdsNum) throws Exception {
		return sql.selectList(namespace+".replyList", gdsNum);
	}



	


	
}
