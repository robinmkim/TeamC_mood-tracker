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
        <div class="userName font-bold text-lg">{{ user.m_name }}</div>
        <div class="userHandle text-sm text-slate-500 ml-1">
          {{ user.m_handle }}
        </div>
        <div class="text-slate-400 text-sm ml-2">
          {{ userInfo.m_handle }} {{ formatTime(board.regdate) }}
        </div>
      </div>
      <div class="icon ml-auto -mr-3 mt-3 relative inline-block">
        <!-- 미트볼 아이콘 -->
        <button @click="BoardToggleDropdown">
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
          v-show="isDoardToggleDropdownOpen"
          class="absolute flex flex-col bg-white shadow-md mt-2 rounded-md py-2 w-32 right-[1px] z-10"
        >
          <router-link
            :to="{ path: '/jh_post/update', query: { b_id: this.board.b_id } }"
            ><span class="border-b">수정하기</span></router-link
          >
          <router-link to="/login"
            ><span class="border-b" @click="toggleDropdown"
              >Log In</span
            ></router-link
          >
          <router-link to="/" @click="toggleDropdown"
            ><span>Home</span></router-link
          >
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
          <span class="text-sm ml-1 mr-1">55</span>
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
            <span class="text-sm ml-1 mr-1">{{ commentCount }}</span>
          </a>

          <span class="ml-2">{{ sentimentEmoji }}</span>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import apiClient from "@/utils/apiClient";

export default {
  props: {
    // Step 1: Props 정의
    b_id: {
      type: Number,
      required: true,
    },
    m_id: {
      type: Number,
      required: true,
    },
  },
  data() {
    return {
      isDoardToggleDropdownOpen: false,
      currentImageIndex: 1,
      isLikeClicked: false,
      showMoreText: false,
      mid: 1,
      board: {
        b_id: null,
        m_id: null,
        b_content: "",
        b_sentiment: "",
        regdate: "",
        mediaList: [],
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
    handleScroll() {
      this.$emit("post-detail-scroll");
    },
    getCommentCount() {
      apiClient
        .get(`/jh_comment/allCommentCount?cm_bid=${this.b_id}`)
        .then((response) => {
          console.log("--------------" + response);
          this.commentCount = response.data;
        })
        .catch((error) => {
          console.log("--------------!!");
          console.error("Error fetching the board data:", error);
        });
    },
    getUserInfo() {
      apiClient
        .get(`/member/userInfo/${this.board.m_id}`)
        .then((response) => {
          this.user = response.data;
        })
        .catch((error) => {
          console.error("Error fetching the board data:", error);
        });
    },
    getUserImageUrl() {
      return `http://localhost:8083/${this.user.m_img_path}${this.user.m_img_name}`;
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
        .get(`/post/get/${this.b_id}`)
        .then((response) => {
          this.board = response.data;
          this.getUserInfo(); // user 데이터 갖고오기
        })
        .catch((error) => {
          console.error("Error fetching the board data:", error);
        });
    },
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
      this.isDoardToggleDropdownOpen = !this.isDoardToggleDropdownOpen;
    },
  },
  created() {
    this.getMemberInfo();
    this.loadBoardData();
    this.getCommentCount();
  },
};
</script>

<style scoped></style>
