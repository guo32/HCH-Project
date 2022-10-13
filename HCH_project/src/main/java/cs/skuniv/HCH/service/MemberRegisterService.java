package cs.skuniv.HCH.service;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cs.skuniv.HCH.dao.MemberDao;
import cs.skuniv.HCH.dto.Member;
import cs.skuniv.HCH.request.MemberRegisterRequest;

public class MemberRegisterService {
	
	private MemberDao memberDao;
	
	public MemberRegisterService(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	
	/* 회원가입 처리 */
	public String regist(MemberRegisterRequest req) throws Exception {
		Member member = memberDao.selectById(req.getId());
		if(member != null) {
			throw new Exception("DuplicateMemberException");
		}
		Member newMember = new Member(req.getId(),
				req.getPassword(), req.getEmail(),
				req.getName(), req.getBirth(),
				LocalDateTime.now());
		memberDao.insert(newMember);
		return newMember.getId();
	}
	
	/* 회원정보수정 처리 */
	public String edit(MemberRegisterRequest regReq, HttpServletRequest req, HttpSession session) throws Exception {
		Member member = memberDao.selectById(regReq.getId());
		if(member == null) {
			throw new Exception("NonexistentMemberException");
		}
		member.setEmail(regReq.getEmail());
		member.setName(regReq.getName());
		member.setBirth(regReq.getBirth());		
		memberDao.update(member);
		
		session.invalidate(); // 세션 해제 후 
		session = req.getSession();
		session.setAttribute("member", member); // 재할당
		
		return member.getEmail();
	}
	
	/* 회원삭제 처리 */
	public void remove(HttpSession session) {
		Member member = (Member)session.getAttribute("member");
		memberDao.delete(member);
		session.invalidate();
	}

}
