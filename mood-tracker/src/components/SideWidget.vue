<template>
  <div class="flex flex-col mt-16">
    <div
      class="flex flex-col w-48 mx-2 mt-2 p-4 bg-gradient-to-b from-[#E9F6FF] rounded-xl text-center shadow-lg"
    >
      <div></div>
      <span class="font-bold text-lg">{{ location }}</span>

      <div class="flex justify-center items-center">
        <!--맑음-->
        <div v-if="skystatus === 1">
          <svg
            v-if="isDayTime"
            fill="#525252"
            width="100px"
            height="100px"
            viewBox="-6.4 -6.4 76.80 76.80"
            version="1.1"
            xml:space="preserve"
            xmlns="http://www.w3.org/2000/svg"
            xmlns:xlink="http://www.w3.org/1999/xlink"
            stroke="#525252"
            class="animate-pulse"
          >
            <g
              id="SVGRepo_bgCarrier"
              stroke-width="0"
              transform="translate(16.32,16.32), scale(0.49)"
            >
              <rect
                x="-6.4"
                y="-6.4"
                width="76.80"
                height="76.80"
                rx="38.4"
                fill="#fff480"
                strokewidth="0"
              ></rect>
            </g>
            <g
              id="SVGRepo_tracerCarrier"
              stroke-linecap="round"
              stroke-linejoin="round"
            ></g>
            <g id="SVGRepo_iconCarrier">
              <g id="moon">
                <g>
                  <path
                    d="M32,11c-11.6,0-21,9.4-21,21s9.4,21,21,21s21-9.4,21-21S43.6,11,32,11z M32,51c-10.5,0-19-8.5-19-19s8.5-19,19-19 s19,8.5,19,19S42.5,51,32,51z"
                  ></path>
                  <path
                    d="M58.1,27.8c-0.9-0.7-1.9-1.6-2.1-2.3c-0.2-0.7,0.2-1.9,0.7-3.1c0.6-1.7,1.3-3.5,0.4-4.9c-0.8-1.5-2.7-1.8-4.5-2.1 c-1.2-0.2-2.5-0.4-3-1c-0.5-0.5-0.8-1.8-1-3c-0.3-1.8-0.7-3.7-2.1-4.5c-1.4-0.8-3.2-0.2-4.9,0.4c-1.2,0.4-2.4,0.9-3.1,0.7 c-0.7-0.2-1.5-1.2-2.3-2.1C35,4.5,33.7,3,32,3s-3,1.5-4.2,2.9c-0.7,0.9-1.6,1.9-2.3,2.1c-0.7,0.2-1.9-0.2-3.1-0.7 c-1.7-0.6-3.5-1.3-4.9-0.4c-1.5,0.8-1.8,2.7-2.1,4.5c-0.2,1.2-0.4,2.5-1,3c-0.5,0.5-1.8,0.7-3,1c-1.8,0.3-3.7,0.7-4.5,2.1 c-0.8,1.4-0.2,3.2,0.4,4.9c0.4,1.2,0.9,2.4,0.7,3.1c-0.2,0.7-1.2,1.5-2.1,2.3C4.5,29,3,30.3,3,32s1.5,3,2.9,4.2 c0.9,0.7,1.9,1.6,2.1,2.3c0.2,0.7-0.2,1.9-0.7,3.1c-0.6,1.7-1.3,3.5-0.4,4.9c0.8,1.5,2.7,1.8,4.5,2.1c1.2,0.2,2.5,0.4,3,1 c0.5,0.5,0.8,1.8,1,3c0.3,1.8,0.7,3.7,2.1,4.5c1.4,0.8,3.2,0.2,4.9-0.4c1.2-0.4,2.4-0.9,3.1-0.7c0.7,0.2,1.5,1.2,2.3,2.1 C29,59.5,30.3,61,32,61s3-1.5,4.2-2.9c0.7-0.9,1.6-1.9,2.3-2.1c0.7-0.2,1.9,0.2,3.1,0.7c1.7,0.6,3.5,1.3,4.9,0.4 c1.5-0.8,1.8-2.7,2.1-4.5c0.2-1.2,0.4-2.5,1-3c0.5-0.5,1.8-0.7,3-1c1.8-0.3,3.7-0.7,4.5-2.1c0.8-1.4,0.2-3.2-0.4-4.9 c-0.4-1.2-0.9-2.4-0.7-3.1c0.2-0.7,1.2-1.5,2.1-2.3C59.5,35,61,33.7,61,32S59.5,29,58.1,27.8z M56.8,34.6c-1.2,1-2.4,2-2.7,3.3 c-0.4,1.3,0.2,2.9,0.7,4.3c0.4,1.2,1,2.6,0.6,3.3c-0.4,0.7-1.9,0.9-3.2,1.1c-1.5,0.3-3.1,0.5-4.1,1.5c-1,1-1.2,2.5-1.5,4.1 c-0.2,1.3-0.5,2.8-1.1,3.2c-0.6,0.4-2-0.1-3.3-0.6c-1.5-0.5-3-1.1-4.3-0.7c-1.3,0.3-2.3,1.6-3.3,2.7c-0.9,1-1.8,2.2-2.6,2.2 s-1.8-1.2-2.6-2.2c-1-1.2-2-2.4-3.3-2.7C25.8,54,25.5,54,25.2,54c-1.1,0-2.3,0.4-3.4,0.8c-1.2,0.4-2.6,1-3.3,0.6 c-0.7-0.4-0.9-1.9-1.1-3.2c-0.3-1.5-0.5-3.1-1.5-4.1s-2.5-1.2-4.1-1.5c-1.3-0.2-2.8-0.5-3.2-1.1c-0.4-0.6,0.1-2,0.6-3.3 c0.5-1.5,1.1-3,0.7-4.3c-0.3-1.3-1.6-2.3-2.7-3.3C6.2,33.8,5,32.8,5,32s1.2-1.8,2.2-2.6c1.2-1,2.4-2,2.7-3.3 c0.4-1.3-0.2-2.9-0.7-4.3c-0.4-1.2-1-2.6-0.6-3.3c0.4-0.7,1.9-0.9,3.2-1.1c1.5-0.3,3.1-0.5,4.1-1.5c1-1,1.2-2.5,1.5-4.1 c0.2-1.3,0.5-2.8,1.1-3.2c0.6-0.4,2,0.1,3.3,0.6c1.5,0.5,3,1.1,4.3,0.7c1.3-0.3,2.3-1.6,3.3-2.7C30.2,6.2,31.2,5,32,5 s1.8,1.2,2.6,2.2c1,1.2,2,2.4,3.3,2.7c1.3,0.4,2.9-0.2,4.3-0.7c1.2-0.4,2.6-1,3.3-0.6c0.7,0.4,0.9,1.9,1.1,3.2 c0.3,1.5,0.5,3.1,1.5,4.1s2.5,1.2,4.1,1.5c1.3,0.2,2.8,0.5,3.2,1.1c0.4,0.6-0.1,2-0.6,3.3c-0.5,1.5-1.1,3-0.7,4.3 c0.3,1.3,1.6,2.3,2.7,3.3c1,0.9,2.2,1.8,2.2,2.6S57.8,33.8,56.8,34.6z"
                  ></path>
                </g>
              </g>
              <g id="sun"></g>
            </g>
          </svg>
          <svg
            v-else
            fill="#525252"
            width="100px"
            height="100px"
            viewBox="0 0 64 64"
            version="1.1"
            xml:space="preserve"
            xmlns="http://www.w3.org/2000/svg"
            xmlns:xlink="http://www.w3.org/1999/xlink"
            stroke="#525252"
            class="animate-pulse"
          >
            <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
            <g
              id="SVGRepo_tracerCarrier"
              stroke-linecap="round"
              stroke-linejoin="round"
            ></g>
            <g id="SVGRepo_iconCarrier">
              <g id="sun">
                <g>
                  <path
                    d="M33.9,61C18,61,5,48,5,32S18,3,33.9,3c10.2,0,19.8,5.5,25,14.4c0.3,0.4,0.1,1-0.3,1.3c-0.4,0.3-1,0.3-1.3-0.1 C53.7,15,49,13,43.9,13c-10.4,0-18.9,8.5-18.9,19s8.5,19,18.9,19c5.1,0,9.9-2,13.4-5.6c0.4-0.4,0.9-0.4,1.3-0.1 c0.4,0.3,0.5,0.9,0.3,1.3C53.7,55.5,44.1,61,33.9,61z M33.9,5C19.1,5,7,17.1,7,32s12.1,27,26.9,27c7.3,0,14.3-3,19.3-8.2 c-2.8,1.4-6,2.2-9.3,2.2c-11.5,0-20.9-9.4-20.9-21s9.4-21,20.9-21c3.3,0,6.4,0.8,9.3,2.2C48.2,8,41.2,5,33.9,5z"
                  ></path>
                </g>
              </g>
            </g>
          </svg>
        </div>

        <!--구름 많음-->
        <div v-else-if="skystatus === 3">
          <svg
            v-if="isDayTime"
            fill="#525252"
            width="100px"
            height="100px"
            viewBox="-6.4 -6.4 76.80 76.80"
            version="1.1"
            xml:space="preserve"
            xmlns="http://www.w3.org/2000/svg"
            xmlns:xlink="http://www.w3.org/1999/xlink"
            stroke="#525252"
            class="animate-pulse"
          >
            <g
              id="SVGRepo_bgCarrier"
              stroke-width="0"
              transform="translate(23.04,23.04), scale(0.28)"
            ></g>
            <g
              id="SVGRepo_tracerCarrier"
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke="#57cdff"
              stroke-width="0.8960000000000001"
            ></g>
            <g id="SVGRepo_iconCarrier">
              <g id="cloudy_sunny">
                <g>
                  <path
                    d="M38,12c-9.6,0-17.5,7.6-18,17.1c-2.2,1.5-3.9,3.6-4.8,6.2c-0.5-0.1-0.9-0.1-1.4-0.1C7.9,35.2,3,40.1,3,46.1 S7.9,57,13.9,57h27.9c5.1,0,9.2-4.1,9.2-9.2c0-1.6-0.4-3-1.1-4.3C53.8,40.1,56,35.2,56,30C56,20.1,47.9,12,38,12z M41.8,55H13.9 C9,55,5,51,5,46.1s4-8.9,8.9-8.9c0.6,0,1.2,0.1,1.9,0.2c0.5,0.1,1-0.2,1.2-0.7C18.1,32.2,22.3,29,27,29c5.8,0,10.5,4.7,10.5,10.5 c0,0.6,0.4,1,1,1h3.3c4,0,7.2,3.2,7.2,7.2S45.8,55,41.8,55z M48.8,41.8c-1.7-2-4.2-3.2-7-3.2h-2.3C39,32.1,33.6,27,27,27 c-1.7,0-3.4,0.4-4.9,1c1-7.9,7.8-14,15.9-14c8.8,0,16,7.2,16,16C54,34.5,52.1,38.8,48.8,41.8z"
                  ></path>
                  <path
                    d="M38,10c0.6,0,1-0.4,1-1V8c0-0.6-0.4-1-1-1s-1,0.4-1,1v1C37,9.6,37.4,10,38,10z"
                  ></path>
                  <path
                    d="M60,29h-1c-0.6,0-1,0.4-1,1s0.4,1,1,1h1c0.6,0,1-0.4,1-1S60.6,29,60,29z"
                  ></path>
                  <path
                    d="M16,31h1c0.6,0,1-0.4,1-1s-0.4-1-1-1h-1c-0.6,0-1,0.4-1,1S15.4,31,16,31z"
                  ></path>
                  <path
                    d="M53,16c0.3,0,0.5-0.1,0.7-0.3l0.6-0.6c0.4-0.4,0.4-1,0-1.4s-1-0.4-1.4,0l-0.6,0.6c-0.4,0.4-0.4,1,0,1.4 C52.5,15.9,52.7,16,53,16z"
                  ></path>
                  <path
                    d="M53.7,44.3c-0.4-0.4-1-0.4-1.4,0s-0.4,1,0,1.4l0.6,0.6c0.2,0.2,0.5,0.3,0.7,0.3s0.5-0.1,0.7-0.3c0.4-0.4,0.4-1,0-1.4 L53.7,44.3z"
                  ></path>
                  <path
                    d="M22.3,15.7c0.2,0.2,0.5,0.3,0.7,0.3s0.5-0.1,0.7-0.3c0.4-0.4,0.4-1,0-1.4l-0.6-0.6c-0.4-0.4-1-0.4-1.4,0s-0.4,1,0,1.4 L22.3,15.7z"
                  ></path>
                </g>
              </g>
            </g>
          </svg>
          <svg
            v-else
            fill="#525252"
            width="100px"
            height="100px"
            viewBox="0 0 64 64"
            version="1.1"
            xml:space="preserve"
            xmlns="http://www.w3.org/2000/svg"
            xmlns:xlink="http://www.w3.org/1999/xlink"
            stroke="#525252"
            class="animate-pulse"
          >
            <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
            <g
              id="SVGRepo_tracerCarrier"
              stroke-linecap="round"
              stroke-linejoin="round"
            ></g>
            <g id="SVGRepo_iconCarrier">
              <g id="cloudy_night">
                <path
                  d="M61,29c0-1.6-0.2-3.2-0.6-4.8c-0.1-0.4-0.5-0.7-0.9-0.8c-0.5,0-0.8,0.3-1,0.7C57.1,28.2,53.3,31,49,31c-5.5,0-10-4.5-10-10 c0-4.3,2.8-8.1,6.9-9.5c0.4-0.1,0.7-0.5,0.7-1c0-0.4-0.3-0.8-0.8-0.9C44.2,9.2,42.6,9,41,9c-10.1,0-18.6,7.5-19.8,17.5 c-2.7,1.4-4.9,3.9-6,6.9c-0.5-0.1-0.9-0.1-1.4-0.1C7.9,33.2,3,38.1,3,44.1S7.9,55,13.9,55h27.9c4.9,0,8.9-3.8,9.2-8.7 C57.2,42.8,61,36.2,61,29z M41.8,53H13.9C9,53,5,49,5,44.1s4-8.9,8.9-8.9c0.6,0,1.2,0.1,1.9,0.2c0.5,0.1,1-0.2,1.2-0.7 C18.1,30.2,22.3,27,27,27c5.8,0,10.5,4.7,10.5,10.5c0,0.6,0.4,1,1,1h3.3c4,0,7.2,3.2,7.2,7.2S45.8,53,41.8,53z M50.8,44.1 c-0.8-4.3-4.6-7.5-9.1-7.5h-2.3C39,30.1,33.6,25,27,25c-1.3,0-2.5,0.2-3.7,0.6C24.9,17.2,32.3,11,41,11c0.4,0,0.9,0,1.3,0 C39,13.2,37,16.9,37,21c0,6.6,5.4,12,12,12c4.1,0,7.8-2,10-5.3c0,0.4,0,0.9,0,1.3C59,35.1,55.9,40.8,50.8,44.1z"
                ></path>
              </g>
            </g>
          </svg>
        </div>
        <!--흐림-->
        <div v-else-if="skystatus === 4">
          <svg
            fill="#525252"
            width="100px"
            height="100px"
            viewBox="0 0 64 64"
            version="1.1"
            xml:space="preserve"
            xmlns="http://www.w3.org/2000/svg"
            xmlns:xlink="http://www.w3.org/1999/xlink"
            stroke="#525252"
            class="animate-pulse"
          >
            <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
            <g
              id="SVGRepo_tracerCarrier"
              stroke-linecap="round"
              stroke-linejoin="round"
            ></g>
            <g id="SVGRepo_iconCarrier">
              <g id="cloudy">
                <g>
                  <path
                    d="M35.2,37.5h-1.8c-0.5-5.3-5-9.5-10.5-9.5c-4.4,0-8.3,2.8-9.8,6.8c-0.3,0-0.7-0.1-1-0.1c-5,0-9.1,4.1-9.1,9.1 S7.1,53,12.1,53h23.1c4.3,0,7.8-3.5,7.8-7.8S39.5,37.5,35.2,37.5z M35.2,51H12.1C8.2,51,5,47.8,5,43.9s3.2-7.1,7.1-7.1 c0.5,0,1,0.1,1.5,0.2c0.5,0.1,1-0.2,1.2-0.7c1-3.7,4.4-6.2,8.2-6.2c4.7,0,8.5,3.8,8.5,8.5c0,0.6,0.4,1,1,1h2.7 c3.2,0,5.8,2.6,5.8,5.8S38.4,51,35.2,51z"
                  ></path>
                  <path
                    d="M53.4,16c-1.2-3-4.1-5-7.4-5c-4.1,0-7.4,3.1-7.9,7H37c-3.3,0-6,2.7-6,6s2.7,6,6,6h17c3.9,0,7-3.1,7-7 C61,18.9,57.5,15.7,53.4,16z M54,28H37c-2.2,0-4-1.8-4-4s1.8-4,4-4h2c0.6,0,1-0.4,1-1c0-3.3,2.7-6,6-6c2.7,0,5.1,1.8,5.8,4.4 c0.1,0.5,0.7,0.8,1.2,0.7c0.3-0.1,0.7-0.1,1-0.1c2.8,0,5,2.2,5,5S56.8,28,54,28z"
                  ></path>
                </g>
              </g>
            </g>
          </svg>
        </div>
        <span class="text-xl mt-10">{{ degree }}℃</span>
      </div>

      <span class="mr-3">강수량: {{ rain }}</span>
      <span class="mr-3">습도: {{ humidity }}%</span>
    </div>
  </div>
