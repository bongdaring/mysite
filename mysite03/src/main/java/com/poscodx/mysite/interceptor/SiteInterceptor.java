package com.poscodx.mysite.interceptor;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.poscodx.mysite.service.SiteService;
import com.poscodx.mysite.vo.SiteVo;

public class SiteInterceptor implements HandlerInterceptor{
	@Autowired
	private SiteService siteService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		SiteVo siteVo = (SiteVo)request.getServletContext().getAttribute("siteVo");
		if(siteVo == null) {
			siteVo = siteService.findSite();
			request.getServletContext().setAttribute("siteVo", siteVo);
		}
//		ServletContext sc = request.getServletContext();
//		if(sc.getAttribute("siteVo") != null) {
//			return true;
//		}
//		SiteVo siteVo = siteService.findSite();
//		sc.setAttribute("siteVo", siteVo);
		
		return true;
	}

	
}
