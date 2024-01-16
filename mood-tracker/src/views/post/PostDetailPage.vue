<template>
  <div class="flex h-full" @scroll="handleScroll">
    <div class="w-1/5">
      <side-bar></side-bar>
    </div>
    <div class="flex-1 border-x flex">
      <div class="flex-1 flex flex-col border-r h-full relative">
        <!-- post 내용 -->
        <postDetail
          :b_id="b_id"
          ref="postDetail"
          @post-detail-scroll="handlePostDetailScroll"
        ></postDetail>
      </div>
      <div class="flex-1 flex flex-col h-full relative">
        <!-- comment 내용 -->
        <div v-if="commentCount > 0" class="overflow-hidden h-[95%]">
          <postComment
            :b_id="b_id"
            ref="postComment"
            @post-comment-scroll="handlePostCommentScroll"
            :childClass="'h-full'"
          ></postComment>
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
import SideBar from "@/components/SideBar";
import PostDetail from "@/components/post/PostDetail";
import postComment from "@/components/post/PostComment";
import { jwtDecode } from "jwt-decode";
export default {
  data() {
    return {
      b_id: this.$route.query.b_id,
      commentCount: -1,
      loginuserId: null,
      content: "",
    };
  },
  methods: {
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
            // 응답 값이 1이면 페이지를 새로고침
            window.location.reload();
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
      // console.log("this.loginuserId: " + this.loginuserId);

      // const formData = new FormData();
      // formData.append("file1", this.$refs.fileInput.files[0]);
      // formData.append("m_id", decoded.m_id); // 유저 회원번호
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
          this.commentCount = response.data;
          console.log("==>" + this.commentCount);
        })
        .catch((error) => {
          console.error("Error fetching the board data:", error);
        });
    },
  },
  props: {},
  name: "PostDetailPage",
  components: {
    SideBar,
    PostDetail,
    postComment,
  },
  created() {
    this.getCommentCount();
    this.getLoginUser();
  },
};
</script>

<style scoped></style>
