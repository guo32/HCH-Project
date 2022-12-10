package cs.skuniv.HCH.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {

	@RequestMapping(value="/error-404")
	public String error404(HttpServletRequest req, HttpServletResponse res) {
		return "/error-404";
	}
	
	@RequestMapping(value="/error-500")
	public String error500(HttpServletRequest req, HttpServletResponse res) {
		return "/error-500";
	}
	
}