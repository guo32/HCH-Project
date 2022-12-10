package cs.skuniv.HCH.controller;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cs.skuniv.HCH.dao.CoffeeDao;
import cs.skuniv.HCH.dao.EtcDao;
import cs.skuniv.HCH.dao.MachineDao;
import cs.skuniv.HCH.dto.Coffee;
import cs.skuniv.HCH.dto.Etc;
import cs.skuniv.HCH.dto.Machine;
import cs.skuniv.HCH.service.AdminLoginService;

@Controller
public class AdminController {
	
	@Autowired
	private CoffeeDao coffeeDao;
	@Autowired
	private MachineDao machineDao;
	@Autowired
	private EtcDao etcDao;
	@Autowired
	private AdminLoginService adminLoginSvc;
	/*
	 * 관리자 로그인
	 * 
	 */
	
	/* 로그인 페이지 접근 */
	@RequestMapping({"/admin", "/admin/login"})
	public String getLogin() { return "admin/login"; }
	
	/* 로그인 */
	@RequestMapping(value="/admin/login-success", method = RequestMethod.POST)
	public String postLogin(Model model, @RequestParam(value="id") String id,
			@RequestParam(value="password") String password, HttpServletRequest req) {
		// Member member = memberLoginSvc.login(id, password, req);
		
		Boolean adminLogin = adminLoginSvc.login(id, password, req);
		
		if(adminLogin) {
			coffeeList(model, req);
			return "admin/manage-coffee-item";
		}
		return "admin/login";
	}
	
	/* 로그아웃 */
	@RequestMapping(value="/admin/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/"; // 첫 페이지(일반)
	}
	
	/* 원두 관리 페이지 */
	@RequestMapping(value="/admin/manage-coffee-item", method=RequestMethod.GET)
	public String coffeeList(Model model, HttpServletRequest req) {	
		HttpSession session = req.getSession();
		Object admin = session.getAttribute("admin");
		
		if(admin == null) return "admin/login";
		
		// 일반 검색
		String search = req.getParameter("q");
		// 제조사별 분류
		String manufacturer = req.getParameter("manufacturer");
		List<Coffee> coffeeList;
		
		if(search != null) {
			coffeeList = coffeeDao.selectSearchString(search);			
		} else if(manufacturer != null) {
			coffeeList = coffeeDao.selectManufacturer(manufacturer);			
		} else coffeeList = coffeeDao.selectAll();
		
		
		model.addAttribute("search", search);
		model.addAttribute("manufacturerSearch", manufacturer);
				
		Collections.reverse(coffeeList);
		model.addAttribute("coffeeList", coffeeList);
		
		return "admin/manage-coffee-item";
	}
	
	/* 가전 관리 페이지 */
	@RequestMapping(value="/admin/manage-machine-item", method=RequestMethod.GET)
	public String machineList(Model model, HttpServletRequest req) {	
		HttpSession session = req.getSession();
		Object admin = session.getAttribute("admin");
		
		if(admin == null) return "admin/login";
		
		// 일반 검색
		String search = req.getParameter("q");
		// 브랜드별 분류
		String brand = req.getParameter("brand");
		List<Machine> machineList;
		
		if(search != null) {
			machineList = machineDao.selectSearchString(search);			
		} else if(brand != null) {
			machineList = machineDao.selectBrand(brand);			
		} else machineList = machineDao.selectAll();
		
		
		model.addAttribute("search", search);
		model.addAttribute("brandSearch", brand);
				
		Collections.reverse(machineList);
		model.addAttribute("machineList", machineList);
		
		return "admin/manage-machine-item";
	}
	
	/* 기타 제품 관리 페이지 */
	@RequestMapping(value="/admin/manage-etc-item", method=RequestMethod.GET)
	public String etcList(Model model, HttpServletRequest req) {
		HttpSession session = req.getSession();
		Object admin = session.getAttribute("admin");
		
		if(admin == null) return "admin/login";
		
		// 일반 검색
		String search = req.getParameter("q");
		// 브랜드별 분류
		String brand = req.getParameter("brand");
		List<Etc> etcList;
		
		if(search != null) {
			etcList = etcDao.selectSearchString(search);			
		} else if(brand != null) {
			etcList = etcDao.selectBrand(brand);			
		} else etcList = etcDao.selectAll();
		
		
		model.addAttribute("search", search);
		model.addAttribute("brandSearch", brand);
				
		Collections.reverse(etcList);
		model.addAttribute("etcList", etcList);
		
		return "admin/manage-etc-item";
	}

}
