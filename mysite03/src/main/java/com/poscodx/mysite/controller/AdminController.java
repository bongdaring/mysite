package com.poscodx.mysite.controller;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.poscodx.mysite.security.Auth;
import com.poscodx.mysite.security.AuthUser;
import com.poscodx.mysite.service.FileUploadService;
import com.poscodx.mysite.service.SiteService;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.SiteVo;
import com.poscodx.mysite.vo.UserVo;

//여기에서 인증받고 들어오는거
@Auth(Role = "ADMIN")
@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private SiteService siteService;
	
	@Autowired
	private FileUploadService fileUploadService;

	@RequestMapping("")
	public String main(Model model) {
		SiteVo vo = siteService.findSite(); 
		model.addAttribute("siteVo", vo);
		return "admin/main";
	}

	@RequestMapping(value = "/main/update", method = RequestMethod.POST)
	public String updateSite(SiteVo siteVo, 
			@RequestParam("file") MultipartFile file, Model model) {
		String url = fileUploadService.restore(file);
		siteService.updateSite(siteVo, url);
		model.addAttribute("siteVo", siteVo);
		return "redirect:/admin";
	}

	@RequestMapping("/guestbook")
	public String guestbook() {
		return "admin/guestbook";
	}

	@RequestMapping("/board")
	public String board() {
		return "admin/board";
	}

	@RequestMapping("/user")
	public String user() {
		return "admin/user";
	}
}
