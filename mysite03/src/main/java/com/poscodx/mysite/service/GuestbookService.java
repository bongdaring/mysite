package com.poscodx.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.mysite.repository.GuestBookRepository;
import com.poscodx.mysite.vo.GuestBookVo;

@Service
public class GuestbookService {
	
	@Autowired
	private GuestBookRepository guestbookRepository;

	public List<GuestBookVo> getContentsList() {
		return guestbookRepository.findAll();
	}

	public Boolean deleteContents(int no, String password) {
		GuestBookVo vo = guestbookRepository.findByNo(no);
		if(vo.getPassword().equals(password)) {
			guestbookRepository.deleteByNo(no);			
		}
		return true;
	}
	
	public Boolean addContents(GuestBookVo vo) {
		guestbookRepository.insert(vo);
		return true;
	}
}
