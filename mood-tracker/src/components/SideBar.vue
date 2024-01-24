<template>
  <div class="h-full w-48">
    <!-- 슬라이드 바 -->
    <div class="relative flex flex-col">
      <div
        :class="{
          'translate-x-0': showSidebar,
          '-translate-x-full': !showSidebar,
        }"
        class="real-sidebar rounded-br-lg rounded-tr-lg bg-gradient-to-b from-[#4bc9c0] to-white text-white transition-transform duration-300 z-20 fixed top-0 left-0 w-[400px] h-full overflow-y-auto shadow-2xl"
      >
        <div class="h-10"></div>
        <!-- 검색 창 -->
        <div class="flex search-container p-4 mt-5">
          <input
            v-model="searchQuery"
            @input="onSearchInputChange($event)"
            @keyup.space="onSearchInputChange"
            type="text"
            placeholder="검색"
            class="w-full border-b border-white text-sm focus:outline-none text-white bg-transparent"
          />
          <button
            @click="fetchData()"
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

        <div v-if="searchResults.length >= 1">
          -------------------유저 이름--------------------
        </div>

        <!-- 유저의 정보가 검색되는 부분 -->
        <div class="mt-3">
          <div
            v-for="(bean, index) in searchResults.slice(0, 2)"
            :key="index"
            :id="bean.n_id"
          >
            <!-- 인물 들어갈 자리  -->
            <div
              class="notiItem followNoti flex justify-start p-4 mt-[-12px] border-b border-gray-200 hover:bg-[#dad9d9] focus:outline-none rounded-md"
            >
              <div class="notiItemImg z-0 h-14 w-14 overflow-hidden relative">
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
                      @{{ bean.m_handle }}
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

        <div
          v-if="searchResults.length > 2"
          @click="showAll = !showAll"
          style="cursor: pointer"
          class="mb-10 mt-5"
        >
          <router-link
            :to="{
              path: '/search',
              query: { searchQuery: searchQuery, searchType: 'userSearch' },
            }"
            class="text-lg text-stone-900"
            @click="toggleSidebar()"
            >모두보기</router-link
          >
        </div>

        <!-- 유저의 정보가 검색되는 부분 끝 -->
        <div v-if="searchResultsBoard.length >= 1">
          -------------------게시글 내용-------------------
        </div>

        <!-- 게시글이 나오는 페이지 -->
        <div
          v-for="(bean, index) in searchResultsBoard"
          :key="index"
          :id="bean.n_id"
        >
          <div v-if="index == 1">
            <PostList
              v-for="bId in searchResultsBoard.slice(0, 5)"
              :key="bId"
              :b_id="bId"
            />

            <div
              v-if="searchResultsBoard.length > 5"
              @click="showAll = !showAll"
              style="cursor: pointer"
              class="mb-10 mt-5"
            >
              <router-link
                :to="{
                  path: '/search',
                  query: { searchQuery: searchQuery, searchType: 'postSearch' },
                }"
                class="text-lg text-stone-900"
                @click="toggleSidebar()"
                >모두보기</router-link
              >
            </div>
          </div>
        </div>

        <!-- 게시글이 나오는 페이지 끝 -->
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
              alt="profile img"
              style="aspect-ratio: 100/100; object-fit: cover"
            />
          </div>
        </div>
        <router-link to="/">
          <div class="text-center mt-2 mb-6">
            <h2 class="text-lg font-bold">{{ userInfo.m_name }}</h2>
            <p class="text-slate-500">{{ userInfo.m_handle }}</p>
          </div>
        </router-link>
      </div>
    </div>
    <!-- 사용자 정보 및 이미지 끝 -->

    <!-- 메뉴 시작 -->
    <nav class="p-4 text-left">
      <router-link to="/timeline" class="text-lg">
        <div class="flex px-2 hover:bg-[#F67D73]/50 rounded-lg">
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

      <div class="text-lg">
        <div
          class="flex px-2 hover:bg-[#FFDDE4]/50 rounded-lg"
          @click="postWrite()"
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
              d="m16.862 4.487 1.687-1.688a1.875 1.875 0 1 1 2.652 2.652L10.582 16.07a4.5 4.5 0 0 1-1.897 1.13L6 18l.8-2.685a4.5 4.5 0 0 1 1.13-1.897l8.932-8.931Zm0 0L19.5 7.125M18 14v4.75A2.25 2.25 0 0 1 15.75 21H5.25A2.25 2.25 0 0 1 3 18.75V8.25A2.25 2.25 0 0 1 5.25 6H10"
            />
          </svg>
          <span class="ml-2">게시물 작성</span>
        </div>

        <!-- 게시물 모달 창 -->
        <div
          v-if="postMakeModal"
          class="fixed top-0 left-0 w-full h-full bg-black bg-opacity-50 flex items-center justify-center z-40"
          @click="closepostModal"
        >
          <!-- 모달 내용 -->
          <div
            class="bg-white rounded-lg max-w-screen-sm w-1/3 z-45"
            @click.stop
          >
            <span
              class="close absolute top-4 right-4 cursor-pointer"
              @click="postWriteModal()"
            ></span>
            <p class="border-b py-2">게시물을 작성하세요~</p>
            <!-- 게시물 작성 페이지 -->
            <div>
              <div class="h-full overflow-y-auto">
                <form @submit.prevent="submitForm">
                  <div class="flex flex-col">
                    <div
                      class="h-auto px-2 pt-2 border-b"
                      @drop.prevent="handleFileDrop"
                      @dragover.prevent
                    >
                      <div class="flex items-center border-b h-10">
                        <div class="w-9 h-9 rounded-full overflow-hidden">
                          <img
                            class="object-contain rounded-full"
                            :src="getUserImageUrl()"
                            alt="user icon"
                            style="aspect-ratio: 100/100; object-fit: cover"
                          />
                        </div>
                        <span class="ml-2">{{ memberHandle }}</span>
                        <img
                          class="cursor-pointer ml-2"
                          @click="isExpanded = !isExpanded"
                          :src="`http://localhost:8083/images/${selectedEmoji}.png`"
                          width="25"
                          height="25"
                        />
                        <div
                          v-if="isExpanded"
                          class="cursor-pointer flex flex-col my-1"
                        >
                          <div class="flex">
                            <img
                              v-for="(sentiment, index) in sentimentList"
                              :key="index"
                              :src="`http://localhost:8083/images/${sentiment}.png`"
                              alt="sentiment"
                              class="inline-block ml-2"
                              width="20"
                              height="20"
                              @click="selectEmoji(sentiment, index)"
                            />
                          </div>
                        </div>
                      </div>
                      <div class="border-b mb-1">
                        <textarea
                          class="bg-inherit w-full h-auto resize-none focus:outline-none"
                          placeholder="오늘의 기분을 알려주세요 :)"
                          v-model="text"
                          @input="adjustHeight"
                        ></textarea>
                      </div>
                      <div class="flex">
                        <div
                          v-for="(file, index) in files"
                          :key="index"
                          class="py-1"
                        >
                          <img :src="file.preview" class="w-24 border" />
                        </div>
                      </div>
                    </div>
                    <div class="flex justify-between px-3 py-2 border-b">
                      <input
                        type="file"
                        ref="fileInput"
                        multiple
                        style="display: none"
                        @change="handleFileChange"
                      />
                      <svg
                        @click="triggerFileInput"
                        xmlns="http://www.w3.org/2000/svg"
                        fill="none"
                        viewBox="0 0 24 24"
                        stroke-width="1.5"
                        stroke="currentColor"
                        class="w-6 h-6 cursor-pointer"
                      >
                        <path
                          stroke-linecap="round"
                          stroke-linejoin="round"
                          d="M2.25 15.75l5.159-5.159a2.25 2.25 0 013.182 0l5.159 5.159m-1.5-1.5l1.409-1.409a2.25 2.25 0 013.182 0l2.909 2.909m-18 3.75h16.5a1.5 1.5 0 001.5-1.5V6a1.5 1.5 0 00-1.5-1.5H3.75A1.5 1.5 0 002.25 6v12a1.5 1.5 0 001.5 1.5zm10.5-11.25h.008v.008h-.008V8.25zm.375 0a.375.375 0 11-.75 0 .375.375 0 01.75 0z"
                        />
                      </svg>

                      <input
                        class="bg-[#ffede6] w-16 h-8 rounded-md p-0.5"
                        type="submit"
                        value="입력"
                      />
                    </div>
                  </div>
                </form>
              </div>
            </div>
            <!-- 게시물 작성 -->
          </div>
        </div>
        <!-- 게시물 모달 창 끝 -->
      </div>

      <router-link to="/faceanalyze" class="text-lg">
        <div class="flex px-2 hover:bg-[#FFE778]/50 rounded-lg">
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
        <div class="flex px-2 hover:bg-[#D9F3C1]/50 rounded-lg">
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
        <div class="flex px-2 hover:bg-[#597F61]/50 rounded-lg">
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
          class="searchbaricon px-2 flex cursor-pointer hover:bg-[#A4BED3]/50 rounded-lg"
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
        <div class="flex px-2 hover:bg-[#C9BCE8]/50 rounded-lg">
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
      <div
        class="mt-6 text-md text-slate-400 flex px-2 cursor-pointer hover:text-[#64CCC5]"
        @click="openTab"
      >
        <span class="">about Songti ▶</span>
      </div>
    </nav>
    <!-- 메뉴 끝 -->
  </div>
