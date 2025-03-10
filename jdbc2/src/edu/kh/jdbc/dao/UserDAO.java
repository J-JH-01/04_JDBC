package edu.kh.jdbc.dao;

import static edu.kh.jdbc.common.JDBCTemplate.close;
import static edu.kh.jdbc.common.JDBCTemplate.commit;
import static edu.kh.jdbc.common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.jdbc.dto.User;


// DAO (Data Access Object)
// 데이터가 저장된 곳에 접근하는 용도의 객체
// -> DB에 접근하여 Java에서 원하는 결과를 얻기위해
//	 sql을 수행하고 결과를 반환하는 역할
public class UserDAO {

	// 필드
	// - DB 접근 관련한 JDBC 객체 참조 변수 미리 선언
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	
	/** 전달받은 Connection을 이용해서 DB에 접근하여
	 * 전달 받은 아이디와 일치하는 User 정보 조회하기
	 * @param conn : Service에서 생성한 Connection 객체
	 * @param input : View에서 입력받은 아이디
	 * @return 아이디가 일치하는 회원의 User 객체 또는 null
	 */
	public User selecId(Connection conn, String input) {
		
		User user = null;// 결과 저장용 변수
		
		try {
			
			//SQL 작성
			String sql = "SELECT * FROM TB_USER WHERE USER_ID= ? ";
			
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// ? (위치 홀더)에 알맞는 값 대입
			pstmt.setString(1, input);
	
			// SQL 수행 후 결과 반환 받기
			rs = pstmt.executeQuery();
			
			// 조회 결과가 있을 경우
			//	-> 중복되는 아이디가 없을 경우
			// 1행만 조회되기 때문에 while문 보다는 if를 사용하는게 좋을것임
			
			if(rs.next()) {
				// 첫 행에 데이터가 존재한다면
				
				// 각 컬럼의 값 얻어오기
				int userNo = rs.getInt("USER_NO");
				String userId = rs.getString("USER_ID");
				String userPw = rs.getString("USER_PW");
				String userName = rs.getString("USER_NAME");
				// java.sql.Date 활용
				Date enrollDate = rs.getDate("ENROLL_DATE");

				// 조회된 컬럼값을 이용해서 User 객체 생성
				user = new User(userNo,
								userId,
								userPw,
								userName,
								enrollDate.toString());

				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 사용한 JDBC 객체 자원 반환(close)
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
			
			// Connection 객체는 생성된 Service에서 close 할 거임

			
		}
		
		return user; // 결과 반환 ( 생성된 User 또는 null) 
	}


	/** User 등록 DAO 메서드
	 * @param conn : DB 연결 정보가 담겨있는 Connection 객체
	 * @param user : 입력받은 id,pw,name이 세팅된 User 객체
	 * @return INSERT 결과 행의 개수
	 */
	public int insertUser(Connection conn, User user) throws Exception{
		
		//SQL 수행 중 발생하는 예외를
		// catch로 처리하지 않고, throws를 이용해서 호출부로 던져 처리
		// -> catch문 필요 없다!
		
		// 1. 결과 저장용 변수 선언
		int result = 0;
		
		try {
			
			// 2.SQL 작성
			String sql = """
					INSERT INTO TB_USER
					VALUES(SEQ_USER_NO3.NEXTVAL,?,?,?,DEFAULT)
					""";
			
			// 3. PreparedStatement 생성
			pstmt = conn.prepareStatement(sql);
			
			// 4. ?(위치홀더) 알맞은 값 대입
			pstmt.setString(1, user.getUserid());
			pstmt.setString(2, user.getUserPw());
			pstmt.setString(3, user.getUserName());
			
			// 5.SQL(INSERT) 수행 후(executeUpdate) 결과(삽입된 행의 개수) 반환받기
			
			result = pstmt.executeUpdate();
			
		} finally {
			// 6. 사용한 jdbc 객체 자원 반환(close)
			close(pstmt);
			
		}
		
		// 결과 저장용 변수에 저장된 값 반환 
		return result;
	}


	
	/** User 전체 조회 DAO 메서드
	 * @param conn
	 * @return userList
	 */
	public List<User> selectAll(Connection conn) throws Exception {
		
		// 1. 결과저장용 번수 선언
		// -> List 같은 컬렉션을 반환하는 경우에는
		//	변수 선언시 객체도 같이 선언해주자!
		List<User> userList = new ArrayList<User>();
		
		
		try {
			
			 // 2. SQL 작성
			String sql = """
					SELECT USER_NO, USER_ID, USER_PW, USER_NAME, 
					TO_CHAR(ENROLL_DATE,'YYYY"년" MM"월" DD"일"') ENROLL_DATE
					FROM TB_USER
					ORDER BY USER_NO ASC
					""";
			// 3. PreparedStatement 생성(Statement로 안해도 됨)
			pstmt = conn.prepareStatement(sql);
			
			// 4. ? 에 알맞은 값 대입 (없으면 패스)
			
			// 5. SQL(SELECT) 수행 후(executeQuery())
			// 결과 반환 받기(ResultSet) 받기
			rs = pstmt.executeQuery();
			
			// 6. 조회 결과를 1행씩 접근하여 컬럼 값 얻어오기
			
			// * 몇 행이 조회될지 모른다 -> while
			//   무조건 1행이 조회된다 -> if
			while(rs.next()) {
				
				int userNo = rs.getInt("USER_NO");
				String userId = rs.getString("USER_ID");
				String userPw = rs.getString("USER_PW");
				String userName = rs.getString("USER_NAME");
				String enrollDate = rs.getString("ENROLL_DATE");
				// - java.sql.Date 타입으로 값을 저장하지 않는 이유
				// -> TO_CHAR()를 이용해서 문자열로 변환했기 때문!
				
				// userList 에 추가
				// -> User 객체를 생성해 조회된 값을 담고
				// userList에 추가하기!
				
				User user = new User(userNo,userId,userPw,userName,enrollDate);
				
				userList.add(user);				
								
			}
			
			
			
			
			
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		// 조회 결과가 담긴 List 반환
		return userList;
	}


	public List<User> selectName(Connection conn,String keyword) throws Exception {


		List<User> userList = new ArrayList<User>();
		
		try {

			String sql = """
					SELECT USER_NO, USER_ID, USER_PW, USER_NAME, 
					TO_CHAR(ENROLL_DATE,'YYYY"년" MM"월" DD"일"') ENROLL_DATE
					FROM TB_USER
					WHERE USER_NAME LIKE '%' || ? || '%'
					ORDER BY USER_NO ASC
					""";
					
			// 3. PreparedStatement 생성(Statement로 안해도 됨)
			
			pstmt = conn.prepareStatement(sql);
			
			
			// 4. ? 에 알맞은 값 대입 (없으면 패스)
			pstmt.setString(1, keyword);
			
			// 5. SQL(SELECT) 수행 후(executeQuery())
			// 결과 반환 받기(ResultSet) 받기
			rs = pstmt.executeQuery();
			
			// 6. 조회 결과를 1행씩 접근하여 컬럼 값 얻어오기
			
			// * 몇 행이 조회될지 모른다 -> while
			//   무조건 1행이 조회된다 -> if
			while(rs.next()) {
				
				int userNo = rs.getInt("USER_NO");
				String userId = rs.getString("USER_ID");
				String userPw = rs.getString("USER_PW");
				String userName = rs.getString("USER_NAME");
				String enrollDate = rs.getString("ENROLL_DATE");

				
				User user = new User(userNo,userId,userPw,userName,enrollDate);
				
				userList.add(user);				
								
			}

		} finally {
			close(rs);
			close(pstmt);
		}
		
		return userList;
	}


	public User selectNo(Connection conn, int input) throws Exception{
		User user = null;// 결과 저장용 변수

		try {
			String sql = """
					SELECT USER_NO, USER_ID, USER_PW, USER_NAME, 
					TO_CHAR(ENROLL_DATE,'YYYY"년" MM"월" DD"일"') ENROLL_DATE
					FROM TB_USER
					WHERE USER_NO = ?
					ORDER BY USER_NO ASC
					""";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, input);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				int userNo = rs.getInt("USER_NO");
				String userId = rs.getString("USER_ID");
				String userPw = rs.getString("USER_PW");
				String userName = rs.getString("USER_NAME");
				String enrollDate = rs.getString("ENROLL_DATE");

				user = new User(userNo, userId, userPw, userName, enrollDate);
			}

		} finally {
			close(rs);
			close(pstmt);
		}

		return user; // 결과 반환 ( 생성된 User 또는 null)
	}


