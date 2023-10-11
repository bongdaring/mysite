package com.poscodx.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.mysite.repository.BoardRepository;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.UserVo;

@Service
public class BoardService {
	@Autowired
	private BoardRepository boardRepository;

	private final int PAGE_SIZE = 5;

	public List<BoardVo> findAllList(int page) {
		page = page < 1 ? 1 : page; 
		int totalSize = this.getTotalSize();
		int startCount = totalSize - (page - 1) * PAGE_SIZE;
		List<BoardVo> list = boardRepository.findAllByPage(page - 1, totalSize);

		for (BoardVo vo : list) {
			vo.setListNo(startCount--);
		}
		
		return list;
	}

	public int getTotalPage(int totalSize) {
		return totalSize % PAGE_SIZE == 0 ? totalSize / PAGE_SIZE : totalSize / PAGE_SIZE + 1;
	}

	public int getTotalSize() {
		return boardRepository.findAll().size();
	}

	public BoardVo findBoardByNo(Long no) {
		boardRepository.updateHitByNo(no);
		BoardVo boardVo = boardRepository.findByNo(no);
		return boardVo;
	}

	public void addBoard(BoardVo boardVo, UserVo authUser) {
		boardVo.setUserNo(authUser.getNo());
		boardRepository.insert(boardVo);
	}

	public void updateBoard(Long no, BoardVo boardVo) {
		boardRepository.updateBoard(no, boardVo);
	}

	public void deleteBoardByNo(Long no, UserVo authUser) {
		BoardVo vo = new BoardVo();
		vo.setNo(no);
		vo.setUserEmail(authUser.getEmail());
		boardRepository.deleteByNo(vo);
	}

	public void commandBoard(Long no, BoardVo boardVo, UserVo userVo) {
		BoardVo parentVo = boardRepository.findByNo(no);
		boardRepository.updateOrderNo(parentVo.getgNo(), parentVo.getoNo() + 1);

		boardVo.setgNo(parentVo.getgNo());

		if (parentVo.getDepth() == 0) {
			boardVo.setoNo(1);
		} else {
			boardVo.setoNo(parentVo.getoNo() + 1);
		}
		boardVo.setDepth(parentVo.getDepth() + 1);
		boardVo.setUserNo(userVo.getNo());

		boardRepository.insert(boardVo);

	}
}
