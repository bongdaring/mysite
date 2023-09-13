package com.poscodx.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.dao.GuestBookDao;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.GuestBookVo;
import com.poscodx.web.mvc.Action;
import com.poscodx.web.utils.WebUtil;

public class DeleteBoardAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String no = request.getParameter("no");	

		new BoardDao().deleteByNo(Long.parseLong(no));

		response.sendRedirect(request.getContextPath()+"/board?page=1");

	}

}
