package edu.kh.todoList.controller;


import edu.kh.todoList.dto.Todo;
import edu.kh.todoList.service.TodoListService;
import edu.kh.todoList.service.TodoListServiceImpl;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/todo/delete")
public class DeleteServlet extends HttpServlet{

	// 수정 화면 전환하는 GET 요청
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp){

		try {
			
			int todoNo = Integer.parseInt(req.getParameter("todoNo"));
		
			TodoListService service = new TodoListServiceImpl();
			int result = service.todoDelete(todoNo);		
			
			// 4. 성공/실패 메시지 세팅하기
			String message = null;
			if(result > 0) message = "할 일이 삭제 되었습니다!";
			else		 message = "todo가 존재하지 않습니다";
			
			// 5. 기존 req를 사용할 수 없기 때문에
			// session을 이용해서 message를 세팅
			HttpSession session = req.getSession();
			session.setAttribute("message", message);
			
			
			// 6. 메인 페이지로 redirect(재요청)
			resp.sendRedirect("/");
			// ->@WebServlet("/")가 작성된 Servlet을 재요청
			
			// redirect는 무조건 GET 방식 !
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
