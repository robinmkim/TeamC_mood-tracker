<template>
  <div class="m-4 border-b" @scroll="handleScroll">
    <!-- 게시글 헤더 영역 -->
    <div class="postHerder flex flex-row mb-3">
      <div class="h-[45px] w-[45px] overflow-hidden relative rounded-full">
        <img
          class="postDetailUserImg object-contain rounded-full"
          :src="getUserImageUrl()"
          alt="user icon"
        />
        <!-- src="../../assets/notiProfileImage01.jpg" -->
      </div>
      <div class="flex flex-row items-center mx-3">
        <div class="userName font-[600] text-lg">
          {{ this.board.member ? this.board.member.m_name : "No Name" }}
        </div>
        <div class="userHandle text-sm text-slate-500 ml-1">
          {{ this.board.member ? this.board.member.m_handle : "No Handle" }}
        </div>
        <div class="text-slate-400 text-sm ml-2">
          {{ formatTime(board.regdate) }}
        </div>
      </div>
      <div class="icon ml-auto -mr-3 mt-3 relative inline-block">
        <!-- 미트볼 아이콘 -->
        <button @click="BoardToggleDropdown()" class="commentDropdown">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 24 24"
            stroke-width="1.5"
            stroke="currentColor"
            class="w-6 h-6"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              d="M12 6.75a.75.75 0 1 1 0-1.5.75.75 0 0 1 0 1.5ZM12 12.75a.75.75 0 1 1 0-1.5.75.75 0 0 1 0 1.5ZM12 18.75a.75.75 0 1 1 0-1.5.75.75 0 0 1 0 1.5Z"
            />
          </svg>
        </button>
        <div
          v-show="isDropdownOpen"
          class="absolute flex flex-col bg-white shadow-md mt-2 rounded-md py-2 w-32 right-[1px] top-4 z-10"
        >
          <router-link
            :to="{ path: '/jh_post/update', query: { b_id: this.board.b_id } }"
            ><span class="border-b" v-if="isMain">수정하기</span></router-link
          >
          <span class="border-b" @click="delPost()" v-if="isMain"
            >삭제하기</span
          >
          <span class="border-b" @click="addReport()">신고하기</span>
        </div>
      </div>
    </div>

    <!-- 게시글 이미지 영역 -->
    <div class="postImage relative mb-3" v-if="board.mediaList.length > 0">
      <div class="flex items-center justify-center relative">
        <div
          class="h-60 overflow-hidden relative rounded-lg flex items-center justify-center"
        >
          <img
            :src="getImageUrl(board.mediaList[currentImageIndex - 1])"
            alt="Post image"
            class="items-center rounded-lg"
          />
        </div>
        <a
          v-if="currentImageIndex > 1"
          class="absolute top-1/2 transform -translate-y-1/2 cursor-pointer left-2.5 text-black"
          @click="prevImage"
        >
          &#10094;
        </a>
        <a
          v-if="currentImageIndex < board.mediaList.length"
          class="absolute top-1/2 transform -translate-y-1/2 cursor-pointer right-2.5 text-black"
          @click="nextImage"
        >
          &#10095;
        </a>
      </div>
    </div>

    <!-- 게시글 본문 영역 -->
    <div>
      <div class="text-left text-base font-normal flex mb-3">
        <div v-if="!showMoreText">
          {{ shotText }}
          <span v-if="showMoreText">
            ...<button @click="expandText" class="text-[#b3b3b3]">
              더보기
            </button>
          </span>
        </div>
        <div v-else>
          {{ board.b_content }}
        </div>
      </div>

      <!-- like, 이모지 -->
      <div class="flex flex-row">
        <div class="flex items-center">
          <div v-if="!board.myLike" @click="likeThis">
            <svg
              id="likeButton"
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              stroke-width="2"
              stroke="currentColor"
              class="w-6 h-6"
              @click="toggleLike"
            >
              <path
                ref="likePath"
                stroke-linecap="round"
                stroke-linejoin="round"
                d="M21 8.25c0-2.485-2.099-4.5-4.688-4.5-1.935 0-3.597 1.126-4.312 2.733-.715-1.607-2.377-2.733-4.313-2.733C5.1 3.75 3 5.765 3 8.25c0 7.22 9 12 9 12s9-4.78 9-12Z"
              />
            </svg>
          </div>
          <div v-else @click="delLike">
            <svg
              id="likeButton"
              xmlns="http://www.w3.org/2000/svg"
              fill="red"
              viewBox="0 0 24 24"
              stroke-width="0"
              stroke="currentColor"
              class="w-6 h-6"
              @click="toggleLike"
            >
              <path
                ref="likePath"
                stroke-linecap="round"
                stroke-linejoin="round"
                d="M21 8.25c0-2.485-2.099-4.5-4.688-4.5-1.935 0-3.597 1.126-4.312 2.733-.715-1.607-2.377-2.733-4.313-2.733C5.1 3.75 3 5.765 3 8.25c0 7.22 9 12 9 12s9-4.78 9-12Z"
              />
            </svg>
          </div>

          <span class="text-sm ml-1 mr-1">{{ this.board.countLike }}</span>
          <a
            :href="'/postDetail/?b_id=' + this.board.b_id"
            class="flex items-center"
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              stroke-width="1.5"
              stroke="currentColor"
              class="w-6 h-6"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                d="M7.5 8.25h9m-9 3H12m-9.75 1.51c0 1.6 1.123 2.994 2.707 3.227 1.129.166 2.27.293 3.423.379.35.026.67.21.865.501L12 21l2.755-4.133a1.14 1.14 0 0 1 .865-.501 48.172 48.172 0 0 0 3.423-.379c1.584-.233 2.707-1.626 2.707-3.228V6.741c0-1.602-1.123-2.995-2.707-3.228A48.394 48.394 0 0 0 12 3c-2.392 0-4.744.175-7.043.513C3.373 3.746 2.25 5.14 2.25 6.741v6.018Z"
              />
            </svg>
            <span class="text-sm ml-1 mr-1">{{
              this.board.countComments
            }}</span>
          </a>

          <span class="ml-2">{{ sentimentEmoji }}</span>
          <!-- 자연어처리 부분 -->
          <span class="ml-2">{{ sentimentEmoji }}</span>
          <div class="ml-2">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="24"
              height="24"
              viewBox="0 0 24 24"
              @click="evaluatePositivity"
            >
              <path
                d="M12 2c5.514 0 10 4.486 10 10s-4.486 10-10 10-10-4.486-10-10 4.486-10 10-10zm0-2c-6.627 0-12 5.373-12 12s5.373 12 12 12 12-5.373 12-12-5.373-12-12-12zm2.5 8.5c-.98 0-1.865.404-2.502 1.054-.634-.649-1.519-1.054-2.498-1.054-1.933 0-3.5 1.567-3.5 3.5s1.567 3.5 3.5 3.5c.979 0 1.864-.404 2.498-1.054.637.649 1.522 1.054 2.502 1.054 1.933 0 3.5-1.566 3.5-3.5s-1.567-3.5-3.5-3.5zm0 6c-1.378 0-2.5-1.122-2.5-2.5s1.122-2.5 2.5-2.5c1.379 0 2.5 1.122 2.5 2.5s-1.121 2.5-2.5 2.5z"
              />
            </svg>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import apiClient from "@/utils/apiClient";
