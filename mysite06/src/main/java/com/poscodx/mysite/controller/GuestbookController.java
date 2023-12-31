package com.poscodx.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.poscodx.mysite.security.Auth;
import com.poscodx.mysite.service.GuestbookService;
import com.poscodx.mysite.vo.GuestBookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {
	@Autowired
	private GuestbookService guestbookService;
	
	@Auth // 액세tm 제어하는 부분에 달거임
	@RequestMapping("/")
	public String main(Model model) {
		List<GuestBookVo> list = guestbookService.getContentsList();
		model.addAttribute("list", list);
		return "guestbook/main";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(@ModelAttribute GuestBookVo vo) {
		 // @ModelAttribute : 화면에서 넘어온 파라미터들을 이용해서 DTO 생성 후 바로 받을 수 있다.
		guestbookService.addContents(vo);
		return "redirect:/guestbook/";
	}
	
	@RequestMapping(value = "/delete/{no}", method = RequestMethod.GET)
	public String delete(@PathVariable("no") Long no, Model model) {
		model.addAttribute("no", no);
		return "guestbook/delete";
	}
	
	@RequestMapping(value = "/delete/{no}", method = RequestMethod.POST)
	public String delete(@PathVariable(value="no", required=true) int no, String password) {
		guestbookService.deleteContents(no, password);
		return "redirect:/guestbook/";
	}
	
	@RequestMapping("/ajax")
	public String ajax(Model model) {
		return "guestbook/main-ajax";
	}
	
}
