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
import cs.skuniv.HCH.dao.MachineDao;
import cs.skuniv.HCH.dto.Comment;
import cs.skuniv.HCH.dto.Machine;
import cs.skuniv.HCH.dto.Member;
import cs.skuniv.HCH.request.CommentRegisterRequest;
import cs.skuniv.HCH.request.MachineRegisterRequest;
import cs.skuniv.HCH.request.MachineSearchDetailRequest;
import cs.skuniv.HCH.service.CommentRegisterService;
import cs.skuniv.HCH.service.FavoriteService;
import cs.skuniv.HCH.service.MachineRegisterService;
import cs.skuniv.HCH.service.MachineSearchDetailService;

@Controller
public class MachineController {
	
	@Autowired
	private MachineRegisterService machineRegSvc;
	@Autowired
	private MachineSearchDetailService machineSearchSvc;
	@Autowired
	private MachineDao machineDao;
	
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
	
	/* 제품(가전) 등록 페이지 접근 */
	@RequestMapping(value="/machine/register", method=RequestMethod.GET)
	public String getMachineRegist() { return "machine/register"; }
	
	@RequestMapping(value="/machine/register", method=RequestMethod.POST)
	public String postMachineRegist(Model model) {
		model.addAttribute("machineRegisterRequest", new MachineRegisterRequest());
		return "machine/register";
	}
	
	@RequestMapping(value="/machine/register-completion", method=RequestMethod.POST)
	public String postMachineRegistCompletion(MachineRegisterRequest regReq, 
			@RequestParam("imagefile") MultipartFile file, HttpServletRequest req) {
		try {
			/* 이미지 파일 처리 */
			String filename = fileUpload(file, req);
			
			/* 바뀐 파일 이름 설정 & 등록 */
			regReq.setFilename(filename);
			machineRegSvc.regist(regReq);
			
			return "machine/register-completion";
		} catch (Exception ex) {
			System.out.println("등록 실패");
			return "machine/register";
		}
	}
	
	/* 상세 검색 */
	@RequestMapping(value="/machine/posts-datail-search", method=RequestMethod.GET)
	public String machineDetailSearchList(Model model, MachineSearchDetailRequest msReq) {
		List<Machine> machineList = machineSearchSvc.search(msReq);
		
		Collections.reverse(machineList);
		model.addAttribute("machineList", machineList);
		
		return "machine/posts";
	}
	
	/* 제품(가전) 수정 페이지 */
	@RequestMapping(value="/machine/edit-machine", method=RequestMethod.GET)
	public String getEditMachineInfo(Model model, HttpServletRequest req) {
		int id = Integer.parseInt(req.getParameter("id"));
		Machine machine = machineDao.selectById(id);
		model.addAttribute("machine", machine);
		
		return "machine/edit-machine";
	}
	
	@RequestMapping(value="/machine/edit-machine", method=RequestMethod.POST)
	public String postEditMachineInfo(Model model) {
		model.addAttribute("machineRegisterRequest", new MachineRegisterRequest());
		return "machine/edit-machine";
	}
	
	@RequestMapping(value="/machine/edit-machine-completion", method=RequestMethod.POST)
	public String postEditMachineInfoCompletion(Model model, MachineRegisterRequest regReq,
			@RequestParam("imagefile") MultipartFile file, HttpServletRequest req) {
		try {
			int machineId = Integer.parseInt(req.getParameter("id"));
			
			Machine machine = machineDao.selectById(machineId);
			
			model.addAttribute("machine", machine); // 없어도 될 것 같은데
			
			/* 변경된 이미지 파일이 없는 경우 */
			if(file.isEmpty() || file == null) {
				regReq.setFilename(machine.getFilename());				
			} else {
				String filename = fileUpload(file, req);
				regReq.setFilename(filename);
			}			
			
			machineRegSvc.edit(regReq, machineId);
			
			machineDetailInfo(model, req);
			
			return "machine/post";
		} catch (Exception ex) {
			System.out.println("수정 실패");
			return "machine/posts";
		}
	}
	
	/* 제품(가전) 삭제 */
	@RequestMapping(value="/machine/delete-machine", method=RequestMethod.GET)
	public String removeMachine(HttpServletRequest req) {
		try {
			machineRegSvc.remove(Integer.parseInt(req.getParameter("id")));
			return "machine/delete-machine";
		} catch (Exception ex) {
			return "machine/post";
		}
	}
	
