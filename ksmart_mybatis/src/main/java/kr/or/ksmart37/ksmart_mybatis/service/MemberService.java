package kr.or.ksmart37.ksmart_mybatis.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ksmart37.ksmart_mybatis.dto.Member;
import kr.or.ksmart37.ksmart_mybatis.mapper.MemberMapper;

@Service
@Transactional
public class MemberService {
	// DI
	@Autowired 
	private MemberMapper MemberMapper;
	
	public List<Member> getSellerList(){
		List<Member> sellerList = MemberMapper.getSellerList();
		
		return sellerList;
	}
	
	public String removeMember(String memberId, String memberPw, String memberLevel) {
		String result = "회원 삭제 실패";
		
		// id member 테이블 조회하고 조회한 결괏값 중 비밀번호와 화면에서 입력받은 비밀번호가 일치하면 삭제 처리
		
		Member member = MemberMapper.getMemberById(memberId);
		
		if(member != null && member.getMemberPw() != null && memberPw.equals(member.getMemberPw())) {
			int removeCheck = MemberMapper.removeLoginById(memberId);
			if("판매자".equals(memberLevel)) MemberMapper.removeGoodsById(memberId);
			if("구매자".equals(memberLevel)) MemberMapper.removeOrderById(memberId);
			removeCheck += MemberMapper.removeMemberById(memberId);
			
			if(removeCheck > 0) result = "회원 삭제 성공";
		}
		
		return result;
	}
	
	public String removeMember(Member member) {
		return "";
	}
	
	public String modifyMember(Member member) {
		String result = "회원수정 실패";
		
		int modifyCheck = MemberMapper.modifyMember(member);
		
		if(modifyCheck > 0) result = "회원수정 성공";
		return result;
	}
	
	public Member getMemberById(String memberId) {
		Member member = MemberMapper.getMemberById(memberId);
		
		return member;
	}
	
	public String member_insert(Member member) {
			String insertCheck = "회원가입 실패";
			if(member != null) {
				int result = MemberMapper.member_insert(member);
				if(result > 0) insertCheck = "회원가입 성공";
			}
		return insertCheck;
	}
	
	public List<Member> getMemberList(){
		
		List<Member> memberList = MemberMapper.getMemberList();
		int listSize = memberList.size();
		for(int i=0; i<listSize; i++) {
			if("1".equals(memberList.get(i).getMemberLevel())) {
				memberList.get(i).setMemberLevel("관리자");
			}else if("2".equals(memberList.get(i).getMemberLevel())) {
				memberList.get(i).setMemberLevel("판매자");
			}else if("3".equals(memberList.get(i).getMemberLevel())) {
				memberList.get(i).setMemberLevel("구매자");				
			}else {
				memberList.get(i).setMemberLevel("일반회원");
			}
		}
		
		return memberList;
	}
}
