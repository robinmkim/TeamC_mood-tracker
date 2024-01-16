<template>
  <div
    class="flex overflow-y-auto h-3/4"
    @scroll="handleScroll"
    :class="childClass"
  >
    <!-- min-h-screen -->
    <!-- post 내용 -->
    <!-- <postDetail :b_id="b_id"></postDetail> -->

    <div class="flex-1 border-l border-l-slate-300">
      <!-- 댓글창 -->
      <div v-if="comment != null">
        <div v-for="(item, index) in comment[0]" :key="index">
          <div class="p-3 pb-0 border-b border-t-slate-300">
            <div class="postHerder flex flex-row">
              <div class="h-14 w-14 overflow-hidden relative rounded-full">
                <img
                  class="postDetailUserImg object-contain rounded-full"
                  :src="
                    getUserImageUrl(
                      item.member.m_img_path,
                      item.member.m_img_name
                    )
                  "
                  alt="user icon"
                />
              </div>
              <div class="flex flex-row items-center mx-3">
                <div class="notiUserName font-bold text-lg">
                  {{ item.member.m_name }}
                </div>
                <div class="userHandle text-sm text-slate-500 ml-1">
                  {{ item.member.m_handle }}
                </div>
                <div class="text-slate-400 text-sm ml-2">
                  {{ formatTime(item.regdate) }}
                </div>
              </div>
              <div class="icon ml-auto -mr-3 mt-3 relative inline-block">
                <!-- 미트볼 아이콘 -->
                <button @click="BoardToggleDropdown(index, -1)">
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
                  v-show="item.showDrop"
                  class="absolute flex flex-col bg-white shadow-md mt-2 rounded-md py-2 w-32 right-[1px] z-10"
                >
                  <span class="border-b" @click="updateData(index)"
                    >리플달기</span
                  >
                  <span class="border-b" @click="delComment(item.cm_id)"
                    >삭제하기</span
                  >
                </div>
              </div>
            </div>
            <div class="text-left ml-6 mt-3 mb-3">
              {{ item.cm_content }}
            </div>
            확인 : {{ index }} /
            <div v-if="!item.myLike" @click="likeThis(item.cm_id)">
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
            <div v-else @click="delLike(item.cm_id)">
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
            <div
              class="flex border-t items-center justify-center p-2"
              v-show="item.showAddReply"
            >
              <textarea
                class="text-sm border-b border-slate-200 w-[95%] h-6 resize-none focus:outline-slate-400"
                placeholder=" 답글입력"
                v-model="content[index]"
                @input="adjustHeight"
              ></textarea>

              <div
                class="flex flex-col text-xs justify-center items-center flex-grow"
              >
                <div
                  class="hover:bg-teal-100 rounded-lg flex w-[80%] h-[80%] justify-center items-center"
                  @click="addReply(index, item.cm_id)"
                  type="submit"
                >
                  등록
                </div>
              </div>
            </div>

            <div v-if="item.reply_count > 0" class="flex flex-col ml-2">
              <div class="flex flex-col">
                <div
                  class="text-sm text-slate-400 cursor-pointer text-left"
                  @click="moreReply(index, item.cm_id)"
                >
                  댓글 {{ item.reply_count }}개 더보기...
                </div>
              </div>

              <div v-show="item.showReplies" class="flex flex-col">
                <div v-for="(rItem, rIndex) in reply[0]" :key="rIndex">
                  <div v-if="rItem.re_id !== 0">
                    <div class="p-3 pr-0 border-t border-b-slate-300 pl-10">
                      <div class="postHerder flex flex-row m">
                        <div
                          class="h-14 w-14 overflow-hidden relative rounded-full"
                        >
                          <img
                            class="postDetailUserImg object-contain rounded-full"
                            :src="
                              getUserImageUrl(
                                rItem.member.m_img_path,
                                rItem.member.m_img_name
                              )
                            "
                            alt="user icon"
                          />
                        </div>
                        <div class="flex flex-row items-center mx-3">
                          <div class="notiUserName font-bold text-lg">
                            {{ rItem.member.m_name }}
                          </div>
                          <div class="userHandle text-sm text-slate-500 ml-1">
                            {{ rItem.member.m_handle }}
                          </div>
                          <div class="text-slate-400 text-sm ml-2">
                            {{ formatTime(rItem.regdate) }}
                          </div>
                        </div>
                        <div
                          class="icon ml-auto -mr-3 mt-3 relative inline-block"
                        >
                          <!-- 미트볼 아이콘 -->
                          <button @click="BoardToggleDropdown2(index, rIndex)">
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
                            v-show="rIndex.showDrop"
                            class="absolute flex flex-col bg-white shadow-md mt-2 rounded-md py-2 w-32 right-[1px] z-10"
                          >
                            <router-link
                              :to="{
                                path: '/jh_reply/delReply',
                                query: { b_id: this.b_id },
                              }"
                              ><span class="border-b"
                                >삭제하기</span
                              ></router-link
                            >
                          </div>
                        </div>
                      </div>
                      <div class="text-left ml-6 mt-2">
                        {{ rItem.re_content }}
                      </div>
                      확인: {{ index }} / {{ rIndex }}
                    </div>
                    <div
                      v-if="!rItem.myLike"
                      @click="likeThisReply(rItem.re_id)"
                    >
                      <svg
                        id="likeButton"
                        xmlns="http://www.w3.org/2000/svg"
                        fill="none"
                        viewBox="0 0 24 24"
                        stroke-width="2"
                        stroke="currentColor"
                        class="w-6 h-6"
                      >
                        <path
                          ref="likePath"
                          stroke-linecap="round"
                          stroke-linejoin="round"
                          d="M21 8.25c0-2.485-2.099-4.5-4.688-4.5-1.935 0-3.597 1.126-4.312 2.733-.715-1.607-2.377-2.733-4.313-2.733C5.1 3.75 3 5.765 3 8.25c0 7.22 9 12 9 12s9-4.78 9-12Z"
                        />
                      </svg>
                    </div>
                    <div v-else @click="delLikeReply(rItem.re_id)">
                      <svg
                        id="likeButton"
                        xmlns="http://www.w3.org/2000/svg"
                        fill="red"
                        viewBox="0 0 24 24"
                        stroke-width="0"
                        stroke="currentColor"
                        class="w-6 h-6"
                      >
                        <path
                          ref="likePath"
                          stroke-linecap="round"
                          stroke-linejoin="round"
                          d="M21 8.25c0-2.485-2.099-4.5-4.688-4.5-1.935 0-3.597 1.126-4.312 2.733-.715-1.607-2.377-2.733-4.313-2.733C5.1 3.75 3 5.765 3 8.25c0 7.22 9 12 9 12s9-4.78 9-12Z"
                        />
                      </svg>
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
  <!-- <div class="w-1/5">side menu</div> -->
