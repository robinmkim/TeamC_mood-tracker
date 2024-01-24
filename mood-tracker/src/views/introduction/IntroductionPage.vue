<template>
  <div class="w-3/5 ipages">
    <!-- page 1 -->
    <div class="flex w-full h-screen ipage">
      <div class="w-1/3 bg-gradient-to-b from-[#64CCC5] from-45% to-[#FFF2E2]">
        <div class="mt-40 mr-2 text-end">
          <span class="text-white font-extrabold text-left text-[128px]"
            >SONG</span
          >
        </div>
      </div>
      <div class="w-2/3">
        <img src="../../assets/introimg.png" class="h-full w-full" />
        <div class="flex flex-col">
          <div class="absolute top-40 text-start">
            <span class="font-extrabold text-[#64CCC5] text-[128px] ml-2"
              >TIMENT</span
            >
            <div class="ml-60">
              <span class="text-[36px]">간편하게 기록하는 오늘의</span>
              <span class="text-[36px] text-[#64CCC5] font-bold"> 감정</span>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- page 1 끝 -->
    <!-- <div class="flex w-full h-10 bg-[#FCF1E1]"></div> -->

    <!-- page 2 -->
    <div class="flex w-full h-screen ipage">
      <div class="flex flex-col w-2/3 mt-[500px] ml-[260px] text-end">
        <span class="font-extrabold text-[#64CCC5] text-[128px]">SN</span>
        <div class="mr-2">
          <span class="text-[36px]">다른 유저들과의 소통, 기록 </span>
          <span class="text-[36px] text-[#64CCC5] font-bold"> 그리고 </span>
        </div>
      </div>
      <div class="w-1/3 bg-gradient-to-b from-[#FFF2E2] from-45% to-[#64CCC5]">
        <div class="mt-[500px] mr-2 text-start">
          <span class="text-white font-extrabold text-right text-[128px]"
            >S</span
          >
          <div>
            <span class="text-[36px] ml-3">공유 </span>
          </div>
        </div>
      </div>
    </div>
    <!-- page 2 끝 -->

    <!-- page 3 -->
    <div class="flex w-full h-screen ipage">
      <div class="flex w-2/3 flex-col mt-[200px] ml-[258px] text-end">
        <div class="mr-20">
          <img src="../../assets/image 127.png" />
        </div>
        <span class="font-extrabold text-[#64CCC5] text-[128px]"
          >Record Analy</span
        >
        <div>
          <span class="text-[36px]">당신의 기록을 분석 </span>
          <span class="text-[36px] text-[#64CCC5] font-bold mr-2">
            그리고
          </span>
        </div>
      </div>
      <div class="w-1/3 bg-gradient-to-b from-[#64CCC5] from-45% to-[#FFF2E2]">
        <div class="mt-[560px] mr-2 text-start">
          <span class="text-white font-extrabold text-right text-[128px]"
            >sis</span
          >
          <div>
            <span class="text-[30px] ml-1">결과 </span>
          </div>
        </div>
      </div>
    </div>
    <!-- page 3 끝 -->
    <!-- <div class="flex w-full h-10 bg-[#FCF1E1]"></div> -->
    <!-- page 4 -->
    <div class="flex w-full h-screen ipage">
      <div class="w-1/3 bg-gradient-to-b from-[#FFF2E2] to-[#64CCC5] to-45%">
        <div class="mt-48 mr-2 text-end">
          <span class="font-extrabold text-white text-[128px]">AI</span>
        </div>
      </div>
      <div class="w-2/3">
        <div class="flex flex-col mt-48 ml-6 text-start">
          <span class="font-extrabold text-[#64CCC5] text-[128px]"
            >Service</span
          >
          <div>
            <span class="text-[36px]">얼굴 표정분석, 기록의 긍정도 </span>
            <span class="text-[36px] text-[#64CCC5] font-bold"> 그리고 </span>
            <span class="text-[36px]"> 노래 추천</span>
          </div>
        </div>
      </div>
    </div>
    <!-- page 4 끝 -->
  </div>
</template>
<script>
export default {
  name: "IntroductionPage",
  methods: {},
  created() {
    window.addEventListener("load", () => {
      const Slider = function (pages) {
        let slides = [],
          count = 0,
          current = 0,
          touchstart = 0,
          animation_state = false;

        const init = () => {
          slides = pages.children;
          count = slides.length;
          for (let i = 0; i < count; i++) {
            slides[i].style.bottom = -(i * 100) + "%";
          }
        };

        const gotoNum = (index) => {
          if (index != current && !animation_state) {
            animation_state = true;
            setTimeout(() => (animation_state = false), 500);
            current = index;
            for (let i = 0; i < count; i++) {
              slides[i].style.bottom = (current - i) * 100 + "%";
            }
          }
        };

        const gotoNext = () =>
          current < count - 1 ? gotoNum(current + 1) : false;
        const gotoPrev = () => (current > 0 ? gotoNum(current - 1) : false);
        pages.ontouchstart = (e) => (touchstart = e.touches[0].screenY);
        pages.ontouchend = (e) =>
          touchstart < e.changedTouches[0].screenY ? gotoPrev() : gotoNext();
        pages.onmousewheel = pages.onwheel = (e) =>
          e.deltaY < 0 ? gotoPrev() : gotoNext();

        init();
      };

      let pages = document.querySelector(".ipages");
      let slider = new Slider(pages);
      console.log("에러방지", slider);
    });
  },
  mounted() {},
};
</script>
<style lang="css">
.ipages {
  padding: 0;
  margin: 0;
  height: 100vh;
  width: 60%;
  position: relative;
  overflow: hidden;
}

.ipage {
  padding: 0;
  margin: 0;
  height: 100%;
  position: absolute;
  bottom: -100%;
  transition: bottom 0.7s;

  background-attachment: fixed;
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;
}

.ipage:first-child {
  bottom: 0;
}
</style>
