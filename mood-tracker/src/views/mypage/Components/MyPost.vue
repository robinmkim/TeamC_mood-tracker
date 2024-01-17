<template lang="">
  <div>
    <div
      style="overflow: scroll  width: 100%; height: 500px; padding: 10px;"
      class="flex-1 border-x overflow-auto h-full"
      ref="PostScroll"
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
export default {
  name: "MyPost",
  data() {
    return {
      MybIdList: [],
      MylastRowNum: 0,
      isLoading: false,
    };
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
      console.log("Post Scroll event triggered");
      console.log("지금 아이디" + this.currentTab);

      if (this.loadTime) {
        clearTimeout(this.loadTime);
      }

      this.loadTime = setTimeout(() => {
        if (this.currentTab === 1) {
          const container = this.$refs.postScrollContainer;
          if (
            !this.isLoading &&
            container.scrollHeight - container.scrollTop <=
              container.clientHeight + 50
          ) {
            console.log("post scroll 후 데이터 로딩");
            this.getMyBoardList();
          }
        }
      }, 500);
    },
  },
  mounted() {
    // console.log("안녕 전");
    // console.log("현재 탭 번호? : " + this.currentTab);
    //document.addEventListener("scroll", this.handleScroll, true);
    this.$watch("currentTab", () => {
      if (this.currentTab === 1) {
        // 'post' 탭의 인덱스가 1이라 가정합니다. 만약 다르다면 해당 인덱스로 변경하세요.
        this.$refs.postScrollContainer.addEventListener(
          "scroll",
          this.handlePostScroll
        );
      }
    });
  },

  beforeUnmount() {
    //window.removeEventListener("scroll", this.handleScroll);
    if (this.currentTab === 1) {
      this.$refs.postScrollContainer.removeEventListener(
        "scroll",
        this.handlePostScroll()
      );
    }
  },
  created() {
    this.getMyBoardList();
  },
};
</script>
<style lang=""></style>
