<template lang="">
  <div>
    <div>
      <!--그래프-->
      <div class="flex flex-col justify-center items-center flex-grow">
        <BarChart class="w-4/5 flex-grow" />
      </div>

      <div class="flex justify-between mx-6 mt-4">
        <div
          class="w-52 h-32 items-center justify-center flex flex-col bg-slate-300 rounded-3xl"
        >
          <div class="flex w-full justify-center items-center">
            <div>
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
          </div>
          <div class="text-lg font-bold">연속 기록 중!!</div>
        </div>
        <div
          class="w-52 h-32 items-center justify-center flex flex-col bg-slate-300 rounded-3xl"
        >
          <div class="flex w-full justify-center items-center">
            <div>
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
            <div class="font-extrabold text-4xl">{{ cntPosts }}개</div>
          </div>
          <div class="text-lg font-bold">Mood 기록</div>
        </div>
        <div
          class="w-52 h-32 items-center justify-center flex flex-col bg-slate-300 rounded-3xl"
        >
          <div class="flex w-full text-6xl m-3 justify-center items-center">
            <div>{{ mainSenti }}</div>
          </div>
          <div class="text-lg font-bold">이달의 감정</div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import BarChart from "@/components/BarChart.vue";
import apiClient from "@/utils/apiClient";
export default {
  name: "MyMood",
  components: {
    BarChart,
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
        .get(`/mypage/const`)
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
        .get(`/mypage/postcnt`)
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
        .get(`/mypage/mainsenti`)
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
};
</script>
<style lang=""></style>
