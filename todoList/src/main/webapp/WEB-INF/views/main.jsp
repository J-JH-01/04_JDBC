<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Todo List</title>

<%-- css 파일 연결 (webapp 폴더 기준으로 경로 작성)--%>
<link rel="stylesheet" href="/resources/css/main.css">
</head>
<body>
	<h1>Todo List</h1>

	<h3>전체 Todo 개수 : ${fn:length(todoList)} /
	 완료된 Todo 개수 : ${completeCount}</h3>

	<hr>

	<h4>할 일 추가</h4>
	<form action="/todo/add" method="post" id="addForm">
		<div>
			제목 : <input type="text" name="title">
		</div>
		
		<div>
			<textarea name="detail" 
			rows="3" cols="50" placeholder="상세 내용"></textarea>
		</div>

		<button>추가</button>
	</form>

	<hr>

	<%-- 할 일 목록 출력 --%>
	<table id="todoList" border="1">
		<thead>
			<tr>
				<th>출력 번호</th><!-- 페이지에서 보이는 용도의 단순 출력 번호 -->
				<th>todo 번호</th><!-- 실제 이 데이터의 todoNo 고유번호 -->
				<th>할 일 제목</th>
				<th>완료 여부</th>
				<th>등록 날짜</th>
			</tr>
		</thead>

		<tbody>
		<c:forEach items="${todoList}" varStatus="vs" var="todo"> <%-- todo = vs.current --%>
			<tr>

				<th>${vs.count}</th>
				<%-- 단순 출력 번호 --%>
				<th>${todo.todoNo}</th>
				<%-- todoNo --%>

				<td>
				<%-- 제목 클릭시
				인덱스 번호를 이용하여 todoList의
				인덱스 번째 요소 내용을 조회하기 --%>
				 <a href="/todo/detail?todoNo=${todo.todoNo}">${todo.title}</a>
				</td>

				<%-- 완료 여부 --%>
				<th>
					<c:if test="${todo.complete}">O</c:if> <%-- todo의 complete가 true라면 0 출력 --%>
					<c:if test="${not todo.complete}">X</c:if> <%-- todo의 complete가 true가 아니라면 X 출력 --%>
				</th>


				<td>
					${todo.regDate}
				</td>
				<%-- 등록일 --%>
			</tr>
			</c:forEach>
		</tbody>
	</table>

	<%-- session 범위에 message가 있을 경우 --%>
	<c:if test="${not empty sessionScope.message}">
		<script>
			alert("${message}");
			// JSP 해석 우선순위
			// 1 순위 : Java(EL/JSTL)
			// 2 순위 : Front(HTML,CSS,JS)
		</script>
		
		<%-- message를 한 번만 출력하고 제거 --%>
		<c:remove var="message" scope="session" />
	</c:if>
	

	<%-- JS 연결 --%>
	<script src="/resources/js/main.js"></script>
</body>
</html>