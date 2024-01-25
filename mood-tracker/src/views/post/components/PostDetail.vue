<template>
  <div
    class="m-1 mb-3 p-4 border bg-white shadow rounded-lg"
    @scroll="handleScroll"
    :class="customClass"
  >
    <!-- 모달 부분 -->
    <MediaModal
      :isOpen="isModalOpen"
      @close="isModalOpen = false"
      :mediaList="board.mediaList"
    />
    <!-- 게시글 헤더 영역 -->
    <div class="postHerder flex flex-row mb-3">
      <div class="h-[45px] w-[45px] overflow-hidden relative rounded-full">
        <img
          class="postDetailUserImg object-contain rounded-full"
          :src="getUserImageUrl()"
          alt="user icon"
          style="aspect-ratio: 100/100; object-fit: cover"
        />
        <!-- src="../../assets/notiProfileImage01.jpg" -->
      </div>
      <div class="flex flex-row items-center mx-3">
        <div class="userName font-[600] text-lg">
          {{ this.board.member ? this.board.member.m_name : "No Name" }}
        </div>
        <div class="userHandle text-sm text-slate-500 ml-1">
          @{{ this.board.member ? this.board.member.m_handle : "No Handle" }}
        </div>
        <div class="text-slate-400 text-sm ml-2">
          {{ formatTime(board.regdate) }}
        </div>
      </div>
      <div class="icon ml-auto -mr-3 mt-3 relative inline-block">
        <!-- 미트볼 아이콘 -->
        <button @click="toggleDropdown" class="commentDropdown">
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
          v-if="isOpen"
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
          <!-- 신고하기 모달창 -->
          <div
            v-if="reportModal"
            class="fixed top-0 left-0 w-full h-full bg-black bg-opacity-50 flex items-center justify-center"
            @click="closeReportModal"
          >
            <!-- 모달 내용 -->
            <div class="bg-white rounded-lg max-w-screen-sm w-1/3">
              <span
                class="close absolute top-4 right-4 cursor-pointer"
                @click="closeReportModal"
              ></span>
              <p class="border-b py-2">신고 사유를 선택하세요:</p>
              <ul>
                <li
                  @click="submitReport('스팸')"
                  class="cursor-pointer border-b py-2"
                >
                  스팸
                </li>
                <li
                  @click="submitReport('나체 이미지 또는 성적 행위')"
                  class="cursor-pointer border-b py-2"
                >
                  나체 이미지 또는 성적 행위
                </li>
                <li
                  @click="submitReport('혐오 발언 또는 상징')"
                  class="cursor-pointer border-b py-2"
                >
                  혐오 발언 또는 상징
                </li>
                <li
                  @click="submitReport('폭력 또는 위험 단체')"
                  class="cursor-pointer border-b py-2"
                >
                  폭력 또는 위험 단체
                </li>
                <li
                  @click="submitReport('불법 또는 규제 상품 판매')"
                  class="cursor-pointer border-b py-2"
                >
                  불법 또는 규제 상품 판매
                </li>
                <li
                  @click="submitReport('따돌림 또는 괴롭힘')"
                  class="cursor-pointer border-b py-2"
                >
                  따돌림 또는 괴롭힘
                </li>
                <li
                  @click="submitReport('지식 재산권 침해')"
                  class="cursor-pointer border-b py-2"
                >
                  지식 재산권 침해
                </li>
              </ul>
            </div>
          </div>
          <div @click.stop="preventClose"></div>
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
            @click="mediaModalOpen"
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
        <div v-else class="whitespace-pre">
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
              class="w-6 h-6 cursor-pointer"
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
              class="w-6 h-6 cursor-pointer"
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
            :href="'/postDetail?b_id=' + this.board.b_id"
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
            <span
              class="text-sm ml-1 mr-1"
              v-if="caller === 'postDetailPage'"
              >{{ $store.state.totalCommentCount }}</span
            >
            <span class="text-sm ml-1 mr-1" v-else>{{
              board.countComments
            }}</span>
          </a>

          <img
            class="cursor-pointer ml-2"
            :src="`http://localhost:8083/images/${board.b_sentiment}.png`"
            width="20"
            height="20"
          />
          <!-- 자연어처리 부분 -->
          <div class="ml-2">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="24"
              height="24"
              viewBox="0 0 24 24"
              class="cursor-pointer"
              @click="evaluatePositivity"
            >
              <path
                d="M12 2c5.514 0 10 4.486 10 10s-4.486 10-10 10-10-4.486-10-10 4.486-10 10-10zm0-2c-6.627 0-12 5.373-12 12s5.373 12 12 12 12-5.373 12-12-5.373-12-12-12zm2.5 8.5c-.98 0-1.865.404-2.502 1.054-.634-.649-1.519-1.054-2.498-1.054-1.933 0-3.5 1.567-3.5 3.5s1.567 3.5 3.5 3.5c.979 0 1.864-.404 2.498-1.054.637.649 1.522 1.054 2.502 1.054 1.933 0 3.5-1.566 3.5-3.5s-1.567-3.5-3.5-3.5zm0 6c-1.378 0-2.5-1.122-2.5-2.5s1.122-2.5 2.5-2.5c1.379 0 2.5 1.122 2.5 2.5s-1.121 2.5-2.5 2.5z"
              />
            </svg>
          </div>
          <div v-if="isLoading" class="loading-container mr-16">
            <div class="loading">
              <Fade-loader />
            </div>
          </div>
          <!-- 값이 있으면 보여주고 싶은 부분 -->
          <div v-if="processedText !== ''" class="ml-2">
            <p>{{ processedText }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import FadeLoader from "vue-spinner/src/FadeLoader.vue";
import apiClient from "@/utils/apiClient";
import { jwtDecode } from "jwt-decode";
import axios from "axios";
import MediaModal from "@/components/MediaModal.vue";

export default {
  components: {
    MediaModal,
    FadeLoader,
  },
  props: {
    // Step 1: Props 정의
    b_id: {
      type: Number,
      required: false,
    },
    isDropdownOpen: {
      type: Boolean,
      required: false,
    },
    onBoardDataLoaded: {
      type: Function,
      required: false,
    },
    caller: {
      type: String,
      required: false,
    },
  },
  watch: {
    b_id(newBId) {
      this.loadBoardData(newBId);
    },
  },
  data() {
    return {
      currentImageIndex: 1,
      isLikeClicked: false,
      showMoreText: false,
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
        processedText: "",
      },
      sentimentList: [
        "happy",
        "angry",
        "anxiety",
        "hurt",
        "neutral",
        "sad",
        "surprise",
      ],
      user: {
        m_name: null,
        m_hanble: null,
        m_img_name: "",
        m_img_path: "",
      },
      commentCount: 0,
      isMain: false,
      isOpen: false,
      reportModal: false,
      isModalOpen: false,
      isLoading: false,
    };
  },
  computed: {
    shotText() {
      // 본문의 일부만 보여주되, 본문이 존재하는 경우에만 작업을 수행합니다.
      return this.board.b_content ? this.board.b_content.slice(0, 20) : "";
    },
    imageCount() {
      // mediaList 배열의 길이를 반환합니다.
      return this.board.mediaList.length;
    },
  },
  methods: {
    mediaModalOpen() {
      // alert("!!" + this.board.mediaList[0].md_id);
      this.isModalOpen = true;
    },
    closeReportModal() {
      this.reportModal = false;
    },
    // 신고하기
    addReport() {
      // 모달 열기 등의 동작 추가
      this.reportModal = true;
    },
    submitReport(reportType) {
      const reportData = {
        b_c_id: this.board.b_id,
        report_type: reportType,
        regdate: this.getCurrentDate(),
        r_type: 0,
      };
      console.log("reportData : ", reportData);

      apiClient
        .post("/report/add", reportData)
        .then((res) => {
          // 보냈을때
          alert("신고를 하였습니다.");
          console.log("신고 전송", res);
        })
        .catch((err) => {
          console.log("then으로 들어가지 못하고 여기로 옴");
          console.log(reportData);
          console.error("전송에 오류가 있습니다.", err);
        });
    },

    getCurrentDate() {
      // 현재 날짜를 가져오기
      const currentDate = new Date();
      return currentDate.toLocaleString();
    },

    // 장고로 자연어처리 보내기 부분
    async evaluatePositivity() {
      console.log("b_content 확인: ", this.board.b_content);
      this.isLoading = true;

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
          "http://192.168.0.43:9000/emotion/evaluatePositivity",
          requestData
        );
        this.isLoading = false;
        // 장고에서 받아온데이터 확인하기
        // const serverResponse = res.data;
        const serverResponse = JSON.stringify(res.data);
        console.log("데이터 확인 : " + serverResponse);

        this.processedText = res.data.additional_message;
        console.log("데이터 확인2 : " + this.processedText);
        this.$forceUpdate();
      } catch (error) {
        console.error("서버 오류 : ", error);
      }
    },

    likeThis() {
      apiClient
        .get(`/jh_postLike/addBoardLike?b_id=${this.b_id}`)
        .then(() => {
          this.loadBoardData(this.b_id);
        })
        .catch((error) => {
          console.error("Error fetching the board data:", error);
        });
    },
    delLike() {
      apiClient
        .get(`/jh_postLike/delBoardLike?b_id=${this.b_id}`)
        .then(() => {
          this.loadBoardData(this.b_id);
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
            console.error("Error fetching the board data:", error);
          });
      }
    },
    handleScroll() {
      this.$emit("post-detail-scroll");
    },
    getUserImageUrl() {
      if (this.board.member && this.board.member.m_img_path) {
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
    loadBoardData(bId) {
      // 게시글 데이터를 로드합니다.
      apiClient
        .get(`/post/get/${bId}`)
        .then((response) => {
          this.board = response.data;
          console.log("!" + this.board.b_id);

          // jwtToken을 decode해서 m_id를 추출한다.
          const token = localStorage.getItem("jwtToken");
          const decoded = jwtDecode(token);
          this.isMain = this.board.member.m_id === decoded.m_id ? true : false;

          this.onBoardDataLoaded();
          this.processedText = "";
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
    toggleDropdown() {
      this.isOpen = !this.isOpen;
      if (this.isOpen) {
        // 다른 드롭다운 닫기 이벤트 등록
        window.addEventListener("click", this.closeDropdowns);
      } else {
        // 다른 드롭다운 닫기 이벤트 제거
        window.removeEventListener("click", this.closeDropdowns);
      }
    },
    closeDropdowns(event) {
      // 다른 드롭다운 닫기
      if (!this.$el.contains(event.target)) {
        this.isOpen = false;
      }
    },
    preventClose(event) {
      // 클릭 이벤트 전파 방지
      event.stopPropagation();
    },
    beforeDestroy() {
      // 컴포넌트 파괴 시 이벤트 제거
      window.removeEventListener("click", this.closeDropdowns);
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
    this.loadBoardData(this.b_id);
  },
  mounted() {},
  beforeUnmount() {},
};
</script>

<style scoped>
.loading {
  z-index: 2;
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  box-shadow: rgba(0, 0, 0, 0.1) 0 0 0 9999px;
}
</style>
