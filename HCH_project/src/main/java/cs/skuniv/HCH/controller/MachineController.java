package cs.skuniv.HCH.controller;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import cs.skuniv.HCH.dao.MachineDao;
import cs.skuniv.HCH.dto.Machine;
import cs.skuniv.HCH.request.MachineRegisterRequest;
import cs.skuniv.HCH.service.MachineRegisterService;

@Controller
public class MachineController {
	
	@Autowired
	private MachineRegisterService machineRegSvc;
	@Autowired
	private MachineDao machineDao;
	
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
	
	/* 제품(가전)등록 페이지 접근 */
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
	
	/* 제품(가전) 페이지 */
	@RequestMapping(value="/machine/posts", method=RequestMethod.GET)
	public String machineList(Model model) {
		List<Machine> machineList = machineDao.selectAll();
		Collections.reverse(machineList);
		model.addAttribute("machineList", machineList);
		return "machine/posts";
	}

}
