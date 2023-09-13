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

public class CommentAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Long no = Long.parseLong(request.getParameter("no"));
		String title = request.getParameter("title");
		String content = request.getParameter("content");
	
		HttpSession session = request.getSession(true);
		UserVo userVo = (UserVo)session.getAttribute("authUser");
		
		BoardVo parentVo = new BoardDao().findByNo(no);
		new BoardDao().updateOrderNo(parentVo.getgNo(), parentVo.getoNo()+1);
		
		BoardVo vo = new BoardVo();
		vo.setTitle(title);
		vo.setContent(content);
		vo.setgNo(parentVo.getgNo());
		if(parentVo.getDepth() == 0) {
			vo.setoNo(1);
		} else {			
			vo.setoNo(parentVo.getoNo()+1);
		}
		vo.setDepth(parentVo.getDepth()+1);
		vo.setUserNo(userVo.getNo());
//		
		new BoardDao().insert(vo);
//		
		response.sendRedirect(request.getContextPath()+"/board?page=1");

	}

}
