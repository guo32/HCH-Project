package cs.skuniv.HCH.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AdminLoginService {
	
	public Boolean login(String id, String password, HttpServletRequest req) {
		if(id.equals("admin_acc")) {
			if(password.equals("admin1234")) {
				HttpSession session = req.getSession();
				session.setAttribute("admin", id);
				
				return true;
			}
			else return false;
		}
		return false;
	}

}
