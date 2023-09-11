package com.poscodx.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import com.poscodx.mysite.vo.UserVo;

public class UserDao {
	public void insert(UserVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql =
					" insert " +
					" 	into user " +
					" values(null, ?, ?, password(?), ?, curdate())";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());
			
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
	
	public UserVo findByEmailAndPassword(String email, String password) {
		UserVo userVo = null;
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql =
					"select no, name, email, gender from user where email=? and password=password(?)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			
			rs = pstmt.executeQuery();

			if(rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				String findEmail = rs.getString(3);
				String gender = rs.getString(4);
				
				userVo = new UserVo();
				userVo.setNo(no);
				userVo.setName(name);
				userVo.setEmail(findEmail);
				userVo.setGender(gender);
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
		
		
		return userVo;
	}
	
	public UserVo findByNo(Long no) {
		UserVo userVo = null;
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql =
					"select email, password, gender from user where no=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, no);
			
			rs = pstmt.executeQuery();

			if(rs.next()) {
				String email = rs.getString(1);
				String password = rs.getString(2);
				String gender = rs.getString(3);
				
				userVo = new UserVo();
				userVo.setEmail(email);
				userVo.setPassword(password);
				userVo.setGender(gender);
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
		
		
		return userVo;
	}
	
	public void update(UserVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
//			String sql = "update user set name=?,  where email='dooly@gmail.com'";
			StringBuilder sb = new StringBuilder();
			sb.append("update user set name=?, gender=?");
			
			if(vo.getPassword() != "") {
				sb.append(", password=password(?)");
			}
			
			sb.append(" where no="+vo.getNo());
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getGender());
			
			if(vo.getPassword() != "") {
				pstmt.setString(3, vo.getPassword());
			} 
			
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
