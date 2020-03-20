package com.shopping.persistence;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.shopping.domain.CategoryVO;
import com.shopping.domain.GoodsVO;
import com.shopping.domain.GoodsViewVO;

@Repository
public class AdminDAOImpl implements AdminDAO {
	
	@Inject
	private SqlSession sql;
	
	private static final String namespace = "com.shopping.mappers.adminMapper";

	@Override
	public List<CategoryVO> category() throws Exception {
		return sql.selectList(namespace+".category");
	}

	// 상품 등록
	@Override
	public void register(GoodsVO vo) throws Exception {
		sql.insert(namespace+".register",vo);
	}

	// 상품 목록
	@Override
	public List<GoodsViewVO> goodsList() throws Exception {
		return sql.selectList(namespace + ".goodsList");
	}

	/*
	 // 상품 조회
	  @Override 
	  public GoodsVO goodsView(int gdsNum) throws Exception { 
	  	return sql.selectOne(namespace + ".goodsView", gdsNum); 
	  }
	 */
	
	 // 상품 조회 + 카테고리 조인
	@Override 
	public GoodsViewVO goodsView(int gdsNum) throws Exception { 
		return sql.selectOne(namespace + ".goodsView", gdsNum); 
	 }

	// 상품 수정
	@Override
	public void goodsModify(GoodsVO vo) throws Exception {
		System.out.println(vo.toString());
		sql.update(namespace+".goodsModify", vo);
	}

	// 상품 삭제
	@Override
	public void goodsDelete(int gdsNum) throws Exception {
		System.out.println("DAOImple -> gdsNum : " +gdsNum);
		sql.delete(namespace+".goodsDelete", gdsNum);
	}
	  
	  

}
