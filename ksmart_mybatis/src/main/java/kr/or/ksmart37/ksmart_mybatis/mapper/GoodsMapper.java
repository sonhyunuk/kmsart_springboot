package kr.or.ksmart37.ksmart_mybatis.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ksmart37.ksmart_mybatis.dto.Goods;

@Mapper
public interface GoodsMapper {
	public Goods getGoodsByCode(String goodsCode);
	public List<Goods>getGoodsList();
	public int ModifyGoods(Goods goods);
	public int removeGoods(String goodsCode);
	

}
