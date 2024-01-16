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
        <!--리스트-->
        <div class="flex flex-col min-h-5/6 flex-grow overflow-auto">
          <div class="flex h-14 border-b justify-center items-center">
            {{ this.month }}월 {{ day }}일
          </div>
          <div
            class="flex flex-col h-16 border-b items-center justify-start pl-4"
            v-if="ByDateList.length > 0"
          >
            <post-detail v-for="bId in ByDateList" :key="bId" :b_id="bId" />
          </div>
          <div v-else>데이터가 없습니다.</div>
        </div>
        <div class="border-x flex flex-grow">
          <div class="h-full">
            <!-- post 내용 -->
            <postDetail></postDetail>
          </div>
          <div class="flex flex-col flex-grow border-l border-l-slate-300">
            <!-- 댓글창 -->
            <div class="p-3 border-b border-b-slate-300">
              <div class="postHerder flex flex-row m">
                <div class="h-14 w-14 overflow-hidden relative">
                  <img
                    class="postDetailUserImg object-contain rounded-full"
                    src="@/assets/logo.png"
                    alt="user icon"
                  />
                </div>
                <div class="flex flex-row items-center mx-3">
                  <div class="notiUserName font-bold text-lg">UserName</div>
                  <div class="text-slate-400 text-sm ml-2">2분전</div>
                </div>
                <div class="icon ml-auto -mr-3 mt-3 relative inline-block">
                  <!-- 미트볼 아이콘-->
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    fill="none"
                    viewBox="0 0 24 24"
                    stroke-width="1.5"
                    stroke="currentColor"
                    class="w-6 h-6 pt-1"
                  >
                    <path
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      d="M12 6.75a.75.75 0 1 1 0-1.5.75.75 0 0 1 0 1.5ZM12 12.75a.75.75 0 1 1 0-1.5.75.75 0 0 1 0 1.5ZM12 18.75a.75.75 0 1 1 0-1.5.75.75 0 0 1 0 1.5Z"
                    />
                  </svg>
                </div>
              </div>
              <div class="text-left ml-6 mt-2">댓글내용~~~ 01</div>
            </div>

            <div class="p-3 border-b border-b-slate-300 pl-10">
              <div class="postHerder flex flex-row m">
                <div class="h-14 w-14 overflow-hidden relative">
                  <img
                    class="postDetailUserImg object-contain rounded-full"
                    src="@/assets/logo.png"
                    alt="user icon"
                  />
                </div>
                <div class="flex flex-row items-center mx-3">
                  <div class="notiUserName font-bold text-lg">UserName2</div>
                  <div class="text-slate-400 text-sm ml-2">2분전</div>
                </div>
                <div class="icon ml-auto -mr-3 mt-3 relative inline-block">
                  <!-- 미트볼 아이콘-->
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    fill="none"
                    viewBox="0 0 24 24"
                    stroke-width="1.5"
                    stroke="currentColor"
                    class="w-6 h-6 pt-1"
                  >
                    <path
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      d="M12 6.75a.75.75 0 1 1 0-1.5.75.75 0 0 1 0 1.5ZM12 12.75a.75.75 0 1 1 0-1.5.75.75 0 0 1 0 1.5ZM12 18.75a.75.75 0 1 1 0-1.5.75.75 0 0 1 0 1.5Z"
                    />
                  </svg>
                </div>
              </div>
              <div class="text-left ml-6 mt-2">댓글내용~~~ 02</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { number } from "yup";
import PostDetail from "./post/PostDetail";
export default {
  name: "PostModal",
  components: {
    PostDetail,
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
    closeModal() {
      this.$emit("close");
    },
  },
};
</script>
