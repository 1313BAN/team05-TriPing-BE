<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>ENJOYTRIP!</title>
<link href="../assets/css/global.css" rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<style>
#carouselExampleAutoplaying {
	width: 80%;
	margin: 50px auto 0 auto;
}

.carousel-item img {
	border-radius: 20px;
	will-change: transform, opacity;
}
</style>
<link rel="preload" href="../assets/img/tripImgs/1.jpg" as="image">
<link rel="preload" href="../assets/img/tripImgs/2.jpg" as="image">
<link rel="preload" href="../assets/img/tripImgs/3.jpg" as="image">
<link rel="preload" href="../assets/img/tripImgs/4.jpg" as="image">
<link rel="preload" href="../assets/img/tripImgs/5.jpg" as="image">
<link rel="preload" href="../assets/img/tripImgs/6.jpg" as="image">
<link rel="preload" href="../assets/img/tripImgs/7.jpg" as="image">
</head>
<body>
	<jsp:include page="common/navbar.jsp" />
	<main id="home-main">
		<div id="carouselExampleAutoplaying" class="carousel slide"
			data-bs-ride="carousel">
			<div class="carousel-inner">
				<div class="carousel-item active">
					<img src="${root }/assets/img/tripImgs/1.jpg" class="d-block w-100">
				</div>
				<div class="carousel-item">
					<img src="${root }/assets/img/tripImgs/2.jpg" class="d-block w-100">
				</div>
				<div class="carousel-item">
					<img src="${root }/assets/img/tripImgs/3.jpg" class="d-block w-100">
				</div>
				<div class="carousel-item">
					<img src="${root }/assets/img/tripImgs/4.jpg" class="d-block w-100">
				</div>
				<div class="carousel-item">
					<img src="${root }/assets/img/tripImgs/5.jpg" class="d-block w-100">
				</div>
				<div class="carousel-item">
					<img src="${root }/assets/img/tripImgs/6.jpg" class="d-block w-100">
				</div>
				<div class="carousel-item">
					<img src="${root }/assets/img/tripImgs/7.jpg" class="d-block w-100">
				</div>
			</div>
			<button class="carousel-control-prev" type="button"
				data-bs-target="#carouselExampleAutoplaying" data-bs-slide="prev">
				<span class="carousel-control-prev-icon" aria-hidden="true"></span>
				<span class="visually-hidden">Previous</span>
			</button>
			<button class="carousel-control-next" type="button"
				data-bs-target="#carouselExampleAutoplaying" data-bs-slide="next">
				<span class="carousel-control-next-icon" aria-hidden="true"></span>
				<span class="visually-hidden">Next</span>
			</button>
		</div>

		<!-- 여행 설명 문구 -->
		<div class="container mt-5">
			<div class="row">
				<div class="col text-center">
					<h2 style="font-weight: bold;">새로운 여행의 시작!</h2>
					<p style="margin-top: 20px;">당신의 여행을 특별하게 만들어 줄 다양한 패키지와 정보를
						만나보세요. ENJOYTRIP와 함께라면 여행은 더욱 즐거워집니다.</p>
				</div>
			</div>
		</div>
	</main>
	<script src="${root }/assets/js/TopNav.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
