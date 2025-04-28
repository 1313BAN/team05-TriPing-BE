<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="modal fade" id="loginModal" tabindex="-1" aria-labelledby="loginModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
        
            <div class="modal-header">
                <h5 class="modal-title" id="loginModalLabel">로그인</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            
            <div class="modal-body">
                <form action="${root}/user/login" method="post">
                    <div class="mb-3">
                        <label for="login-email" class="form-label">이메일</label>
                        <input type="email" class="form-control" id="login-email" name="userEmail" 
                               placeholder="이메일을 입력하세요" required/>
                    </div>

                    <div class="mb-3">
                        <label for="login-password" class="form-label">비밀번호</label>
                        <input type="password" class="form-control" id="login-password" name="userPassword" 
                               placeholder="비밀번호를 입력하세요" required/>
                    </div>

                    <button type="submit" class="btn btn-primary w-100">로그인</button>
                </form>
            </div>

        </div>
    </div>
</div>
