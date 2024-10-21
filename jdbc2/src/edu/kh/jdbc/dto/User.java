package edu.kh.jdbc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// DTO (Data Transfer Object) : 값을 묶어서 전달하는 용도의 객체
// -> DB에 데이터를 전달하거나, 가져올 때 사용
// == DB 특징 테이블에 한 행의 데이터를 저장할 수 있는 형태로 class 작성
@Getter	
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
	private int userNO;
	private String userid;
	private String userPw;
	private String userName;
	private String enrollDate;
	
	// --> enrolldate를 왜 java.sql.Date 타입이 아니라 String으로 썻나
	// --> DB 조회 시 날짜 데이터를 원하는 형태의 무나 문자열ㄹ
	//	--> 변환하여 조회할 예정 -> TO_CHAR() 이용
}
