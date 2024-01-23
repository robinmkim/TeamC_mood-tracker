<template>
  <div
    v-if="isOpen"
    class="fixed inset-0 bg-black bg-opacity-50 overflow-y-auto h-full w-full flex justify-center items-center z-50"
    @click="closeModal"
  >
    <div class="lg:w-3/5 h-2/3 flex flex-col">
      <div class="ml-auto cursor-pointer mb-1" @click="closeModal">
        <!--x버튼-->
        <svg
          xmlns="http://www.w3.org/2000/svg"
          viewBox="0 0 24 24"
          fill="currentColor"
          class="w-10 h-10"
        >
          <path
            fill-rule="evenodd"
            d="M5.47 5.47a.75.75 0 0 1 1.06 0L12 10.94l5.47-5.47a.75.75 0 1 1 1.06 1.06L13.06 12l5.47 5.47a.75.75 0 1 1-1.06 1.06L12 13.06l-5.47 5.47a.75.75 0 0 1-1.06-1.06L10.94 12 5.47 6.53a.75.75 0 0 1 0-1.06Z"
            clip-rule="evenodd"
          />
        </svg>
      </div>
      <div class="w-full h-full bg-white rounded shadow-lg flex" @click.stop="">
        <!-- PostList -->
        <div class="w-1/3 flex flex-col min-h-5/6 bg-red-200">
          <div class="flex border-b justify-center items-center h-1/6">
            {{ this.month }}월 {{ day }}일
          </div>
          <div
            class="flex flex-col overflow-y-auto"
            v-if="ByDateList.length > 0"
          >
            <PostList
              v-for="bId in ByDateList"
              :key="bId"
              :b_id="bId"
              @postSelected="handlePostSelected"
            />
          </div>
          <div v-else>데이터가 없습니다.</div>
        </div>
        <!-- postDetail -->
        <div class="border-x flex flex-grow w-2/3 overflow-y-auto">
          <div v-if="this.selectedPostId === null">아무것도 안 불러옴!</div>
          <div v-else class="w-full">
            <div class="shadow">
              <postDetail
                :b_id="selectedPostId"
                :isDropdownOpen="openCm_id === cm_id"
                :on-board-data-loaded="getCm_idList"
                customClass="w-full"
                @toggle-dropdown="toggleDropdown"
              ></postDetail>
            </div>

            <div>
              <CommentList
                v-for="cm_id in commentList"
                :key="cm_id"
                :cm_id="cm_id"
                :isDropdownOpen="openCm_id === cm_id"
                @toggle-dropdown="toggleDropdown"
                class="mx-6"
              />
            </div>

            <!-- <PostDetailPage :bb_id="selectedPostId" /> -->
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import apiClient from "@/utils/apiClient";
import { number } from "yup";
// import PostDetail from "./post/PostDetail";
import PostList from "./post/PostList";
import PostDetail from "@/components/post/PostDetail";
import CommentList from "@/components/post/commentAndReply/CommentList";

export default {
  data() {
    return {
      selectedPostId: null,
      commentList: [],
    };
  },
  name: "PostModal",
  components: {
    // PostDetail,
    PostList,
    PostDetail,
    CommentList,
  },
  props: {
    isOpen: {
      type: Boolean,
      default: false,
    },
    ByDateList: {
      type: Array,
      default: () => [],
    },
    day: {
      type: String,
      default: null,
    },
    month: {
      type: number,
      required: true,
    },
  },
  watch: {
    isOpen(newValue) {
      if (newValue) {
        document.body.classList.add("overflow-hidden");
        console.log(this.selectedDate);
      } else {
        document.body.classList.remove("overflow-hidden");
      }
    },
  },
  mounted() {
    if (this.isOpen) {
      document.body.classList.add("overflow-hidden");
    }
  },
  beforeUnmount() {
    document.body.classList.remove("overflow-hidden");
  },
  methods: {
    getCm_idList() {
      apiClient
        .get(`/jh_comment/getCm_idList?b_id=${this.selectedPostId}`)
        .then((response) => {
          this.commentList = response.data;
        })
        .catch((error) => {
          console.error("Error fetching the board data:", error);
        });
    },
    handlePostSelected(b_id) {
      this.selectedPostId = b_id;
    },
    closeModal() {
      this.$emit("close");
      this.selectedPostId = null;
      this.getCm_idList();
    },
  },
};
</script>
