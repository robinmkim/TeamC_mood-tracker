<template>
  <div
    style="overflow: scroll  width: 100%; height: 500px; padding: 10px;"
    class="flex-1 border-x overflow-auto"
    ref="likedScrollContainer"
    @scroll="handleLikedScroll"
  >
    <post-detail v-for="bId in LikebIdList" :key="bId" :b_id="bId" />
    <div v-if="isLoading" class="loading-spinner">
      <!-- 로딩 스피너 -->
    </div>
  </div>
</template>

<script>
import apiClient from "@/utils/apiClient";
import PostDetail from "@/views/post/components/PostDetail";

export default {
  name: "MyLikePost",
  components: {
    PostDetail,
  },
  data() {
    return {
      LikebIdList: [],
      isLoading: false,
      LikedlastRowNum: 0,
    };
  },
  props: {
    m_id: {
      type: Number,
      required: true,
    },
  },
  created() {
    this.getLikedBoardList();
  },

  methods: {
    getLikedBoardList() {
      if (this.isLoading) {
        console.log("like로딩중");
        return; // 이미 로딩 중이면 요청을 하지 않음
      }
      this.isLoading = true;
      apiClient
        .get(
          `/mypage/likelist?lastRowNum=${this.LikedlastRowNum}&m_id=${this.m_id}`
        )
        .then((res) => {
          console.log("liked post 넘어옴");
          this.LikedlastRowNum += res.data.length;
          this.LikebIdList = [...this.LikebIdList, ...res.data];
          this.handleLikedScroll();
        })
        .catch((err) => {
          console.log(err, "like 뭔가 안됨");
        })
        .finally(() => {
          this.isLoading = false; // 로딩 완료
        });
    },

    handleLikedScroll() {
      console.log("Like Scroll event triggered");
      const container = this.$refs.likedScrollContainer;
      if (
        !this.isLoading &&
        container.scrollHeight - container.scrollTop <=
          container.clientHeight + 50
      ) {
        console.log("like scroll 후 데이터 로딩");
        this.getLikedBoardList();
      }
    },
  },
  mounted() {
    // console.log("안녕 전");
    // console.log("현재 탭 번호? : " + this.currentTab);
    //document.addEventListener("scroll", this.handleScroll, true);
    // 'post' 탭의 인덱스가 1이라 가정합니다. 만약 다르다면 해당 인덱스로 변경하세요.
    this.$refs.likedScrollContainer.addEventListener(
      "scroll",
      this.handleLikedScroll
    );
  },

  beforeUnmount() {
    //window.removeEventListener("scroll", this.handleScroll);
    this.$refs.likedScrollContainer.removeEventListener(
      "scroll",
      this.handleLikedScroll
    );
  },
};
</script>

<style scoped="scoped"></style>
