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

area.addEventListener("change", async function() {
	await areaCode1(area.value);
});

document.querySelector("#contentType").addEventListener("click", async () => {
	const queryObj = {
		action: "attractions",
	};
	// 추가로 설정할 조건이 있다면 추가하기
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
		const json = await getFetch(
			"http://localhost:8080/enjoytrip_BE_01/attraction",
			queryObj
		);

		const spots = json;
		spots.forEach((element) => {
			// 기본적으로 통계청의 SGIS map은 utmk 기반이므로 WG384(lat, lng)기반을 utmk 로 변경
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

console.log(searchButton);
searchButton.addEventListener("click", async () => {
	const queryObj = {
		action: "search",
	};
	// 추가로 설정할 조건이 있다면 추가하기
	queryObj.keyword = searchText.value;
	console.log(searchText.value);
	if(!searchText.value) return;

	try {
		const json = await getFetch(
			"http://localhost:8080/enjoytrip_BE_01/attraction",
			queryObj
		);
		
		const spots = json;
		console.log(spots);

		spots.forEach((element) => {
			// 기본적으로 통계청의 SGIS map은 utmk 기반이므로 WG384(lat, lng)기반을 utmk 로 변경
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
})
