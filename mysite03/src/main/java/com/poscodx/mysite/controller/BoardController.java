package com.poscodx.mysite.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.poscodx.mysite.service.BoardService;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String getBoardList(
			Model model,
			@RequestParam(value="page", required = true, defaultValue = "1") int page
	) {	
		if(page < 1){
			page = 1;
		}
		
		int totalSize = boardService.getTotalSize();
		int totalPage = boardService.getTotalPage(totalSize);
		List<BoardVo> boardList = boardService.findAllList(page);
		model.addAttribute("list", boardList);
		model.addAttribute("totalpage", totalPage);
		model.addAttribute("page",page);
		
		return "board/list";
	}
	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String getBoardByNo(
			Model model,
			@RequestParam(value = "no", required = true, defaultValue = "") int no) {
		
		BoardVo boardVo = boardService.findBoardByNo(no);
		model.addAttribute("vo", boardVo);
		return "board/view";
		
	}
	
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String writeBoard() {
		return "board/write";
	}
	
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String writeBoard(HttpSession session, 
			BoardVo boardVo) {
		// Access Control(접근제어)
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		if(authUser == null) {
			return "redirect:/user/login";
		}
		///////////////////////////////////////////////
	
		boardService.addBoard(boardVo, authUser);
		return "redirect:/board";	
	}
	
	@RequestMapping(value="/modify", method = RequestMethod.GET)
	public String updateBoard() {
		return "board/modify";
	}
}
