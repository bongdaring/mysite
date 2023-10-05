package com.poscodx.mysite.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscodx.mysite.vo.SiteVo;

@Repository
public class SiteRepository {
	@Autowired
	private SqlSession session;
	// site.xml 만들기
	public SiteVo find() {
		return session.selectOne("site.find");
	}
	
	public void update(SiteVo vo) {
		session.update("site.update", vo);
	}

}
