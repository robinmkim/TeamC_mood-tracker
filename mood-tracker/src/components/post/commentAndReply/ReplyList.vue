<template>
  <div class="flex" :class="childClass">
    <div class="flex-1 border-t border-l-slate-300">
      <div class="p-3 pb-0 border-b border-t-slate-300">
        <div class="postHerder flex flex-row">
          <div class="h-14 w-14 overflow-hidden relative rounded-full">
            <img
              class="postDetailUserImg object-contain rounded-full"
              :src="
                getUserImageUrl(
                  reply.member.m_img_path,
                  reply.member.m_img_name
                )
              "
              alt="user icon"
            />
          </div>
          <div class="flex flex-row items-center mx-3">
            <div class="notiUserName font-bold text-lg">
              {{ reply.member.m_name }}
            </div>
            <div class="userHandle text-sm text-slate-500 ml-1">
              {{ reply.member.m_handle }}
            </div>
            <div class="text-slate-400 text-sm ml-2">
              {{ formatTime(reply.regdate) }}
            </div>
          </div>
          <div class="icon ml-auto -mr-3 mt-3 relative inline-block">
            <button @click="toggleDropdownReply()" class="replyDropdown">
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
              class="replyDropdown absolute flex flex-col bg-white shadow-md mt-2 top-4 rounded-md py-2 w-32 right-[1px] z-10"
            >
              <span class="border-b" @click="addReport()">신고하기</span>

              <span class="border-b" v-if="isMain" @click="delReply()"
                >삭제하기</span
              >
            </div>
          </div>
        </div>
        <div class="text-left ml-6 mt-3 mb-3">
          {{ reply.re_content }}
        </div>
        <div class="flex flex-row ml-2">
          <div class="flex items-center">
            <div v-if="!reply.myLike" @click="likeThis()">
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
            <div v-else @click="delLike()">
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
            <span class="text-sm ml-1 mr-1">{{ this.reply.likeCount }}</span>
          </div>
        </div>

        <div
          class="flex border-t items-center justify-center p-2"
          v-show="reply.showAddReply"
        >
          <textarea
            class="text-sm border-b border-slate-200 w-[95%] h-6 resize-none focus:outline-slate-400"
            placeholder=" 답글입력"
            v-model="content"
          ></textarea>

          <div
            class="flex flex-col text-xs justify-center items-center flex-grow"
          >
            <div
              class="hover:bg-teal-100 rounded-lg flex w-[80%] h-[80%] justify-center items-center"
              @click="addReply()"
              type="submit"
            >
              등록
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import apiClient from "@/utils/apiClient";
export default {
  name: "replyList",
  components: {},
  props: {
    // Step 1: Props 정의
    re_id: {
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
      reply: {
        cm_id: null,
        m_id: null,
        re_content: "",
        regdate: "",
        member: {},
        isMyLike: false,
        likeCount: null,
        showDrop: this.isDropdownOpen,
      },
      isMain: false,
    };
  },
  created() {
    this.getReplyDetail();
  },
  methods: {
    addReport() {},
    addReply() {
      const currentContent = this.content;
      const formData = new FormData();
      formData.append("cm_id", this.reply.cm_id);
      formData.append("re_content", `${currentContent}`);

      for (let [key, value] of formData.entries()) {
        console.log(`${key}: ${value}`);
      }
      apiClient
        .post("/jh_reply/addReply", formData, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        })
        .then((res) => {
          if (res.data === 1) {
            // 응답 값이 1이면 페이지를 새로고침
            // window.location.reload();
            this.getReplyDetail();
          }
        })
        .catch((error) => {
          console.log("formData" + formData);

          console.log(error);
        });
    },

    likeThis() {
      apiClient
        .get(`/jh_ReplyLike/addReplyLike?re_id=${this.re_id}`)
        .then(() => {
          this.getReplyDetail();
        })
        .catch((error) => {
          console.error("Error fetching the board data:", error);
        });
    },

    delLike() {
      apiClient
        .get(`/jh_ReplyLike/delReplyLike?re_id=${this.re_id}`)
        .then(() => {
          this.getReplyDetail();
        })
        .catch((error) => {
          console.error("Error fetching the board data:", error);
        });
    },
    delReply() {
      apiClient
        .get(`/jh_reply/delReply?re_id=${this.re_id}`)
        .then(() => {
          this.getReplyDetail();
        })
        .catch((error) => {
          console.log(error);
        });
    },
    toggleDropdownReply() {
      this.$emit("toggle-dropdown", this.re_id);
      // 부모로부터 전달된 isDropdownOpen 값을 내부 상태에 할당
      this.reply.showDrop = this.isDropdownOpen;
      // 내부 상태를 이용해 드롭다운을 토글
      this.reply.showDrop = !this.reply.showDrop;
    },
    handleDocumentClick(event) {
      // 클릭된 엘리먼트가 드롭다운 영역인지 확인
      const isDropdown = event.target.closest(".replyDropdown") !== null;
      // 만약 드롭다운 영역이 아니면 드롭다운을 닫기
      if (!isDropdown) {
        this.$emit("toggle-dropdown", this.cm_id);
      }
    },

    getUserImageUrl(m_img_path, m_img_name) {
      return "http://localhost:8083/" + m_img_path + m_img_name;
    },
    getReplyDetail() {
      apiClient
        .get(`/jh_reply/getReplyDetail?re_id=${this.re_id}`)
        .then((response) => {
          this.reply = response.data;
          console.log("member: " + response.data.member.m_name);
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
    handleScroll() {
      this.$emit("post-reply-scroll");
    },
  },
  mounted() {
    document.addEventListener("click", this.handleDocumentClick);
  },
  beforeUnmount() {
    document.removeEventListener("click", this.handleDocumentClick);
  },
};
</script>
