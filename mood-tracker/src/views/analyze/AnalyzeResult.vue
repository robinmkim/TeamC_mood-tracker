<template>
  <div class="flex h-screen">
    <div class="w-1/5">
      <side-bar></side-bar>
    </div>

    <div class="flex-1 w-full bg-[#E7F1E5]">
      <!-- 여기서부터 ~~~ -->
      <div class="flex justify-center items-center">
        <div
          class="flex flex-col w-[700px] h-auto mt-5 bg-slate-200 items-center justify-center"
        >
          <!-- 감정 이모티콘 -->
          <div class="flex flex-col w-[500px] h-auto bg-[#ffede6]" id="imoji">
            <div class="relative m-3">
              <div class="flex justify-center relative">
                <!-- <img
                  src="..\..\assets\emoticon01.png"
                  alt="Post image"
                  class="w-20 h-20"
                /> -->
                <div class="w-20 h-20 bg-green-500" id="emojiPlace"></div>
              </div>
            </div>
            <div class="flex flex-col justify-center text-center">
              <p class="w-auto mt-2">오늘의 기분<br /></p>
              <p class="w-auto m-2 text-lg">C'est PARFAIT !!</p>
            </div>
          </div>

          <div
            id="result"
            class="flex flex-col items-center justify-center w-[500px] h-auto bg-[#e39a7d]"
          >
            <!-- radar chart -->
            <!-- <div class="flex">차트</div>
            <result-chart class="flex"></result-chart> -->

            <div class="w-72 h-72 bg-slate-700 text-white">
              result image place
            </div>

            <div class="text-center">내용</div>
          </div>
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
