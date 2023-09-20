package com.poscodx.mysite.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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
	public String getBoardList(Model model,
			@RequestParam(value = "page", required = true, defaultValue = "1") int page) {
		if (page < 1) {
			page = 1;
		}

		int totalSize = boardService.getTotalSize();
		int totalPage = boardService.getTotalPage(totalSize);
		List<BoardVo> boardList = boardService.findAllList(page);
		model.addAttribute("list", boardList);
		model.addAttribute("totalpage", totalPage);
		model.addAttribute("page", page);

		return "board/list";
	}

	@RequestMapping(value = "/view/{no}", method = RequestMethod.GET)
	public String getBoardByNo(Model model, @PathVariable(value = "no", required = true) Long no) {

		BoardVo boardVo = boardService.findBoardByNo(no);
		model.addAttribute("vo", boardVo);
		return "board/view";
	}

	@RequestMapping(value = "/write", 
			method = RequestMethod.GET)
	public String writeBoard() {
		return "board/write";
	}
	
	@RequestMapping(value = "/write/{no}", 
			method = RequestMethod.GET)
	public String commendBoard(Model model, @PathVariable(value = "no", required = true) Long no) {
		model.addAttribute("no",no);
		return "board/write";
	}
	
	@RequestMapping(value = "/write/{no}", 
			method = RequestMethod.POST)
	public String commendBoard(HttpSession session, BoardVo boardVo, @PathVariable(value = "no", required = true) Long no) {
		this.checkUser(session);
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		
		boardService.commandBoard(no, boardVo, authUser);
		return "redirect:/board";
	}
	
	@RequestMapping(value = "/comment/{no}", 
			method = RequestMethod.GET)
	public String commentBoard(@PathVariable(value = "no", required = true) Long no) {
		return "board/write";
	}

	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String writeBoard(HttpSession session, BoardVo boardVo) {

		this.checkUser(session);
		UserVo authUser = (UserVo) session.getAttribute("authUser");

		boardService.addBoard(boardVo, authUser);
		return "redirect:/board";
	}

	@RequestMapping(value = "/modify/{no}", method = RequestMethod.GET)
	public String updateBoard(HttpSession session, Model model, @PathVariable(value = "no", required = true) Long no) {
		this.checkUser(session);
		BoardVo boardVo = boardService.findBoardByNo(no);
		model.addAttribute("vo", boardVo);
		return "board/modify";
	}

	@RequestMapping(value = "/modify/{no}", method = RequestMethod.POST)
	public String updateBoard(HttpSession session, BoardVo boardVo, Model model,
			@PathVariable(value = "no", required = true) Long no) {

		this.checkUser(session);
		boardService.updateBoard(no, boardVo);
		model.addAttribute("vo", boardVo);
		return "redirect:/board/view/" + no;
	}

	@RequestMapping(value = "/delete/{no}", method = RequestMethod.GET)
	public String deleteBoardByNo(HttpSession session, @PathVariable(value = "no", required = true) Long no) {
		this.checkUser(session);
		boardService.deleteBoardByNo(no);
		return "redirect:/board?page=1";
	}

	private String checkUser(HttpSession session) {
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			return "redirect:/user/login";
		}
		return "";
	}
}