</template>
<script>
import axios from "axios";
const API_KEY =
  "HgAO0%2BEOZK9p23soEXzEvWp0H8aupgtkzY7BUJrFjlmi%2Fsd8j6UbUCsVrYvLhBj4zcU0fhb96%2B6uuO4CCGXm9A%3D%3D"; // 기상청 api key
const apiKEY = "9ee61467db74011154bc1168c6e1bb77"; // 카카오 resapi key
export default {
  data() {
    return {
      latitude: 0,
      longitude: 0,
      degree: 0, // 기온
      rain: null, // 1시간 강수량
      humidity: 0, //습도
      skystatus: {
        1: 1,
        3: 3,
        4: 4,
      },
      location: null,
      isDayTime: false,
    };
  },
  methods: {
    getWetherInfo(nx, ny) {
      // console.log("toXY된 값: ", nx, ny);
      const currentDate = new Date();

      // 현재 날짜 구하기 (YYYYMMDD)
      const year = currentDate.getFullYear().toString();
      const month = ("0" + (currentDate.getMonth() + 1)).slice(-2);
      const day = ("0" + currentDate.getDate()).slice(-2);
      const base_date = year + month + day;

      // 현재 시간의 30분 전 시간:
      const agoTime = new Date(currentDate.getTime() - 30 * 60000);

      // 현재 시간 구하기 (0000 형태)
      const hours = ("0" + agoTime.getHours()).slice(-2);
      const minutes = ("0" + agoTime.getMinutes()).slice(-2);
      const base_time = hours + minutes;

      // console.log("YYYYMMDD:", base_date);
      // console.log("0000 형태 시간:", base_time);

      const apiCalls = [
        axios.get(
          `http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst?serviceKey=${API_KEY}&numOfRows=10&pageNo=1&base_date=${base_date}&base_time=${base_time}&nx=${nx}&ny=${ny}&dataType=json`
        ),
        axios.get(
          `http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst?serviceKey=${API_KEY}&numOfRows=1000&pageNo=1&base_date=${base_date}&base_time=${base_time}&nx=${nx}&ny=${ny}&dataType=json`
        ),
      ];

      axios
        .all(apiCalls)
        .then(
          axios.spread((res1, res2) => {
            const data1 = res1.data.response.body.items.item;
            const data2 = res2.data.response.body.items.item;
            // console.log("단기실황: ", data1);
            // console.log("단기예보: ", data2);
            this.degree = data1[3].obsrValue;
            this.humidity = data1[1].obsrValue;
            this.rain = data2[12].fcstValue;
            this.skystatus = this.skystatus[data2[18].fcstValue];
          })
        )
        .catch((err) => {
          console.log("apiCalls로 불러오기 안됨", err);
        });
    },
    getPosition() {
      navigator.geolocation.getCurrentPosition(this.getGeoInfo, this.getError);
    },
    getGeoInfo(position) {
      this.latitude = position.coords.latitude;
      this.longitude = position.coords.longitude;
      // console.log("현재 위치", this.latitude, this.longitude);
      this.dfs_xy_conv("toXY", this.latitude, this.longitude);
      this.getLocationInfo(this.latitude, this.longitude);
    },
    getError() {
      console.log("위치 가져오기 뭔가 안됨");
    },

    dfs_xy_conv(code, v1, v2) {
      // LCC DFS 좌표변환 ( code : "toXY"(위경도->좌표, v1:위도, v2:경도), "toLL"(좌표->위경도,v1:x, v2:y) )
      var RE = 6371.00877; // 지구 반경(km)
      var GRID = 5.0; // 격자 간격(km)
      var SLAT1 = 30.0; // 투영 위도1(degree)
      var SLAT2 = 60.0; // 투영 위도2(degree)
      var OLON = 126.0; // 기준점 경도(degree)
      var OLAT = 38.0; // 기준점 위도(degree)
      var XO = 43; // 기준점 X좌표(GRID)
      var YO = 136; // 기1준점 Y좌표(GRID)

      var DEGRAD = Math.PI / 180.0;
      var RADDEG = 180.0 / Math.PI;

      var re = RE / GRID;
      var slat1 = SLAT1 * DEGRAD;
      var slat2 = SLAT2 * DEGRAD;
      var olon = OLON * DEGRAD;
      var olat = OLAT * DEGRAD;

      var sn =
        Math.tan(Math.PI * 0.25 + slat2 * 0.5) /
        Math.tan(Math.PI * 0.25 + slat1 * 0.5);
      sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn);
      var sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5);
      sf = (Math.pow(sf, sn) * Math.cos(slat1)) / sn;
      var ro = Math.tan(Math.PI * 0.25 + olat * 0.5);
      ro = (re * sf) / Math.pow(ro, sn);
      var rs = {};
      if (code == "toXY") {
        rs["lat"] = v1;
        rs["lng"] = v2;
        var ra = Math.tan(Math.PI * 0.25 + v1 * DEGRAD * 0.5);
        ra = (re * sf) / Math.pow(ra, sn);
        var theta = v2 * DEGRAD - olon;
        if (theta > Math.PI) theta -= 2.0 * Math.PI;
        if (theta < -Math.PI) theta += 2.0 * Math.PI;
        theta *= sn;
        rs["x"] = Math.floor(ra * Math.sin(theta) + XO + 0.5);
        rs["y"] = Math.floor(ro - ra * Math.cos(theta) + YO + 0.5);
      } else {
        rs["x"] = v1;
        rs["y"] = v2;
        var xn = v1 - XO;
        var yn = ro - v2 + YO;
        ra = Math.sqrt(xn * xn + yn * yn);
        if (sn < 0.0) -ra;
        var alat = Math.pow((re * sf) / ra, 1.0 / sn);
        alat = 2.0 * Math.atan(alat) - Math.PI * 0.5;

        if (Math.abs(xn) <= 0.0) {
          theta = 0.0;
        } else {
          if (Math.abs(yn) <= 0.0) {
            theta = Math.PI * 0.5;
            if (xn < 0.0) -theta;
          } else theta = Math.atan2(xn, yn);
        }
        var alon = theta / sn + olon;
        rs["lat"] = alat * RADDEG;
        rs["lng"] = alon * RADDEG;
      }
      // return console.log("rs란 무엇이냐", rs);
      this.nx = rs.x;
      this.ny = rs.y;
      this.getWetherInfo(this.nx, this.ny);
    },

    async getLocationInfo(lat, lang) {
      const apiUrl = `https://dapi.kakao.com/v2/local/geo/coord2address.json?x=${lang}&y=${lat}`;

      await axios
        .get(apiUrl, {
          headers: {
            Authorization: `KakaoAK ${apiKEY}`,
          },
        })
        .then((res) => {
          const address = res.data.documents[0].address;
          const region2depth = address.region_2depth_name;
          const region3depth = address.region_3depth_name;
          this.location = region2depth + " " + region3depth;
        })
        .catch((err) => {
          console.log("카카오api안됨", err);
        });
    },
    getTime() {},
  },
  computed: {
    // skystatus와 현재 시간을 기반으로 낮인지 여부를 계산하는 computed property 추가
    isDaytime() {
      const currentHour = new Date().getHours();
      return currentHour >= 7 && currentHour < 17;
    },
  },
  created() {
    this.getPosition();
  },
};
</script>
