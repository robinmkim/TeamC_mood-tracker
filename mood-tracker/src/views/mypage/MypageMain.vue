<template>
  <div class="flex">
    <div class="w-1/5 border-r min-h-screen">
      <side-bar />
    </div>
    <div class="w-5/6">
      <div class="">
        <div class="w-full h-40 bg-slate-200"></div>
        <div class="flex h-28">
          <div class="relative flex">
            <div
              class="border-4 absolute top-[-40px] left-8 flex border-white bg-gray-300 w-32 h-32 rounded-full overflow-hidden"
            >
              <img :src="getPrfileImgUrl()" alt="profile_img" />
            </div>
          </div>
          <div class="flex ml-48 mt-2 w-full items-center">
            <div class="flex flex-col mr-5">
              <span class="text-3xl font-bold">{{ userInfo.m_name }}</span>
              <p class="text-xl">{{ userInfo.m_handle }}</p>
            </div>
            <div class="user-stats">
              <span class="text-lg">팔로워 12</span> |
              <span class="text-lg">팔로잉 26</span>
            </div>
          </div>
        </div>
        <PostModal :isOpen="isModalOpen" @close="isModalOpen = false" />
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
                  <BarChart />
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
                        <div class="font-extrabold text-4xl">10일</div>
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
                        <div class="font-extrabold text-4xl">24개</div>
                      </div>
                      <div class="text-lg font-bold">Mood 기록</div>
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
                              fill-rule="evenodd"
                              d="M12 2.25c-5.385 0-9.75 4.365-9.75 9.75s4.365 9.75 9.75 9.75 9.75-4.365 9.75-9.75S17.385 2.25 12 2.25Zm-2.625 6c-.54 0-.828.419-.936.634a1.96 1.96 0 0 0-.189.866c0 .298.059.605.189.866.108.215.395.634.936.634.54 0 .828-.419.936-.634.13-.26.189-.568.189-.866 0-.298-.059-.605-.189-.866-.108-.215-.395-.634-.936-.634Zm4.314.634c.108-.215.395-.634.936-.634.54 0 .828.419.936.634.13.26.189.568.189.866 0 .298-.059.605-.189.866-.108.215-.395.634-.936.634-.54 0-.828-.419-.936-.634a1.96 1.96 0 0 1-.189-.866c0-.298.059-.605.189-.866Zm2.023 6.828a.75.75 0 1 0-1.06-1.06 3.75 3.75 0 0 1-5.304 0 .75.75 0 0 0-1.06 1.06 5.25 5.25 0 0 0 7.424 0Z"
                              clip-rule="evenodd"
                            />
                          </svg>
                        </div>
                      </div>
                      <div class="text-lg font-bold">이번 달은 Happy :)</div>
                    </div>
                  </div>
                </div>
              </div>
              <!--내 게시글 목록-->
              <div v-else-if="tab.id === 'post'">
                <div
                  class="flex-1 border-x overflow-auto"
                  ref="postScrollContainer"
                  @scroll="handlePostScroll"
                >
                  <post-detail
                    v-for="bId in MybIdList"
                    :key="bId"
                    :b_id="bId"
                  />
                  <div v-if="isLoading" class="loading-spinner">
                    <!-- 로딩 스피너 -->
                  </div>
                </div>
              </div>
              <!--달력-->
              <div v-else-if="tab.id === 'calander'" class="flex-grow">
                <div>
                  <div>
                    <button @click="prevMonth" class="p-2">◀</button>
                    <span class="text-lg">{{ currentMonth }}월</span>
                    <button @click="nextMonth" class="p-2">▶</button>
                  </div>
                  <table class="w-full">
                    <thead class="w-full">
                      <th
                        v-for="day in days"
                        :key="day"
                        class="p-2 border-r w-auto h-10 xl:text-sm text-xs"
                      >
                        <span
                          class="xl:block lg:block md:block sm:block hidden"
                          >{{ day }}</span
                        >
                        <span
                          class="xl:hidden lg:hidden md:hidden sm:hidden block"
                          >{{ day.slice(0, 3) }}</span
                        >
                      </th>
                    </thead>
                    <tbody class="w-full">
                      <tr
                        tr
                        v-for="(date, idx) in dates"
                        :key="idx"
                        class="text-center h-12"
                      >
                        <td
                          v-for="(day, secondIdx) in date"
                          :key="secondIdx"
                          class="border p-1 h-24 w-96 transition cursor-pointer duration-500 ease hover:bg-gray-300"
                        >
                          <div
                            @click="isModalOpen = true"
                            class="flex flex-col h-24 mx-auto w-full overflow-hidden bg-[#AAD7D9]"
                          >
                            <div class="top h-5 w-full">
                              <span class="text-gray-500">{{ day }}</span>
                            </div>
                            <div
                              class="bottom flex-grow h-30 py-2 w-full cursor-pointer item-center justify-center"
                            >
                              <!--상위 두개 감정 보여주기-->
                              <div
                                class="mt-2 flex justify-center items-center gap-2"
                              >
                                <div class="m-2 w-[20px]">
                                  <img
                                    src="https://pbs.twimg.com/media/GDeOnXZakAAbkf2?format=png&name=120x120"
                                    alt="angry"
                                  />
                                  <!-- <span class="px-2 opacity-75">7</span> -->
                                </div>
                                <div class="m-2 w-[20px]">
                                  <img
                                    src="https://pbs.twimg.com/media/GDeOl2pasAApYny?format=png&name=120x120"
                                    alt="happy"
                                  />
                                  <!-- <span class="px-2 opacity-75">5</span> -->
                                </div>
                              </div>
                            </div>
                          </div>
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </div>
              </div>
              <!--좋아요 목록-->
              <div v-else-if="tab.id === 'like'" ref="likedScrollContainer">
                <div
                  class="flex-1 border-x overflow-auto"
                  @scroll="handleLikedScroll"
                >
                  <post-detail
                    v-for="bId in LikebIdList"
                    :key="bId"
                    :b_id="bId"
                  />
                  <div v-if="isLoading" class="loading-spinner">
                    <!-- 로딩 스피너 -->
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import SideBar from "@/components/SideBar.vue";
import PostDetail from "@/components/post/PostDetail.vue";
import BarChart from "@/components/BarChart.vue";
import PostModal from "@/components/PostModal.vue";
import apiClient from "@/utils/apiClient";

