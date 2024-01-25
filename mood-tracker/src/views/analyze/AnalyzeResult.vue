<template>
  <div class="flex h-screen">
    <div class="flex-1 w-full h-fit border-r border-l border-gray-200">
      <!-- 여기서부터 ~~~ -->
      <div class="flex flex-col justify-center items-center">
        <div class="flex flex-col w-[700px] mt-5 items-center justify-center">
          <!-- 감정 이모티콘 -->
          <div class="flex flex-col w-[500px] h-auto" id="imoji">
            <div class="relative m-2">
              <div class="flex justify-center relative"></div>
            </div>
            <div class="flex flex-col justify-center text-center">
              <p class="w-auto mt-1 text-xl font-bold">오늘의 기분은?</p>
              <div class="flex justify-center relative">
                <img
                  v-bind:src="emojiImageSource"
                  alt="Post image"
                  class="w-20 h-20 mt-5"
                />
              </div>

              <p class="w-auto m-2 text-lg">{{ myExpresion }}</p>
            </div>
          </div>

          <div
            id="result"
            class="flex flex-col items-center justify-center w-[500px] h-auto"
          >
            <div class="flex-row w-auto h-auto">
              <img v-bind:src="generatedImageSrc" alt="Post image" />

              <button
                class="flex ml-auto rounded-lg bg-[#e6e6e6] p-1 m-1 hover:bg-[#a0f9f0] hover:scale-110 duration-300"
                v-on:click="imageDownload"
              >
                이미지 다운로드
              </button>
            </div>

            <div class="m-2">
              <div class="flex">
                <button
                  :class="{
                    'bg-[#7deee2]': showPieChart,
                    'bg-[#dcf7f5]': !showPieChart,
                  }"
                  class="rounded-tl-lg p-2 bg-[#7deee2] hover:bg-[#7deee2] duration-300"
                  style="
                    border: 1px solid;
                    border-bottom: 0px;
                    border-color: #64ccc5;
                  "
                  @click="changeToPieChart"
                >
                  파이 차트</button
                ><button
                  :class="{
                    'bg-[#7deee2]': showRadarChart,
                    'bg-[#dcf7f5]': !showRadarChart,
                  }"
                  class="rounded-tr-lg p-2 hover:bg-[#7deee2] duration-300"
                  style="
                    border: 1px solid;
                    border-bottom: 0px;
                    border-color: #64ccc5;
                  "
                  @click="changeToRadarChart"
                >
                  레이다 차트
                </button>
              </div>

              <div
                class="border-teal-300 myChartContainer p-2 rounded-lg rounded-tl-none"
                style="border-width: 1px; border-color: #64ccc5"
              >
                <div
                  v-if="showPieChart"
                  class="flex flex-col justify-center items-center m-2 myChart"
                >
                  <!-- <div class="flex">파이차트</div> -->
                  <PieChart class="flex-grow w-full h-full" />
                </div>
                <div
                  v-if="showRadarChart"
                  class="flex flex-col justify-center items-center m-2 myChart"
                >
                  <!-- <div class="flex">RADAR차트</div> -->
                  <ResultChart class="flex-grow" />
                </div>
              </div>
            </div>

            <button
              class="flex rounded-lg bg-[#7deee2] p-2 m-1 hover:bg-[#a0f9f0] hover:scale-125 duration-300"
              v-on:click="writePost"
            >
              게시물 작성하기
            </button>

            <div class="m-2">
              <div class="flex">
                <span class="self-center m-1">분석결과 피드백 하기</span>
                <button
                  class="flex rounded-lg bg-[#7deee2] p-1 m-1 hover:bg-[#a0f9f0] hover:scale-125 duration-300"
                  v-on:click="feedbackGood"
                >
                  <span>👍</span>
                </button>
                <button
                  class="flex rounded-lg bg-[#fd6e66] p-1 m-1 hover:bg-[#f9857f] hover:scale-125 duration-300"
                  v-on:click="feedbackBad"
                >
                  👎
                </button>
              </div>
            </div>
          </div>
          <!-- 음악추천 채팅창 -->
          <!-- <div id="recMusic" class="flex justify-center overflow-scroll">
            <div
              id="customChat"
              class="flex flex-col bg-[#ADC4CE] w-[500px] h-fit"
            >
              <div class="flex pl-4 pr-4 py-1 justify-end m-2">
                <div class="flex items-end w-full justify-end">
                  <div class="self-end text-xs mr-1 mb-1">오후 11:33</div>
                  <div class="flex rounded-lg bg-[#DAFFFB] p-2" id="moi">
                    <div class="whitespace-normal break-all">
                      오늘의 감정에 어울리는 노래를 추천해 줘
                    </div>
                  </div>
                </div>
              </div>

              <div class="flex pl-4 pr-4 py-1 justify-start m-2">
                <div class="flex flex-col items-start">
                  <div class="flex">
                    <div class="flex flex-col">
                      <div class="flex rounded-lg bg-slate-200 p-2 mb-1">
                        <div>이런 음악은 어떠세요 ?</div>
                      </div>
                      <div class="flex rounded-lg bg-slate-200 p-2 mb-1">
                        <div>플레이리스트 찾는 중...</div>
                      </div>
                    </div>
                    <div class="self-end text-xs ml-1">오후 11:45</div>
                  </div>
                  <div class="flex mt-1">
                    <div class="flex">
                      <div
                        class="flex flex-col rounded-lg bg-slate-200 p-2 mb-1 text-left"
                      >
                        ooo님께 추천드리는 <br />
                        오늘의 플레이리스트입니다😊

                        <div id="playlist" class="flex">
                          <iframe
                            width="300"
                            height="180"
                            src="https://www.youtube.com/embed/videoseries?list=PLF6R3PXnaP_OCKtNXo7yfEqTbOrDBiueC&si=FOvPc2ITvFORcsI-"
                            frameborder="0"
                            allowfullscreen
                          ></iframe>
                        </div>
                        <a
                          href="https://www.youtube.com/playlist?list=PLF6R3PXnaP_OCKtNXo7yfEqTbOrDBiueC&si=FOvPc2ITvFORcsI-"
                          target="_blank"
                          class="text-blue-400 flex self-end"
                          >youtube에서 재생 ▶</a
                        >
                      </div>
                    </div>
                    <div class="self-end text-xs ml-1">오후 11:46</div>
                  </div>
                </div>
              </div>
            </div>
          </div> -->
          <!-- 음악추천 fini -->
        </div>
      </div>
      <!-- 여까지 -->
    </div>
  </div>
