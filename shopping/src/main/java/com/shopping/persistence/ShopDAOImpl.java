package com.shopping.persistence;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.shopping.domain.GoodsViewVO;


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



	


	
}
