/* jshint esversion : 6 */

let accessToken;
const map = sop.map("map");
// marker 목록
const markers = [];
// 경계 목록
const bounds = [];

// access token 가져오기
const getAccessToken = async () => {
  try {
    const json = await getFetch(
      "https://sgisapi.kostat.go.kr/OpenAPI3/auth/authentication.json",
      {
        consumer_key: "46d0d3813d9c4bc8af25", // 서비스 id
        consumer_secret: "bd0c408816314cdaa46f", // 보안 key
      }
    );
    accessToken = json.result.accessToken;
  } catch (e) {
    console.log(e);
  }
};

// 주소를 UTM-K좌표로 변환해서 반환: - json의 errCd ==-401에서 access token 확보!!
const getCoords = async (address) => {
  try {
    const json = await getFetch(
      "https://sgisapi.kostat.go.kr/OpenAPI3/addr/geocode.json",
      {
        accessToken: accessToken,
        address: address,
        resultcount: 1,
      }
    );
    if (json.errCd === -401) {
      // -401 에러 : 엑세스 토큰 만료(4시간 마다 만료) or 없음
      await getAccessToken();
      return await getCoords(address);
    }
    return json.result.resultdata[0];
  } catch (e) {
    console.log(e);
  }
};

const updateMap = (infos) => {
  resetMarker();
  // 지도에 표시된 마커 리셋

  try {
    for (let i = 0; i < infos.length; i++) {
      const info = infos[i];
	  console.log(info);
      const marker = sop.marker([info.utmk.x, info.utmk.y]); 
      if(info.ctype == 12){
        const customIcon = sop.icon({
          iconUrl: "data:image/svg+xml;charset=UTF-8," + encodeURIComponent(`
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512">
              <path d="M0 80c0 26.5 21.5 48 48 48l16 0 0 64 64 0 0-64 96 0 0 64 64 0 0-64 96 0 0 64 64 0 0-64 16 0c26.5 0 48-21.5 48-48l0-66.6C512 6 506 0 498.6 0c-1.7 0-3.4 .3-5 1l-49 19.6C425.7 28.1 405.5 32 385.2 32L126.8 32c-20.4 0-40.5-3.9-59.4-11.4L18.4 1c-1.6-.6-3.3-1-5-1C6 0 0 6 0 13.4L0 80zM64 288l0 192c0 17.7 14.3 32 32 32s32-14.3 32-32l0-192 256 0 0 192c0 17.7 14.3 32 32 32s32-14.3 32-32l0-192 32 0c17.7 0 32-14.3 32-32s-14.3-32-32-32L32 224c-17.7 0-32 14.3-32 32s14.3 32 32 32l32 0z"/>
            </svg>
          `),
          iconSize: [27, 27], // 아이콘 크기 (너비, 높이)
          iconAnchor: [16, 32], // 기준점 (가운데 정렬)
        });
        marker.setIcon(customIcon);
      }
      else if(info.ctype == 39){
        const customIcon = sop.icon({
          iconUrl: "data:image/svg+xml;charset=UTF-8," + encodeURIComponent(`
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 448 512"><!--!Font Awesome Free 6.7.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2025 Fonticons, Inc.--><path d="M416 0C400 0 288 32 288 176l0 112c0 35.3 28.7 64 64 64l32 0 0 128c0 17.7 14.3 32 32 32s32-14.3 32-32l0-128 0-112 0-208c0-17.7-14.3-32-32-32zM64 16C64 7.8 57.9 1 49.7 .1S34.2 4.6 32.4 12.5L2.1 148.8C.7 155.1 0 161.5 0 167.9c0 45.9 35.1 83.6 80 87.7L80 480c0 17.7 14.3 32 32 32s32-14.3 32-32l0-224.4c44.9-4.1 80-41.8 80-87.7c0-6.4-.7-12.8-2.1-19.1L191.6 12.5c-1.8-8-9.3-13.3-17.4-12.4S160 7.8 160 16l0 134.2c0 5.4-4.4 9.8-9.8 9.8c-5.1 0-9.3-3.9-9.8-9L127.9 14.6C127.2 6.3 120.3 0 112 0s-15.2 6.3-15.9 14.6L83.7 151c-.5 5.1-4.7 9-9.8 9c-5.4 0-9.8-4.4-9.8-9.8L64 16zm48.3 152l-.3 0-.3 0 .3-.7 .3 .7z"/></svg>
          `),
          iconSize: [27, 27], // 아이콘 크기 (너비, 높이)
          iconAnchor: [16, 32], // 기준점 (가운데 정렬)
        });
        marker.setIcon(customIcon);
      }
      
      marker.addTo(map).bindInfoWindow(`
        <div style="text-align: center;">
          <p><strong>${info.label}</strong></p>
          <img src="${info.img ? info.img : './assets/img/noimg.png'}" alt="이미지 없음" style="width: 100px; height: auto;">
          <p style="color: ${info.phone ? 'black' : 'gray'};">
  ${info.phone || "전화번호 없음"}
		</p>

        </div>
		
      `);
      
      markers.push(marker);
      bounds.push([info.utmk.x, info.utmk.y]);
    }
    // 경계를 기준으로 map을 중앙에 위치하도록 함
    if (bounds.length > 1) {
      map.setView(
        map._getBoundsCenterZoom(bounds).center,
        map._getBoundsCenterZoom(bounds).zoom
      );
    } else {
      map.setView(map._getBoundsCenterZoom(bounds).center, 9);
    }
  } catch (e) {
    console.log(e);
  }
};

// 마커와 경계 초기화
const resetMarker = () => {
  markers.forEach((item) => item.remove());
  bounds.length = 0;
  //bounds 배열 초기화
};