</template>

<script>
// import { Radar } from "vue-chartjs";
import ResultChart from "./ResultChart.vue";
import PieChart from "@/components/PieChart.vue";
import apiClient from "@/utils/apiClient";
import axios from "axios";
export default {
  props: ["lastResultId"],
  // props: ["formData"],
  name: "AnalyzeResult",
  components: {
    ResultChart,
    PieChart,
  },
  data() {
    return {
      showPieChart: true,
      showRadarChart: false,
      memberNum: 1,
      myExpresion: null,
      faceAnalyzeResult: null,
      // lastResultId: null,
      generatedImageFileName: null,
      generatedImageSrc: null,

      emojiImageSource: null,
    };
  },
  mounted() {
    apiClient
      .get("/faceresult/detail", {
        params: {
          ar_id: this.lastResultId,
        },
      })
      .then((res) => {
        console.log("스프링->결과Detail=> ", res.data);
        this.myExpresion = res.data.ar_content_max;
        this.faceAnalyzeResult = res.data.ar_content;
        this.generatedImageFileName = res.data.ar_generated_img;

        // 감정 이미지
        this.emojiImageSource = require("../../assets/emoji/" +
          this.myExpresion +
          ".png");

        //  장고로부터 사진 base64형태로 반환받음
        axios
          .get("http://localhost:9000/face/getGeneratedImage", {
            params: {
              imageName: this.generatedImageFileName,
            },
          })
          .then((res) => {
            console.log(res.data);
            this.generatedImageSrc = `data:image/jpeg;base64, ${res.data.generatedImg}`;
          });
      });
  },
  methods: {
    changeToPieChart: function () {
      this.showPieChart = true;
      this.showRadarChart = false;
    },
    changeToRadarChart: function () {
      this.showPieChart = false;
      this.showRadarChart = true;
    },
    feedbackBad: function () {
      const formData = new FormData();
      formData.append("ar_id", this.lastResultId);

      apiClient
        .post("/faceresult/feedback/bad", {
          ar_id: this.lastResultId,
        })
        .then(() => {
          console.log("feedback Bad Success");
          alert("feedback Bad Success");
        })
        .catch((error) => {
          console.log("error", error);
        });
    },
    feedbackGood: function () {
      apiClient
        .post("/faceresult/feedback/good", {
          ar_id: this.lastResultId,
        })
        .then(() => {
          console.log("feedback Good Success");
          alert("feedback Good Success");
        })
        .catch((error) => {
          console.log("error", error);
        });
    },
    imageDownload: function () {
      //장고
      axios
        .get("http://localhost:9000/face/downloadGeneratedImage", {
          params: {
            imageName: this.generatedImageFileName,
          },
          responseType: "blob",
        })
        .then((response) => {
          const url = window.URL.createObjectURL(new Blob([response.data]));
          const link = document.createElement("a");
          link.href = url;
          link.setAttribute("download", this.generatedImageFileName);
          document.body.appendChild(link);
          link.click();
        })
        .catch((error) => {
          console.log(error);
        });
    },
    writePost: function () {
      // confirm("게시물 작성하러 가기");
      this.$router.push({
        name: "PostWriteWithAnalyzeResult",
        params: { resultId: this.$props.lastResultId },
      });
    },
  },
};
</script>

<style scoped>
.myChart {
  width: 500px;
  height: 500px;
}
</style>
