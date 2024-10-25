<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>학생 정보 수정</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" href="/resources/css/style.css">
</head>
<body>
    <!-- 학생 정보 수정 -->
    <div class="container">
        <div class="card">
            <div class="header">
                <h1><i class="fas fa-user-edit"></i> 학생 정보 수정</h1>
            </div>
            
            <form action="updateStudent.jsp" method="post" onsubmit="return validateForm()">
                <input type="hidden" name="id" value="<%=request.getParameter("id")%>">
                
                <div class="form-group">
                    <label><i class="fas fa-user"></i> 이름${toy.stdNo}</label>
                    <input type="text" name="name" class="form-control" value="${toy.stdName}" required>
                </div>
                
                <div class="form-group">
                    <label><i class="fas fa-birthday-cake"></i> 나이</label>
                      <input type="number" class="form-control" min="0" max="100" required>
                </div>
                
                 <div class="form-group">
                    <label><i class="fas fa-venus-mars"></i> 성별</label>
                    <div class="radio-group">
                        <label>
                            <input type="radio" name="gender" required>
                            <i class="fas fa-male"></i> 남자
                        </label>
                        <label>
                            <input type="radio" name="gender" required>
                            <i class="fas fa-female"></i> 여자
                        </label>
                    </div>
                </div>

                <div class="form-group">
                    <label><i class="fas fa-star"></i> 성적</label>
                    <input type="number" name="score" class="form-control"  min="0" max="100" required>
                </div>
                
                <div class="btn-group">
                    <button type="submit" class="btn btn-success">
                        <i class="fas fa-save"></i> 저장
                    </button>
                    <a href="detail.jsp?id=<%=request.getParameter("id")%>" class="btn btn-danger">
                        <i class="fas fa-times"></i> 취소
                    </a>
                </div>
            </form>
        </div>
    </div>
    
 
</body>
</html>