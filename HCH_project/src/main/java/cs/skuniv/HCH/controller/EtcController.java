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

import cs.skuniv.HCH.dao.CommentDao;
import cs.skuniv.HCH.dao.EtcDao;
import cs.skuniv.HCH.dto.Comment;
import cs.skuniv.HCH.dto.Etc;
import cs.skuniv.HCH.dto.Member;
import cs.skuniv.HCH.request.CommentRegisterRequest;
import cs.skuniv.HCH.request.EtcRegisterRequest;
import cs.skuniv.HCH.request.EtcSearchDetailRequest;
import cs.skuniv.HCH.service.CommentRegisterService;
import cs.skuniv.HCH.service.EtcRegisterService;
import cs.skuniv.HCH.service.EtcSearchDetailService;
import cs.skuniv.HCH.service.FavoriteService;

@Controller
public class EtcController {
		
	@Autowired
	private EtcRegisterService etcRegSvc;
	@Autowired
	private EtcSearchDetailService etcSearchSvc;
	@Autowired
	private EtcDao etcDao;
	
	@Autowired
	private CommentRegisterService commentRegSvc;
	@Autowired
	private CommentDao commentDao;
	
	@Autowired
	private FavoriteService favoriteSvc;
	
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
	
	/* 제품(기타) 등록 페이지 접근 */
	@RequestMapping(value="/etc/register", method=RequestMethod.GET)
	public String getEtcRegist() { return "etc/register"; }
	
	@RequestMapping(value="/etc/register", method=RequestMethod.POST)
	public String postEtcRegist(Model model) {
		model.addAttribute("etcRegisterRequest", new EtcRegisterRequest());
		return "etc/register";
	}
	
	@RequestMapping(value="/etc/register-completion", method=RequestMethod.POST)
	public String postEtcRegistCompletion(EtcRegisterRequest regReq, 
			@RequestParam("imagefile") MultipartFile file, HttpServletRequest req) {
		try {
			/* 이미지 파일 처리 */
			String filename = fileUpload(file, req);
			
			/* 바뀐 파일 이름 설정 & 등록 */
			regReq.setFilename(filename);
			etcRegSvc.regist(regReq);
			
			return "etc/register-completion";
		} catch (Exception ex) {
			System.out.println("등록 실패");
			return "etc/register";
		}
	}
	
	/* 제품(가전) 수정 페이지 */
	@RequestMapping(value="/etc/edit-etc", method=RequestMethod.GET)
	public String getEditEtcInfo(Model model, HttpServletRequest req) {
		int id = Integer.parseInt(req.getParameter("id"));
		Etc etc = etcDao.selectById(id);
		model.addAttribute("etc", etc);
		
		return "etc/edit-etc";
	}
	
	@RequestMapping(value="/etc/edit-etc", method=RequestMethod.POST)
	public String postEditEtcInfo(Model model) {
		model.addAttribute("etcRegisterRequest", new EtcRegisterRequest());
		return "etc/edit-etc";
	}
	
	@RequestMapping(value="/etc/edit-etc-completion", method=RequestMethod.POST)
	public String postEditEtcInfoCompletion(Model model, EtcRegisterRequest regReq,
			@RequestParam("imagefile") MultipartFile file, HttpServletRequest req) {
		try {
			int etcId = Integer.parseInt(req.getParameter("id"));
			
			HttpSession session = req.getSession();
			Member member = (Member)session.getAttribute("member");
			Etc etc = etcDao.selectById(etcId);
			
			model.addAttribute("etc", etc); // 없어도 될 것 같은데
			
			/* 변경된 이미지 파일이 없는 경우 */
			if(file.isEmpty() || file == null) {
				regReq.setFilename(etc.getFilename());				
			} else {
				String filename = fileUpload(file, req);
				regReq.setFilename(filename);
			}			
			
			etcRegSvc.edit(regReq, etcId);
			
			// 재검색
			etc = etcDao.selectById(etcId);
			model.addAttribute("etc", etc);
			
			// 등록된 댓글
			List<Comment> comments = getRegistedComment(etcId, etc.getCategory());
			model.addAttribute("comments", comments);
					
			// 평점 평균
			double ratingAvg = etc.getRatingsum() / (comments.size() + 1);
			model.addAttribute("ratingAvg", ratingAvg);
					
			// 현재 접속 중인 회원의 좋아요 여부
			if(member != null) {
				boolean favoriteCheck = favoriteSvc.check(member, etc.getCategory(), etcId);
				if(favoriteCheck) model.addAttribute("favorite", favoriteCheck);			
			}
			
			return "etc/post";
		} catch (Exception ex) {
			System.out.println("수정 실패");
			return "etc/posts";
		}
	}
	
	/* 제품(가전) 삭제 */
	@RequestMapping(value="/etc/delete-etc", method=RequestMethod.GET)
	public String removeEtc(HttpServletRequest req) {
		try {
			etcRegSvc.remove(Integer.parseInt(req.getParameter("id")));
			return "etc/delete-etc";
		} catch (Exception ex) {
			return "etc/post";
		}
	}
	
