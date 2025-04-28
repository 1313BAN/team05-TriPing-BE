<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<nav class="navbar navbar-expand-md">
	<div class="container-fluid"
		style="min-height: 70px; margin: 4px; top: 0">
		<a href="${root}/" class="navbar-brand" style="font-weight: bold;">ENJOYTRIP!</a>
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse"
			data-bs-target="#expand" aria-controls="expand" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>

		<div class="collapse navbar-collapse" id="expand">
			<ul class="navbar-nav">
				<li class="nav-item"><a href="${root}/place" class="nav-link">지역별관광지</a></li>

				<!-- 비로그인 상태 -->
				<c:if test="${empty sessionScope.user}">
					<li class="nav-item"><a href="#" class="nav-link"
						id="signup-link" data-bs-toggle="modal"
						data-bs-target="#signupModal">회원가입</a></li>
					<li class="nav-item"><a href="#" class="nav-link"
						id="login-link" data-bs-toggle="modal"
						data-bs-target="#loginModal">로그인</a></li>
				</c:if>

				<!-- 로그인 상태 -->
				<c:if test="${not empty sessionScope.user}">
					<li class="nav-item"><a href="${root}/user/mypage"
						class="nav-link">마이페이지</a></li>
					<li class="nav-item" style="padding: 8px;">
					<a
						href="${root}/user/logout" class="btn btn-link nav-link"
						style="border: none; background: none; padding: 0; color: inherit; text-decoration: none;">
							로그아웃 </a>
					</li>
				</c:if>
			</ul>
		</div>
	</div>
</nav>

<jsp:include page="/WEB-INF/views/common/login-modal.jsp" />
<jsp:include page="/WEB-INF/views/common/signup-modal.jsp" />

