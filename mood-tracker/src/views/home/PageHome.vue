<template>
  <div class="flex h-full">
    <div class="w-1/5">
      <side-bar></side-bar>
    </div>
    <div class="flex-1 border-x overflow-auto" ref="scrollContainer">
      <post-detail
        v-for="bId in bIdList"
        :key="bId"
        :b_id="bId"
        :isDropdownOpen="openB_id === bId"
        @toggle-dropdown="toggleDropdown"
      />

      <div v-if="isLoading" class="loading-spinner">
        <!-- 로딩 스피너 -->
      </div>
    </div>
    <div class="w-1/5">side menu</div>
  </div>
</template>

<script>
import apiClient from "@/utils/apiClient";
import SideBar from "@/components/SideBar";
import PostDetail from "@/components/post/PostDetail";
export default {
  name: "PageHome",
  components: {
    SideBar,
    PostDetail,
  },
  data() {
    return {
      bIdList: [],
      lastRowNum: 0,
      isLoading: false,
      openB_id: null,
    };
  },
  created() {
    this.getBIdList();
  },
  methods: {
    toggleDropdown(b_id) {
      // 클릭한 댓글 ID와 현재 열린 드롭다운의 댓글 ID를 비교하여 상태를 토글
      this.openB_id = this.openB_id === b_id ? null : b_id;
    },
    getBIdList() {
      if (this.isLoading) {
        return; // 이미 로딩 중이면 요청을 하지 않음
      }
      this.isLoading = true; // 로딩 시작

      apiClient
        .get(`/post/list?lastRowNum=${this.lastRowNum}`)
        .then((res) => {
          this.bIdList = [...this.bIdList, ...res.data];
          this.lastRowNum += res.data.length;
        })
        .catch((err) => {
          console.log(err);
        })
        .finally(() => {
          this.isLoading = false; // 로딩 완료
        });
    },
    handleScroll() {
      console.log("Scroll event triggered"); // 스크롤 이벤트가 발생했음을 나타내는 로그
      const container = this.$refs.scrollContainer;
      if (
        !this.isLoading &&
        container.scrollHeight - container.scrollTop <=
          container.clientHeight + 50
      ) {
        this.getBIdList();
      }
    },
  },
  mounted() {
    this.$refs.scrollContainer.addEventListener("scroll", this.handleScroll);
  },
  beforeUnmount() {
    this.$refs.scrollContainer.removeEventListener("scroll", this.handleScroll);
  },
};
</script>
