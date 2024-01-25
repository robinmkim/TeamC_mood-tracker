<template lang="">
  <div class="h-full">
    <div class="items-center justify-center">
      <!-- page 1 : 차트 -->
      <div class="flex flex-col items-center justify-center">
        <div class="flex w-4/5 items-center justify-center m-2">
          <div class="flex w-full h-24 items-center justify-center flex">
            <div class="flex w-1/5 text-5xl justify-center items-center">
              <div>{{ mainSenti }}</div>
            </div>
            <div class="text-lg font-bold">: 가장 많이 기록된 감정</div>
          </div>
        </div>
      </div>
      <div>
        <!--그래프-->
        <div class="flex flex-col mb-4 justify-center items-center flex-grow">
          <BarChart class="w-4/5 flex-grow" :m_id="m_id" />
        </div>
      </div>
      <!-- 기록수 -->
      <div class="flex items-center justify-center">
        <div class="flex flex-col w-4/5 justify-center items-center">
          <div
            class="flex h-20 w-full items-center justify-center flex bg-slate-100 rounded-3xl"
          >
            <div class="text-lg font-bold align-middle mr-4">
              <span>이번 달에 기록한 감정들의 수 : </span>
            </div>
            <div class="font-extrabold text-2xl">{{ cntPosts }}개</div>
            <div class="flex items-center justify-center m-3">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                viewBox="0 0 24 24"
                fill="currentColor"
                class="w-10 h-10"
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
          this.$emit("handleMainSenti", res.data);
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
<style lang="css"></style>
