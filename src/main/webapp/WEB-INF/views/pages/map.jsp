<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>ENJOYTRIP!</title>
<link href="${root }/assets/css/global.css" rel="stylesheet" />
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet" />
<style>
div {
	/* border: 1px solid black; */
	margin: 5px 0;
	padding: 5px;
}

#map {
	position: relative;
}
</style>
<script type="text/javascript"
	src="https://sgisapi.kostat.go.kr/OpenAPI3/auth/javascriptAuth?consumer_key=b7ed068cff1940d2bb98"></script>
</head>
<body>
	<jsp:include page="../common/navbar.jsp" />
	<main>
		<div class="container-xl text-center" style="padding-top: 0px">
			<div class="row" style="border-bottom: 2px solid rgb(109, 109, 109)">
				<div class="col">
					<p class="fs-1 fw-bold" style="color: rgb(104, 104, 104)">지역별
						관광정보</p>
				</div>
			</div>
			<div class="row">
				<div class="col-md-4">
					<select id="areaCode" class="form-select"
						aria-label="Default select example">
						<option value="" selected disabled>시도 선택</option>
					</select>
				</div>
				<div class="col-md-4">
					<select id="sigunguCode" class="form-select"
						aria-label="Default select example">
						<option value="" selected disabled>시군구 선택</option>
					</select>
				</div>
				<div class="col-md-4">
					<select id="contentType" class="form-select"
						aria-label="Default select example">
						<option value="" selected disabled>관광타입 선택</option>
						<option value="12">관광지</option>
						<option value="14">문화시설</option>
						<option value="15">축제공연행사</option>
						<option value="25">여행코스</option>
						<option value="28">레포츠</option>
						<option value="32">숙박</option>
						<option value="38">쇼핑</option>
						<option value="39">음식점</option>
					</select>
					<!-- <button id="btn_trip_search">관광지 조회</button> -->
				</div>
			</div>
			<div class="row">
				<div class="col-md-3">
					<input class="form-control editable-input" id = "searchInput">
					<button type="button" class="btn btn-primary" id="searchButton">검색하기</button>
				</div>
			</div>

			<div class="row">
				<div class="col">
					<div id="map" style="width: 100%; height: 800px"></div>
				</div>
			</div>
		</div>
	</main>
	<script>
		ROOT='${root}';
	</script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
	<script src="${root }/assets/js/keys.js"></script>
	<script src="${root }/assets/js/common.js"></script>
	<script src="${root }/assets/js/kostat.js"></script>
	<script src="${root }/assets/js/enjoytrip.js"></script>
	<script src="${root }/assets/js/TopNav.js"></script>
	<script>
      const init = async () => {
        areaCode1(); // enjoytrip.js
        updateMap([
          {
            address: "서울특별시 강남구 테헤란로 212",
            utmk: await getCoords("서울특별시 강남구 테헤란로 212"),
            label: "멀티캠퍼스",
          },
        ]);
        //{ address: address, utmk: getCoords(address), label: aptNm }
      };
      init();
    </script>
</body>
</html>
