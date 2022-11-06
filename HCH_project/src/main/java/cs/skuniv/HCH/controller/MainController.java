package cs.skuniv.HCH.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import cs.skuniv.HCH.dao.CoffeeDao;
import cs.skuniv.HCH.dao.EtcDao;
import cs.skuniv.HCH.dao.MachineDao;
import cs.skuniv.HCH.dto.Coffee;
import cs.skuniv.HCH.dto.Etc;
import cs.skuniv.HCH.dto.Machine;
import cs.skuniv.HCH.dto.Member;
import cs.skuniv.HCH.service.FavoriteService;

@Controller
public class MainController {
	
	@Autowired
	private CoffeeDao coffeeDao;
	@Autowired
	private MachineDao machineDao;
	@Autowired
	private EtcDao etcDao;
	@Autowired
	private FavoriteService favoriteSvc;
	
	@RequestMapping({"/", "/index"})
	public String home(Model model, HttpServletRequest req) {
		List<Coffee> coffeeList = coffeeDao.selectAll();
		List<Machine> machineList = machineDao.selectAll();
		List<Etc> etcList = etcDao.selectAll();
		// 접속 중인 멤버
		HttpSession session = req.getSession();
		Member member = (Member)session.getAttribute("member");	
		
		Collections.reverse(coffeeList);
		Collections.reverse(machineList);
		Collections.reverse(etcList);
		
		// 좋아요 여부
		if(member != null) {
			List<Integer> favoriteList = new ArrayList<>();
			for(Coffee coffee:coffeeList) {
				if(favoriteSvc.check(member, coffee.getCategory(), coffee.getNum())) favoriteList.add(coffee.getNum());
			}
			if(!favoriteList.isEmpty()) model.addAttribute("favorite", favoriteList);
		}
		
		model.addAttribute("coffeeList", coffeeList);
		model.addAttribute("machineList", machineList);
		model.addAttribute("etcList", etcList);
		
		return "index";
	}
	
	@RequestMapping(value="/select-category")
	public String selectCategory() { return "select-category"; }
	
	@RequestMapping(value="/search-result")
	public String searchResult(Model model, HttpServletRequest req) { 
		String search = req.getParameter("q");
		
		if(search != null) {
			List<Coffee> coffeeList = coffeeDao.selectSearchString(search);
			List<Machine> machineList = machineDao.selectSearchString(search);
			List<Etc> etcList = etcDao.selectSearchString(search);
			
			model.addAttribute("coffeeList", coffeeList);
			model.addAttribute("machineList", machineList);
			model.addAttribute("etcList", etcList);
			model.addAttribute("search", search);
		} else { // 입력이 없을 때
			model.addAttribute("blankSearch", true);
		}
		return "search-result"; 
	}
	
	/* 파일 업로드 테스트 
	@RequestMapping("/file-upload-test")
	public String getFile() {
		return "file-upload-test";
	}
	
	@RequestMapping(value="/fileUploadTest", method=RequestMethod.POST)
	public String postFile(@RequestParam("imagefile") MultipartFile file) {
		UUID uuid = UUID.randomUUID();
		String filename = uuid + "_" + file.getOriginalFilename();
		File saveFile = new File("C:\\upload", filename);
			
		try {
			file.transferTo(saveFile);
		} catch (Exception e) {
			System.out.println("파일 업로드 실패");
		}
		
		return "file-upload-test";
	} */

}
