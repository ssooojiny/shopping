package com.shopping.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.shopping.domain.GoodsViewVO;
import com.shopping.persistence.ShopDAO;

@Service
public class ShopServiceImpl implements ShopService {
	
	@Inject
	private ShopDAO dao;
	
	// 카테고리별 상품 리스트
	@Override
	public List<GoodsViewVO> list(int cateCode, int level) throws Exception {
		
		int cateCodeRef = 0;
		if(level == 1) { // 1차분류이면
			cateCodeRef = cateCode;
			return dao.list(cateCode, cateCodeRef);
		}else {	// 2차분류
		return dao.list(cateCode);
		}
	}

	

}