import { jwtDecode } from "jwt-decode";
import axios from "axios";

export default {
  props: {
    // Step 1: Props 정의
    b_id: {
      type: Number,
      required: true,
    },
    isDropdownOpen: {
      type: Boolean,
      required: true,
    },
  },
  data() {
    return {
      currentImageIndex: 1,
      isLikeClicked: false,
      showMoreText: false,
      mid: 1,
      board: {
        b_id: null,
        m_id: null,
        isMylike: false,
        b_content: "",
        b_sentiment: "",
        regdate: "",
        mediaList: [],
        member: null,
        countLike: 0,
        countComments: 0,
        myLike: false,
        showDrop: this.isDropdownOpen,
      },
      emotionMap: {
        "😆": "happy",
        "😡": "angry",
        "😬": "anxiety",
        "🤕": "hurt",
        "😐": "neutral",
        "😢": "sad",
        "😨": "surprise",
      },
      user: {
        m_name: null,
        m_hanble: null,
        m_img_name: "",
        m_img_path: "",
      },
      commentCount: 0,
      isMain: false,
    };
  },
  computed: {
    shotText() {
      // 본문의 일부만 보여주되, 본문이 존재하는 경우에만 작업을 수행합니다.
      return this.board.b_content ? this.board.b_content.slice(0, 20) : "";
    },
    sentimentEmoji() {
      // 감정에 해당하는 이모지를 반환합니다.
      return (
        Object.keys(this.emotionMap).find(
          (key) => this.emotionMap[key] === this.board.b_sentiment
        ) || ""
      );
    },
    imageCount() {
      // mediaList 배열의 길이를 반환합니다.
      return this.board.mediaList.length;
    },
  },
  methods: {
    // 장고로 자연어처리 보내기 부분
    async evaluatePositivity() {
      console.log("b_content 확인: ", this.board.b_content);

      // 추가 데이터 가능하게
      // const requestData = {
      //   b_content: this.board.b_content,
      // };
      const requestData = new FormData();
      requestData.append("b_content", this.board.b_content);
      //
      try {
        // 장고로 보내기
        const res = await axios.post(
          "http://localhost:9000/emotion/evaluatePositivity",
          requestData
        );
        // 장고에서 받아온데이터 확인하기
        // const serverResponse = res.data;
        const serverResponse = JSON.stringify(res.data);
        console.log("데이터 확인 : " + serverResponse);

        console.log("데이터 확인2 : " + res.data.additional_message);
      } catch (error) {
        console.error("서버 오류 : ", error);
      }
    },
    addReport() {},

    likeThis() {
      apiClient
        .get(`/jh_postLike/addBoardLike?b_id=${this.b_id}`)
        .then((response) => {
          // this.isMylike = response.data;
          console.log("likeThis!: " + response.data);
          this.loadBoardData();
          // console.log("isMylike: " + this.isMylike);
        })
        .catch((error) => {
          console.error("Error fetching the board data:", error);
        });
    },
    delLike() {
      apiClient
        .get(`/jh_postLike/delBoardLike?b_id=${this.b_id}`)
        .then(() => {
          // this.isMylike = response.data;
          console.log("delLike! 성공!");
          this.loadBoardData();

          // console.log("isMylike: " + this.isMylike);
        })
        .catch((error) => {
          console.error("Error fetching the board data:", error);
        });
    },
    delPost() {
      const userConfirmed = confirm("게시물을 삭제하시겠습니까?");
      if (userConfirmed) {
        apiClient
          .get(`/jh_post/delPost?b_id=${this.b_id}`)
          .then(() => {
            this.$router.push("/timeline");
          })
          .catch((error) => {
            console.log("--------------!!");
            console.error("Error fetching the board data:", error);
          });
      }
    },
    handleScroll() {
      this.$emit("post-detail-scroll");
    },
    // getCommentCount() {
    //   apiClient
    //     .get(`/jh_comment/allCommentCount?b_id=${this.b_id}`)
    //     .then((response) => {
    //       console.log("--------------" + response);
    //       this.commentCount = response.data;
    //     })
    //     .catch((error) => {
    //       console.log("--------------!!");
    //       console.error("Error fetching the board data:", error);
    //     });
    // },
    // getUserInfo() {
    //   apiClient
    //     .get(`/member/userInfo/${this.board.m_id}`)
    //     .then((response) => {
    //       this.user = response.data;
    //     })
    //     .catch((error) => {
    //       console.error("Error fetching the board data:", error);
    //     });
    // },
    getUserImageUrl() {
      if (this.board.member && this.board.member.m_img_path) {
        console.log(
          `${this.board.member.m_img_path}${this.board.member.m_img_name}`
        );
        return `http://localhost:8083/${this.board.member.m_img_path}${this.board.member.m_img_name}`;
      } else {
        // 여기에 기본 이미지 URL 또는 다른 처리를 추가하세요.
        return "http://images/Logo.png";
      }
    },

    getImageUrl(media) {
      // md_path와 md_name을 결합하여 이미지의 전체 경로를 반환합니다.
      return `http://localhost:8083/${media.md_path}${media.md_name}`;
    },
    prevImage() {
      // 이전 이미지로 이동합니다.
      if (this.currentImageIndex > 1) {
        this.currentImageIndex -= 1;
      }
    },
    nextImage() {
      // 다음 이미지로 이동합니다.
      if (this.currentImageIndex < this.imageCount) {
        this.currentImageIndex += 1;
      }
    },
    toggleLike() {
      // 좋아요 버튼 상태를 토글합니다.
      // apiClient.get(``).then(this.isLikeClicked = true;);
      this.isLikeClicked = !this.isLikeClicked;
    },
    expandText() {
      // 게시글 전문을 보여줍니다.
      this.showMoreText = true;
    },
    loadBoardData() {
      // 게시글 데이터를 로드합니다.
      apiClient
        .get(`/jh_post/get/${this.b_id}`)
        .then((response) => {
          this.board = response.data;
          console.log(this.board);
          console.log(this.board.myLike);
          console.log("loadBoardData: " + this.board.member.m_id);

          // jwtToken을 decode해서 m_id를 추출한다.
          const token = localStorage.getItem("jwtToken");
          const decoded = jwtDecode(token);
          this.isMain = this.board.member.m_id === decoded.m_id ? true : false;
        })
        .catch((error) => {
          console.error("Error fetching the board data:", error);
        });
    },
    // isMylikeMethod() {
    //   apiClient
    //     .get(`/jh_postLike/isMylike?b_id=${this.b_id}`)
    //     .then((response) => {
    //       this.isMylike = response.data;
    //       console.log("isMylike!: " + this.isMylike);
    //       // console.log("isMylike: " + this.isMylike);
    //     })
    //     .catch((error) => {
    //       console.error("Error fetching the board data:", error);
    //     });
    // },
    formatTime(dateString) {
      const now = new Date();
      const postDate = new Date(dateString);
      const diffInSeconds = Math.floor((now - postDate) / 1000);
      if (diffInSeconds < 60) {
        return `${diffInSeconds}초 전`;
      }

      const diffInMinutes = Math.floor(diffInSeconds / 60);
      if (diffInMinutes < 60) {
        return `${diffInMinutes}분 전`;
      }

      const diffInHours = Math.floor(diffInMinutes / 60);
      if (diffInHours < 24) {
        return `${diffInHours}시간 전`;
      }

      return postDate.toLocaleDateString("ko-KR");
    },
    BoardToggleDropdown() {
      this.$emit("toggle-dropdown", this.b_id);
      // 부모로부터 전달된 isDropdownOpen 값을 내부 상태에 할당
      this.board.showDrop = this.isDropdownOpen;
      // 내부 상태를 이용해 드롭다운을 토글
      this.board.showDrop = !this.board.showDrop;
    },
    handleDocumentClick(event) {
      // 클릭된 엘리먼트가 드롭다운 영역인지 확인
      const isDropdown = event.target.closest(".commentDropdown") !== null;
      // 만약 드롭다운 영역이 아니면 드롭다운을 닫기
      if (!isDropdown) {
        this.$emit("toggle-dropdown", this.b_id);
      }
    },
  },
  created() {
    // this.getUserInfo();
    this.loadBoardData();
    // this.getCommentCount();
  },
  mounted() {
    document.addEventListener("click", this.handleDocumentClick);
  },
  beforeUnmount() {
    document.removeEventListener("click", this.handleDocumentClick);
  },
};
</script>

<style scoped></style>
