package kr.or.ksmart37.ksmart_mybatis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ksmart37.ksmart_mybatis.dto.Goods;
import kr.or.ksmart37.ksmart_mybatis.dto.Member;
import kr.or.ksmart37.ksmart_mybatis.mapper.GoodsMapper;
import kr.or.ksmart37.ksmart_mybatis.mapper.MemberMapper;

@Service
@Transactional
public class GoodsService {
	@Autowired
	private GoodsMapper goodsMapper;
	
	@Autowired
	private MemberMapper memberMapper;
	
	public List<Goods> getGoodsList(){
		
		return goodsMapper.getGoodsList();
	}
	
	public Goods getGoodsByCode(String goodsCode) {
		//goodsMapper에 코드에 일치 하는 상품정보가 담긴 객체 받아오기 
		Goods goods = goodsMapper.getGoodsByCode(goodsCode);
		//Goods객체를 return 해준다.
		return goods;
	}
	
	public String modifyGoods(Goods goods) {
		String updateChecked = "회원수정 실패";
		int result = goodsMapper.ModifyGoods(goods);
		if(result > 0) updateChecked = "회원수정성공";
		return updateChecked;
	}
	public String removeGoods(String goodsCode, String memberId, String memberPw) {
		String result = "상품 삭제 실패";
		
		Member member = memberMapper.getMemberById(memberId);
		if(member != null && member.getMemberPw() != null && memberPw.equals(member.getMemberPw())) {
			int removeCheck = goodsMapper.removeGoods(goodsCode);
			
			if(removeCheck>0)result ="상품삭제완료";
			
			
		}
		return result;
		
	}


}
