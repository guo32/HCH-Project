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

import cs.skuniv.HCH.dao.CommentDao;
import cs.skuniv.HCH.dao.MachineDao;
import cs.skuniv.HCH.dto.Coffee;
import cs.skuniv.HCH.dto.Comment;
import cs.skuniv.HCH.dto.Machine;
import cs.skuniv.HCH.dto.Member;
import cs.skuniv.HCH.request.CommentRegisterRequest;
import cs.skuniv.HCH.request.MachineRegisterRequest;
import cs.skuniv.HCH.service.CommentRegisterService;
import cs.skuniv.HCH.service.FavoriteService;
import cs.skuniv.HCH.service.MachineRegisterService;

@Controller
public class MachineController {
	
	@Autowired
	private MachineRegisterService machineRegSvc;
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
	
	/* 제품(가전) 페이지 */
	@RequestMapping(value="/machine/posts", method=RequestMethod.GET)
	public String machineList(Model model) {
		List<Machine> machineList = machineDao.selectAll();
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
		double ratingAvg = machine.getRatingsum() / (comments.size() + 1);
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
			
			// 게시물 번호
			int machineId = Integer.parseInt(req.getParameter("id"));
			Machine machine = machineDao.selectById(machineId);	
			
			// 접속 중인 멤버
			HttpSession session = req.getSession();
			Member member = (Member)session.getAttribute("member");
			
			// 재검색
			machine = machineDao.selectById(machineId);
			model.addAttribute("machine", machine);
				
			// 등록된 댓글
			List<Comment> comments = getRegistedComment(machineId, machine.getCategory());
			model.addAttribute("comments", comments);
					
			// 평점 평균
			double ratingAvg = machine.getRatingsum() / (comments.size() + 1);
			model.addAttribute("ratingAvg", ratingAvg);
						
			// 좋아요 여부
			if(member != null) {
				boolean favoriteCheck = favoriteSvc.check(member, machine.getCategory(), machineId);
				if(favoriteCheck) model.addAttribute("favorite", favoriteCheck);		
			}
		} catch (Exception ex) {
			System.out.println("댓글 등록 실패");
		}
		
		return "machine/post";
	}

}
