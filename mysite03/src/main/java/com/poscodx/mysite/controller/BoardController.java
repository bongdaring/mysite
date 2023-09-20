package com.poscodx.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.poscodx.mysite.security.Auth;
import com.poscodx.mysite.security.AuthUser;
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
		
		int totalSize = boardService.getTotalSize();
		int totalPage = boardService.getTotalPage(totalSize);
		List<BoardVo> boardList = boardService.findAllList(page);
		model.addAttribute("list", boardList);
		model.addAttribute("totalpage", totalPage);
		model.addAttribute("page", page);

		return "board/list";
	}

	
	// @ModelAttribute를 사용하면 model.addAttribute 없이 자동으로 주입 가능?
	@RequestMapping(value = "/view/{no}", method = RequestMethod.GET)
	public String getBoardByNo(Model model, @PathVariable(value = "no", required = true) Long no) {
		BoardVo boardVo = boardService.findBoardByNo(no);
		model.addAttribute("vo", boardVo);
		return "board/view";
	}
	
	@Auth
	@RequestMapping(value = "/write", 
			method = RequestMethod.GET)
	public String writeBoard() {
		return "board/write";
	}
	
	@Auth
	@RequestMapping(value = "/write/{no}", 
			method = RequestMethod.GET)
	public String commendBoard(Model model, @PathVariable(value = "no", required = true) Long no) {
		model.addAttribute("no",no);
		return "board/write";
	}
	
	@Auth
	@RequestMapping(value = "/write/{no}", 
			method = RequestMethod.POST)
	public String commendBoard(@AuthUser UserVo authUser, BoardVo boardVo, @PathVariable(value = "no", required = true) Long no) {		
		boardService.commandBoard(no, boardVo, authUser);
		return "redirect:/board";
	}
	
	@RequestMapping(value = "/comment/{no}", 
			method = RequestMethod.GET)
	public String commentBoard(@PathVariable(value = "no", required = true) Long no) {
		return "board/write";
	}

	@Auth
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String writeBoard(@AuthUser UserVo authUser, BoardVo boardVo) {
		boardService.addBoard(boardVo, authUser);
		return "redirect:/board";
	}

	@Auth
	@RequestMapping(value = "/modify/{no}", method = RequestMethod.GET)
	public String updateBoard(@AuthUser UserVo authUser, Model model, @PathVariable(value = "no", required = true) Long no) {
		BoardVo boardVo = boardService.findBoardByNo(no);
		model.addAttribute("vo", boardVo);
		return "board/modify";
	}

	@Auth
	@RequestMapping(value = "/modify/{no}", method = RequestMethod.POST)
	public String updateBoard(@AuthUser UserVo authUser, BoardVo boardVo, Model model,
			@PathVariable(value = "no", required = true) Long no) {
		boardService.updateBoard(no, boardVo);
		model.addAttribute("vo", boardVo);
		return "redirect:/board/view/" + no;
	}
	
	@Auth
	@RequestMapping(value = "/delete/{no}", method = RequestMethod.GET)
	public String deleteBoardByNo(@AuthUser UserVo authUser, @PathVariable(value = "no", required = true) Long no) {
		boardService.deleteBoardByNo(no, authUser);
		return "redirect:/board?page=1";
	}

}
