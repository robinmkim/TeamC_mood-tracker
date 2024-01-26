<template>
  <div class="w-3/5 ipages">
    <!-- page 1 -->
    <div class="flex w-full h-screen ipage">
      <div class="w-1/3 bg-gradient-to-b from-[#64CCC5] from-45% to-[#FFF2E2]">
        <div class="mt-40 mr-2 text-end animated-text">
          <span class="text-white font-extrabold text-left text-[128px]"
            >SONG</span
          >
        </div>
      </div>
      <div class="w-2/3">
        <img src="../../assets/introimg.png" class="h-full w-full" />
        <div class="flex flex-col">
          <div class="absolute top-40 text-start animated-text">
            <span class="font-extrabold text-[#64CCC5] text-[128px]"
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
      <div class="flex flex-col w-2/3 mt-[235px] ml-[90px] mr-[165px] text-end">
        <div class="animated-photo w-[800px] h-[500px] z-01">
          <img src="../../assets/introImg_sns.png" />
        </div>
        <span
          class="absolute top-[650px] left-[930px] animated-text font-extrabold text-[#64CCC5] text-[128px] z-30"
          >SN</span
        >
        <div class="animated-text absolute top-[843px] left-[570px] mr-2 z-30">
          <span class="text-[36px]">다른 유저들과의 소통, 기록 </span>
          <span class="text-[36px] text-[#64CCC5] font-bold"> 그리고 </span>
        </div>
      </div>
      <div class="w-1/3 bg-gradient-to-b from-[#FFF2E2] from-45% to-[#64CCC5]">
        <div class="animated-text mt-[650px] mr-2 text-start">
          <span class="text-white font-extrabold text-right text-[128px]"
            >S</span
          >
          <div class="animated-text">
            <span class="text-[36px] ml-3">공유 </span>
          </div>
        </div>
      </div>
    </div>
    <!-- page 2 끝 -->

    <!-- page 3 -->
    <div class="flex w-full h-screen ipage">
      <div class="flex w-2/3 flex-col mt-[212px] ml-[258px] text-end">
        <div class="animated-photo mr-20">
          <img src="../../assets/image 127.png" />
        </div>
        <span class="animated-text font-extrabold text-[#64CCC5] text-[128px]"
          >Record Analy</span
        >
        <div class="animated-text">
          <span class="text-[36px]">당신이 기록한 감정들 </span>
          <span class="text-[36px] text-[#64CCC5] font-bold mr-2">
            그리고
          </span>
        </div>
      </div>
      <div class="w-1/3 bg-gradient-to-b from-[#64CCC5] from-45% to-[#FFF2E2]">
        <div class="animated-text mt-[560px] mr-2 text-start">
          <span class="text-white font-extrabold text-right text-[128px]"
            >sis</span
          >
          <div class="animated-text">
            <span class="text-[36px] ml-1">차트 </span>
          </div>
        </div>
      </div>
    </div>
    <!-- page 3 끝 -->
    <!-- <div class="flex w-full h-10 bg-[#FCF1E1]"></div> -->
    <!-- page 4 -->
    <div class="flex w-full h-screen ipage">
      <div class="w-1/3 bg-gradient-to-b from-[#FFF2E2] to-[#64CCC5] to-45%">
        <div class="animated-photo mt-48 mr-2 text-end">
          <span class="font-extrabold text-white text-[128px]">AI</span>
        </div>
      </div>
      <div class="w-2/3">
        <div class="flex flex-col mt-48 ml-6 text-start">
          <span
            class="animated-photo font-extrabold text-[#64CCC5] text-[128px]"
            >Service</span
          >
          <div class="animated-photo">
            <span class="text-[36px]">얼굴 표정분석, 기록의 긍정도 </span>
            <span class="text-[36px] text-[#64CCC5] font-bold"> 그리고 </span>
            <span class="text-[36px]"> 노래 추천</span>
          </div>
        </div>
        <div class="animated-text flex ml-5">
          <img src="../../assets/introImg_ai.png" />
          <img
            src="../../assets/introImg_ai2.png"
            class="h-[150px] w-[400px] mt-16 ml-10"
          />
        </div>
      </div>
    </div>
    <!-- page 4 끝 -->
  </div>
</template>
<script>
export default {
  name: "IntroductionPage",
  created() {
    window.addEventListener("load", () => {
      const Slider = function (pages, applyStyleCallback) {
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

            applyStyleCallback(); // applyStyle 호출
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
      let applyStyle = () => {
        // .animated-text에 대한 스타일 초기화
        let animatedTexts = document.querySelectorAll(".animated-text");
        animatedTexts.forEach((text) => {
          text.style.opacity = 0;
          text.style.animation = "none";
        });

        // .animated-photo
        let animatedPhotos = document.querySelectorAll(".animated-photo");
        animatedPhotos.forEach((photo) => {
          photo.style.opacity = 0;
          photo.style.animation = "none";
        });

        // 애니메이션 다시 시작
        setTimeout(() => {
          animatedTexts.forEach((text) => {
            text.style.animation = "";
          });
          animatedPhotos.forEach((photo) => {
            photo.style.animation = "";
          });
        }, 10);
      };

      //let slider = new Slider(pages, applyStyle);
      new Slider(pages, applyStyle);
    });
  },
};
</script>

<style lang="css">
.ipages {
  padding: 0;
  margin: 0;
  height: 100vh;
  width: 1500px;
  position: relative;
  overflow: hidden;
}

.ipage {
  padding: 0;
  margin: 0;
  height: 100%;
  position: absolute;
  bottom: -100%;
  transition: bottom 0.7s cubic-bezier(0.42, 0, 0.58, 1); /* 이 부분에 cubic-bezier 함수를 활용하여 이징 효과를 조절할 수 있습니다. */
  background-attachment: fixed;
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;
}
.animated-photo,
.animated-text {
  opacity: 0;
  animation: fadeIn 1s ease-in-out forwards; /* 페이드인 애니메이션 설정 */
}

@keyframes fadeIn {
  0% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
}

.animated-text {
  opacity: 0;
  animation: fadeInRight 1s ease-in-out forwards;
}

.animated-photo {
  opacity: 0;
  animation: fadeInLeft 1s ease-in-out forwards;
}

@keyframes fadeInRight {
  0% {
    opacity: 0;
    transform: translateX(20px);
  }
  100% {
    opacity: 1;
    transform: translateX(0);
  }
}

@keyframes fadeInLeft {
  0% {
    opacity: 0;
    transform: translateX(-20px);
  }
  100% {
    opacity: 1;
    transform: translateX(0);
  }
}
</style>
