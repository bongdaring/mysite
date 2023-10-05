package com.poscodx.mysite.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscodx.mysite.vo.BoardVo;

@Repository
public class BoardRepository {
	@Autowired
	private SqlSession session;
	private final int PAGE_SIZE = 5;
	
	public List<BoardVo> findAllByPage(int page, int totalSize) {
		return session.selectList("board.findAllByPage", page*PAGE_SIZE);
	}
	
	public List<BoardVo> findAll() {
		return session.selectList("board.findAll");
	}
	
	
	public boolean insert(BoardVo vo) {
		System.out.println("g:" + vo.getgNo());
		int count = session.insert("board.insert", vo);
		
		return count==1;
	}
	
	public BoardVo findByNo(long no) {
		return session.selectOne("board.findByNo", no);
	}
	
	public void updateHitByNo(long no) {
		session.update("board.updateHitByNo", no);
		
	}

	public void updateOrderNo(int no, int oNo) {
		BoardVo vo = new BoardVo();
		vo.setgNo(no);
		vo.setoNo(oNo);
		session.update("board.updateOrderNo", vo);
	}
	
	public BoardVo updateBoard(Long no, BoardVo boardVo) {
		boardVo.setNo(no);
		session.update("board.updateBoard", boardVo);
		return boardVo;
	}
	
	public void deleteByNo(BoardVo boardVo) {
		session.delete("board.deleteByNo", boardVo);
	}

}
