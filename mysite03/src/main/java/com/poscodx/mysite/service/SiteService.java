package com.poscodx.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.mysite.repository.SiteRepository;
import com.poscodx.mysite.vo.SiteVo;

@Service
public class SiteService {
	
	@Autowired
	private SiteRepository repository;
	
	public SiteVo getSite() {
		return null;
	}
	
	public void updateSite(SiteVo vo, String url) {
		vo.setProfile(url);
		repository.update(vo);
	}

	public SiteVo findSite() {
		return repository.find();
	}
	
}