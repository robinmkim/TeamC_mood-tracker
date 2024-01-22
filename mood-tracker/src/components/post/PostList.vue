<template>
  <div class="flex h-full items-center border-b p-3" @click="selectPost">
    <div class="flex ml-3" style="justify-content: flex-start">
      <img :src="getImageUrl()" alt="postEmotion" class="w-8 h-8" />
    </div>
    <div class="flex ml-3 from-neutral-800">{{ getShortenedContent() }}</div>
  </div>
</template>

<script>
import apiClient from "@/utils/apiClient";
export default {
  name: "PostList",
  props: {
    // Step 1: Props 정의
    b_id: {
      type: Number,
      required: true,
    },
  },
  components: {},
  data() {
    return {
      board: {
        b_id: null,
        m_id: null,
        isMylike: false,
        b_content: "",
        b_sentiment: "",
        regdate: "",
        mediaList: [],
        member: null,
        countLike: 0,
        countComments: 0,
        myLike: false,
        showDrop: this.isDropdownOpen,
      },
      maxContentLength: 20,
    };
  },
  created() {
    this.loadBoardData();
  },
  methods: {
    selectPost() {
      this.$emit("postSelected", this.b_id);
    },
    loadBoardData() {
      apiClient
        .get(`/jh_post/get/${this.b_id}`)
        .then((response) => {
          this.board = response.data;
          console.log(this.board.b_sentiment);
        })
        .catch((error) => {
          console.error("Error fetching the board data:", error);
        });
    },
    getImageUrl() {
      // md_path와 md_name을 결합하여 이미지의 전체 경로를 반환합니다.
      return `http://localhost:8083/images/${this.board.b_sentiment}.png`;
    },
    getShortenedContent() {
      if (this.board.b_content.length > this.maxContentLength) {
        return this.board.b_content.slice(0, this.maxContentLength) + "  ...";
      } else {
        return this.board.b_content;
      }
    },
  },
  mounted() {},
  beforeUnmount() {},
};
</script>
