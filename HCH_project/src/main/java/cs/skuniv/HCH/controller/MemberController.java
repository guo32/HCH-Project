package cs.skuniv.HCH.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cs.skuniv.HCH.dto.Member;
import cs.skuniv.HCH.request.MemberRegisterRequest;
import cs.skuniv.HCH.service.MemberLoginService;
import cs.skuniv.HCH.service.MemberRegisterService;

@Controller
public class MemberController {
	
	@Autowired
	private MemberRegisterService memberRegSvc;
	@Autowired
	private MemberLoginService memberLoginSvc;
		
	/* 회원가입 페이지 접근 */
	@RequestMapping("/member/register")
	public String getRegist() { return "member/register"; }
	
	/* 회원가입 */
	@RequestMapping(value="/member/register", method=RequestMethod.POST)
	public String postRegist(Model model) {
		model.addAttribute("registerRequest", new MemberRegisterRequest());
		return "member/register";
	}
	
	@RequestMapping(value="/member/register-completion", method=RequestMethod.POST)
	public String postRegistCompletion(MemberRegisterRequest regReq, Model model) {
		try {
			memberRegSvc.regist(regReq);
			return "member/register-completion";
		} catch (Exception ex) {
			model.addAttribute("usedId", regReq.getId());
			return "member/register";
		}
	}
	
	/* 로그인 페이지 접근 */
	@RequestMapping(value="/member/login")
	public String getLogin() { return "member/login"; }
	
	/* 로그인 */
	@RequestMapping(value="/member/login-success")
	public String postLogin(Model model, @RequestParam(value="id") String id,
			@RequestParam(value="password") String password, HttpServletRequest req) {
		Member member = memberLoginSvc.login(id, password, req);
		
		if(member != null) {
			//HttpSession session = req.getSession();
			//session.setAttribute("member", member);
			
			return "redirect:/";
		}
		return "member/login";
	}
	
	/* 로그아웃 */
	@RequestMapping(value="/member/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	/* 마이페이지 접근 */
	@RequestMapping(value="/member/my-page")
	public String getMyPage() { return "member/my-page"; }
	
	/* 회원정보수정 페이지 접근 */
	@RequestMapping(value="/member/edit-member")
	public String getEditMemberInfo() { return "member/edit-member"; }
	
	/* 회원정보수정 */
	@RequestMapping(value="/member/edit-member", method=RequestMethod.POST)
	public String postEditMemberInfo(Model model) {
		model.addAttribute("registerRequest", new MemberRegisterRequest());
		return "member/edit-member";
	}
	
	@RequestMapping(value="/member/edit-member-completion", method=RequestMethod.POST)
	public String postEditMemberInfoCompletion(MemberRegisterRequest regReq, HttpServletRequest req, HttpSession session) {
		try {
			memberRegSvc.edit(regReq, req, session);
			return "member/my-page";
		} catch (Exception ex) {
			return "member/edit-member";
		}
	}
	
	/* 회원탈퇴 */
	@RequestMapping(value="/member/withdrawal-member")
	public String memberWithdrawal(HttpSession session) {
		memberRegSvc.remove(session);
		return "redirect:/";
	}	

}