package edu.kh.toy.common;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/* Template : 양식,틀,주형
 * -> "미리 만들어뒀다" 의미
 * 
 * 
 * JDBCTemplate : 
 * 	JDBC 관련 작업을 위한 코드를
 *  미리 작성해서 제공하는 클래스
 *  
 *  
 *  
 *  
 *  - Connection 생성
 *  - AutoCommit false
 *  - commit / rollback
 *  - 각종 close()
 * 
 * 
 * 
 * ********** 중요 ***********
 * 어디서든 JDBCTemplate 클래스를
 * 객체로 만들지 않고도 메서드를 사용할 수 있도록 하기 위해
 * 모든 메서드를 public static 으로 선언
 * 
 * 
 * 
 * 
 * 
 * 
 */
public class JDBCTemplate {

	// 필드
	private static Connection conn = null;
	// -> static 메서드에서 사용 가능한 필드로 static 필드 선언

	// 메서드

	/**
	 * 호출 시 Connection 객체를 생성해서 반환하는 메서드 + AutoCommit 끄기
	 * 
	 * @return Conn
	 */
	public static Connection getConnection() {

		try {

			// 이전에 참조하던 Connection 객체가 존재하고
			// 아직 close된 상태가 아니라면
			// 새로 만들지 않고 기존 COnnection 반환

			if (conn != null && !conn.isClosed())
				return conn;

			/*
			 * driver.xml 파일 내용 읽어오기
			 * 
			 * 이유 1 : 보안상의 이유 (Github에 DB연결 정보 등 올리면 해킹하라는 뜻...) --> .gitignore 파일에
			 * driver.xml 작성하여 git이 관리X
			 * 
			 * 이유 2 : 혹시라도 DB 연결 정보가 변경될 경우 Java 코드가 아닌 읽어오는 파일의 내용을 수정하면 되기 때문에 Java 코드 수정
			 * X -> 추가 재컴파일 필요 X
			 * 
			 */
			// 1. Properties 객체 생성
			// - Map의 자식 클래스
			// - K, V가 모두 String 타입
			// - xml 파일 입출력을 쉽게 할 수 있는 메서드 제공
			// Properties.storeToXML() -> xml 파일 만들기
			// Properties.loadFromXML() - > xml 파일 읽어오기

			Properties prop = new Properties();

			// 2. Properties 메서드를 이용해서
			// driver.xml 파일 내용을 읽어오기

			String filePath = JDBCTemplate.class.getResource("/xml/driver.xml").getPath();
			// -> 빌드 시 컴파일된 JDBCTemplate.class 파일의 위치에서
			// /xml/driver.xml 파일을 찾아 실제 경로를 얻어오는 방법
			// (src/main/resources 폴더 기준으로 경로 작성)

			System.out.println(filePath);
			
			prop.loadFromXML(new FileInputStream(filePath));

			// prop에 저장된(driver.xml 에서 읽어온 값)을 이용해서
			// Connection 객체 생성

			// prop.getProperty("key") : key가 일치하는 value를 반환
			Class.forName(prop.getProperty("driver"));

			String url = prop.getProperty("url");
			// oracle.jdbc.driver.OracleDriver

			String userName = prop.getProperty("userName");
			// toyList_jdbc

			String password = prop.getProperty("password");
			// toyList1234

			conn = DriverManager.getConnection(url, userName, password);

			// 만들어진 Connection 객체에서 AutoCommit 끄기
			conn.setAutoCommit(false);

		} catch (Exception e) {
			System.out.println("커넥션 생성 중 예외 발생...");
			e.printStackTrace();
		}

		return conn;
	}

	// ----------------------------------------------------------------------------

	/**
	 * 전달 받은 커넥션에서 수행한 SQL을 COMMIT 하는 메서드
	 * 
	 * @param conn
	 */
	public static void commit(Connection conn) {
		try {
			if (conn != null && !conn.isClosed())
				conn.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 전달 받은 커넥션에서 수행한 SQL을 ROLLBACK 하는 메서드
	 * 
	 * @param conn
	 */
	public static void rollback(Connection conn) {
		try {
			if (conn != null && !conn.isClosed())
				conn.rollback();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// ---------------------------------------------------------------------------

	// 자원반환

	/**
	 * 전달 받은 커넥션을 clsoe(자원 반환)하는 메서드
	 * 
	 * @param conn
	 */
	public static void close(Connection conn) {

		try {
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 전달 받은 Statement + PreparedStatement 둘 다 close() + 다형성 업캐스팅 적용 ->
	 * PreparedStatement는 Statement 자식
	 * 
	 * @param stmt
	 */
	public static void close(Statement stmt) {
		try {
			if (stmt != null && !stmt.isClosed())
				stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 전달 받은 ResultSet을 clsoe()하는 메서드
	 * 
	 * @param rs
	 */
	public static void close(ResultSet rs) {
		try {
			if (rs != null && !rs.isClosed())
				rs.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
