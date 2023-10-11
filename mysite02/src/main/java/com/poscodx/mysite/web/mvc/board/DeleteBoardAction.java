package com.poscodx.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.UserVo;
import com.poscodx.web.mvc.Action;


public class DeleteBoardAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(true);
		String no = request.getParameter("no");	
		UserVo userVo = (UserVo)session.getAttribute("authUser");
		new BoardDao().deleteByNo(Long.parseLong(no), userVo.getEmail());

		response.sendRedirect(request.getContextPath()+"/board?page=1");

	}

}
