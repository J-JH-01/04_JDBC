package edu.kh.toyList.DAO;

import java.sql.Connection;
import java.util.List;

import edu.kh.toy.dto.Toy;


public interface toyListDAO {

	/** 할 일 목록 전체 조회
	 * @param conn
	 * @return toyList
	 */
	List<Toy> toyListFullView(Connection conn) throws Exception;

	/** 완료된 할 일 개수 조회
	 * @param conn
	 * @return 완료된 개수
	 */
	int getCompleteCount(Connection conn) throws Exception;

	/** 할 일 추가
	 * @param conn
	 * @param title
	 * @param detail
	 * @return int
	 * @throws Exception 
	 */
	int toyAdd(Connection conn, String title, String detail) throws Exception;

	
	/** 할 일 상세 조회
	 * @param conn
	 * @param toyNo
	 * @return toy 객체 or null
	 * @throws Exception 
	 */
	Toy toyDetailView(Connection conn, int toyNo) throws Exception;

	/** 완료 여부 변경
	 * @param conn
	 * @param toyNo
	 * @return
	 * @throws Exception
	 */
	int toyComplete(Connection conn, int toyNo) throws Exception;

	/** 할 일 수정 
	 * @param conn
	 * @param toyNo
	 * @param title
	 * @param detail
	 * @return
	 */
	int toyEdit(Connection conn, int toyNo, String title, String detail) throws Exception;

	/** 할 일 삭제
	 * @param conn
	 * @param toyNo
	 * @return
	 * @throws Exception
	 */
	int toyDelete(Connection conn, int toyNo) throws Exception;

}
