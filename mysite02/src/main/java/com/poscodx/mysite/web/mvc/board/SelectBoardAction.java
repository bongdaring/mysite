package com.poscodx.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.dao.UserDao;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.UserVo;
import com.poscodx.web.mvc.Action;
import com.poscodx.web.utils.WebUtil;

public class SelectBoardAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String no = request.getParameter("no");
		
		BoardVo readBoardVo = new BoardDao().findByNo(Long.parseLong(no));
		UserVo vo = new UserDao().findByNo(readBoardVo.getUserNo());
		readBoardVo.setUserEmail(vo.getEmail());
		request.setAttribute("vo", readBoardVo);
		
		
		new BoardDao().updateHitByNo(Long.parseLong(no));
		WebUtil.forward("board/view", request, response);

	}

}
