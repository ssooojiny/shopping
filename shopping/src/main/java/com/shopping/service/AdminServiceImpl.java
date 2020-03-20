package com.shopping.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.shopping.domain.CategoryVO;
import com.shopping.domain.GoodsVO;
import com.shopping.domain.GoodsViewVO;
import com.shopping.persistence.AdminDAO;

@Service
public class AdminServiceImpl implements AdminService {

		@Inject
		private AdminDAO dao;
		
		// 카테고리
		@Override
		public List<CategoryVO> category() throws Exception {
			return dao.category();
		}

		// 상품등록
		@Override
		public void register(GoodsVO vo) throws Exception {
			dao.register(vo);
		}

		// 상품 목록
		@Override
		public List<GoodsViewVO> goodsList() throws Exception {
			System.out.println("서비스");
			return dao.goodsList();
		}

		/*
		// 상품 조회
		@Override
		public GoodsVO goodsView(int gdsNum) throws Exception {
			return dao.goodsView(gdsNum);
		}
		*/
		
		// 상품 조회 + 카테고리 조인
		@Override
		public GoodsViewVO goodsView(int gdsNum) throws Exception {
			return dao.goodsView(gdsNum);
		}

		// 상품 수정
		@Override
		public void goodsModify(GoodsVO vo) throws Exception {
			System.out.println(vo.toString());
			dao.goodsModify(vo);
		}

		// 상품삭제
		@Override
		public void goodsDelete(int gdsNum) throws Exception {
			System.out.println("Service -> gdsNum : " + gdsNum);
			dao.goodsDelete(gdsNum);
		}

}
