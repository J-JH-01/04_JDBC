package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExample4 {

	public static void main(String[] args) {

		// 부서명을 입력받아
		// 해당부서에 근무하는 사원의
		// 사번,이름,부서명,직급명을
		// 직급코드 오름차순으로 조회
		
		// 부서명 입력 : 총무부
		// 200 / 선동일 / 총무부 / 대표
		// 202 / 노옹철 / 총무부 / 부사장
		// ...
		
		// hint : SQL에서 문자열은 양쪽에 ''(홑따옴표) 필요
		// ex) 총무부 입력 => '총무부'
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String type = "jdbc:oracle:thin:@"; // 드라이버의 종류
			String host = "localhost"; // DB 서버 컴퓨터의 IP 또는 도메인 주소 , localhost == 현재컴퓨터
			String port = ":1521"; // 프로그램 연결을 위한 port 번호 (콜론필요)
			String dbName = ":XE"; // DBMS 이름(XE == eXpress Edition)
			String userName = "kh_jjh"; // 사용자 계정명
			String password = "kh1234"; // 계정 비밀번호
			
			conn = DriverManager.getConnection(type+host+port+dbName,userName,password);
			
			
			Scanner sc = new Scanner(System.in);
			System.out.print("부서명 입력 : ");
			String str = sc.nextLine();
			
			String sql = """
					SELECT EMP_ID,EMP_NAME,NVL(DEPT_TITLE,'없음') DEPT_TITLE,JOB_NAME
					FROM EMPLOYEE 
					JOIN JOB USING (JOB_CODE)
					LEFT JOIN DEPARTMENT ON (DEPT_ID = DEPT_CODE)
					WHERE DEPT_TITLE = '"""
					+str
					+"' ORDER BY JOB_CODE";
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			boolean flag = true;
			// 조회결과가 있다면 false, 없으면 true
			
			while(rs.next()) {
				flag = false;
				
				String empId = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				String deptTilte = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");
				
				System.out.printf(" %s / %s / %s / %s \n",empId,empName,deptTilte,jobName);
			}
			// flag == true -> while문이 수행된 적없음
			if(flag) System.out.println("일치하는 부서가 없던데요..");
			
			
		} catch (Exception e) {
				
		} finally {
			try {
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			} catch (Exception e) {
			}
		
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

}
