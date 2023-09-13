package com.poscodx.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.UserVo;
import com.poscodx.web.mvc.Action;
import com.poscodx.web.utils.WebUtil;

public class WriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		HttpSession session = request.getSession(true);
		UserVo userVo = (UserVo)session.getAttribute("authUser");
		
		BoardVo vo = new BoardVo();
		vo.setTitle(title);
		vo.setContent(content);
		vo.setoNo(0);
		vo.setDepth(0);
		vo.setUserNo(userVo.getNo());
		
		new BoardDao().insert(vo);
		
		response.sendRedirect(request.getContextPath()+"/board?page=1");

	}

}
