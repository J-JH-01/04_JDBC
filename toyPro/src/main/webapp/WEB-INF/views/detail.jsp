
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>학생 상세 페이지</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" href="/resources/css/style.css">
</head>
<body>
   
        <!-- 상세 정보 페이지 -->
        <div class="card">
            <div class="header">
                <h1><i class="fas fa-user"></i> STUDENT DETAILS</h1>
            </div>
            
            <c:forEach items="${toyList}" varStatus="vs" var="toy">
            <div class="detail-info">
                <p>
                    <span><i class="fas fa-id-card"></i> 이름</span>
                    <strong>${toy.stdName}</strong>
                </p>
                <p>
                    <span><i class="fas fa-birthday-cake"></i> 나이</span>
                    <strong>${toy.stdAge}</strong>
                </p>
                <p>
                    <span><i class="fas fa-venus-mars"></i> 성별</span>
                    <strong>
	                 	${info}
					</strong>
                </p>
                <p>
                    <span><i class="fas fa-star"></i> 성적</span>
                    <strong>${toy.stdScore}</strong>
                </p>
            </div>
            
            </c:forEach>
            
            <div class="btn-group">
                <a href="/main" class="btn btn-primary">
                    <i class="fas fa-arrow-left"></i> 돌아가기
                </a>
                <a href="/toy/edit" class="btn btn-success">
                    <i class="fas fa-edit"></i> 수정
                </a>
                <button class="btn btn-danger">
                    <i class="fas fa-trash"></i> 삭제
                </button>
            </div>
        </div>

      
    <!--  js 연결해주기 -->
    <script></script>
</body>
</html>