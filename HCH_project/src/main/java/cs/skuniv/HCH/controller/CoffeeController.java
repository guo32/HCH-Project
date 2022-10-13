package cs.skuniv.HCH.controller;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
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
import cs.skuniv.HCH.dao.CommentDao;
import cs.skuniv.HCH.dao.FavoriteDao;
import cs.skuniv.HCH.dto.Coffee;
import cs.skuniv.HCH.dto.Comment;
import cs.skuniv.HCH.dto.Favorite;
import cs.skuniv.HCH.dto.Member;
import cs.skuniv.HCH.request.CoffeeRegisterRequest;
import cs.skuniv.HCH.request.CommentRegisterRequest;
import cs.skuniv.HCH.service.CoffeeRegisterService;
import cs.skuniv.HCH.service.CommentRegisterService;
import cs.skuniv.HCH.service.FavoriteService;

@Controller
public class CoffeeController {

	@Autowired
	private CoffeeRegisterService coffeeRegSvc;
	@Autowired
	private CoffeeDao coffeeDao;
	
	@Autowired
	private CommentRegisterService commentRegSvc;
	@Autowired
	private CommentDao commentDao;
	
	@Autowired
	private FavoriteService favoriteSvc;
	@Autowired
	private FavoriteDao favoriteDao;
	
	/* 이미지 파일 업로드 */
	public String fileUpload(MultipartFile file, HttpServletRequest req) {
		ServletContext servletContext = req.getSession().getServletContext();
		String realFolder = servletContext.getRealPath("/resources/image");
		
		/* 파일 이름 변경 */
		UUID uuid = UUID.randomUUID();
		String filename = uuid + "_" + file.getOriginalFilename();
		
		File saveFile = new File(realFolder, filename);
		
		/* 실제 폴더에 파일 업로드 */
		try {
			file.transferTo(saveFile);
		} catch (Exception ex) {
			System.out.println("파일 업로드 실패");
		}
		
		return filename;
	}
		
	/* 제품(커피)등록 페이지 접근 */
	@RequestMapping(value="/coffee/register", method=RequestMethod.GET)
	public String getCoffeeRegist() { return "coffee/register"; }
	
	@RequestMapping(value="/coffee/register", method=RequestMethod.POST)
	public String postCoffeeRegist(Model model) {
		model.addAttribute("coffeeRegisterRequest", new CoffeeRegisterRequest());
		return "coffee/register";
	}
	
	@RequestMapping(value="/coffee/register-completion", method=RequestMethod.POST)
	public String postCoffeeRegistCompletion(CoffeeRegisterRequest regReq, 
			@RequestParam("imagefile") MultipartFile file, HttpServletRequest req) {
		try {
			/* 이미지 파일 처리 */
			String filename = fileUpload(file, req);
			
			/* 바뀐 파일 이름 설정 & 등록 */
			regReq.setFilename(filename);
			coffeeRegSvc.regist(regReq);
			
			return "coffee/register-completion";
		} catch (Exception ex) {
			System.out.println("등록 실패");
			return "coffee/register";
		}
	}
	
	/* 게시물별 등록 댓글 가져오기 */
	public List<Comment> getRegistedComment(int num, String category) {
		List<Comment> comments = commentDao.selectByPost(num, category);
		Collections.reverse(comments); // 최신순
		
		return comments;
	}
	
	/* 제품(커피) 상세 정보 페이지 */
	@RequestMapping(value="/coffee/post", method=RequestMethod.GET)
	public String coffeeDetailInfo(Model model, HttpServletRequest req) {
		int num = Integer.parseInt(req.getParameter("num"));
		
		// 등록된 제품 정보		
		Coffee coffee = coffeeDao.selectByNum(num);
		model.addAttribute("coffee", coffee);
		
		// 등록된 댓글
		List<Comment> comments = getRegistedComment(num, coffee.getCategory());
		model.addAttribute("comments", comments);
		
		// 평점 평균
		double ratingAvg = coffee.getRatingsum() / (comments.size() + 1);
		model.addAttribute("ratingAvg", ratingAvg);
		
		// 현재 접속 중인 회원의 좋아요 여부
		HttpSession session = req.getSession();
		Member member = (Member)session.getAttribute("member");
		if(member != null) {
			boolean favoriteCheck = favoriteSvc.check(member, coffee.getCategory(), num);
			if(favoriteCheck) model.addAttribute("favorite", favoriteCheck);			
		}
		
		return "coffee/post";
	}
	
	/* 제품(커피) 상세 정보 페이지 하단 댓글 처리 */
	@RequestMapping(value="/coffee/post", method=RequestMethod.POST)
	public String postCoffeeDetailInfo(Model model) {
		// 댓글
		model.addAttribute("commentRegisterRequest", new CommentRegisterRequest());
				
		return "coffee/post";
	}
	
	@RequestMapping(value="/coffee/post-comment", method=RequestMethod.POST)
	public String postCoffeeCommentCompletion(CommentRegisterRequest regReq) {
		try {
			commentRegSvc.regist(regReq);
			return "coffee/post-comment";
		} catch (Exception ex) {
			System.out.println("댓글 등록 실패");
			return "coffee/post";
		}
	}
	
