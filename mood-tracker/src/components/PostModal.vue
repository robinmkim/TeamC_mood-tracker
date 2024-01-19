<template>
  <div
    v-if="isOpen"
    class="fixed inset-0 bg-black bg-opacity-50 overflow-y-auto h-full w-full flex justify-center items-center"
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
      <div
        class="h-5/6 bg-white rounded shadow-lg flex overflow-hidden"
        @click.stop=""
      >
        <!-- PostList -->
        <div class="w-2/5 flex flex-col min-h-5/6 overflow-auto">
          <div class="flex h-14 border-b justify-center items-center">
            {{ this.month }}월 {{ day }}일
          </div>
          <div class="flex flex-col" v-if="ByDateList.length > 0">
            <PostList
              v-for="bId in ByDateList"
              @postSelected="onPostSelected"
              :key="bId"
              :b_id="bId"
            />
          </div>
          <div v-else>데이터가 없습니다.</div>
        </div>
        <!-- postDetail -->
        <div class="border-x flex flex-grow w-3/5">
          <PostDetailPage :b_id="b_id" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { number } from "yup";
// import PostDetail from "./post/PostDetail";
import PostList from "./post/PostList";
import PostDetailPage from "@/views/post/PostDetailPage.vue";
export default {
  name: "PostModal",
  components: {
    // PostDetail,
    PostList,
    PostDetailPage,
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
    onPostSelected(b_id) {
      // PostList에서 전달받은 b_id를 사용하여 처리
      this.b_id = b_id;
    },
    closeModal() {
      this.$emit("close");
    },
  },
};
</script>
