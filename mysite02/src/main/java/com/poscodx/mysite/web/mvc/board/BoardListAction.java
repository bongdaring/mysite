package com.poscodx.mysite.web.mvc.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.dao.UserDao;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.UserVo;
import com.poscodx.web.mvc.Action;
import com.poscodx.web.utils.WebUtil;

public class BoardListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		int page = Integer.parseInt(request.getParameter("page"));
		int totalPage = 0;
		
		if(page < 1){
			page = 1;
		}
		
		List<BoardVo> totalList = new BoardDao().findAll();
		List<BoardVo> list = new BoardDao().findAll(page-1, totalList.size());

		if(totalList.size()%5 == 0) {
			totalPage = totalList.size()/5;
		} else {
			totalPage = totalList.size()/5+1;
		}
		
		for(BoardVo vo : list) {
			UserVo userVo = new UserDao().findByNo(vo.getUserNo());
			vo.setUserName(userVo.getName());
			vo.setUserEmail(userVo.getEmail());
		}
		
		request.setAttribute("list", list);
		request.setAttribute("page", page);
		request.setAttribute("totalpage", totalPage);
		
		WebUtil.forward("board/list", request, response);
//		response.sendRedirect(request.getContextPath()+"/board?page="+page);
		

	}

}
