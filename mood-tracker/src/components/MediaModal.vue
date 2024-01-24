<template>
  <div
    v-if="isOpen"
    class="fixed inset-0 bg-black bg-opacity-50 overflow-y-auto h-full w-full flex justify-center items-center z-50"
    @click="closeModal"
  >
    <div class="lg:w-3/5 h-2/3 flex flex-col">
      <div class="ml-auto cursor-pointer mb-1 max-h-full" @click="closeModal">
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

        <a
          v-if="currentImageIndex > 1"
          class="absolute top-1/2 transform -translate-y-1/2 cursor-pointer left-2.5 text-black"
          @click="prevImage"
        >
          &#10094;
        </a>
        <a
          v-if="currentImageIndex < mediaList.length"
          class="absolute top-1/2 transform -translate-y-1/2 cursor-pointer right-2.5 text-black"
          @click="nextImage"
        >
          &#10095;
        </a>
      </div>
      <div
        class="w-full min-h-full bg-white rounded shadow-lg flex items-center justify-center"
        @click.stop=""
      >
        <img :src="getImageUrl(mediaList[currentImageIndex - 1])" alt="" />
      </div>
    </div>
  </div>
</template>

<script>
// import apiClient from "@/utils/apiClient";

export default {
  data() {
    return {
      currentImageIndex: 1,
      selectedPostId: null,
      commentList: [],
    };
  },
  name: "MediaModal",
  components: {},
  props: {
    isOpen: {
      type: Boolean,
      default: false,
    },
    mediaList: {
      type: Object,
      required: false,
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
    getImageUrl(media) {
      // md_path와 md_name을 결합하여 이미지의 전체 경로를 반환합니다.
      return `http://localhost:8083/${media.md_path}${media.md_name}`;
    },
    closeModal() {
      this.$emit("close");
      this.selectedPostId = null;
    },
    prevImage() {
      // 이전 이미지로 이동합니다.
      if (this.currentImageIndex > 1) {
        this.currentImageIndex -= 1;
      }
    },
    nextImage() {
      // 다음 이미지로 이동합니다.
      if (this.currentImageIndex < this.imageCount) {
        this.currentImageIndex += 1;
      }
    },
  },
  created() {},
};
</script>
