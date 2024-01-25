<template>
  <div class="flex">
    <div class="w-full" ref="postScrollContainer">
      <div class="">
        <div class="h-full">
          <nav class="flex" role="tablist">
            <!-- 여기인가???? -->
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
              <!-- for문 시작 -->
              <div
                v-for="(bean, index) in searchResults"
                :key="index"
                :id="bean.n_id"
              >
                <div v-if="tab.id == 'follower'">
                  <!-- 인물 들어갈 자리  -->
                  <div
                    class="notiItem followNoti flex justify-start p-4 mt-[-12px] border-b border-gray-200"
                  >
                    <div
                      class="notiItemImg z-0 h-14 w-14 overflow-hidden relative"
                    >
                      <img
                        class="object-contain rounded-full"
                        :src="`http://localhost:8083/images/${bean.m_img_name}`"
                        alt="프로필 이미지"
                      />
                    </div>
                    <div class="notiItemContent flex-1 flex h-14">
                      <div
                        class="notiItemContent_ justify-center flex flex-col w-3/4 text-left pl-3"
                      >
                        <span class="notiItemContentTime font-bold text-lg">{{
                          bean.m_name
                        }}</span>
                        <div
                          class="notiItemContentMain w-auto flex items-center cursor-pointer"
                        >
                          <span class="notiUserName text-sm text-slate-400">
                            {{ bean.m_email }}
                          </span>
                        </div>
                      </div>

                      <div
                        class="notiItemContentButton flex w-1/4 justify-center items-center"
                      ></div>
                    </div>
                  </div>
                  <!-- 인물 들어갈 자리 2 -->
                </div>

                <!--검색 게시글 목록-->
                <div v-else-if="tab.id === 'following'">
                  <div
                    class="notiItem followNoti flex justify-start p-4 mt-[-12px] border-b border-gray-200"
                  >
                    <div
                      class="notiItemImg z-0 h-14 w-14 overflow-hidden relative"
                    >
                      <img
                        class="object-contain rounded-full"
                        :src="`http://localhost:8083/images/${bean.m_img_name}`"
                        alt="프로필 이미지"
                      />
                    </div>
                    <div class="notiItemContent flex-1 flex h-14">
                      <div
                        class="notiItemContent_ justify-center flex flex-col w-3/4 text-left pl-3"
                      >
                        <span class="notiItemContentTime font-bold text-lg">{{
                          bean.m_name
                        }}</span>
                        <div
                          class="notiItemContentMain w-auto flex items-center cursor-pointer"
                        >
                          <span class="notiUserName text-sm text-slate-400">
                            {{ bean.m_email }}
                          </span>
                        </div>
                      </div>

                      <div
                        class="notiItemContentButton flex w-1/4 justify-center items-center"
                      ></div>
                    </div>
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
import apiClient from "./../../utils/apiClient";
import { jwtDecode } from "jwt-decode";
// import axios from "axios";

export default {
  data() {
    return {
      currentTab: 0,
      tabs: [
        { name: "팔로워", id: "follower" },
        { name: "팔로잉", id: "following" },
      ],
      showList: null,
      searchQuery: this.$route.query.searchQuery || "", // 사용자의 검색어를 담을 변수
      searchResults: [], // API로부터 받아온 결과를 담을 변수
      MylastRowNum: 0,
      m_id: null,
    };
  },
  methods: {
    changeTab(index, tabId) {
      this.currentTab = index;
      console.log(`현재 탭의 id: ${tabId}`);
      //tab에 따라서 검색 결과 보여줌
      this.fetchData(tabId);
    },

    fetchData(tabId) {
      const token = localStorage.getItem("jwtToken");
      const decoded = jwtDecode(token);
      this.m_id = decoded.m_id;

      if (tabId === "follower") {
        console.log("유저 이름 조회");
        apiClient
          .get(`/follow/follower/${this.m_id}`)
          .then((res) => {
            this.searchResults = res.data;
            console.log("데이터 확인", this.searchResults);
          })
          .catch((err) => {
            console.error("err : ", err);
          });
      } else if (tabId === "following") {
        console.log("게시물");
        apiClient
          .get(`/follow/followed/${this.m_id}`)
          .then((res) => {
            this.searchResults = res.data;
            console.log("searchResults : " + this.searchResults);
          })
          .catch((err) => {
            console.error("err : ", err);
          });
      }
    },
    clickTabAfterSearch() {
      this.changeTab(1, "following");
    },
  },
  name: "NotiPage",
  components: {},
  mounted() {
    this.searchQuery = this.$route.query.searchQuery || "";

    // 현재 탭 설정
    this.currentTab = this.$route.query.searchType === "following" ? 1 : 0;

    // 초기 데이터 로딩
    this.fetchData(this.$route.query.searchType);
  },
};
</script>
<style scoped>
.selTab-enter-active {
  animation: bounce-in 0.5s;
}
/* .selTab-leave-active {
  animation: bounce-in 0.5s reverse;
} */
@keyframes bounce-in {
  0% {
    transform: scale(0);
    width: 100%;
  }
  50% {
    transform: scale(1.25);
    width: 90%;
  }
  100% {
    transform: scale(1);
    width: 100%;
  }
}
.deleteButton {
  position: relative;
  left: 16px;
  opacity: 0;
}
.deleteButton:hover {
  opacity: 1;
}
</style>
