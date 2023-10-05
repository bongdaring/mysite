package com.poscodx.mysite.controller;

import javax.servlet.ServletContext;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.poscodx.mysite.security.Auth;
import com.poscodx.mysite.service.FileUploadService;
import com.poscodx.mysite.service.SiteService;
import com.poscodx.mysite.vo.SiteVo;


//여기에서 인증받고 들어오는거
@Auth(Role = "ADMIN")
@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private ServletContext sc;
	
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
		
		if(url != null) {
			siteVo.setProfile(url);
		}
		SiteVo site = applicationContext.getBean(SiteVo.class);
		
		siteService.updateSite(siteVo);
//		model.addAttribute("siteVo", siteVo);
		sc.setAttribute("siteVo", siteVo);
		
//		site.setTitle(siteVo.getTitle());
//		site.setWelcome(siteVo.getWelcome());
//		site.setProfile(siteVo.getProfile());
//		site.setDescription(siteVo.getDescription());
		
		BeanUtils.copyProperties(siteVo, site);
		
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
