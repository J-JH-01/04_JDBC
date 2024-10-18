package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class JDBCExample6 {

	public static void main(String[] args) {
		
		// 아이디,비밀번호,이름을 입력받아
		// 아이디,비밀번호가 일치하는 사용자(TB_USER)의
		// 이름을 수정(UPDATE)
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		
		try {
			
			// Connection 생성, 로그인
			Class.forName("oracle.jdbc.driver.OracleDriver");
			

			String type = "jdbc:oracle:thin:@"; // 드라이버의 종류
			String host = "localhost"; // DB 서버 컴퓨터의 IP 또는 도메인 주소 , localhost == 현재컴퓨터
			String port = ":1521"; // 프로그램 연결을 위한 port 번호 (콜론필요)
			String dbName = ":XE"; // DBMS 이름(XE == eXpress Edition)
			
			String url = type+host+port+dbName;
			String userName = "kh_jjh"; // 사용자 계정명
			String password = "kh1234"; // 계정 비밀번호
			
			conn = DriverManager.getConnection(url,userName,password);
			//오토커밋 끄기
			conn.setAutoCommit(false);
			
			//SQL 작성
			Scanner sc = new Scanner(System.in);
			
			System.out.print("아이디 입력 : ");
			String id = sc.nextLine();
			
			System.out.print("비밀번호 입력 : ");
			String pw = sc.nextLine();
			
			System.out.print("이름 입력 : ");
			String name = sc.nextLine();
			
			String sql ="""
					UPDATE TB_USER SET
					USER_NAME = ?
					WHERE USER_ID = ? 
					AND USER_PW = ?
					""";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1,name);
			pstmt.setString(2,id);
			pstmt.setString(3,pw);
			
			int result = pstmt.executeUpdate();
			
			// 성공 시 "수정 성공" + COMMIT
			
			// 실패 시 "아이디 또는 비밀번호 불일치" + ROLLBACK
			
			if(result > 0) {
				System.out.println(id + "의 정보가 수정되었습니다");
				conn.commit();
			}else {
				System.out.println("추가 실패하였습니다. 되돌립니다");
				conn.rollback();
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		
		
		
		
		
		
	}

}
