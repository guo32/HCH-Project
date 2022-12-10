package cs.skuniv.HCH.controller;

import java.io.File;
import java.util.ArrayList;
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
import cs.skuniv.HCH.dao.NationDao;
import cs.skuniv.HCH.dao.NoteDao;
import cs.skuniv.HCH.dto.Coffee;
import cs.skuniv.HCH.dto.Comment;
import cs.skuniv.HCH.dto.Member;
import cs.skuniv.HCH.dto.Nation;
import cs.skuniv.HCH.dto.Note;
import cs.skuniv.HCH.request.CoffeeRegisterRequest;
import cs.skuniv.HCH.request.CoffeeSearchDetailRequest;
import cs.skuniv.HCH.request.CommentRegisterRequest;
import cs.skuniv.HCH.service.CoffeeRegisterService;
import cs.skuniv.HCH.service.CoffeeSearchDetailService;
import cs.skuniv.HCH.service.CommentRegisterService;
import cs.skuniv.HCH.service.FavoriteService;

@Controller
public class CoffeeController {

	@Autowired
	private CoffeeRegisterService coffeeRegSvc;
	@Autowired
	private CoffeeDao coffeeDao;
	@Autowired
	private CoffeeSearchDetailService coffeeSearchSvc;
	
	@Autowired
	private CommentRegisterService commentRegSvc;
	@Autowired
	private CommentDao commentDao;
	
	@Autowired
	private FavoriteService favoriteSvc;
	
	/* 원산지 정보 */
	@Autowired
	private NationDao nationDao;
	
	/* 커피 노트 정보 */
	@Autowired
	private NoteDao noteDao;
	
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
	public String getCoffeeRegist(Model model) {
		List<Nation> nationList = nationDao.selectAll();
		List<Note> noteList = noteDao.selectAll();
		model.addAttribute("nationList", nationList);
		model.addAttribute("noteList", noteList);
		
		/*
		 * note 데이터 가공에 대해
		 * major 선택 --> middle 선택 --> minor 선택으로 조작하고 싶은데
		 * major 리스트 출력 후에
		 * 해당 major와 middle 리스트 비교
		 * 
		 */
		List<String> majorList = noteDao.selectAllMajor();
		model.addAttribute("majorList", majorList);
		
		return "coffee/register";		
	}
	
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
		try {
			String commentEdit = req.getParameter("commentEdit");
			if(commentEdit != null) {
				if(commentEdit.equals("delete")) {
					int id = Integer.parseInt(req.getParameter("id"));
					commentRegSvc.remove(id);
				}				
			}
		} catch (Exception e) {
			System.out.println("댓글 삭제 실패");
		}
		
		int num = Integer.parseInt(req.getParameter("num"));
		
		// 등록된 제품 정보		
		Coffee coffee = coffeeDao.selectByNum(num);
		model.addAttribute("coffee", coffee);
		
		/* 22.11.16
		 * 등록된 제품 정보 --> 원산지 찾기
		 */
		Nation nation = nationDao.selectById(coffee.getNation());
		model.addAttribute("nation", nation); // view에서 원산지(그룹) 형식으로 출력할 것
		
		/*
		 * 22.11.17
		 * 등록된 제품 정보 --> note 찾기
		 * */
		Note note = noteDao.selectById(coffee.getTaste());
		model.addAttribute("note", note);
		
		// 등록된 댓글
		List<Comment> comments = getRegistedComment(num, coffee.getCategory());
		model.addAttribute("comments", comments);
		
		// 평점 평균
		double ratingAvg = coffee.getRatingsum() / coffee.getComment();
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
	public String postCoffeeCommentCompletion(Model model, CommentRegisterRequest regReq, HttpServletRequest req) {
		try {			
			commentRegSvc.regist(regReq);			
			coffeeDetailInfo(model, req);
		} catch (Exception ex) {
			System.out.println("댓글 등록 실패");		
		}
		
		return "coffee/post";
	}
	
