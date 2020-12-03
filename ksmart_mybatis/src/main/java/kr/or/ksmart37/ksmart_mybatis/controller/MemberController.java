package kr.or.ksmart37.ksmart_mybatis.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ksmart37.ksmart_mybatis.dto.Goods;
import kr.or.ksmart37.ksmart_mybatis.dto.Member;
import kr.or.ksmart37.ksmart_mybatis.service.GoodsService;
import kr.or.ksmart37.ksmart_mybatis.service.MemberService;

@Controller
public class MemberController {
	@Autowired
	private MemberService memberService;
	@Autowired
	private GoodsService goodsService;
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
	
	@PostMapping("/login")
	public String login(@RequestParam(name = "memberId",required = false)String memberId,
						@RequestParam(name = "memberPw", required = false)String memberPw,
						HttpSession session
						,RedirectAttributes rAttr){
			System.out.println("로그인 화면에서 입력받은 id"+memberId);
			System.out.println("로그인 화면에서 입력받은 pw"+memberPw);
			
		Member member = memberService.getMemberById(memberId);
		if(memberId != null && memberPw != null && member !=null && member.getMemberPw() != null && memberPw.equals(member.getMemberPw())) {
			session.setAttribute("SID", memberId);
			session.setAttribute("SLEVEL", member.getMemberLevel());
			session.setAttribute("SNAME", member.getMemberName());
			System.out.println(memberId+"로그인성공");
		}else {
			rAttr.addAttribute("result","입력하신정보는 없습니다.");
			System.out.println(memberId+"로그인실패");
			return "redirect:/login";
		}
		return "redirect:/";
	}
	
	@GetMapping("/login")
	public String login(Model model,
						@RequestParam(name = "result",required = false)String result) {
		model.addAttribute("title","로그인화면");
		if(result!= null) model.addAttribute("result",result);
		return "login/login";
	}
	
	@PostMapping("/removeGoods")
	public String removeGoods(@RequestParam(name = "goodsCode", required = false) String goodsCode,
							@RequestParam(name = "memberId", required = false) String memberId,
							@RequestParam(name = "memberPw", required = false) String memberPw) {
		System.out.println("상품삭제화면에서 입력받은값 "+goodsCode);
		System.out.println("상품삭제화면에서 입력받은값 "+memberId);
		System.out.println("상품삭제화면에서 입력받은값 "+memberPw);
		
		String result = goodsService.removeGoods(goodsCode, memberId, memberPw);
		System.out.println("처리결과"+result);
		
		return "redirect:/sellerList";
	}
	
	@GetMapping("/sellerModifyGoods")
	public ModelAndView sellerModify(@RequestParam(name = "goodsCode", required = false) String goodsCode) {
		System.out.println("판매자별 상품현황 페이지에서 요청 받은 값 >>"+goodsCode);
		Goods goods = goodsService.getGoodsByCode(goodsCode);
		//goodsService에서 코드에 일치하는 상품정보가 담긴 객체 받아오기 
		
		//모델앤드 뷰 로 할수도 있다.
		ModelAndView mav = new ModelAndView();
		mav.addObject("title","상품수정");
		mav.addObject("goods",goods);
		mav.setViewName("goods/gUpdate");
		
		return mav;
	}
	@GetMapping("/sellerRemoveGoods")
	public ModelAndView sellerRemoveGoods(@RequestParam(name = "goodsCode", required = false) String goodsCode) {
		System.out.println("판매자별 상품현황 페이지에서 요청 받은 값 >>"+goodsCode);
		Goods goods = goodsService.getGoodsByCode(goodsCode);
		//goodsService에서 코드에 일치하는 상품정보가 담긴 객체 받아오기 
		
		//모델앤드 뷰 로 할수도 있다.
		ModelAndView mav = new ModelAndView();
		mav.addObject("title","상품삭제");
		mav.addObject("goodsCode",goods.getGoodsCode());
		mav.addObject("goodsSellerId",goods.getGoodsSellerId());
		mav.setViewName("goods/gDelete");
		
		return mav;
	}
	
	@GetMapping("/sellerList")
	public String sellerList(Model model) {
		List<Member> sellerList = memberService.getSellerList();
		System.out.println("=============판매자별 상품현황==================");
		System.out.println(sellerList);
		System.out.println("===========================================");
		model.addAttribute("title","판매자상품현황");
		model.addAttribute("sellerList", sellerList);
		
		return "member/s_list";
		
		//return "member/sellerList";
	}
	
	@PostConstruct
	public void initialize() {
		System.out.println("--------------------------------------------------------------");
		System.out.println("MemberController 객체 생성");
		System.out.println("--------------------------------------------------------------");
	}
	
	@GetMapping("/removeMember")
	public String removeMember(Model model
			, @RequestParam(name = "memberId", required = false) String memberId
			,@RequestParam(name = "memberLevel", required = false) String memberLevel){
		
		model.addAttribute("title", "회원 탈퇴");
		model.addAttribute("memberId", memberId);
		model.addAttribute("memberLevel", memberLevel);
		return "member/mdelete";
	}
	
	@PostMapping("/removeMember")
	public String removeMember(@RequestParam(name ="memberId", required = false) String memberId
								,@RequestParam(name = "memberPw", required = false) String memberPw
								,@RequestParam(name = "memberLevel", required = false) String memberLevel){
				System.out.println("회원탈퇴화면에서 입력받은 값 (id) ---->" + memberId);
				System.out.println("회원탈퇴화면에서 입력받은 값 (pw) ---->" + memberPw);
				System.out.println("회원탈퇴화면에서 입력받은 값 (level) ---->" + memberLevel);
								
				// 서비스 계층에서 권한 별 삭제 처리 후 결과
				String result = memberService.removeMember(memberId, memberPw, memberLevel);
								
								
				System.out.println(result);
								
				return "redirect:/memberList";
	}
	
	
	@GetMapping("/modifyMember")
	public String modifyMember(Model model
								, @RequestParam(name ="memberId", required = false) String memberId) {
		System.out.println("회원 수정폼에 보여질 회원 아이디" + memberId);
		Member member = memberService.getMemberById(memberId);
		
		System.out.println("db에서 검색한 회원정보 -->" + member);
		
		model.addAttribute("title", "회원수정화면");
		// db에서 검색한 회원정보
		model.addAttribute("member", member);
		return "member/mUpdate";
	}
	
	@PostMapping("/modifyMember")
	public String modifyMember(Member member) {
		System.out.println("회원 수정 폼에서 입력받은 값" + member);
		
		// update 처리
		String result = memberService.modifyMember(member);
		
		// update 결과
		System.out.println(result);
		return "redirect:/memberList";
	}
	
	@RequestMapping(value = "/member_insert", method = RequestMethod.POST)
	public String member_insert(Member member, @RequestParam(name = "memberId", required = false) String memberId) {
		System.out.println("회원가입화면에서 입력받은 값" + member);
		System.out.println(memberId);
		String result = memberService.member_insert(member);
		System.out.println(result);
		return"redirect:/memberList";
	}
	
	@GetMapping("/memberInsert")
	public String memberInsert(Model model) {
		model.addAttribute("title", "회원 가입");
		return "member/member_insert";
	}
	
	@GetMapping("/memberList")
	public String getMemberList(Model model) {
		List<Member> memberList = memberService.getMemberList();
		System.out.println(memberList);
		model.addAttribute("title", "회원 목록");
		model.addAttribute("memberList", memberList);
		
		return "member/m_List";
	}
}
