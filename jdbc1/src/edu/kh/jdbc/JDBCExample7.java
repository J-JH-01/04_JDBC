package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class JDBCExample7 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		// EMPLOYEE	테이블에서
		// 사번, 이름, 성별, 급여, 직급명, 부서명을 조회
		// 단, 입력 받은 조건에 맞는 결과만 조회하고 정렬할 것
		
		// - 조건 1 : 성별 (M, F)
		// - 조건 2 : 급여 범위
		// - 조건 3 : 급여 오름차순/내림차순
		String ord =null;
		int gen ;
		System.out.print("조회할 성별(M/F) : ");
		String gender = sc.next().toUpperCase();
		if(gender.equals("F")) gen = 2;
		else if(gender.equals("M")) gen = 1;
		else {System.out.println("다시입력하세요");return;}
		
		System.out.print("급여 범위(최소,최대 순서로 작성)");
		int sal1 = sc.nextInt();
		int sal2 = sc.nextInt();
		
		if(sal2<=sal1) {
			System.out.println("제대로 써주세요");
			return;
		}
		
		System.out.print("급여 정렬 (1.ASC, 2.DESC)");
		int ordIn = sc.nextInt();
		
		if(ordIn == 1) ord = "ASC";
		else if(ordIn == 2) ord = "DESC";
		else {System.out.println("다시입력하세요");return;}
		// [실행화면]
		// 조회할 성별(M/F) : F
		// 급여 범위(최소, 최대 순서로 작성) : 3000000 4000000
		// 급여 정렬(1.ASC, 2.DESC) : 2
		
		// 사번 | 이름   | 성별 | 급여    | 직급명 | 부서명
		//--------------------------------------------------------
		// 218  | 이오리 | F    | 3890000 | 사원   | 없음
		// 203  | 송은희 | F    | 3800000 | 차장   | 해외영업2부
		// 212  | 장쯔위 | F    | 3550000 | 대리   | 기술지원부
		// 222  | 이태림 | F    | 3436240 | 대리   | 기술지원부
		// 207  | 하이유 | F    | 3200000 | 과장   | 해외영업1부
		// 210  | 윤은해 | F    | 3000000 | 사원   | 해외영업1부

		
		// 1. JDBD 객체 참조 변수 선언
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			// 2. DrivereManager를 이용해서 Connection 생성
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String type = "jdbc:oracle:thin:@"; // 드라이버의 종류
			String host = "localhost"; // DB 서버 컴퓨터의 IP 또는 도메인 주소 , localhost == 현재컴퓨터
			String port = ":1521"; // 프로그램 연결을 위한 port 번호 (콜론필요)
			String dbName = ":XE"; // DBMS 이름(XE == eXpress Edition)
			
			String url = type+host+port+dbName;
			String userName = "kh_jjh"; // 사용자 계정명
			String password = "kh1234"; // 계정 비밀번호
			
			
			 // 3.SQL 작성
			String sql = """
				SELECT EMP_ID,EMP_NAME,
				DECODE(SUBSTR(EMP_NO,8,1),'1','M','2','F') GENDER,
				SALARY,JOB_NAME,NVL(DEPT_TITLE,'없음') DEPT_TITLE 
				FROM EMPLOYEE 
				JOIN JOB USING(JOB_CODE)
				LEFT JOIN DEPARTMENT ON (DEPT_CODE=DEPT_ID)
				WHERE(SALARY BETWEEN ? AND ?) AND SUBSTR(EMP_NO,8,1) = ?
				ORDER BY SALARY"""+" "+ord;
			// -> placeholder -> String -> 리터럴표기법 '' 붙혀서 나옴
			// -> int -> 값
			
			// 입력받은 정렬(sort) 값에 따라서 sql에
			// 오름/내림 차순 SQL 추가하기
			
			// 4.preparedStatement 객체 생성
			conn = DriverManager.getConnection(url,userName,password);
			pstmt = conn.prepareStatement(sql);
			
			// 5. ?(위치홀더)에 알맞은 값 세팅
			pstmt.setInt(1, sal1);
			pstmt.setInt(2, sal2);
			pstmt.setInt(3, gen);
			
			// 6.SQL 수행 후결과 반환 받기
			rs = pstmt.executeQuery();
			
			
			System.out.println("사번 | 이름   | 성별 | 급여    | 직급명 | 부서명");
			System.out.println("--------------------------------------------------------");
			
			
			boolean flag = true; // true : 조회결과가 없음, false: 조회 결과가 존재함!!
			
			while(rs.next()) {
				flag = false; // while 1회 이상 반복함 == 조회결과가 1행이라도 있다
				
				String empId = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				String gend = rs.getString("GENDER");
				int salary = rs.getInt("SALARY");
				String jobName = rs.getString("JOB_NAME");
				String deptTitle= rs.getString("DEPT_TITLE");
				
				
				System.out.printf("%-4s | %3s | %-4s | %7d | %-3s  | %s \n",
						empId, empName, gend, salary, jobName, deptTitle);
				
			}
			
			if(flag) { // flag == true -> while문 수행 x  -> 조회결과없음
				System.out.println("조회 결과 없음");
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 8. 사용한 JDBC 객체 자원 반환
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

}
