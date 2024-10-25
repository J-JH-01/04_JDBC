package edu.kh.toyList.DAO;

import static edu.kh.toy.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.kh.toy.dto.Toy;



public class toyListDAOImpl implements toyListDAO {

	// JDBC 객체 참조변수 + Properties 참조 변수 선언
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private Properties prop;
	
	public toyListDAOImpl() {
		// toyListImpl 객체가 생성될 때
		// sql.xml 파일의 내용을 읽어와
		// Properties prop 객체에 K:V 세팅
		try {
			
			String filePath = toyListDAOImpl.class.getResource("/xml/sql.xml").getPath();
		
			prop = new Properties();
			prop.loadFromXML(new FileInputStream(filePath));
			
			
			
		} catch (Exception e) {
			System.out.println("sql.xml 로드 중 예외 발생");
			e.printStackTrace();
		}
		
	}
	
	@Override
	public List<Toy> toyListFullView(Connection conn) throws Exception{
		// 결과 저장용 변수 선언
		List <Toy> toyList = new ArrayList<Toy>();

		
		try {
			String sql = prop.getProperty("toyListFullView");
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				// Builder 패턴 : 특정 값으로 초기화된 객체를 쉽게 만드는 방법
				// -> Lombok에서 제공하는 @Builder 어노테이션을 DTO에 작성해 두면 사용가능한 패턴
				
				Toy toy = Toy.builder()
							.stdNo(rs.getInt("STD_NO"))
							.stdName(rs.getString("STD_NAME"))
							.stdAge(rs.getInt("STD_AGE"))
							.stdGen(rs.getString("STD_GENDER"))
							.stdScore(rs.getString("STD_SCORE"))
							.build();
				
				toyList.add(toy);
				
			}
			
		} finally {
			close(rs);
			close(stmt);
			
		}
		return toyList;
		
		
	}

	@Override
	public int getCompleteCount(Connection conn) throws Exception {

		int completeCount = 0 ;
		
		try {
			
			String sql = prop.getProperty("getCompleteCount");
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				completeCount = rs.getInt(1);
				
			}
		} finally {
			close(rs);
			close(stmt);
		}
		
		return completeCount;
	}
		
	
	@Override
	public int toyAdd(Connection conn, String title, String detail) throws Exception {
		
		int result = 0;
		
		try {
			String sql = prop.getProperty("toyAdd");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, title);
			pstmt.setString(2, detail);

			result = pstmt.executeUpdate();
			
			
		} finally {
			close(pstmt);
			
		} 
		
		return result;
	}

	@Override
	public Toy toyDetailView(Connection conn, int toyNo) throws Exception{

		Toy toy = null; // 결과 저장용 변수 선언
		
		try {
			
			String sql = prop.getProperty("toyDetailView");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, toyNo);
			
			rs = pstmt.executeQuery();

			if(rs.next()) {
				
				toy = toy.builder()
						.stdNo(rs.getInt("STD_NO"))
						.stdName(rs.getString("STD_NAME"))
						.stdAge(rs.getInt("STD_AGE"))
						.stdGen(rs.getString("STD_GENDER"))
						.stdScore(rs.getString("STD_SCORE"))
						.build();
				
			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return toy;
	}

	@Override
	public int toyComplete(Connection conn, int toyNo) throws Exception {

		int result = 0;
		
		try {
			
			String sql = prop.getProperty("toyComplete");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, toyNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
			
		}
		
		return result;
	
	}

	@Override
	public int toyEdit(Connection conn, int toyNo, String title, String detail) throws Exception {

		int result = 0;
		
		try {
			String sql = prop.getProperty("toyUpdate");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, title);
			pstmt.setString(2, detail);
			pstmt.setInt(3, toyNo);
			
			result = pstmt.executeUpdate();
			
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}
	
	@Override
	public int toyDelete(Connection conn, int toyNo) throws Exception {
		
		int result = 0;
		
		try {
			
			String sql = prop.getProperty("toyDelete");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, toyNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}
	
	
	
	
	
	
	
	
}
