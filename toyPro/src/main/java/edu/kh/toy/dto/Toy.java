package edu.kh.toy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Toy {
	
	private int stdNo;			// 학생 번호
	private String stdName;		// 학생 이름
	private int stdAge;		// 학생 나이
	private String stdGen;		// 학생 성별
	private String stdScore;	// 학생 성적
	
	
	
	
}
