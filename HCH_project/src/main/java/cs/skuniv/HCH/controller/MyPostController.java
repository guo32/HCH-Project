package cs.skuniv.HCH.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cs.skuniv.HCH.dao.CoffeeDao;
import cs.skuniv.HCH.dao.EtcDao;
import cs.skuniv.HCH.dao.FavoriteDao;
import cs.skuniv.HCH.dao.MachineDao;
import cs.skuniv.HCH.dto.Coffee;
import cs.skuniv.HCH.dto.Etc;
import cs.skuniv.HCH.dto.Favorite;
import cs.skuniv.HCH.dto.Machine;
import cs.skuniv.HCH.dto.Member;

@Controller
public class MyPostController {
	
	@Autowired
	private CoffeeDao coffeeDao;
	@Autowired
	private MachineDao machineDao;
	@Autowired
	private EtcDao etcDao;
	@Autowired
	private FavoriteDao favoriteDao;
	
	/* 내 게시물 페이지 */
	@RequestMapping(value="/member/my-post")
	public String myPost(Model model, HttpSession session) {
		Member member = (Member)session.getAttribute("member");
		List<Coffee> coffeePostingList = coffeeDao.selectByUserId(member.getId());
		List<Machine> machinePostingList = machineDao.selectByRegistrant(member.getId());
		List<Etc> etcPostingList = etcDao.selectByRegistrant(member.getId());
		
		model.addAttribute("coffeePosting", coffeePostingList);
		model.addAttribute("machinePosting", machinePostingList);
		model.addAttribute("etcPosting", etcPostingList);
		
		return "member/my-post";
	}
	
	/* 관심 게시물 페이지 */
	@RequestMapping(value="/member/favorite-post")
	public String favoritePost(Model model, HttpSession session) {
		Member member = (Member)session.getAttribute("member");
		List<Favorite> favoriteList = favoriteDao.selectUser(member.getId());
		List<Coffee> coffeeList = new ArrayList<>(); List<Machine> machineList = new ArrayList<>(); List<Etc> etcList = new ArrayList<>();
		
		for(Favorite favorite : favoriteList) {
			if(favorite.getCategory().equals("cb")) coffeeList.add(coffeeDao.selectByNum(favorite.getPosting()));
			if(favorite.getCategory().equals("cm")) machineList.add(machineDao.selectById(favorite.getPosting()));
			if(favorite.getCategory().equals("ce")) etcList.add(etcDao.selectById(favorite.getPosting()));
		}
		
		model.addAttribute("coffeeList", coffeeList);
		model.addAttribute("machineList", machineList);
		model.addAttribute("etcList", etcList);
		
		return "member/favorite-post";
	}

}
