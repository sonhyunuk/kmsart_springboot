package kr.or.ksmart37.ksmart_mybatis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ksmart37.ksmart_mybatis.dto.Goods;
import kr.or.ksmart37.ksmart_mybatis.mapper.GoodsMapper;
import kr.or.ksmart37.ksmart_mybatis.service.GoodsService;

@Controller
public class GoodsController {
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private GoodsMapper goodsMapper;
	
	@GetMapping("/ModifyGoods")
	public String modifyGoods(@RequestParam(name = "goodsCode",required = false)String goodsCode
								,Model model) {
		System.out.println("상품 수정화면에 입력 받은 값 ->" +goodsCode);
		
		Goods goods = goodsService.getGoodsByCode(goodsCode);
		model.addAttribute("goods",goods);
		return "goods/gUpdate";
	}
	@GetMapping("/RemoveGoods")
	public String removeGoods(@RequestParam(name = "goodsCode",required = false)String goodsCode
			,Model model) {
		Goods goods = goodsService.getGoodsByCode(goodsCode);
		model.addAttribute("goodsCode",goodsCode);
		model.addAttribute("goodsSellerId",goods.getGoodsSellerId());
		
		return "goods/gDelete";
	}
	
	@PostMapping("/sellerModifyGoods")
	public String updateGoods(Goods good) {
		
		String result = goodsService.modifyGoods(good);
		System.out.println("처리결과 확인 1이면 o"+result);
		
		return "redirect:/sellerList";
	}
	@GetMapping("/goodsList")
	public String getGoodsList(Model model) {
		model.addAttribute("title","상품목록");
		List<Goods> goodsList = goodsService.getGoodsList();
		System.out.println("==================================상품목록=====================");
		System.out.println(goodsList);
		System.out.println("=============================================================");
		model.addAttribute("goodsList",goodsMapper.getGoodsList());
		
		return "redirect:/sellerList";
	}


}
