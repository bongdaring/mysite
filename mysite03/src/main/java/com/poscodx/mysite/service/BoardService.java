package com.poscodx.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.mysite.repository.BoardRepository;
import com.poscodx.mysite.repository.UserRepository;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.UserVo;

@Service
public class BoardService {
	@Autowired
	private BoardRepository boardRepository;
	@Autowired
	private UserRepository userRepository;

	public List<BoardVo> findAllList(int page) {
		
		int totalSize = this.getTotalSize();
		List<BoardVo> list = boardRepository.findAll(page-1, totalSize);
		
		for(BoardVo vo : list) {
			UserVo userVo = userRepository.findByNo(vo.getUserNo());
			vo.setUserName(userVo.getName());
			vo.setUserEmail(userVo.getEmail());
		}
		
		return list;
	}

	public int getTotalPage(int totalSize) {
		int totalPage = 0;
		
		if(totalSize%5 == 0) {
			totalPage = totalSize/5;
		} else {
			totalPage = totalSize/5+1;
		}
		
		return totalPage;
	}
	
	public int getTotalSize() {
		return boardRepository.findAll().size();
	}
	
	public BoardVo findBoardByNo(int no) {
		boardRepository.updateHitByNo(no);
		BoardVo boardVo = boardRepository.findByNo(no);
		UserVo userVo = userRepository.findByNo(boardVo.getUserNo());
		boardVo.setUserEmail(userVo.getEmail());
		return boardVo;
	}

	public void addBoard(BoardVo boardVo, UserVo authUser) {
		boardVo.setUserNo(authUser.getNo());
		boardRepository.insert(boardVo);
	}


	
	

}
