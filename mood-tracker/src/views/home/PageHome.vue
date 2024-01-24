<template>
  <div class="flex h-full relative">
    <div class="flex-1 h-full overflow-hidden">
      <div class="flex flex-col h-full">
        <!-- PostWriteVue를 스크롤 영역 밖에 위치 -->
        <PostWriteVue class="p-3" @update-parent-data="updateParentData" />
        <!-- post-detail 스크롤 영역 내에 배치 -->
        <div class="overflow-y-auto">
          <post-detail
            v-for="bId in bIdList"
            :key="bId"
            :b_id="bId"
            :isDropdownOpen="openB_id === bId"
            @toggle-dropdown="toggleDropdown"
          />
        </div>
      </div>
      <div v-if="isLoading" class="loading-spinner">
        <!-- 로딩 스피너 -->
      </div>
    </div>
  </div>
</template>

<script>
import apiClient from "@/utils/apiClient";
import PostDetail from "@/views/post/components/PostDetail";
import PostWriteVue from "../post/PostWrite.vue";

export default {
  name: "PageHome",
  components: {
    PostDetail,
    PostWriteVue,
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
    updateParentData() {
      window.location.reload();
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
