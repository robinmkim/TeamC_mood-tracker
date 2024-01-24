<template lang="">
  <div class="h-full">
    <div class="pages">
      <!-- page 1 : 글귀, 차트 -->
      <div class="page flex flex-col m-5">
        <!-- 명? 언?-->
        <div class="flex m-4 justify-center items-center">
          <div
            class="flex border-2 h-32 w-4/5 border-slate-300 rounded-md justify-center items-center"
          >
            <span class="m-3">무언가가 들어올 자리입니다</span>
          </div>
        </div>
        <!--그래프-->
        <div class="flex flex-col justify-center items-center flex-grow">
          <BarChart class="w-4/5 flex-grow" :m_id="m_id" />
        </div>
      </div>
      <!-- page 2 : 연속일수, 기록수-->
      <div class="page flex flex-col m-4 justify-center items-center">
        <!--연속일수-->
        <div class="flex w-4/5 justify-between m-4">
          <div class="w-full h-32 items-center flex bg-slate-300 rounded-3xl">
            <div class="flex w-1/5 items-center justify-center">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                viewBox="0 0 24 24"
                fill="currentColor"
                class="w-20 h-20"
              >
                <path
                  fill-rule="evenodd"
                  d="M12.963 2.286a.75.75 0 0 0-1.071-.136 9.742 9.742 0 0 0-3.539 6.176 7.547 7.547 0 0 1-1.705-1.715.75.75 0 0 0-1.152-.082A9 9 0 1 0 15.68 4.534a7.46 7.46 0 0 1-2.717-2.248ZM15.75 14.25a3.75 3.75 0 1 1-7.313-1.172c.628.465 1.35.81 2.133 1a5.99 5.99 0 0 1 1.925-3.546 3.75 3.75 0 0 1 3.255 3.718Z"
                  clip-rule="evenodd"
                />
              </svg>
            </div>
            <div class="font-extrabold text-4xl">{{ consecPosts }}일째</div>
            <div class="text-lg font-bold">
              <span>연속으로 감정을 기록한 기간</span>
            </div>
          </div>
        </div>

        <!-- 기록수 -->
        <div class="flex w-4/5 justify-between m-4">
          <div class="w-full h-32 items-center flex bg-slate-300 rounded-3xl">
            <div class="text-lg font-bold">
              <span>이번 달에 기록한 감정들의 수</span>
            </div>
            <div class="font-extrabold text-4xl">{{ cntPosts }}개</div>
            <div class="flex w-1/5 items-center justify-center m-3">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                viewBox="0 0 24 24"
                fill="currentColor"
                class="w-20 h-20"
              >
                <path
                  d="M21.731 2.269a2.625 2.625 0 0 0-3.712 0l-1.157 1.157 3.712 3.712 1.157-1.157a2.625 2.625 0 0 0 0-3.712ZM19.513 8.199l-3.712-3.712-8.4 8.4a5.25 5.25 0 0 0-1.32 2.214l-.8 2.685a.75.75 0 0 0 .933.933l2.685-.8a5.25 5.25 0 0 0 2.214-1.32l8.4-8.4Z"
                />
                <path
                  d="M5.25 5.25a3 3 0 0 0-3 3v10.5a3 3 0 0 0 3 3h10.5a3 3 0 0 0 3-3V13.5a.75.75 0 0 0-1.5 0v5.25a1.5 1.5 0 0 1-1.5 1.5H5.25a1.5 1.5 0 0 1-1.5-1.5V8.25a1.5 1.5 0 0 1 1.5-1.5h5.25a.75.75 0 0 0 0-1.5H5.25Z"
                />
              </svg>
            </div>
          </div>
        </div>
      </div>

      <!-- page 3 : 감정 분석 -->
      <div class="page flex m-4 justify-center items-center">
        <div class="flex w-4/5 justify-between m-4">
          <div
            class="w-full h-48 items-center flex rounded-md bg-gradient-to-r from-[#F67D73] to-[#D9F3C1]"
          >
            <div class="flex w-1/5 text-6xl m-3 justify-center items-center">
              <div>{{ mainSenti }}</div>
            </div>
            <div class="text-lg font-bold">이달의 감정</div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <!-- 페이징 -->
  <!-- <ul class="pagination"></ul> -->
</template>
<script>
import BarChart from "@/components/BarChart.vue";
import apiClient from "@/utils/apiClient";
export default {
  name: "MyMood",
  components: {
    BarChart,
  },
  props: {
    m_id: {
      type: Number,
      required: true,
    },
  },
  data: () => ({
    cntPosts: 0,
    consecPosts: 0,
    mainSenti: null,
    emotionMap: {
      happy: "😆",
      angry: "😡",
      anxiety: "😬",
      hurt: "🤕",
      neutral: "😐",
      sad: "😢",
      surprise: "😨",
    },
  }),
  methods: {
    // 연속으로 글 쓴 날짜
    getConsecPost() {
      apiClient
        .get(`/mypage/const?m_id=${this.m_id}`)
        .then((cnt) => {
          this.consecPosts = cnt.data;
        })
        .catch((err) => {
          console.log("연속 일자 가져오기 안됨", err);
        });
    },
    // 이번달에 쓴 게시글 개수
    getThisMonthPosts() {
      apiClient
        .get(`/mypage/postcnt?m_id=${this.m_id}`)
        .then((cnt) => {
          this.cntPosts = cnt.data;
          console.log("이번달에 쓴 게시글 개수는? ", this.cntPosts);
        })
        .catch((err) => {
          console.log("게시글 수 못가져옴", err);
        });
    },

    getMainSentiment() {
      apiClient
        .get(`/mypage/mainsenti?m_id=${this.m_id}`)
        .then((res) => {
          console.log("이번 달 메인 감정: ", res.data);
          const senti = res.data;
          console.log("emotion", this.emotionMap);
          this.mainSenti = this.emotionMap[senti];
          console.log(this.mainSenti);
        })
        .catch((err) => {
          console.log("메인 감정 못불러옴", err);
        });
    },
  },
  created() {
    this.getConsecPost();
    this.getThisMonthPosts();
    this.getMainSentiment();
  },
  mounted() {
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

      let pages = document.querySelector(".pages");
      let slider = new Slider(pages);
      console.log("에러방지", slider);
    });
  },
};
</script>
<style lang="css">
.pages {
  padding: 0;
  margin: 0;
  height: 100vh;
  width: 100%;
  position: relative;
  overflow: hidden;
}

.page {
  padding: 0;
  margin: 0;
  height: 100%;
  width: 100%;
  position: absolute;
  bottom: -100%;
  transition: bottom 0.7s;

  background-attachment: fixed;
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;
}

.page:first-child {
  bottom: 0;
}

.title {
  width: 100%;
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  text-align: center;
}
</style>
