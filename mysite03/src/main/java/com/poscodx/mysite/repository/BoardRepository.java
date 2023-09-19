package com.poscodx.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.poscodx.mysite.vo.BoardVo;

@Repository
public class BoardRepository {
	
	public List<BoardVo> findAll(int page, int totalSize) {
		List<BoardVo> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			String sql =
					"select no, title, contents, hit, reg_date, g_no, o_no, depth, user_no from board order by g_no DESC, o_no ASC limit 5 offset ?";
			
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, page*5);
			
			rs = pstmt.executeQuery();

			while(rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String content = rs.getString(3);
				int hit = rs.getInt(4);
				String registerDate = rs.getString(5);
				int gno = rs.getInt(6);
				int ono = rs.getInt(7);
				int depth = rs.getInt(8);
				Long user_no = rs.getLong(9);
				
				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContent(content);
				vo.setHit(hit);
				vo.setRegisterDate(registerDate);
				vo.setgNo(gno);
				vo.setoNo(ono);
				vo.setDepth(depth);
				vo.setUserNo(user_no);
				
				result.add(vo);
			}	
			
		} catch (SQLException e) {
			System.out.println("error:"+e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public List<BoardVo> findAll() {
		List<BoardVo> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();

			String sql =
					"select no, title, contents, hit, reg_date, g_no, o_no, depth, user_no from board order by g_no DESC, o_no ASC";
					
			pstmt = conn.prepareStatement(sql);			
			rs = pstmt.executeQuery();

			while(rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String content = rs.getString(3);
				int hit = rs.getInt(4);
				String registerDate = rs.getString(5);
				int gno = rs.getInt(6);
				int ono = rs.getInt(7);
				int depth = rs.getInt(8);
				Long user_no = rs.getLong(9);
				
				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContent(content);
				vo.setHit(hit);
				vo.setRegisterDate(registerDate);
				vo.setgNo(gno);
				vo.setoNo(ono);
				vo.setDepth(depth);
				vo.setUserNo(user_no);
				
				result.add(vo);
			}	
			
		} catch (SQLException e) {
			System.out.println("error:"+e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	
	public void insert(BoardVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			String sql ="";
			conn = getConnection();
			if(vo.getgNo() == 0) {
				sql = "insert into board values(null, ?, ?, 0, now(), IFNULL((select max(b.g_no) from board b),0)+1, ?, ?, ?)";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, vo.getTitle());
				pstmt.setString(2, vo.getContent());
//				pstmt.setLong(3, vo.getgNo());
				pstmt.setInt(3, vo.getoNo());
				pstmt.setInt(4, vo.getDepth());
				pstmt.setLong(5, vo.getUserNo());
				
			} else {
				sql = "insert into board values(null, ?, ?, 0, now(), ?, ?, ?, ?)";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, vo.getTitle());
				pstmt.setString(2, vo.getContent());
				pstmt.setLong(3, vo.getgNo());
				pstmt.setInt(4, vo.getoNo());
				pstmt.setInt(5, vo.getDepth());
				pstmt.setLong(6, vo.getUserNo());
			}

			
			pstmt.executeQuery();
			
		} catch (SQLException e) {
			System.out.println("error:"+e);
		} finally {
			// 6. 자원정리
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public BoardVo findByNo(long no) {
		BoardVo vo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {	
			conn = getConnection();
			
			String sql =
					"select no, title, contents, g_no, o_no, depth, user_no from board where no=?";
			pstmt = conn.prepareStatement(sql);	
			pstmt.setLong(1, no);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Long findNo = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				int gNo = rs.getInt(4);
				int oNo = rs.getInt(5);
				int depth = rs.getInt(6);
				Long userNo = rs.getLong(7);
				
				vo = new BoardVo();
				vo.setNo(findNo);
				vo.setTitle(title);
				vo.setContent(contents);
				vo.setgNo(gNo);
				vo.setoNo(oNo);
				vo.setDepth(depth);
				vo.setUserNo(userNo);
			}		
		} catch (SQLException e) {
			System.out.println("error:"+e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return vo;
	}
	
	public void updateHitByNo(long no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			String sql = "update board set hit=hit+1  where no=?";

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, no);
			
			pstmt.executeQuery();
			
		} catch (SQLException e) {
			System.out.println("error:"+e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void updateOrderNo(int no, int test) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			String sql = "update board set o_no=o_no+1 where g_no=? and o_no!=0 and o_no >= ?";

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, no);
			pstmt.setInt(2, test);
			
			pstmt.executeQuery();
			
		} catch (SQLException e) {
			System.out.println("error:"+e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
	public BoardVo updateBoard(Long no, BoardVo boardVo) {
		BoardVo vo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			String sql = "update board set title=?, contents=? where no=?";

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContent());
			pstmt.setLong(3, no);
			
			pstmt.executeQuery();
			
			vo = new BoardVo();
			vo.setNo(no);
			vo.setTitle(boardVo.getTitle());
			vo.setContent(boardVo.getContent());
			
		} catch (SQLException e) {
			System.out.println("error:"+e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return vo;
		
	}
	
	public void deleteByNo(Long no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql =
					"delete from board where no=?";
			pstmt = conn.prepareStatement(sql);		
			pstmt.setLong(1, no);
			
			pstmt.executeQuery();
			
		} catch (SQLException e) {
			System.out.println("error:"+e);
		} finally {
			// 6. 자원정리
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
	private Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			String url = "jdbc:mariadb://192.168.0.173:3307/webdb?charset=utf8";
			conn = DriverManager.getConnection(url, "webdb", "mysql123");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패 : "+e);
		}

		return conn;
	}






}
