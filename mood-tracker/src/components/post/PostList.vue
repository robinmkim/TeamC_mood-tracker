<template>
  <div
    @click="selectPost"
    class="flex h-16 pl-4 items-center justify-start border-b"
  >
    <div class="flex justify-start">
      <img :src="getImageUrl()" alt="postEmotion" class="w-8 h-8" />
    </div>
    <div class="flex justify-start ml-3">{{ board.b_content }}</div>
  </div>
</template>

<script>
import apiClient from "@/utils/apiClient";

export default {
  props: {
    b_id: {
      type: Number,
      required: true,
    },
  },
  name: "PostList",
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
      emotionMap: {
        "😆": "happy",
        "😡": "angry",
        "😬": "anxiety",
        "🤕": "hurt",
        "😐": "neutral",
        "😢": "sad",
        "😨": "surprise",
      },
      user: {
        m_name: null,
        m_hanble: null,
        m_img_name: "",
        m_img_path: "",
      },
    };
  },
  computed: {
    shotText() {
      // 본문의 일부만 보여주되, 본문이 존재하는 경우에만 작업을 수행합니다.
      return this.board.b_content ? this.board.b_content.slice(0, 20) : "";
    },
    sentimentEmoji() {
      // 감정에 해당하는 이모지를 반환합니다.
      return (
        Object.keys(this.emotionMap).find(
          (key) => this.emotionMap[key] === this.board.b_sentiment
        ) || ""
      );
    },
  },
  created() {
    this.loadBoardData();
  },
  methods: {
    selectPost() {
      this.$emit("postSelected", this.b_id);
    },
    getImageUrl() {
      // md_path와 md_name을 결합하여 이미지의 전체 경로를 반환합니다.
      return `http://localhost:8083/images/${this.board.b_sentiment}.png`;
    },
    loadBoardData() {
      // 게시글 데이터를 로드합니다.
      apiClient
        .get(`/post/get/${this.b_id}`)
        .then((response) => {
          this.board = response.data;
          console.log(this.board);
        })
        .catch((error) => {
          console.error("Error fetching the board data:", error);
        });
    },
  },
  mounted() {},
  beforeUnmount() {},
};
</script>
