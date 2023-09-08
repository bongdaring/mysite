package com.poscodx.mysite.web.mvc.guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.dao.GuestBookDao;
import com.poscodx.mysite.vo.GuestBookVo;
import com.poscodx.web.mvc.Action;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String no = request.getParameter("no");	
		String password = request.getParameter("password");

		GuestBookVo vo = new GuestBookDao().findByNo(Integer.parseInt(no));
		
		if(vo.getPassword().equals(password)){
			new GuestBookDao().deleteByNo(Integer.parseInt(no));
		} 

		response.sendRedirect(request.getContextPath()+"/guestbook");

	}

}
