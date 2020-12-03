package kr.or.ksmart37.ksmart_mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ksmart37.ksmart_mybatis.dto.Member;

@Mapper
public interface MemberMapper {
	List<Member> getSellerList();

	int removeLoginById(String memberId);
	
	int removeOrderById(String memberId);
	
	int removeGoodsById(String memberId);
	
	int removeMemberById(String memberId);
	
	int modifyMember(Member member);
	
	public Member getMemberById (String memberId);
	
	public int member_insert(Member member);
	
	public List<Member> getMemberList();
}