</template>

<script>
import apiClient from "@/utils/apiClient";
import PostList from "@/views/post/components/PostList";
import { jwtDecode } from "jwt-decode";

export default {
  name: "SideBar",
  data() {
    return {
      showSidebar: true,
      memberId: "",
      userInfo: {},
      searchQuery: "", // 사용자의 검색어를 담을 변수
      searchResults: [], // API로부터 받아온 결과를 담을 변수
      searchResultsBoard: [],
      postMakeModal: false,

      // 게시물 작성에 필요한 data
      memberHandle: "",
      text: "",
      files: [],
      isExpanded: false,
      selectedEmoji: "happy", // 기본 이모지
      sentimentList: [
        "happy",
        "angry",
        "anxiety",
        "hurt",
        "neutral",
        "sad",
        "surprise",
      ],
      emotion: "happy",
      user: {
        m_name: null,
        m_hanble: null,
        m_img_name: "",
        m_img_path: "",
      },

      // 게시물 작성 필요한 data 끝
    };
  },
  components: {
    PostList,
  },
  methods: {
    // 게시물 작성에 필요한 함수
    getUsetInfo() {
      // jwtToken을 decode해서 m_id를 추출한다.
      const token = localStorage.getItem("jwtToken");
      const decoded = jwtDecode(token);
      const m_id = decoded.m_id;
      apiClient
        .get(`/member/info/${m_id}`)
        .then((response) => {
          this.user = response.data;
        })
        .catch((error) => {
          console.error("Error fetching the board data:", error);
        });
    },
    getUserImageUrl() {
      if (this.user && this.user.m_img_path) {
        return `http://localhost:8083/${this.user.m_img_path}${this.user.m_img_name}`;
      } else {
        // 여기에 기본 이미지 URL 또는 다른 처리를 추가하세요.
        return "http://images/Logo.png";
      }
    },
    adjustHeight(e) {
      // textarea높이 자동 조절
      const element = e.target;
      element.style.height = "auto";
      element.style.height = element.scrollHeight + "px";
    },

    handleFileDrop(e) {
      // 파일 드롭
      const files = e.dataTransfer.files;
      this.processFiles(files);
    },

    handleFileChange(e) {
      // 파일이 선택되었을 때
      const files = e.target.files;
      this.processFiles(files);
    },

    processFiles(files) {
      // 파일 목록을 받아 각 파일을 처리, files배열에 추가
      //FileReader 를 생성해서 메타데이터와 실제 파일 객체 저장
      Array.from(files).forEach((file) => {
        const reader = new FileReader();
        reader.onload = (e) => {
          // 파일의 메타데이터와 실제 파일 객체 저장
          this.files.push({
            name: file.name,
            preview: e.target.result,
            file: file, // 실제 파일 객체 추가
          });
        };
        reader.readAsDataURL(file);
      });
    },

    triggerFileInput() {
      // 파일 input 클릭하게 만듦
      this.$refs.fileInput.click();
    },

    selectEmoji(sentiment, index) {
      this.selectedEmoji = sentiment;
      console.log(this.sentimentList[index]);
      this.emotion = this.sentimentList[index];
      this.isExpanded = false;
    },

    submitForm() {
      const formData = new FormData();
      formData.append("m_id", 1);
      formData.append("b_content", this.text);

      this.files.forEach((fileObj) => {
        formData.append("mediaList", fileObj.file); // 실제 파일 객체를 사용
      });
      formData.append("b_sentiment", this.emotion);
      for (let [key, value] of formData.entries()) {
        console.log(`${key}: ${value}`);
      }
      apiClient
        .post("/post/add", formData, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        })
        .then(() => {
          console.log("success");
          alert("전송이 완료되었습니다!");
          this.$emit("update-parent-data");

          // 전송 후에 데이터 초기화
          this.text = "";
          this.files = [];
          this.emotion = "";
          this.selectedEmoji = "happy";
          this.closepostModal();
        })
        .catch((error) => {
          console.log("formData" + formData);

          console.log(error);
        });
    },

    // 게시물 모델창 끄기
    closepostModal() {
      this.postMakeModal = false;
      console.log("안녕 난 모달이야2");
      console.log("this.postMakeModal =====", this.postMakeModal);
    },
    // 신고하기
    postWrite() {
      // 모달 열기 등의 동작 추가
      console.log("안녕 난 모달이야");
      this.postMakeModal = true;
      console.log("this.postMakeModal >>>", this.postMakeModal);
    },

    onSearchInputChange() {
      // 검색어 입력이 변경될 때마다 수행할 로직 추가
      console.log("검색어 입력 변경:", this.searchQuery);

      this.fetchData();
    },
    // 일단킵

    fetchData() {
      // 검색어가 비어있을 때는 API 호출을 하지 않음
      if (this.searchQuery.trim() === "") {
        this.searchResults = [];
        this.searchResultsBoard = [];
        return;
      }

      apiClient
        .get(`/member/search?memberName=${this.searchQuery}`)
        .then((res) => {
          this.searchResults = res.data;
        })
        .catch((err) => {
          console.error("err : ", err);
        });

      apiClient
        .get(`/mypage/search?boardContent=${this.searchQuery}`)
        .then((res) => {
          this.searchResultsBoard = res.data;
          console.log("searchResults : " + this.searchResults);
        })
        .catch((err) => {
          console.error("err : ", err);
        });
    },

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
      this.searchQuery = "";
      this.fetchData();
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
    openTab() {
      window.open(this.$router.resolve("/introduction").href, "_blank");
    },
  },
  computed: {
    isMyPage() {
      // 현재 경로가 / 이거나 /mypage로 시작할 때 true

      const mypage =
        this.$route.path.startsWith("/mypage") ||
        /^\/(\d+)$/.test(this.$route.path) ||
        this.$route.path === "/";

      return !mypage;
    },
  },
  created() {
    this.getMemberInfo();
    this.getPrfileImgUrl();
    //게시글 추가
    this.getUsetInfo();
  },
  mounted() {
    // 게시글 추가
    // 회원이름(m_handle)을 가져옵니다.
    apiClient.get("/member/userInfo/memberHandle").then((res) => {
      this.memberHandle = res.data;
    });

    // 추가 끝

    // Add a global click event listener to close the search bar when clicking outside
    document.addEventListener("click", this.toggleSidebarOutside);
  },
};
</script>

<style>
/* 슬라이드 바 스타일링 */
.sidebar {
  width: 25%;
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
