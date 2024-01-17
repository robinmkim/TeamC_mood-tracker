<template>
  <div class="flex">
    <div class="w-5/6" ref="postScrollContainer">
      <div class="">
        <div class="flex h-28">
          <div class="relative flex"></div>
          <div class="flex ml-48 mt-2 w-full items-center">
            <div class="flex flex-col mr-5">
              <span class="text-3xl font-bold">{{ userInfo.m_name }}</span>
              <p class="text-xl">{{ userInfo.m_handle }}</p>
            </div>
            <div class="user-stats"></div>
          </div>
        </div>
        <div class="h-full">
          <nav class="flex" role="tablist">
            <div
              v-for="(tab, index) in tabs"
              :key="index"
              class="text-base h-10 flex-1 flex flex-grow justify-center items-center border-b-4"
              :class="{
                'border-[#64CCC5]': currentTab === index,
                'hover:border-[#e0e0e0] border-transparent':
                  currentTab !== index,
              }"
              @click="changeTab(index, tab.id)"
              role="tab"
            >
              <span class="notiTabName align-middle">
                {{ tab.name }}
              </span>
            </div>
          </nav>

          <div class="mt-3" ref="scrollContainer">
            <div
              v-for="(tab, index) in tabs"
              :key="index"
              :id="tab.id"
              v-show="currentTab === index"
              role="tabpanel"
            >
              <div v-if="tab.id === 'mood'">
                <div>
                  <!--그래프-->
                  <div
                    class="flex flex-col justify-center items-center flex-grow"
                  >
                    <BarChart class="w-4/5 flex-grow" />
                  </div>

                  <div class="flex justify-between mx-6 mt-4">
                    <div
                      class="w-52 h-32 items-center justify-center flex flex-col bg-slate-300 rounded-3xl"
                    >
                      <div class="flex w-full justify-center items-center">
                        <div class="font-extrabold text-4xl">
                          {{ consecPosts }}일째
                        </div>
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
                        <div class="font-extrabold text-4xl">
                          {{ cntPosts }}개
                        </div>
                      </div>
                      <div class="text-lg font-bold">Mood 기록</div>
                    </div>
                    <div
                      class="w-52 h-32 items-center justify-center flex flex-col bg-slate-300 rounded-3xl"
                    >
                      <div
                        class="flex w-full text-6xl m-3 justify-center items-center"
                      >
                        <div>{{ mainSenti }}</div>
                      </div>
                      <div class="text-lg font-bold">이달의 감정</div>
                    </div>
                  </div>
                </div>
              </div>
              <div if="tab.id === 'member'"></div>
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
  name: "MypageMain",
  components: {
    BarChart,
  },
  data() {
    return {
      userInfo: {},
      MybIdList: [],
      MylastRowNum: 0,
      LikebIdList: [],
      LikedlastRowNum: 0,
      ByDateList: [],
      isLoading: false,
      consecPosts: 0, // 연속 기록 수
      cntPosts: 0, // 이번 달 기록한 감정 수
      mainSenti: null, // 이번 달 주요 감정
      board: {
        b_id: null,
        m_id: null,
        b_content: "",
        b_sentiment: "",
        regdate: "",
        mediaList: [],
      },
      emotionMap: {
        happy: "😆",
        angry: "😡",
        anxiety: "😬",
        hurt: "🤕",
        neutral: "😐",
        sad: "😢",
        surprise: "😨",
      },

      weekend: [
        "Sunday",
        "Monday",
        "Tuesday",
        "Wednesday",
        "Thursday",
        "Friday",
        "Saturday",
      ],
      dates: [],
      currentYear: 0,
      currentMonth: 0,
      year: 0,
      month: 0,
      lastMonthDates: 0,
      nextMonthDates: 0,
      modalData: {
        mday: 0,
        mmonth: 0,
      },
      currentTab: 0,
      tabs: [
        { name: "전체 Mood", id: "mood" },
        { name: "가장많이 기록한자", id: "member" },
      ],
      isBoardToggleDropdownOpen: false,
      isModalOpen: false,
      selectedDate: null,
    };
  },

  methods: {
    // 유저 정보
    getMemberInfo() {
      apiClient
        .get(`/member/myInfo`)
        .then((info) => {
          console.log("유저 정보를 불러옵니다");
          info.data.m_handle = "@" + info.data.m_handle;
          this.userInfo = info.data;
        })
        .catch((err) => {
          console.log(err, "유저 정보 못불러옴");
        });
    },
    getPrfileImgUrl() {
      return `http://localhost:8083/${this.userInfo.m_img_path}${this.userInfo.m_img_name}`;
    },

    changeTab(index, tabId) {
      this.currentTab = index;
      // 탭이 변경되면
      if (tabId === "post") {
        console.log(`현재 탭의 id: ${tabId}`);
        if (this.MybIdList.length === 0) {
          this.getMyBoardList();
        }
        this.handlePostScroll();
      } else if (tabId === "like") {
        console.log(`현재 탭의 id: ${tabId}`);
        if (this.LikebIdList.length === 0) {
          this.getLikedBoardList();
        }
        this.handleLikedScroll();
      } else if (tabId === "mood") {
        this.getConsecPost();
        this.getThisMonthPosts();
      } else {
        console.log(`현재 탭의 id: ${tabId}`);
      }
    },

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

  mounted() {
    // console.log("안녕 전");
    // console.log("현재 탭 번호? : " + this.currentTab);
    //document.addEventListener("scroll", this.handleScroll, true);
    this.$watch("currentTab", () => {
      if (this.currentTab === 1) {
        // 'post' 탭의 인덱스가 1이라 가정합니다. 만약 다르다면 해당 인덱스로 변경하세요.
        this.$refs.postScrollContainer.addEventListener(
          "scroll",
          this.handlePostScroll
        );
      }
    });
  },

  beforeUnmount() {
    //window.removeEventListener("scroll", this.handleScroll);
    if (this.currentTab === 1) {
      this.$refs.postScrollContainer.removeEventListener(
        "scroll",
        this.handlePostScroll()
      );
    }
  },

  created() {
    // 데이터에 접근이 가능한 첫 번째 라이프 사이클
    this.getMemberInfo();
    this.getPrfileImgUrl();
    this.getConsecPost();
    this.getThisMonthPosts();
    this.getMainSentiment();
  },
};
</script>

<style scoped="scoped"></style>
