<template>
  <div class="h-full w-48">
    <!-- 슬라이드 바 -->
    <div class="relative flex flex-col">
      <div
        :class="{
          'translate-x-0': showSidebar,
          '-translate-x-full': !showSidebar,
        }"
        class="real-sidebar rounded-br-lg rounded-tr-lg bg-[#67d1e9] text-white transition-transform duration-300 z-20 fixed top-0 left-0 w-[400px] h-full"
      >
        <!-- 검색 창 -->
        <div class="flex search-container p-4 mt-5">
          <input
            type="text"
            placeholder="검색"
            class="w-full border-b border-white text-sm focus:outline-none text-black bg-transparent"
          />
          <button
            @click="toggleSidebar"
            class="text-white rounded-full hover:bg-blue-600 focus:outline-none"
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
        <div class="border-b border-gray-500 h-3 w-full"></div>
        <div class="relative p-1 gap-4">
          <!-- 첫 번째 div (최근 검색 항목) -->
          <div>최근 검색 항목</div>
          <!-- 두 번째 div (모두 지우기) -->
        </div>

        <div class="flex relative p-1 mt-2">
          <div class="flex-grow"></div>
          <div class="text-[#f74141] px-1 text-sm">모두 지우기</div>
        </div>

        <div class="mt-3">
          <div class="flex hover:bg-blue-600 rounded-lg">
            <div class="h-10 w-10 mb-1 ml-2 overflow-hidden relative">
              <img
                class="postDetailUserImg object-contain rounded-full"
                src="..\assets\notiProfileImage01.jpg"
                alt="user icon"
              />
            </div>
            <div class="flex flex-row items-center w-2/3">
              <div class="text-sm ml-3">UserName</div>
            </div>
            <div class="flex items-center ml-auto cursor-pointer">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                stroke-width="1.5"
                stroke="currentColor"
                class="w-4 h-4 mr-3"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  d="M6 18 18 6M6 6l12 12"
                />
              </svg>
            </div>
          </div>

          <div class="flex hover:bg-blue-600 rounded-lg">
            <div class="h-10 w-10 mb-1 ml-2 overflow-hidden relative">
              <img
                class="postDetailUserImg object-contain rounded-full"
                src="..\assets\notiProfileImage01.jpg"
                alt="user icon"
              />
            </div>
            <div class="flex flex-row items-center w-2/3">
              <div class="text-sm ml-3">우진</div>
            </div>
            <div class="flex items-center ml-auto cursor-pointer">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                stroke-width="1.5"
                stroke="currentColor"
                class="w-4 h-4 mr-3"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  d="M6 18 18 6M6 6l12 12"
                />
              </svg>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- relative 추가하여 프로필과 메뉴를 묶어준다 -->
    <!-- 사용자 정보 및 이미지 시작 -->
    <div v-if="isMyPage">
      <div class="rounded-lg shadow-lg mx-2 mt-2 bg-[#D9F3C1] p-4">
        <!-- 사용자 정보 좌우에 여백 추가 -->
        <div class="my-4">
          <div
            class="flex w-32 h-32 rounded-full overflow-hidden border-4 border-white mx-auto bg-slate-200 items-center justify-center"
          >
            <img
              :src="getPrfileImgUrl()"
              height="100"
              width="100"
              alt="profile img"
              style="aspect-ratio: 100/100; object-fit: cover"
            />
          </div>
        </div>
        <div class="text-center mt-2 mb-6">
          <h2 class="text-lg font-bold">{{ userInfo.m_name }}</h2>
          <p class="text-slate-500">{{ userInfo.m_handle }}</p>
        </div>
      </div>
    </div>
    <!-- 사용자 정보 및 이미지 끝 -->

    <!-- 메뉴 시작 -->
    <nav class="p-4 text-left">
      <router-link to="/" class="text-lg">
        <div class="flex px-2 py-1">
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
              d="m2.25 12 8.954-8.955c.44-.439 1.152-.439 1.591 0L21.75 12M4.5 9.75v10.125c0 .621.504 1.125 1.125 1.125H9.75v-4.875c0-.621.504-1.125 1.125-1.125h2.25c.621 0 1.125.504 1.125 1.125V21h4.125c.621 0 1.125-.504 1.125-1.125V9.75M8.25 21h8.25"
            />
          </svg>
          <span class="ml-2">홈</span>
        </div>
      </router-link>
      <router-link to="/postwrite" class="text-lg">
        <div class="flex px-2 py-1">
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
              d="m16.862 4.487 1.687-1.688a1.875 1.875 0 1 1 2.652 2.652L10.582 16.07a4.5 4.5 0 0 1-1.897 1.13L6 18l.8-2.685a4.5 4.5 0 0 1 1.13-1.897l8.932-8.931Zm0 0L19.5 7.125M18 14v4.75A2.25 2.25 0 0 1 15.75 21H5.25A2.25 2.25 0 0 1 3 18.75V8.25A2.25 2.25 0 0 1 5.25 6H10"
            />
          </svg>

          <span class="ml-2">게시물 작성</span>
        </div>
      </router-link>
      <router-link to="/faceanalyze" class="text-lg">
        <div class="flex px-2 py-1">
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
              d="M15.182 15.182a4.5 4.5 0 0 1-6.364 0M21 12a9 9 0 1 1-18 0 9 9 0 0 1 18 0ZM9.75 9.75c0 .414-.168.75-.375.75S9 10.164 9 9.75 9.168 9 9.375 9s.375.336.375.75Zm-.375 0h.008v.015h-.008V9.75Zm5.625 0c0 .414-.168.75-.375.75s-.375-.336-.375-.75.168-.75.375-.75.375.336.375.75Zm-.375 0h.008v.015h-.008V9.75Z"
            />
          </svg>
          <span class="ml-2">감정 분석</span>
        </div>
      </router-link>

      <router-link to="/musicrecommand" class="text-lg">
        <div class="flex px-2 py-1">
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
              d="m9 9 10.5-3m0 6.553v3.75a2.25 2.25 0 0 1-1.632 2.163l-1.32.377a1.803 1.803 0 1 1-.99-3.467l2.31-.66a2.25 2.25 0 0 0 1.632-2.163Zm0 0V2.25L9 5.25v10.303m0 0v3.75a2.25 2.25 0 0 1-1.632 2.163l-1.32.377a1.803 1.803 0 0 1-.99-3.467l2.31-.66A2.25 2.25 0 0 0 9 15.553Z"
            />
          </svg>
          <span class="ml-2">음악 추천</span>
        </div>
      </router-link>
      <router-link to="/chat" class="text-lg">
        <div class="flex px-2 py-1">
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
              d="M2.25 12.76c0 1.6 1.123 2.994 2.707 3.227 1.068.157 2.148.279 3.238.364.466.037.893.281 1.153.671L12 21l2.652-3.978c.26-.39.687-.634 1.153-.67 1.09-.086 2.17-.208 3.238-.365 1.584-.233 2.707-1.626 2.707-3.228V6.741c0-1.602-1.123-2.995-2.707-3.228A48.394 48.394 0 0 0 12 3c-2.392 0-4.744.175-7.043.513C3.373 3.746 2.25 5.14 2.25 6.741v6.018Z"
            />
          </svg>
          <span class="ml-2">채팅</span>
        </div>
      </router-link>
      <div class="text-lg">
        <div
          class="searchbaricon px-2 flex cursor-pointer hover:bg-[#E0F2F6] rounded-lg"
          @click="toggleSidebar"
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
          <button class="ml-2">검색</button>
        </div>
      </div>
      <router-link to="/admin" class="text-lg">
        <div class="flex px-2 py-1">
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
              d="M9.879 7.519c1.171-1.025 3.071-1.025 4.242 0 1.172 1.025 1.172 2.687 0 3.712-.203.179-.43.326-.67.442-.745.361-1.45.999-1.45 1.827v.75M21 12a9 9 0 1 1-18 0 9 9 0 0 1 18 0Zm-9 5.25h.008v.008H12v-.008Z"
            />
          </svg>
          <span class="ml-2">문의</span>
        </div>
      </router-link>
    </nav>
    <!-- 메뉴 끝 -->
  </div>