export default {
  name: "MypageMain",
  components: {
    SideBar,
    PostDetail,
    BarChart,
    PostModal,
  },
  data() {
    return {
      userInfo: {},
      MybIdList: [],
      MylastRowNum: 0,
      LikebIdList: [],
      LikedlastRowNum: 0,
      isLoading: false,
      mid: 1, // 회원번호 넘겨주기 가능해지면 삭제할 것
      apiEndpoint: "",

      days: [
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
      currentTab: 0,
      tabs: [
        { name: "나의 Mood", id: "mood" },
        { name: "게시물", id: "post" },
        { name: "Mood 달력", id: "calander" },
        { name: "좋아요", id: "like" },
      ],
      isModalOpen: false,
    };
  },

  methods: {
    // 유저 정보
    getMemberInfo() {
      apiClient
        .get(`/member/userInfo/${this.mid}`)
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

    // 내 게시글 불러오기
    getMyBoardList() {
      // this.getBIdList();
      if (this.isLoading) {
        console.log("post로딩중");
        return; // 이미 로딩 중이면 요청을 하지 않음
      }
      this.isLoading = true;
      apiClient
        .get(`/mypage/list?lastRowNum=${this.MylastRowNum}&mid=${this.mid}`)
        .then((res) => {
          console.log("my post 넘어옴");
          this.MylastRowNum += res.data.length;
          this.MybIdList = [...this.MybIdList, ...res.data];
          this.handlePostScroll();
        })
        .catch((err) => {
          console.log(err, "post 뭔가 안됨");
        })
        .finally(() => {
          this.isLoading = false; // 로딩 완료
        });
    },

    // 내가 좋아요를 누른 게시글 불러오기
    getLikedBoardList() {
      if (this.isLoading) {
        console.log("like로딩중");
        return; // 이미 로딩 중이면 요청을 하지 않음
      }
      this.isLoading = true;
      apiClient
        .get(
          `/mypage/likelist?lastRowNum=${this.LikedlastRowNum}&mid=${this.mid}`
        )
        .then((res) => {
          console.log("liked post 넘어옴");
          this.LikedlastRowNum += res.data.length;
          this.LikebIdList = [...this.LikebIdList, ...res.data];
          this.handleLikedScroll();
        })
        .catch((err) => {
          console.log(err, "like 뭔가 안됨");
        })
        .finally(() => {
          this.isLoading = false; // 로딩 완료
        });
    },

    handlePostScroll() {
      console.log("Post Scroll event triggered");
      const container = this.$refs.postScrollContainer;
      if (
        !this.isLoading &&
        container.scrollHeight - container.scrollTop <=
          container.clientHeight + 50
      ) {
        console.log("post scroll 후 데이터 로딩");
        this.getMyBoardList();
      }
    },
    handleLikedScroll() {
      console.log("Like Scroll event triggered");
      const container = this.$refs.likedScrollContainer;
      if (
        !this.isLoading &&
        container.scrollHeight - container.scrollTop <=
          container.clientHeight + 50
      ) {
        console.log("like scroll 후 데이터 로딩");
        this.getLikedBoardList();
      }
    },
    handleScroll() {
      this.currentTab = 0;
      if (this.currentTab === 1) {
        this.changeTab(1, "post");
        console.log("~~~post scroll triggered");
        const container = this.$refs.postScrollContainer;
        if (
          !this.isLoading &&
          container.scrollHeight - container.scrollTop <=
            container.clientHeight + 50
        ) {
          this.getMyBoardList();
        }
      } else if (this.currentTab === 3) {
        this.changeTab(3, "like");
        console.log("~~~liked scroll triggered");
        const container = this.$refs.likedScrollContainer;
        if (
          !this.isLoading &&
          container.scrollHeight - container.scrollTop <=
            container.clientHeight + 50
        ) {
          this.getLikedBoardList();
        }
      }
    },
    // addPostScrollEventHandler() {
    //   this.$el.postScrollContainer.addEventListener(
    //     "scroll",
    //     this.handlePostScroll
    //   );
    // },

    // addLikedScrollEventHandler() {
    //   this.$refs.likedScrollContainer.addEventListener(
    //     "scroll",
    //     this.handleLikedScroll
    //   );
    // },

    // removeHandleScroll() {
    //   this.$refs.likedScrollContainer.removeEventListener(
    //     "scroll",
    //     this.handleLikedScroll
    //   );
    //   this.$refs.postScrollContainer.removeEventListener(
    //     "scroll",
    //     this.handlePostScroll
    //   );
    // },

    changeTab(index, tabId) {
      this.currentTab = index;
      console.log(`현재 탭의 id: ${tabId}`);
      // 탭이 변경되면
      if (tabId === "post") {
        console.log("post");
        if (this.MybIdList.length === 0) {
          this.getMyBoardList();
        }
        this.handlePostScroll();
        // this.addPostScrollEventHandler();
      } else if (tabId === "like") {
        console.log("like");
        if (this.LikebIdList.length === 0) {
          this.getLikedBoardList();
        }
        this.handleLikedScroll();
        // this.addLikedScrollEventHandler();
      } else {
        console.log("와앙");
        // this.removeHandleScroll();
      }
    },
    calendarData() {
      const [monthFirstDay, monthLastDate, lastMonthLastDate] =
        this.getFirstDayLastDate(this.year, this.month);
      this.dates = this.getMonthOfDays(
        monthFirstDay,
        monthLastDate,
        lastMonthLastDate
      );
    },
    getFirstDayLastDate(year, month) {
      const firstDay = new Date(year, month - 1, 1).getDay(); // 이번 달 시작 요일
      const lastDate = new Date(year, month, 0).getDate(); // 이번 달 마지막 날짜
      let lastYear = year;
      let lastMonth = month - 1;
      if (month === 1) {
        lastMonth = 12;
        lastYear -= 1;
      }
      const prevLastDate = new Date(lastYear, lastMonth, 0).getDate(); // 지난 달 마지막 날짜
      return [firstDay, lastDate, prevLastDate];
    },
    getMonthOfDays(monthFirstDay, monthLastDate, prevMonthLastDate) {
      let day = 1;
      let prevDay = prevMonthLastDate - monthFirstDay + 1;
      const dates = [];
      let weekOfDays = [];
      while (day <= monthLastDate) {
        if (day === 1) {
          // 1일이 어느 요일인지에 따라 테이블에 그리기 위한 지난 셀의 날짜들을 구할 필요가 있다.
          for (let j = 0; j < monthFirstDay; j += 1) {
            weekOfDays.push(prevDay);
            prevDay += 1;
          }
        }
        weekOfDays.push(day);
        if (weekOfDays.length === 7) {
          // 일주일 채우면
          dates.push(weekOfDays);
          weekOfDays = []; // 초기화
        }
        day += 1;
      }
      const len = weekOfDays.length;
      if (len > 0 && len < 7) {
        for (let k = 1; k <= 7 - len; k += 1) {
          weekOfDays.push(k);
        }
      }
      if (weekOfDays.length > 0) dates.push(weekOfDays); // 남은 날짜 추가
      return dates;
    },
    prevMonth() {
      if (this.month === 1) {
        this.year -= 1;
        this.month = 12;
      } else {
        this.month -= 1;
      }
      this.currentMonth = this.month; // 수정된 부분
      this.calendarData();
    },
    nextMonth() {
      if (this.month === 12) {
        this.year += 1;
        this.month = 1;
      } else {
        this.month += 1;
      }
      this.currentMonth = this.month; // 수정된 부분
      this.calendarData();
    },
  },

  created() {
    // 데이터에 접근이 가능한 첫 번째 라이프 사이클
    const date = new Date();
    this.year = date.getFullYear();
    this.month = date.getMonth() + 1;
    this.currentMonth = this.month;
    this.calendarData();
    this.getMemberInfo();
    this.getPrfileImgUrl();
    // this.getLikedBoardList();
    // this.getMyBoardList();
  },
  mounted() {
    // document.addEventListener("scroll", this.handleScroll, true);
  },
  beforeUnmount() {
    // window.removeEventListener("scroll", this.handleScroll);
  },
};
</script>

<style scoped="scoped"></style>
