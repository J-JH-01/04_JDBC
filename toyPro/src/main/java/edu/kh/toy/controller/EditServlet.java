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
@WebServlet("/toy/edit")
public class EditServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {

			// Service 객체 생성
			// 요청 -> Controller -> Service -> DAO -> DB
			// 응답 <- View <- <- <-
			toyListService service = new toyListServiceImpl();

			// 전체 할 일 목록 + 완료된 toy 개수가 담긴 Map을
			// Service 호출해서 얻어오기
			Map<String, Object> map = service.toyListFullView();

			// Map에 저장된 값 풀어내기
			List<Toy> toyList = (List<Toy>) map.get("toyList");

			// request scope에 객체 값 추가하기
			req.setAttribute("toyList", toyList);

			// 메인 페이지 응답을 담당하는 jsp에 요청 위임
			String path = "/WEB-INF/views/edit.jsp";
			req.getRequestDispatcher(path).forward(req, resp);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {

			// 전달 받은 파라미터 얻어오기 (제목,상세내용,toy번호)
			String title = req.getParameter("title"); // 제목
			String detail = req.getParameter("detail"); // 내용
			int toyNo = Integer.parseInt(req.getParameter("toyNo")); // 번호

			// 3개 담기 싫다하면 toy 객체만들어서 담아 보내도 댄다

			toyListService service = new toyListServiceImpl();
			int result = service.toyEdit(toyNo, title, detail);

			// 수정 성공 시
			// 상세 조회 페이지로 redirect
			// "수정 되었습니다" message 출력

			// 수정 실패 시
			// 수정 화면으로 redirect 후
			// "수정 실패" message 출력
			String url = null;
			String message = null;

			if (result > 0) {// 성공
				url = "/toy/detail?toyNo=" + toyNo;
				message = "수정 되었습니다";

			} else { // 실패
				url = "/toy/update?toyNo=" + toyNo;
				message = "수정 실패";
			}

			// session 객체에 속성 추가

			req.getSession().setAttribute("message", message);

			// redirect (GET 방식 요청)
			resp.sendRedirect(url);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
