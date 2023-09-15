package com.poscodx.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.web.mvc.Action;
import com.poscodx.web.utils.WebUtil;

public class UpdateFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(true);
		
		if(session.getAttribute("authUser") == null) {
			WebUtil.forward("user/loginform", request, response);
		} else {
			String no = request.getParameter("no");
			
			BoardVo readBoardVo = new BoardDao().findByNo(Long.parseLong(no));
			request.setAttribute("vo", readBoardVo);
			
			WebUtil.forward("board/write", request, response);
		}
		

	}

}
