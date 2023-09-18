package com.poscodx.mysite.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscodx.mysite.vo.GuestBookVo;

@Repository
public class GuestBookRepository {
	@Autowired
	private DataSource dataSource;
	@Autowired
	private SqlSession session;
	
	public List<GuestBookVo> findAll() {
		
		// 여러가지 xml에 있을 수 있기 때문에 namespace(guestbook)도 같이 넣어줘야 함
		List<GuestBookVo> result = session.selectList("guestbook.findAll");
		return result;
	}
	
	public void insert(GuestBookVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql =
					"insert into guestbook values(null, ?, ?, ?, now());";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getContents());
			
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
	
	
	public GuestBookVo findByNo(int no) {
		GuestBookVo vo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {	
			conn = dataSource.getConnection();
			
			String sql =
					"select password from guestbook where no=?";
			pstmt = conn.prepareStatement(sql);	
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String password = rs.getString(1);
				
				vo = new GuestBookVo();
				vo.setPassword(password);
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
	
	public void deleteByNo(int no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql =
					"delete from guestbook where no=?";
			pstmt = conn.prepareStatement(sql);		
			pstmt.setInt(1, no);
			
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
	
	
//	
//	private Connection getConnection() throws SQLException {
//		Connection conn = null;
//		try {
//			Class.forName("org.mariadb.jdbc.Driver");
//			String url = "jdbc:mariadb://192.168.0.173:3307/webdb?charset=utf8";
//			conn = DriverManager.getConnection(url, "webdb", "mysql123");
//		} catch (ClassNotFoundException e) {
//			System.out.println("드라이버 로딩 실패 : "+e);
//		}
//
//		return conn;
//	}
}
