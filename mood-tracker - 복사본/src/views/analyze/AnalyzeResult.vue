<template>
  <div class="flex h-screen">
    <div class="w-1/5">
      <side-bar></side-bar>
    </div>

    <div class="flex-1 w-full h-screen bg-[#E7F1E5]">
      <!-- 여기서부터 ~~~ -->
      <div class="flex flex-col justify-center items-center">
        <div
          class="flex flex-col w-[700px] h-screen mt-5 items-center justify-center"
        >
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
                src="..\..\assets\resSample.png"
                alt="Post image"
                class="w-665 h-65"
              />
            </div>
            <p class="w-auto m-2 text-lg">Neutral</p>

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
import SideBar from "@/components/SideBar";
export default {
  name: "AnalyzeResult",
  components: {
    // ResultChart,
    SideBar,
  },
};
</script>

<style scoped></style>
