package com.poscodx.mysite.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.poscodx.mysite.vo.UserVo;

public class AuthInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		//1. handler 종류 확인
		if(!(handler instanceof HandlerMethod)) {
			// DefaultServletHandler가 처리하는 경우(정적 자원, /assets/**)
			return true;
		}
		
		//2. casting
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		
		//3-1. Handler Method의 @Auth 가져오기
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
		
		//3-2. Handler Method의 @Auth가 없는 경우, Type(Class)의 @Auth 가져오기
		if(auth == null) {
			auth = handlerMethod.getBeanType().getAnnotation(Auth.class); 
		}
		
		//4. @Auth가 없는 경우
		if(auth == null) {
			return true;
		}
		
		//5. @Auth가 붙어 있는 경우, 인증(Authenficaion) 여부 확인
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		if(authUser == null) {
			response.sendRedirect(request.getContextPath()+"/user/login");
			return false;
		}
		
		//6. 권한(Authorizaion) 체크를 위해서 @Auth의 Role 가져오기("USER","ADMIN")
		String role = auth.Role();
		String authUserRole = authUser.getRole();

		if("ADMIN".equals(role)) {
			if(!role.equals(authUserRole)) {
				request.getRequestDispatcher("/WEB-INF/views/user/main/index.jsp")
				.forward(request, response);
				return false;
			} 			
		}
		//6. 인증확인!!
		
		return true;
	}

}