	public int deleteUser(Connection conn, int input) throws Exception{
		int user=0;
		
		try {
			String sql = """
					DELETE FROM TB_USER WHERE USER_NO = ?
					""";
			
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, input);

			user = pstmt.executeUpdate();
			
			if(user > 0) {
				System.out.println("삭제 성공");
				commit(conn);

			}else {
				System.out.println("사용자 번호가 일치하는 User가 존재하지 않습니다");
				rollback(conn);
			}

		} finally {
			close(pstmt);
		}

		
		return user;
	}


	public int updateName(Connection conn, String userId, String userPw) throws Exception{
		int user=0;
		
		try {
			Scanner sc = new Scanner(System.in);
			System.out.print("USER_NAME 입력 : ");
			String userName = sc.next();
			
			String sql = """
					SELECT USER_NO
					FROM TB_USER
					WHERE USER_ID = ? AND USER_PW = ?
					""";
			
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userId);
			pstmt.setString(2, userPw);

			rs = pstmt.executeQuery();
			
		if(rs.next()) {
			user = rs.getInt("USER_NO");
		}

		} finally {
			close(pstmt);
		}
		
		return user;
	}


	public int updateName2(Connection conn, String userName, int user) throws Exception{
		
		int result = 0 ;
		
		try {
			String sql = """
					UPDATE TB_USER
					SET USER_NAME  = ?
					WHERE USER_NO = ?
					""";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userName);
			pstmt.setInt(2, user);
			
			result = pstmt.executeUpdate();
			
			// 트랜잭션 제어
			if(result > 0 ) commit(conn);
			else			rollback(conn);
			
		} finally {
			close(pstmt);
		}
		
		return result;
	
	}


	/** 아이디 중복 확인 DAO
	 * @param conn
	 * @param userId
	 * @return
	 */
	public int idCheck(Connection conn, String userId) throws Exception{
		
		int count = 0 ;
		
		try {
			String sql="""
					SELECT COUNT(*)
					FROM TB_USER
					WHERE USER_ID = ?
					""";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1); // 조회된 컬럼 순서를 이용해
							  // 컬럼값 얻어오기
			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		
		return count;
	}
}








































