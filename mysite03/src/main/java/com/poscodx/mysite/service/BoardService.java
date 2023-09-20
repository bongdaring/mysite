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
		List<BoardVo> list = boardRepository.findAllByPage(page-1, totalSize);
		
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
	
	public BoardVo findBoardByNo(Long no) {
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

	public void updateBoard(Long no, BoardVo boardVo) {
		boardRepository.updateBoard(no, boardVo);
	}

	public void deleteBoardByNo(Long no) {
		boardRepository.deleteByNo(no);
	}

	public void commandBoard(Long no, BoardVo boardVo, UserVo userVo) {
		BoardVo parentVo = boardRepository.findByNo(no);
		boardRepository.updateOrderNo(parentVo.getgNo(), parentVo.getoNo()+1);
		
		System.out.println("pg:"+parentVo);
		boardVo.setgNo(parentVo.getgNo());
		
		if(parentVo.getDepth() == 0) {
			boardVo.setoNo(1);
		} else {			
			boardVo.setoNo(parentVo.getoNo()+1);
		}
		boardVo.setDepth(parentVo.getDepth()+1);
		boardVo.setUserNo(userVo.getNo());
		
		boardRepository.insert(boardVo);
		
	}


	
	

}
