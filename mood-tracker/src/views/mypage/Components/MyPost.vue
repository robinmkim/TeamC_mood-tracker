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
      // this.getBIdList();
      if (this.isLoading) {
        console.log("post로딩중");
        return; // 이미 로딩 중이면 요청을 하지 않음
      }

      this.isLoading = true;
      apiClient
        .get(`/mypage/mylist?lastRowNum=${this.MylastRowNum}`)
        .then((res) => {
          console.log("my post 넘어옴");
          this.MylastRowNum += res.data.length;
          this.MybIdList = [...this.MybIdList, ...res.data];
          console.los(res.data);
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
