<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="signupModal" class="modal fade" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
        
            <div class="modal-header">
                <h5 class="modal-title">회원가입</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            
            <div class="modal-body">
                <form id="signup-form" action="${root}/user/signup" method="post">
                    <div class="mb-3">
                        <label for="signup-username" class="form-label">이름</label>
                        <input type="text" class="form-control" id="signup-username" name="username"
                            placeholder="이름을 입력하세요" required>
                    </div>

                    <div class="mb-3">
                        <label for="signup-email" class="form-label">이메일</label>
                        <input type="email" class="form-control" id="signup-email" name="userEmail"
                            placeholder="이메일을 입력하세요" required>
                        <div id="id-feedback" class="form-text" style="padding: 0;"></div>
                    </div>

                    <div class="mb-3">
                        <label for="signup-password" class="form-label">비밀번호</label>
                        <input type="password" class="form-control" id="signup-password" name="userPassword"
                            placeholder="비밀번호를 입력하세요 (6자 이상)" required>
                    </div>

                    <div class="mb-3">
                        <label for="signup-password-confirm" class="form-label">비밀번호 확인</label>
                        <input type="password" class="form-control" id="signup-password-confirm" name="passwordConfirm"
                            placeholder="비밀번호를 다시 입력하세요" required>
                        <div id="confirm-feedback" class="form-text" style="padding: 0;"></div>
                    </div>
                </form>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
                <button type="submit" form="signup-form" class="btn btn-primary" id="signup-button">가입하기</button>
            </div>

        </div>
    </div>
</div>
