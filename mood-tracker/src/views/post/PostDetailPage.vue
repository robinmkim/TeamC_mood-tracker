<template>
  <div class="flex min-h-screen" @scroll="handleScroll">
    <div class="w-1/5">
      <side-bar></side-bar>
    </div>
    <div class="flex-1 border-x flex">
      <div class="flex-1 border-r">
        <!-- post 내용 -->
        <postDetail
          :b_id="b_id"
          ref="postDetail"
          @post-detail-scroll="handlePostDetailScroll"
        ></postDetail>
      </div>
      <div class="flex-1 flex flex-col h-screen relative">
        <!-- comment 내용 -->
        <div v-if="commentCount > 0" class="flex-none overflow-hidden h-[100%]">
          <postComment
            :b_id="b_id"
            ref="postComment"
            @post-comment-scroll="handlePostCommentScroll"
            :childClass="'h-3/4'"
          ></postComment>
        </div>
        <div v-else-if="commentCount === 0">comment가 없습니다!</div>
        <div
          id="addComment"
          class="w-auto h-1/5 bg-slate-100 overflow-y-auto fixed bottom-0"
        >
          aa
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
export default {
  data() {
    return {
      b_id: this.$route.query.b_id,
      commentCount: -1,
    };
  },
  methods: {
    // 부모 컴포넌트의 스크롤 이벤트 핸들러
    // handleScroll() {
    //   console.log("Scroll event triggered"); // 스크롤 이벤트가 발생했음을 나타내는 로그
    //   const container = this.$refs.scrollContainer;
    //   if (
    //     !this.isLoading &&
    //     container.scrollHeight - container.scrollTop <=
    //       container.clientHeight + 50
    //   ) {
    //     this.getBIdList();
    //   }
    // },

    // handleScroll() {
    //   console.log("Parent component scroll");
    // },
    // handlePostDetailScroll() {
    //   console.log("PostDetail component scroll");
    // },
    // handlePostCommentScroll() {
    //   console.log("PostComment component scroll");
    // },
    getCommentCount() {
      apiClient
        .get(`/jh_comment/allCommentCount?cm_bid=${this.b_id}`)
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
  },
};
</script>

<style scoped></style>