	/* 제품(가전) 페이지 */
	@RequestMapping(value="/machine/posts", method=RequestMethod.GET)
	public String machineList(Model model, HttpServletRequest req) {
		// 일반 검색
		String search = req.getParameter("q");
		// 브랜드별 분류
		String brand = req.getParameter("brand");
		List<Machine> machineList;
		// 접속 중인 멤버
		HttpSession session = req.getSession();
		Member member = (Member)session.getAttribute("member");	
		
		if(search != null) {
			machineList = machineDao.selectSearchString(search);
		} else if(brand != null) {
			machineList = machineDao.selectBrand(brand);
		} else machineList = machineDao.selectAll();
		
		// 좋아요 여부
		if(member != null) {
			List<Integer> favoriteList = new ArrayList<>();
			for(Machine machine:machineList) {
				if(favoriteSvc.check(member, machine.getCategory(), machine.getId())) favoriteList.add(machine.getId());
			}
			if(!favoriteList.isEmpty()) model.addAttribute("favorite", favoriteList);
		}
		
		model.addAttribute("search", search);
		model.addAttribute("brandSearch", brand);
		
		Collections.reverse(machineList);
		model.addAttribute("machineList", machineList);
		return "machine/posts";
	}
	
	/* 게시물별 등록 댓글 가져오기 */
	public List<Comment> getRegistedComment(int num, String category) {
		List<Comment> comments = commentDao.selectByPost(num, category);
		Collections.reverse(comments); // 최신순
		
		return comments;
	}
	
	/* 제품(가전) 상세 정보 페이지 */
	@RequestMapping(value="/machine/post", method=RequestMethod.GET)
	public String machineDetailInfo(Model model, HttpServletRequest req) {
		int machineId = Integer.parseInt(req.getParameter("id"));
		
		// 등록된 제품 정보
		Machine machine = machineDao.selectById(machineId);
		model.addAttribute("machine", machine);
		
		// 등록된 댓글
		List<Comment> comments = getRegistedComment(machineId, machine.getCategory());
		model.addAttribute("comments", comments);
				
		// 평점 평균
		double ratingAvg = machine.getRatingsum() / machine.getComment();
		model.addAttribute("ratingAvg", ratingAvg);
				
		// 현재 접속 중인 회원의 좋아요 여부
		HttpSession session = req.getSession();
		Member member = (Member)session.getAttribute("member");
		if(member != null) {
			boolean favoriteCheck = favoriteSvc.check(member, machine.getCategory(), machineId);
			if(favoriteCheck) model.addAttribute("favorite", favoriteCheck);			
		}
				
		return "machine/post";
	}
	
	/* 제품(가전) 상세 정보 페이지 하단 댓글 처리 */
	@RequestMapping(value="/machine/post", method=RequestMethod.POST)
	public String postMachineDetailInfo(Model model) {
		// 댓글
		model.addAttribute("commentRegisterRequest", new CommentRegisterRequest());
				
		return "machine/post";
	}
	
	@RequestMapping(value="/machine/post-comment", method=RequestMethod.POST)
	public String postMachineCommentCompletion(Model model, CommentRegisterRequest regReq, HttpServletRequest req) {
		try {			
			commentRegSvc.regist(regReq);
			
			machineDetailInfo(model, req);
		} catch (Exception ex) {
			System.out.println("댓글 등록 실패");
		}
		
		return "machine/post";
	}
	
	/* 관심 등록 처리 */
	@RequestMapping(value="/machine/add-favorite", method=RequestMethod.POST)
	public String addFavorite(Model model, HttpServletRequest req) {
		// 게시물 번호
		int machineId = Integer.parseInt(req.getParameter("id"));
		Machine machine = machineDao.selectById(machineId);	
		
		// 현재 페이지가 posts(게시물 리스트)
		String focusOnPosts = req.getParameter("posts");
		
		// 접속 중인 멤버
		HttpSession session = req.getSession();
		Member member = (Member)session.getAttribute("member");
		
		try {
			favoriteSvc.add(member.getId(), machineId, machine.getCategory());
			if(focusOnPosts != null && focusOnPosts.equals("true")) {
				machineList(model, req);
				return "machine/posts";
			} else machineDetailInfo(model, req);
		} catch (Exception ex) {
			System.out.println("관심 등록 실패");
		}		
		
		return "machine/post";
	}
	
	/* 관심 해제 처리 */
	@RequestMapping(value="/machine/release-favorite", method=RequestMethod.POST)
	public String releaseFavorite(Model model, HttpServletRequest req) {
		// 게시물 번호
		int machineId = Integer.parseInt(req.getParameter("id"));
		Machine machine = machineDao.selectById(machineId);	
		
		// 현재 페이지가 posts(게시물 리스트)
		String focusOnPosts = req.getParameter("posts");
				
		// 접속 중인 멤버
		HttpSession session = req.getSession();
		Member member = (Member)session.getAttribute("member");
		
		try {
			favoriteSvc.release(member.getId(), machineId, machine.getCategory());
			if(focusOnPosts != null && focusOnPosts.equals("true")) {
				machineList(model, req);
				return "machine/posts";
			} else machineDetailInfo(model, req);
		} catch (Exception ex) {
			System.out.println("관심 해제 실패");
		}		
		
		return "machine/post";
	}

}
