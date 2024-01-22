<template lang="">
  <div>
    <div
      style="overflow: scroll  width: 100%; height: 500px; padding: 10px;"
      class="flex-1 border-x overflow-auto h-full"
      ref="postScroll"
      @scroll="handlePostScroll"
    >
      <post-detail v-for="bId in MybIdList" :key="bId" :b_id="bId" />
      <div v-if="isLoading" class="loading-spinner">
        <!-- 로딩 스피너 -->
      </div>
    </div>
  </div>
</template>
<script>
import apiClient from "@/utils/apiClient";
import PostDetail from "@/components/post/PostDetail.vue";
import { jwtDecode } from "jwt-decode";

export default {
  name: "MyPost",
  components: {
    PostDetail,
  },
  data() {
    return {
      MybIdList: [],
      MylastRowNum: 0,
      isLoading: false,
    };
  },
  created() {
    this.getMyBoardList();
  },
  methods: {
    getMyBoardList() {
      let mid = this.$route.path.replace("/", "");
      // 파라미터로 받은 memberId가 비어있으면 내 정보를 가져옴

      if (mid === "") {
        const token = localStorage.getItem("jwtToken");
        const decoded = jwtDecode(token);
        mid = decoded.m_id;
      }

      // this.getBIdList();
      if (this.isLoading) {
        console.log("post로딩중");
        return; // 이미 로딩 중이면 요청을 하지 않음
      }

      this.isLoading = true;
      apiClient
        .get(`/mypage/mylist?lastRowNum=${this.MylastRowNum}&mid=${mid}`)
        .then((res) => {
          console.log("my post 넘어옴");
          this.MylastRowNum += res.data.length;
          this.MybIdList = [...this.MybIdList, ...res.data];
          // this.handlePostScroll();
        })
        .catch((err) => {
          console.log(err, "post 뭔가 안됨");
        })
        .finally(() => {
          this.isLoading = false; // 로딩 완료
        });
    },
    handlePostScroll() {
      console.log("handlePostScroll이 시작했습니다. ");
      const container = this.$refs.postScroll;
      if (
        !this.isLoading &&
        container.scrollHeight - container.scrollTop <=
          container.clientHeight + 5
      ) {
        console.log("post scroll 후 데이터 로딩");
        this.getMyBoardList();
      }
    },
  },
  mounted() {
    // console.log("안녕 전");
    // console.log("현재 탭 번호? : " + this.currentTab);
    //document.addEventListener("scroll", this.handleScroll, true);

    this.$refs.postScroll.addEventListener("scroll", this.handlePostScroll);
  },

  beforeUnmount() {
    //window.removeEventListener("scroll", this.handleScroll);

    this.$refs.postScroll.removeEventListener("scroll", this.handlePostScroll);
  },
};
</script>
<style lang=""></style>
