package cs.skuniv.HCH.controller;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import cs.skuniv.HCH.dao.CoffeeDao;
import cs.skuniv.HCH.dto.Coffee;

@Controller
public class MainController {
	
	@Autowired
	private CoffeeDao coffeeDao;
	
	@RequestMapping({"/", "/index"})
	public String home(Model model) {
		List<Coffee> coffeeList = coffeeDao.selectAll();
		Collections.reverse(coffeeList);
		model.addAttribute("coffeeList", coffeeList);
		return "index";
	}
	
	@RequestMapping(value="/select-category")
	public String selectCategory() { return "select-category"; }
	
	/* 파일 업로드 테스트 */
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
	}

}
