<template>
  <div class="flex h-full" @scroll="handleScroll">
    <div class="flex-1 border-x flex h-full">
      <div
        class="flex-1 flex flex-col border-r h-full relative overflow-y-auto"
      >
        <!-- post 내용 -->
        <postDetail
          :b_id="b_id"
          ref="postDetail"
          @post-detail-scroll="handlePostDetailScroll"
          :isDropdownOpen="openCm_id === cm_id"
          @toggle-dropdown="toggleDropdown"
          :caller="callerComponent"
        ></postDetail>
      </div>
      <div class="flex-1 flex flex-col h-full relative overflow-y-auto">
        <!-- comment 내용 -->
        <div v-if="commentCount > 0" class="overflow-hidden h-[95%]">
          <div class="comment-list max-h-full overflow-y-auto">
            <CommentList
              v-for="cm_id in commentList"
              :key="cm_id"
              :cm_id="cm_id"
              :isDropdownOpen="openCm_id === cm_id"
              @toggle-dropdown="toggleDropdown"
              @delComment="delComment"
              @updateReply="updateReply"
            />
          </div>
        </div>
        <div v-else-if="commentCount === 0" class="h-[95%]">
          comment가 없습니다!
        </div>
        <div class="flex border-t items-center justify-center p-2">
          <textarea
            class="text-sm border-b border-slate-200 w-[95%] h-6 resize-none focus:outline-slate-400"
            placeholder=" 답글입력"
            v-model="content"
            @input="adjustHeight"
          ></textarea>

          <div
            class="flex flex-col text-xs justify-center items-center flex-grow"
          >
            <div
              class="hover:bg-teal-100 rounded-lg flex w-[80%] h-[80%] justify-center items-center"
              @click="addComment"
              type="submit"
            >
              등록
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- <div class="w-1/5">side menu</div> -->
  </div>
</template>

<script>
import apiClient from "@/utils/apiClient";
import PostDetail from "@/views/post/components/PostDetail";
import { jwtDecode } from "jwt-decode";

import CommentList from "@/views/post/components/commentAndReply/CommentList";
// import { number } from "yup";

export default {
  data() {
    return {
      b_id: this.$route.query.b_id,
      commentCount: -1,
      loginuserId: null,
      content: "",
      commentList: [],
      openCm_id: null,
      callerComponent: "postDetailPage",
    };
  },
  methods: {
    updateReply() {
      this.getCommentCount();
    },
    delComment() {
      this.getCm_idList();
      this.getCommentCount();
      // location.reload();
    },
    toggleDropdown(cm_id) {
      // 클릭한 댓글 ID와 현재 열린 드롭다운의 댓글 ID를 비교하여 상태를 토글
      this.openCm_id = this.openCm_id === cm_id ? null : cm_id;
    },

    getCm_idList() {
      apiClient
        .get(`/jh_comment/getCm_idList?b_id=${this.b_id}`)
        .then((response) => {
          this.commentList = response.data;
        })
        .catch((error) => {
          console.error("Error fetching the board data:", error);
        });
    },
    addComment() {
      const formData = new FormData();
      formData.append("cm_content", this.content);
      // formData.append("m_id", this.loginuserId);
      formData.append("b_id", this.b_id);
      for (let [key, value] of formData.entries()) {
        console.log(`${key}: ${value}`);
      }
      apiClient
        .post("/jh_comment/addComment", formData, {
          headers: { "Content-Type": "multipart/form-data" },
        })
        .then((res) => {
          console.log("res" + res);
          if (res.data === 1) {
            this.getCm_idList();
            this.getCommentCount();
            this.content = null;
          }
        })
        .catch((error) => {
          console.log("formData" + formData);

          console.log(error);
        });
    },
    getLoginUser() {
      // jwtToken을 decode해서 m_id를 추출한다.
      const token = localStorage.getItem("jwtToken");
      console.log(token);
      const decoded = jwtDecode(token);
      console.log(decoded);
      this.loginuserId = decoded.m_id;
    },
    adjustHeight(e) {
      // textarea높이 자동 조절
      const element = e.target;
      element.style.height = "auto";
      element.style.height = element.scrollHeight + "px";
    },
    getCommentCount() {
      apiClient
        .get(`/jh_comment/allCommentCount?b_id=${this.b_id}`)
        .then((response) => {
          this.$store.commit("setTotalCommentCount", response.data);
          this.commentCount = response.data;
        })
        .catch((error) => {
          console.error("Error fetching the board data:", error);
        });
    },
  },
  props: {},
  name: "PostDetailPage",
  components: {
    PostDetail,
    // postComment,
    CommentList,
  },
  created() {
    this.getCommentCount();
    this.getLoginUser();
    this.getCm_idList();
  },
};
</script>

<style scoped></style>