	/* 관심 등록 처리 */
	@RequestMapping(value="/coffee/add-favorite", method=RequestMethod.POST)
	public String addFavorite(Model model, HttpServletRequest req) {
		// 게시물 번호
		int num = Integer.parseInt(req.getParameter("num"));
		Coffee coffee = coffeeDao.selectByNum(num);	
		// 접속 중인 멤버
		HttpSession session = req.getSession();
		Member member = (Member)session.getAttribute("member");
		
		try {
			favoriteSvc.add(member.getId(), num, coffee.getCategory());
			// 재검색
			coffee = coffeeDao.selectByNum(num);
			model.addAttribute("coffee", coffee);
			
			// 등록된 댓글
			List<Comment> comments = getRegistedComment(num, coffee.getCategory());
			model.addAttribute("comments", comments);
			
			// 평점 평균
			double ratingAvg = coffee.getRatingsum() / (comments.size() + 1);
			model.addAttribute("ratingAvg", ratingAvg);
			
			// 좋아요 여부
			if(member != null) {
				boolean favoriteCheck = favoriteSvc.check(member, coffee.getCategory(), num);
				if(favoriteCheck) model.addAttribute("favorite", favoriteCheck);			
			}
		} catch (Exception ex) {
			System.out.println("관심 등록 실패");
		}		
		
		return "coffee/post";
	}
	
	/* 관심 해제 처리 */
	@RequestMapping(value="/coffee/release-favorite", method=RequestMethod.POST)
	public String releaseFavorite(Model model, HttpServletRequest req) {
		// 게시물 번호
		int num = Integer.parseInt(req.getParameter("num"));
		Coffee coffee = coffeeDao.selectByNum(num);	
		// 접속 중인 멤버
		HttpSession session = req.getSession();
		Member member = (Member)session.getAttribute("member");
		
		try {
			favoriteSvc.release(member.getId(), num, coffee.getCategory());
			// 재검색
			coffee = coffeeDao.selectByNum(num);
			model.addAttribute("coffee", coffee);
			
			// 등록된 댓글
			List<Comment> comments = getRegistedComment(num, coffee.getCategory());
			model.addAttribute("comments", comments);
			
			// 평점 평균
			double ratingAvg = coffee.getRatingsum() / (comments.size() + 1);
			model.addAttribute("ratingAvg", ratingAvg);
			
			// 좋아요 여부
			if(member != null) {
				boolean favoriteCheck = favoriteSvc.check(member, coffee.getCategory(), num);
				if(favoriteCheck) model.addAttribute("favorite", favoriteCheck);			
			}
		} catch (Exception ex) {
			System.out.println("관심 해제 실패");
		}		
		
		return "coffee/post";
	}
	
	/* 제품(커피) 페이지 */
	@RequestMapping(value="/coffee/posts", method=RequestMethod.GET)
	public String coffeeList(Model model, HttpServletRequest req) {
		String search = req.getParameter("q");
		String manufacturer = req.getParameter("manufacturer");
		List<Coffee> coffeeList;
		
		if(search != null) coffeeList = coffeeDao.selectSearchString(search);
		else if(manufacturer != null) coffeeList = coffeeDao.selectManufacturer(manufacturer);
		else coffeeList = coffeeDao.selectAll();			
		
		Collections.reverse(coffeeList);
		model.addAttribute("coffeeList", coffeeList);
		
		return "coffee/posts";
	}
	
	/* 제품(커피) 수정 페이지 접근 */
	@RequestMapping(value="/coffee/edit-coffee", method=RequestMethod.GET)
	public String getEditCoffeeInfo(Model model, HttpServletRequest req) {
		int num = Integer.parseInt(req.getParameter("num"));
		Coffee coffee = coffeeDao.selectByNum(num);
		model.addAttribute("coffee", coffee);
		
		return "coffee/edit-coffee"; 
	}
	
	/* 제품(커피) 수정 페이지 */
	@RequestMapping(value="/coffee/edit-coffee", method=RequestMethod.POST)
	public String postEditCoffeeInfo(Model model) {
		model.addAttribute("coffeeRegisterRequest", new CoffeeRegisterRequest());
		return "coffee/edit-coffee";
	}
	
	@RequestMapping(value="/coffee/edit-coffee-completion", method=RequestMethod.POST)
	public String postEditCoffeeInfoCompletion(Model model, CoffeeRegisterRequest regReq, 
			@RequestParam("imagefile") MultipartFile file, HttpServletRequest req) {
		try {
			int num = Integer.parseInt(req.getParameter("num"));
			Coffee coffee = coffeeDao.selectByNum(num);
			model.addAttribute("coffee", coffee);
			
			/* 변경된 이미지 파일이 없는 경우 */
			if(file.isEmpty() || file == null) {
				regReq.setFilename(coffee.getFilename());				
			} else {
				String filename = fileUpload(file, req);
				regReq.setFilename(filename);
			}
			
			coffeeRegSvc.edit(regReq, Integer.parseInt(req.getParameter("num")));
			
			return "coffee/edit-coffee-completion";
		} catch (Exception ex) {
			System.out.println("수정 실패");
			return "coffee/posts";
		}
	}
	
	/* 제품(커피) 삭제 */
	@RequestMapping(value="/coffee/delete-coffee", method=RequestMethod.GET)
	public String removeCoffee(HttpServletRequest req) {
		try {
			coffeeRegSvc.remove(Integer.parseInt(req.getParameter("num")));
			return "coffee/delete-coffee";
		} catch (Exception ex) {
			return "redirect:/";
		}
	}
}