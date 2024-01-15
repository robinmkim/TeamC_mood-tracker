<template>
  <div class="flex h-screen">
    <div class="w-1/5">
      <side-bar></side-bar>
    </div>

    <div class="flex-1 w-full bg-[#E7F1E5] h-screen overflow-auto">
      <!-- 여기서부터 ~~~ -->
      <div class="flex flex-col justify-center items-center">
        <div class="flex flex-col w-[700px] mt-5 items-center justify-center">
          <!-- 감정 이모티콘 -->
          <div class="flex flex-col w-[500px] h-auto" id="imoji">
            <div class="relative m-2">
              <div class="flex justify-center relative">
                <img
                  src="..\..\assets\e_neutral.png"
                  alt="Post image"
                  class="w-20 h-20 mt-5"
                />
                <!-- <div class="w-20 h-20 bg-green-500" id="emojiPlace"></div> -->
              </div>
            </div>
            <div class="flex flex-col justify-center text-center">
              <p class="w-auto mt-1">오늘의 기분은 ?<br /></p>
              <p class="w-auto m-2 text-lg">"{{ myExpresion }}"</p>
            </div>
          </div>

          <div
            id="result"
            class="flex flex-col items-center justify-center w-[500px] h-auto"
          >
            <!-- radar chart -->
            <!-- <div class="flex">차트</div>
            <result-chart class="flex"></result-chart> -->

            <div class="w-72 h-auto">
              <img
                v-bind:src="generatedImageSrc"
                alt="Post image"
                class="w-665 h-65"
              />
            </div>
            <button
              class="flex rounded-lg bg-[#DAFFFB] p-2 m-1 hover:bg-[#b6eee8] hover:scale-110 duration-300"
              v-on:click="imageDownload"
            >
              이미지 다운로드
            </button>

            <button
              class="flex rounded-lg bg-[#DAFFFB] p-2 m-1 hover:bg-[#b6eee8] hover:scale-110 duration-300"
              v-on:click="writePost"
            >
              게시물 작성하기
            </button>

            <!-- <p class="w-auto m-2 text-lg">*lastResultId={{ lastResultId }}</p>
            <p class="w-auto m-2 text-lg">
              *faceAnalyzeResult={{ faceAnalyzeResult }}
            </p> -->
            <!-- <p class="w-auto m-2 text-lg">
              *generatedImageFileName={{ generatedImageFileName }}
            </p> -->

            <div><p>분석결과 피드백 하기</p></div>
            <div class="flex">
              <button
                class="flex rounded-lg bg-[#DAFFFB] p-2 m-1 hover:bg-[#b6eee8] hover:scale-110 duration-300"
                v-on:click="feedbackGood"
              >
                마음에 들어요
              </button>
              <button
                class="flex rounded-lg bg-[#ffdcda] p-2 m-1 hover:bg-[#ffccc9] hover:scale-110 duration-300"
                v-on:click="feedbackBad"
              >
                마음에 안 들어요
              </button>
            </div>
            <!-- <p class="w-auto m-2 text-lg">**{{ faceAnalyzeResult }}</p>
            <p class="w-auto m-2 text-lg">**{{ myExpresion }}</p> -->
            <!-- <p class="w-auto m-2 text-lg">Neutral</p> -->

            <!-- <div class="text-center">와아 즐겁다아</div> -->
          </div>
          <!-- 음악추천 채팅창 -->
          <div id="recMusic" class="flex justify-center overflow-scroll">
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
          </div>
          <!-- 음악추천 fini -->
        </div>
      </div>
      <!-- 여까지 -->
    </div>

    <div class="w-1/5 p-4">side menu</div>
  </div>
</template>

<script>
// import { Radar } from "vue-chartjs";
// import ResultChart from "./ResultChart.vue";
import apiClient from "./../../utils/apiClient";
import SideBar from "@/components/SideBar";
import axios from "axios";
export default {
  props: ["lastResultId"],
  // props: ["formData"],
  name: "AnalyzeResult",
  components: {
    // ResultChart,
    SideBar,
  },
  data() {
    return {
      memberNum: 1,
      myExpresion: null,
      faceAnalyzeResult: null,
      // lastResultId: null,
      generatedImageFileName: null,
      generatedImageSrc: null,
    };
  },
  mounted() {
    apiClient
      .get("http://192.168.0.93:8083/faceresult/detail", {
        params: {
          ar_id: this.lastResultId,
        },
      })
      .then((res) => {
        console.log(res.data);
        this.myExpresion = res.data.ar_content_max;
        this.faceAnalyzeResult = res.data.ar_content;
        this.generatedImageFileName = res.data.ar_generated_img;

        //  장고로부터 사진 base64형태로 반환받음
        axios
          .get("http://192.168.0.13:9000/face/getGeneratedImage", {
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
    notHappyWithResult: function () {
      this.showFeedbackMenus = !this.showFeedbackMenus;
      console.log("피드백 하기 클릭됨");
      // alert(
      //   this.lastResultId +
      //     "\n분석결과가 마음에 들지 않으신가요?\n더 나은 서비스를 위해 피드백을 남겨주세요"
      // );
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
        .get("http://192.168.0.13:9000/face/downloadGeneratedImage", {
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
        params: { lastResultId: this.$props.lastResultId },
      });
    },
  },
};
</script>

<style scoped></style>
