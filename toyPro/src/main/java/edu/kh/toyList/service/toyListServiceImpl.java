package edu.kh.toyList.service;

import static edu.kh.toy.common.JDBCTemplate.close;
import static edu.kh.toy.common.JDBCTemplate.commit;
import static edu.kh.toy.common.JDBCTemplate.getConnection;
import static edu.kh.toy.common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.kh.toy.dto.Toy;
import edu.kh.toyList.DAO.toyListDAO;
import edu.kh.toyList.DAO.toyListDAOImpl;

public class toyListServiceImpl implements toyListService {

	private toyListDAO dao = new toyListDAOImpl();
	
	
	@Override
	public Map<String, Object> toyListFullView() throws Exception{
		
		//커넥션 생성
		Connection conn = getConnection();
		
		// 학생 목록 얻어오기 (dao 호출 및 반환 받기)
		List<Toy> toyList = dao.toyListFullView(conn);
		
		
		// 메서드에서 반환은 하나의 값 또는 객체밖에 할 수 없기 때문에
		// Map 이라는 컬렉션을 이용해 여러 값을  한번에 묶어서 반환
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("toyList", toyList);
		// autoboxing 때문에 completeCount가 래퍼클래스로 자동 변환됨
		
		
		return map;
	}
	
	
	@Override
	public int toyAdd(String title, String detail) throws Exception {
		
		Connection conn = getConnection();
		
		int result = dao.toyAdd(conn, title, detail);
		
		if(result > 0 ) commit(conn);
		else			rollback(conn);
		
		close(conn);
		
		return result;
	}
	
	
	@Override
	public Toy toyDetailView(int toyNo) throws Exception {
		
		Connection conn = getConnection();
		
		Toy toy = dao.toyDetailView(conn,toyNo);
				
		close(conn);
		
		return toy;
	}


	@Override
	public int toyComplete(int toyNo) throws Exception {
		
		Connection conn = getConnection();
		
		int result = dao.toyComplete(conn,toyNo);
		
		if(result > 0) commit(conn);
		else		   rollback(conn);
		
		close(conn);
				
		return result;
	}


	@Override
	public int toyEdit(int toyNo, String title, String detail) throws Exception {

		Connection conn = getConnection();
		
		int result = dao.toyEdit(conn, toyNo, title, detail);
		
		if(result > 0) commit(conn);
		else		   rollback(conn);
		
		close(conn);
		
		return result;
	}


	@Override
	public int toyDelete(int toyNo) throws Exception {
		
		Connection conn = getConnection();
		
		int result = dao.toyDelete(conn,toyNo);
		
		if(result > 0) commit(conn);
		else		   rollback(conn);
		
		close(conn);
				
		return result;
	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
