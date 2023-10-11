package com.poscodx.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import com.poscodx.mysite.vo.GuestBookVo;

@Repository
public class GuestBookRepository {
	@Autowired
	private SqlSession session;
	
	public List<GuestBookVo> findAll() {
		// 여러가지 xml에 있을 수 있기 때문에 namespace(guestbook)도 같이 넣어줘야 함
		return session.selectList("guestbook.findAll");
	}
	
	public boolean insert(GuestBookVo vo) {
		int count = session.insert("guestbook.insert", vo);
		
		return count==1;
	}
	
	
	public GuestBookVo findByNo(int no) {
		return session.selectOne("guestbook.findByNo", no);
	}
	
	public boolean deleteByNo(int no) {
		Map<String, Object> map = new HashMap<>();
		map.put("no", no);
		
		int count = session.delete("guestbook.deleteByNo", map);
		return count == 1;
	}

}
