package cs.skuniv.HCH.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cs.skuniv.HCH.dao.MemberDao;
import cs.skuniv.HCH.dto.Member;

public class MemberLoginService {
	
	private MemberDao memberDao;
	
	public MemberLoginService(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	
	public Member login(String id, String password, HttpServletRequest req) {
		Member member = memberDao.selectById(id);
		if(member != null) {
			if(password.equals(member.getPassword())) {
				HttpSession session = req.getSession();
				session.setAttribute("member", member);
				
				return member;
			}				
			else return null;
		}
		return null;
	}

}
