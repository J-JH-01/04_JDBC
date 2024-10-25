package edu.kh.toy.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import edu.kh.toy.dto.Toy;
import edu.kh.toyList.service.toyListService;
import edu.kh.toyList.service.toyListServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// "/main" 요청을 매핑하여 처리하는 서블릿
@WebServlet("/main")
public class MainServlet extends HttpServlet{

	/* 왜 "/main" 메인 페이지 요청을 처리하는 서블릿을 만들었는가?
	 * 
	 *  - Servlet(Back-end)에서 추가한 데이터(DB에서 조회한 데이터)를
	 *    메인페이지에서 사용할 수 있게 하려고
	 */
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		try {


			// Service 객체 생성
			// 요청 -> Controller -> Service -> DAO -> DB
			// 응답 <- View       <-         <-     <-
			toyListService service = new toyListServiceImpl();
			
			// 전체 할 일 목록 + 완료된 Todo 개수가 담긴 Map을
			// Service 호출해서 얻어오기
			Map<String, Object> map = service.toyListFullView();
			
			// Map에 저장된 값 풀어내기
			List<Toy> toyList = (List<Toy>)map.get("toyList");
				
			// request scope에 객체 값 추가하기
			req.setAttribute("toyList", toyList);
			
			// 메인 페이지 응답을 담당하는 jsp에 요청 위임
			String path = "/WEB-INF/views/main.jsp";
			req.getRequestDispatcher(path).forward(req, resp);
		
		
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
}