	/* 관심 등록 처리 */
	@RequestMapping(value="/coffee/add-favorite", method=RequestMethod.POST)
	public String addFavorite(Model model, HttpServletRequest req) {
		// 게시물 번호
		int num = Integer.parseInt(req.getParameter("num"));
		Coffee coffee = coffeeDao.selectByNum(num);	
		
		// 현재 페이지가 posts(게시물 리스트)
		String focusOnPosts = req.getParameter("posts");
		//String focusOnIndex = req.getParameter("index");
		
		// 접속 중인 멤버
		HttpSession session = req.getSession();
		Member member = (Member)session.getAttribute("member");
		
		try {
			favoriteSvc.add(member.getId(), num, coffee.getCategory());
			
			if(focusOnPosts != null && focusOnPosts.equals("true")) {
				coffeeList(model, req);
				return "coffee/posts";
			} else coffeeDetailInfo(model, req);
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
		
		// 현재 페이지가 posts(게시물 리스트)
		String focusOnPosts = req.getParameter("posts");
				
		// 접속 중인 멤버
		HttpSession session = req.getSession();
		Member member = (Member)session.getAttribute("member");
		
		try {
			favoriteSvc.release(member.getId(), num, coffee.getCategory());
			
			if(focusOnPosts != null && focusOnPosts.equals("true")) {
				coffeeList(model, req);
				return "coffee/posts";
			} else coffeeDetailInfo(model, req);
		} catch (Exception ex) {
			System.out.println("관심 해제 실패");
		}		
		
		return "coffee/post";
	}
	
	/* 제품(커피) 페이지 */
	@RequestMapping(value="/coffee/posts", method=RequestMethod.GET)
	public String coffeeList(Model model, HttpServletRequest req) {
		// 일반 검색
		String search = req.getParameter("q");
		// 제조사별 분류
		String manufacturer = req.getParameter("manufacturer");
		List<Coffee> coffeeList;
		// 접속 중인 멤버
		HttpSession session = req.getSession();
		Member member = (Member)session.getAttribute("member");		
		// 원산지 데이터
		List<Nation> nationList = nationDao.selectAll();
		model.addAttribute("nationList", nationList);
		
		/* 22.11.18 : note 관련 데이터 추가하기 */
		List<Note> noteList = noteDao.selectAll();
		model.addAttribute("noteList", noteList);
		
		List<String> majorList = noteDao.selectAllMajor();
		model.addAttribute("majorList", majorList);
		
		if(search != null) {
			coffeeList = coffeeDao.selectSearchString(search);			
		} else if(manufacturer != null) {
			coffeeList = coffeeDao.selectManufacturer(manufacturer);			
		} else coffeeList = coffeeDao.selectAll();
		
		// 좋아요 여부
		if(member != null) {
			List<Integer> favoriteList = new ArrayList<>();
			for(Coffee coffee:coffeeList) {
				if(favoriteSvc.check(member, coffee.getCategory(), coffee.getNum())) favoriteList.add(coffee.getNum());
			}
			if(!favoriteList.isEmpty()) model.addAttribute("favorite", favoriteList);
		}
		
		model.addAttribute("search", search);
		model.addAttribute("manufacturerSearch", manufacturer);
		
		// 상세 검색
		model.addAttribute("coffeeSearchDetailRequest", new CoffeeSearchDetailRequest());
		
		Collections.reverse(coffeeList);
		model.addAttribute("coffeeList", coffeeList);
		
		return "coffee/posts";
	}
	
	/* 상세 검색 */
	@RequestMapping(value="/coffee/posts-datail-search", method=RequestMethod.GET)
	public String coffeeDetailSearchList(Model model, CoffeeSearchDetailRequest dsReq) {
		List<Coffee> coffeeList = coffeeSearchSvc.search(dsReq);
		
		Collections.reverse(coffeeList);
		model.addAttribute("coffeeList", coffeeList);
		
		// 원산지 데이터
		List<Nation> nationList = nationDao.selectAll();
		model.addAttribute("nationList", nationList);
		
		/* 22.11.18 : note 관련 데이터 추가하기 */
		List<Note> noteList = noteDao.selectAll();
		model.addAttribute("noteList", noteList);
		
		List<String> majorList = noteDao.selectAllMajor();
		model.addAttribute("majorList", majorList);
		
		return "coffee/posts";
	}
	
	/* 제품(커피) 수정 페이지 접근 */
	@RequestMapping(value="/coffee/edit-coffee", method=RequestMethod.GET)
	public String getEditCoffeeInfo(Model model, HttpServletRequest req) {
		int num = Integer.parseInt(req.getParameter("num"));
		Coffee coffee = coffeeDao.selectByNum(num);
		model.addAttribute("coffee", coffee);
		
		/* 22.11.15 : nationList 추가하기 */
		List<Nation> nationList = nationDao.selectAll();
		model.addAttribute("nationList", nationList);
		
		/* 22.11.18 : note 관련 데이터 추가하기 */
		List<Note> noteList = noteDao.selectAll();
		model.addAttribute("noteList", noteList);
		
		List<String> majorList = noteDao.selectAllMajor();
		model.addAttribute("majorList", majorList);
		
		/* 해당 커피의 노트 정보 */
		Note note = noteDao.selectById(coffee.getTaste());
		model.addAttribute("note", note);
		
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
			
			coffeeDetailInfo(model, req);
			
			return "coffee/post";
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
