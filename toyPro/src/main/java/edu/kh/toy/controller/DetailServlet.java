package edu.kh.toy.controller;

import java.io.IOException;

import edu.kh.toy.dto.Toy;
import edu.kh.toyList.service.toyListService;
import edu.kh.toyList.service.toyListServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/toy/detail")
public class DetailServlet extends HttpServlet{

	//a태그 요청은 GET 방식
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	try {
		
		// 요청시 전달 받은 파라미터 얻어오기
		
		int toyNo = Integer.parseInt( req.getParameter("toyNo") );
		
		// 서비스 객체 생성
		toyListService service = new toyListServiceImpl();
		

		
		Toy toy = service.toyDetailView(toyNo);
		// toy_NO 컬럼값이 toyNo와 같은 toy가 없으면 null 반환 / 있으면 toy객체 반환
		String info;
		
		if(toy.getStdGen().equals("M")) info = "남자";
		else info = "여자";
		
		System.out.println(info);
		// toy가 존재하지 않을 경우
		// -> 메인페이지(/) redirect 후
		// "할 일이 존재하지 않습니다" -> 살아있는 섹션 객체에 메세지 세팅
		// alert 출력

		

		if(toy == null ) {
			
			// session 객체 생성 후 message 세팅하기
			HttpSession session = req.getSession();
			session.setAttribute("message", "할 일이 존재하지 않습니다");
			
			resp.sendRedirect("/");
			return;
		}
		
		// toy가 존재하는 경우
		// detail.jsp로 forward해서 응답
		req.setAttribute("toy", toy);
		req.setAttribute("info", info);
		
		// JSP 파일 경로
		String path = "/WEB-INF/views/detail.jsp";
				
		// 요청 발송자를 이용해서 요청 위임
		req.getRequestDispatcher(path).forward(req, resp);		
		
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	
	
	
	
	}
}
