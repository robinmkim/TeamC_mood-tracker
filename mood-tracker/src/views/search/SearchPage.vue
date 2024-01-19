<template>
  <div class="flex">
    <div class="w-5/6" ref="postScrollContainer">
      <div class="flex search-container p-4 mt-5">
        <input
          v-model="searchQuery"
          @input="onSearchInputChange($event)"
          @keyup.space="onSearchInputChange"
          type="text"
          placeholder="검색"
          class="w-full border-b text-sm focus:outline-none text-bg-zinc-800 bg-transparent"
        />
        <button
          @click="fetchData('userSearch')"
          class="text-black rounded-full hover:bg-zinc-300 focus:outline-none"
        >
          <svg
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 24 24"
            stroke-width="2.5"
            stroke="currentColor"
            class="w-6 h-6"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              d="m21 21-5.197-5.197m0 0A7.5 7.5 0 1 0 5.196 5.196a7.5 7.5 0 0 0 10.607 10.607Z"
            />
          </svg>
        </button>
      </div>
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
                <div v-if="tab.id == 'userSearch'">
                  <!-- 인물 들어갈 자리  -->
                  <div
                    class="notiItem followNoti flex justify-start p-4 mt-[-12px] border-b border-gray-200"
                  >
                    <div
                      class="notiItemImg z-0 h-14 w-14 overflow-hidden relative"
                    >
                      <img
                        class="object-contain rounded-full"
                        :src="bean.m_img_name"
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
                <div v-else-if="tab.id === 'postSearch' && index == 1">
                  <post-detail
                    v-for="bId in searchResults"
                    :key="bId"
                    :b_id="bId"
                  />
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
import PostDetail from "@/components/post/PostDetail.vue";
// import axios from "axios";

export default {
  data() {
    return {
      currentTab: 0,
      tabs: [
        { name: "유저 검색", id: "userSearch" },
        { name: "게시물 검색", id: "postSearch" },
      ],
      showList: null,
      searchQuery: "", // 사용자의 검색어를 담을 변수
      searchResults: [], // API로부터 받아온 결과를 담을 변수
      MylastRowNum: 0,
    };
  },
  methods: {
    // tab이 위치를 알려줌
    onSearchInputChange() {
      // 검색어 입력이 변경될 때마다 수행할 로직 추가
      console.log("검색어 입력 변경:", this.searchQuery);

      // 여기서 필요한 경우 API 호출을 수행하도록 구현
      if (this.currentTab === 0) {
        this.fetchData("userSearch");
      } else {
        this.fetchData("userSearch");
        this.changeTab(1, "postSearch");
      }
    },
    changeTab(index, tabId) {
      this.currentTab = index;
      console.log(`현재 탭의 id: ${tabId}`);
      //tab에 따라서 검색 결과 보여줌
      this.fetchData(tabId);
    },

    fetchData(tabId) {
      // 검색어가 비어있을 때는 API 호출을 하지 않음
      if (this.searchQuery.trim() === "") {
        this.searchResults = [];
        return;
      }

      if (tabId === "userSearch") {
        console.log("유저 이름 조회");
        apiClient
          .get(`/member/search?memberName=${this.searchQuery}`)
          .then((res) => {
            this.searchResults = res.data;
          })
          .catch((err) => {
            console.error("err : ", err);
          });
      } else if (tabId === "postSearch") {
        console.log("게시물");
        apiClient
          .get(`/mypage/search?boardContent=${this.searchQuery}`)
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
      this.changeTab(1, "postSearch");
    },
  },
  name: "NotiPage",
  components: {
    PostDetail,
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