	/* 제품(기타) 페이지 */
	@RequestMapping(value="/etc/posts", method=RequestMethod.GET)
	public String etcList(Model model, HttpServletRequest req) {
		// 일반 검색
		String search = req.getParameter("q");
		// 브랜드별 분류
		String brand = req.getParameter("brand");
		List<Etc> etcList;
		// 접속 중인 멤버
		HttpSession session = req.getSession();
		Member member = (Member)session.getAttribute("member");		

		if(search != null) {
			etcList = etcDao.selectSearchString(search);
		} else if(brand != null) {
			etcList = etcDao.selectBrand(brand);
		} else etcList = etcDao.selectAll();
		
		// 좋아요 여부
		if(member != null) {
			List<Integer> favoriteList = new ArrayList<>();
			for(Etc etc:etcList) {
				if(favoriteSvc.check(member, etc.getCategory(), etc.getId())) favoriteList.add(etc.getId());
			}
			if(!favoriteList.isEmpty()) model.addAttribute("favorite", favoriteList);
		}
		
		model.addAttribute("search", search);
		model.addAttribute("brandSearch", brand);
		
		Collections.reverse(etcList);
		model.addAttribute("etcList", etcList);
		return "etc/posts";
	}
	
	/* 게시물별 등록 댓글 가져오기 */
	public List<Comment> getRegistedComment(int num, String category) {
		List<Comment> comments = commentDao.selectByPost(num, category);
		Collections.reverse(comments); // 최신순
		
		return comments;
	}
	
	/* 제품(가전) 상세 정보 페이지 */
	@RequestMapping(value="/etc/post", method=RequestMethod.GET)
	public String etcDetailInfo(Model model, HttpServletRequest req) {
		int etcId = Integer.parseInt(req.getParameter("id"));
		
		// 등록된 제품 정보
		Etc etc = etcDao.selectById(etcId);
		model.addAttribute("etc", etc);
		
		// 등록된 댓글
		List<Comment> comments = getRegistedComment(etcId, etc.getCategory());
		model.addAttribute("comments", comments);
				
		// 평점 평균
		double ratingAvg = etc.getRatingsum() / etc.getComment();
		model.addAttribute("ratingAvg", ratingAvg);
				
		// 현재 접속 중인 회원의 좋아요 여부
		HttpSession session = req.getSession();
		Member member = (Member)session.getAttribute("member");
		if(member != null) {
			boolean favoriteCheck = favoriteSvc.check(member, etc.getCategory(), etcId);
			if(favoriteCheck) model.addAttribute("favorite", favoriteCheck);			
		}
				
		return "etc/post";
	}
	
	/* 제품(가전) 상세 정보 페이지 하단 댓글 처리 */
	@RequestMapping(value="/etc/post", method=RequestMethod.POST)
	public String postEtcDetailInfo(Model model) {
		// 댓글
		model.addAttribute("commentRegisterRequest", new CommentRegisterRequest());
				
		return "etc/post";
	}
	
	@RequestMapping(value="/etc/post-comment", method=RequestMethod.POST)
	public String postEtcCommentCompletion(Model model, CommentRegisterRequest regReq, HttpServletRequest req) {
		try {			
			commentRegSvc.regist(regReq);
			etcDetailInfo(model, req);
		} catch (Exception ex) {
			System.out.println("댓글 등록 실패");
		}
		
		return "etc/post";
	}
	
	/* 관심 등록 처리 */
	@RequestMapping(value="/etc/add-favorite", method=RequestMethod.POST)
	public String addFavorite(Model model, HttpServletRequest req) {
		// 게시물 번호
		int etcId = Integer.parseInt(req.getParameter("id"));
		Etc etc = etcDao.selectById(etcId);	
		
		// 현재 페이지가 posts(게시물 리스트)
		String focusOnPosts = req.getParameter("posts");
				
		// 접속 중인 멤버
		HttpSession session = req.getSession();
		Member member = (Member)session.getAttribute("member");
		
		try {
			favoriteSvc.add(member.getId(), etcId, etc.getCategory());
			if(focusOnPosts != null && focusOnPosts.equals("true")) {
				etcList(model, req);
				return "etc/posts";
			} else etcDetailInfo(model, req);
		} catch (Exception ex) {
			System.out.println("관심 등록 실패");
		}		
		
		return "etc/post";
	}
	
	/* 관심 해제 처리 */
	@RequestMapping(value="/etc/release-favorite", method=RequestMethod.POST)
	public String releaseFavorite(Model model, HttpServletRequest req) {
		// 게시물 번호
		int etcId = Integer.parseInt(req.getParameter("id"));
		Etc etc = etcDao.selectById(etcId);	
		
		// 현재 페이지가 posts(게시물 리스트)
		String focusOnPosts = req.getParameter("posts");
				
		// 접속 중인 멤버
		HttpSession session = req.getSession();
		Member member = (Member)session.getAttribute("member");
		
		try {
			favoriteSvc.release(member.getId(), etcId, etc.getCategory());
			if(focusOnPosts != null && focusOnPosts.equals("true")) {
				etcList(model, req);
				return "etc/posts";
			} else etcDetailInfo(model, req);
		} catch (Exception ex) {
			System.out.println("관심 해제 실패");
		}		
		
		return "etc/post";
	}
	
	/* 상세 검색 */
	@RequestMapping(value="/etc/posts-datail-search", method=RequestMethod.GET)
	public String etcDetailSearchList(Model model, EtcSearchDetailRequest esReq) {
		List<Etc> etcList = etcSearchSvc.search(esReq);
		
		Collections.reverse(etcList);
		model.addAttribute("etcList", etcList);
		
		return "etc/posts";
	}

}