</template>

<script>
import apiClient from "@/utils/apiClient";
export default {
  data() {
    return {
      isDoardToggleDropdownOpen: false,
      b_id: this.$route.query.b_id,
      comment: [],
      content: [],
      reply: [],
      isDropdownOpen: Array(0).fill([]),
    };
  },
  methods: {
    likeThisReply(re_id) {
      apiClient
        .get(`/jh_ReplyLike/addReplyLike?re_id=${re_id}`)
        .then((response) => {
          // this.isMylike = response.data;
          console.log("likeThis!: " + response.data);
          this.getCommentListDetail();
          console.log("??");
          // console.log("isMylike: " + this.isMylike);
        })
        .catch((error) => {
          console.error("Error fetching the board data:", error);
        });
    },
    delLikeReply(re_id) {
      apiClient
        .get(`/jh_ReplyLike/delReplyLike?re_id=${re_id}`)
        .then(() => {
          // this.isMylike = response.data;
          console.log("delLike! 성공!");
          this.getCommentListDetail();

          // console.log("isMylike: " + this.isMylike);
        })
        .catch((error) => {
          console.error("Error fetching the board data:", error);
        });
    },
    likeThis(cm_id) {
      apiClient
        .get(`/jh_CommentLike/addCommentLike?cm_id=${cm_id}`)
        .then((response) => {
          // this.isMylike = response.data;
          console.log("likeThis!: " + response.data);
          this.getCommentListDetail();
          console.log("??!");
          // console.log("isMylike: " + this.isMylike);
        })
        .catch((error) => {
          console.error("Error fetching the board data:", error);
        });
    },
    delLike(cm_id) {
      apiClient
        .get(`/jh_CommentLike/delCommentLike?cm_id=${cm_id}`)
        .then(() => {
          // this.isMylike = response.data;
          console.log("delLike! 성공!");
          this.getCommentListDetail();
          console.log("??!!!");

          // console.log("isMylike: " + this.isMylike);
        })
        .catch((error) => {
          console.error("Error fetching the board data:", error);
        });
    },
    delComment(cm_id) {
      apiClient
        .get(`/jh_comment/delComment?cm_id=${cm_id}`)
        .then(() => {
          this.getCommentListDetail();
        })
        .catch((error) => {
          console.log(error);
        });
    },
    addReply(index, cm_id) {
      const currentContent = this.content[index];
      // 현재 댓글에 대한 처리 (API 호출 등)
      // console.log(`댓글 ${index + 1}의 내용: ${currentContent}`);
      // console.log(`cm_id: ` + cm_id);

      const formData = new FormData();
      formData.append("cm_id", cm_id);
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
            window.location.reload();
          }
        })
        .catch((error) => {
          console.log("formData" + formData);

          console.log(error);
        });
    },
    BoardToggleDropdown(index, rIndex) {
      console.log("index: " + index + " / rIndex: " + rIndex);

      // 이전에 열린 드롭다운 닫기
      if (
        this.lastOpenedIndex !== undefined &&
        this.lastOpenedRIndex !== undefined
      ) {
        if (this.lastOpenedRIndex === -1) {
          // 이전에 열린 드롭다운이 댓글에 대한 것이었다면
          this.comment[0][this.lastOpenedIndex].showDrop = false;
        } else {
          // 이전에 열린 드롭다운이 리플에 대한 것이었다면
          this.comment[0][this.lastOpenedIndex].replies[
            this.lastOpenedRIndex
          ].showDrop = false;
        }
      }

      // 현재 선택한 객체의 드롭다운 토글
      if (rIndex === -1) {
        // 현재 선택한 객체가 댓글에 대한 것이라면
        this.comment[0][index].showDrop = !this.comment[0][index].showDrop;
      } else {
        console.log("vv");
        // 현재 선택한 객체가 리플에 대한 것이라면
        this.reply[0][rIndex].showDrop = !this.reply[0][rIndex].showDrop;
      }

      // 현재 열린 드롭다운의 index와 rIndex 기록
      this.lastOpenedIndex = index;
      this.lastOpenedRIndex = rIndex;
    },
    BoardToggleDropdown2(commentIndex, rIndex) {
      // 이전에 열린 드롭다운 닫기
      if (
        this.lastOpenedIndex !== undefined &&
        this.lastOpenedRIndex !== undefined
      ) {
        if (this.lastOpenedRIndex === -1) {
          // 이전에 열린 드롭다운이 댓글에 대한 것이었다면
          this.comment[0][this.lastOpenedIndex].showDrop = false;
        } else {
          // 이전에 열린 드롭다운이 리플에 대한 것이었다면
          this.comment[0][this.lastOpenedIndex].replies[
            this.lastOpenedRIndex
          ].showDrop = false;
        }
      }

      // 현재 선택한 리플의 드롭다운 토글
      this.toggleReplyDropdown(commentIndex, rIndex);

      // 현재 열린 드롭다운의 index와 rIndex 기록
      this.lastOpenedIndex = commentIndex;
      this.lastOpenedRIndex = rIndex;
    },

    toggleReplyDropdown(commentIndex, replyIndex) {
      // 리플이 존재하는 경우에만 처리
      if (
        this.comment[0][commentIndex].replies &&
        this.comment[0][commentIndex].replies.length > 0 &&
        this.comment[0][commentIndex].replies[replyIndex]
      ) {
        this.comment[0][commentIndex].replies[replyIndex].showDrop =
          !this.comment[0][commentIndex].replies[replyIndex].showDrop;
      }
    },

    // BoardToggleDropdown2(rIndex) {
    //   console.log("!!!");
    //   // if (
    //   //   this.lastOpenedIndex !== undefined &&
    //   //   this.lastOpenedRIndex !== undefined
    //   // ) {
    //   //   if (this.lastOpenedRIndex === -1) {
    //   //     // 이전에 열린 드롭다운이 댓글에 대한 것이었다면
    //   //     this.reply[0][this.lastOpenedIndex].showDrop = false;
    //   //   } else {
    //   //     // 이전에 열린 드롭다운이 리플에 대한 것이었다면
    //   //     this.reply[0][this.lastOpenedIndex].replies[
    //   //       this.lastOpenedRIndex
    //   //     ].showDrop = false;
    //   //   }
    //   // }
    //   this.reply[0][rIndex].showDrop = !this.reply[0][rIndex].showDrop;
    //   // this.reply[0][rIndex].showDrop = !this.reply[0][rIndex].showDrop;
    //   console.log(this.reply[0][rIndex]);
    // },

    moreReply(commentIndex, cm_id) {
      // 해당 댓글의 showReplies 상태를 토글
      this.getReplyList(cm_id);

      this.comment[0][commentIndex].showReplies =
        !this.comment[0][commentIndex].showReplies;
    },
    updateData(index) {
      this.comment[0][index].showAddReply =
        !this.comment[0][index].showAddReply;
    },
    handleScroll() {
      this.$emit("post-comment-scroll");
    },
    getReplyList(cm_id) {
      console.log(">>>>>>>>!!!!" + cm_id);
      apiClient
        .get(`/jh_reply/getReplyList?cm_id=${cm_id}`)
        .then((response) => {
          this.reply.push(response.data);
          console.log(
            "??----------------------------------------------------------------------------------->"
          );
          console.log(this.reply[0][0].member.m_name);
          console.log(
            "----------------------------------------------------------------------------------->"
          );
        })
        .catch((error) => {
          console.error("Error fetching the board data:", error);
        });
    },
    getCommentListDetail() {
      console.log("getCommentListDetail");

      apiClient
        .get(`/jh_comment/getCommentListDetail?b_id=${this.b_id}`)
        .then((response) => {
          console.log("!!");

          this.comment.push(response.data);
          console.log(response.data);

          // console.log(this.comment);
        })
        .catch((error) => {
          console.error("Error fetching the board data:", error);
        });
    },
    getUserImageUrl(m_img_path, m_img_name) {
      return "http://localhost:8083/" + m_img_path + m_img_name;
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
  },
  props: { childClass: String },
  name: "PostDetailPage",
  components: {},
  created() {
    this.b_id = this.$route.query.b_id;
    this.getCommentListDetail();
  },
};
</script>

<style scoped></style>
