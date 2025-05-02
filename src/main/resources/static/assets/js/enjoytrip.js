const area = document.querySelector("#areaCode");
const sigungu = document.querySelector("#sigunguCode");
const contentType = document.querySelector("#contentType");
const searchText = document.querySelector("#searchInput");
const searchButton = document.querySelector("#searchButton");

const areaCode1 = async (areaCode) => {
	const queryObj = {
		serviceKey: key_data,
		numOfRows: 120,
		pageNo: 1,
		MobileOS: "ETC",
		MobileApp: "Test",
		_type: "json",
	};

	if (areaCode) {
		queryObj.areaCode = areaCode;
	}

	try {
		const json = await getFetch(
			"https://apis.data.go.kr/B551011/KorService1/areaCode1",
			queryObj
		);
		let info = json.response.body.items.item;

		info.forEach((item) => {
			item.key = item.code;
			item.label = item.name;
		});

		if (areaCode) {
			updateSelect(sigungu, "시군구", info);
		} else {
			updateSelect(area, "지역", info);
		}
	} catch (e) {
		console.log(e);
	}
};

area.addEventListener("change", async function () {
	await areaCode1(area.value);
});

// 관광지 조회
document.querySelector("#contentType").addEventListener("change", async () => {
	const queryObj = {};
	if (area.value) {
		queryObj.areaCode = area.value;
	}
	if (sigungu.value) {
		queryObj.sigunguCode = sigungu.value;
	}
	if (contentType.value) {
		queryObj.contentTypeId = contentType.value;
	}

	try {
		const queryString = new URLSearchParams(queryObj).toString();
		console.log("📡 요청 URL:", `http://localhost:8080/attraction?${queryString}`);

		const json = await getFetch("http://localhost:8080/attraction", queryObj);

		console.log("📥 응답 데이터:", json);

		const spots = json;
		spots.forEach((element) => {
			element.utmk = new sop.LatLng(element.latitude, element.longitude);
			element.address = element.addr1;
			element.label = element.title;
			element.img = element.firstimage;
			element.phone = element.tel;
			element.ctype = element.contentTypeId;
		});

		updateMap(spots);
	} catch (e) {
		console.error("❌ 에러 발생:", e);
	}

});

// 키워드 검색
searchButton.addEventListener("click", async () => {
	const keyword = searchText.value;
	if (!keyword) return;

	try {
		const json = await getFetch(
			"http://localhost:8080/attraction/search",
			{ keyword }
		);

		const spots = json;
		spots.forEach((element) => {
			element.utmk = new sop.LatLng(element.latitude, element.longitude);
			element.address = element.addr1;
			element.label = element.title;
			element.img = element.firstimage;
			element.phone = element.tel;
			element.ctype = element.contentTypeId;
		});
		updateMap(spots);
	} catch (e) {
		console.log(e);
	}
});
