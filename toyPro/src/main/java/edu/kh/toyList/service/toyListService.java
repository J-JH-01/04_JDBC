package edu.kh.toyList.service;

import java.util.Map;

import edu.kh.toy.dto.Toy;

public interface toyListService {

	/** 할 일 목록 반환 서비스
	 * @return toyList + 완료 개수
	 * @throws Exception 
	 */
	Map<String, Object> toyListFullView() throws Exception;

	
	/** 할 일 추가 서비스
	 * @param title
	 * @param detail
	 * @return int 성공 시 추가된 행의 개수 / 실패시 0 반환
	 * @throws Exception 
	 */
	int toyAdd(String title, String detail) throws Exception;


	/** 할 일 상세 조회 서비스
	 * @param toyNo
	 * @return null 또는 toy 객체
	 */
	Toy toyDetailView(int toyNo) throws Exception;


	/** 완료 여부 변경 서비스 
	 * @param toyNo
	 * @return
	 */ 
	int toyComplete(int toyNo) throws Exception;


	/** 할 일 수정 서비스
	 * @param toyNo
	 * @param title
	 * @param detail
	 * @return
	 */
	int toyEdit(int toyNo, String title, String detail) throws Exception;


	/** 할 일 삭제 서비스
	 * @param toyNo
	 * @return
	 * @throws Exception
	 */
	int toyDelete(int toyNo) throws Exception;



	
	
	
	
	
	
	
	
	
}
