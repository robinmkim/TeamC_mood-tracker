<template>
  <div class="flex h-full" ref="scrollContainer">
    <div class="flex-1 border-x h-full">
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
    <!-- <div class="w-1/5">side menu</div> -->
  </div>
</template>

<script>
import apiClient from "@/utils/apiClient";
import PostDetail from "@/components/post/PostDetail";

export default {
  name: "PageHome",
  components: {
    PostDetail,
  },
  data() {
    return {
      bIdList: [],
      lastRowNum: 0,
      isLoading: false,
      openB_id: null,
      scrollPosition: 0, // 스크롤 위치를 저장할 변수
    };
  },
  created() {
    this.getBIdList();
  },
  methods: {
    toggleDropdown(b_id) {
      this.openB_id = this.openB_id === b_id ? null : b_id;
    },
    getBIdList() {
      if (this.isLoading) {
        return;
      }
      this.isLoading = true;

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
          this.isLoading = false;
        });
    },
    handleScroll() {
      this.scrollPosition = window.scrollY;

      if (
        !this.isLoading &&
        window.innerHeight + window.scrollY >= document.body.offsetHeight - 1
      ) {
        this.getBIdList();
      }
    },
  },
  mounted() {
    window.addEventListener("scroll", this.handleScroll);
  },
  beforeUnmount() {
    window.removeEventListener("scroll", this.handleScroll);
  },
};
</script>
