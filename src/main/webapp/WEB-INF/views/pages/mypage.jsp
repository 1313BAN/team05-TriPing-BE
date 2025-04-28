<%-- <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>마이페이지</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        /* 숨겨진 입력 필드 스타일 */
        .editable-input {
            display: none;
        }
    </style>
</head>
<body>
    <jsp:include page="../common/navbar.jsp" />
    <div class="container mt-5">
        <h2 class="mb-4">마이페이지</h2>
        <form id="userForm" action="${root}/user/update" method="post">
    	<input type="hidden" name="userId" value="${sessionScope.user.userId}" />
            <div class="card">
                <div class="card-body">
                    <p class="card-text"><strong>이메일:</strong> <span id="emailDisplay">${user.userEmail}</span>
                        <input name="useremail" type="email" id="emailInput" class="form-control editable-input" value="${user.userEmail}" />
                    </p>
                    <p class="card-text"><strong>비밀번호:</strong> <span id="passwordDisplay">******</span>
                        <input name="userpassword" type="password" id="passwordInput" class="form-control editable-input" value="" />
                    </p>
                    <button type="button" class="btn btn-primary" id="editButton" onclick="toggleEditMode()">수정하기</button>
                    <button type="submit" class="btn btn-success editable-input" id="saveButton">저장</button>
                </div>
            </div>
        </form>
    </div>

    <script>
        // 수정하기 버튼을 클릭하면 이메일과 비밀번호 입력 필드를 표시
        function toggleEditMode() {
            // 숨겨져 있던 입력 필드 보이기
            document.getElementById("emailInput").style.display = "block";
            document.getElementById("passwordInput").style.display = "block";
            document.getElementById("saveButton").style.display = "inline-block";
            document.getElementById("editButton").style.display = "none";

            document.getElementById("emailInput").value = document.getElementById("emailDisplay").innerText;
            document.getElementById("passwordInput").value = "";
        }
    </script>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
 --%>
 
 <%@ page language="java" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>마이페이지</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        /* 숨겨진 입력 필드 스타일 */
        .editable-input {
            display: none;
        }
    </style>
</head>
<body>
    <jsp:include page="../common/navbar.jsp" />
    <div class="container mt-5">
        <h2 class="mb-4">마이페이지</h2>
        <form id="userForm" action="${root}/user/update" method="post">
            <input type="hidden" name="id" value="${sessionScope.user.id}" />
            
            <div class="card">
                <div class="card-body">
                    <!-- username 수정 추가 -->
                    <p class="card-text"><strong>이름:</strong> 
                        <span id="usernameDisplay">${sessionScope.user.username}</span>
                        <input name="username" type="text" id="usernameInput" class="form-control editable-input" value="${sessionScope.user.username}" />
                    </p>

                    <p class="card-text"><strong>이메일:</strong> 
                        <span id="emailDisplay">${sessionScope.user.userEmail}</span>
                        <input name="userEmail" type="email" id="emailInput" class="form-control editable-input" value="${sessionScope.user.userEmail}" />
                    </p>

                    <p class="card-text"><strong>비밀번호:</strong> 
                        <span id="passwordDisplay">******</span>
                        <input name="userPassword" type="password" id="passwordInput" class="form-control editable-input" value="" />
                    </p>

                    <button type="button" class="btn btn-primary" id="editButton" onclick="toggleEditMode()">수정하기</button>
                    <button type="submit" class="btn btn-success editable-input" id="saveButton">저장</button>
                </div>
            </div>
        </form>
    </div>

    <script>
        // 수정하기 버튼을 클릭하면 이메일, 비밀번호, 이름 입력 필드를 표시
        function toggleEditMode() {
            document.getElementById("usernameInput").style.display = "block";
            document.getElementById("emailInput").style.display = "block";
            document.getElementById("passwordInput").style.display = "block";
            document.getElementById("saveButton").style.display = "inline-block";
            document.getElementById("editButton").style.display = "none";

            document.getElementById("usernameInput").value = document.getElementById("usernameDisplay").innerText;
            document.getElementById("emailInput").value = document.getElementById("emailDisplay").innerText;
            document.getElementById("passwordInput").value = "";
        }
    </script>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
 