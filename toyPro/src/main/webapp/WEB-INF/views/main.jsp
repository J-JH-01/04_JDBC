<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>학생 관리 사이트</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
<link rel="stylesheet" href="/resources/css/style.css">
</head>
<body>
	<div class="container">
		<!-- 메인 페이지 -->
		<div class="card">
			<div class="header">
				<h1>
					<i class="fas fa-graduation-cap"></i>학생 조회
				</h1>
				<a href="#" class="btn btn-primary"> <i class="fas fa-plus"></i>
					NEW STUDENT
				</a>
			</div>



			<div class="student-list">

				<c:forEach items="${toyList}" varStatus="vs" var="toy">
					<div class="student-item"  onclick="location.href='/toy/detail?toyNo=${toy.stdNo}'">
						<a class="student-name">
						<i class="fas fa-user-graduate"></i> ${toy.stdName}
						</a> <span class="text-secondary">${toy.stdNo}번</span>
		
					</div>
				</c:forEach>
				<%-- todo = vs.current --%>



			</div>
		</div>

	</div>
	
	


	<!--  js 연결해주기 -->
	<script></script>
</body>
</html>