</template>

<script>
import apiClient from "@/utils/apiClient";
import { jwtDecode } from "jwt-decode";

export default {
  name: "SideBar",
  data() {
    return {
      showSidebar: true,
      memberId: "",
      userInfo: {},
    };
  },
  methods: {
    // 유저 정보
    getMemberInfo() {
      const token = localStorage.getItem("jwtToken");
      const decoded = jwtDecode(token);
      this.memberId = decoded.m_id;
      apiClient
        .get(`/member/info/${this.memberId}`)
        .then((info) => {
          console.log("유저 정보를 불러옵니다");
          this.userInfo = info.data;
        })
        .catch((err) => {
          console.log(err, "유저 정보 못불러옴");
        });
    },
    getPrfileImgUrl() {
      return `http://localhost:8083/${this.userInfo.m_img_path}${this.userInfo.m_img_name}`;
    },
    toggleSidebar() {
      this.showSidebar = !this.showSidebar;
      this.outsideShowSidebar = false;
    },
    toggleSidebarOutside(event) {
      // 검색창 외 다른 것을 클릭하면 사이트 바가 사라짐
      if (
        !event.target.closest(".real-sidebar") &&
        !this.showSidebar &&
        !event.target.closest(".searchbaricon")
      ) {
        this.showSidebar = !this.showSidebar;
      }
    },
  },
  computed: {
    isMyPage() {
      // 현재 경로가 / 이거나 /mypage로 시작할 때 true
      const mypage = this.$route.path.startsWith("/mypage") || /^\/(\d+)$/.test(this.$route.path) || this.$route.path === "/";
      return !mypage;
    },
  },
  created() {
    this.getMemberInfo();
    this.getPrfileImgUrl();
  },
  mounted() {
    // Add a global click event listener to close the search bar when clicking outside
    document.addEventListener("click", this.toggleSidebarOutside);
  },
};
</script>

<style>
/* 슬라이드 바 스타일링 */
.sidebar {
  width: 30%;
  height: 100%;
}

/* 슬라이드 바의 표시 여부에 따라 이동 효과 적용 */
.translate-x-0 {
  transform: translateX(-100%);
}

.-translate-x-full {
  transform: translateX(0%);
}
</style>
