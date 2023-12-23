<template>
  <div class="m-4">
    <!-- 게시글 헤더 영역 -->
    <div class="postHerder flex flex-row mb-3">
      <div class="h-14 w-14 overflow-hidden relative">
        <img
          class="postDetailUserImg object-contain rounded-full"
          src="..\..\assets\notiProfileImage01.jpg"
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
          class="origin-center w-6 h-6 mr-5"
        >
          <path
            stroke-linecap="round"
            stroke-linejoin="round"
            d="M6.75 12a.75.75 0 1 1-1.5 0 .75.75 0 0 1 1.5 0ZM12.75 12a.75.75 0 1 1-1.5 0 .75.75 0 0 1 1.5 0ZM18.75 12a.75.75 0 1 1-1.5 0 .75.75 0 0 1 1.5 0Z"
          />
        </svg>
      </div>
    </div>
    <!-- 게시글 이미지 영역 -->
    <div class="postImage relative mb-3">
      <div class="flex items-center justify-center relative">
        <div
          class="h-60 overflow-hidden relative rounded-lg flex items-center justify-center"
        >
          <img
            :src="getImageUrl(currentImageIndex)"
            alt="Post image"
            class="items-center rounded-lg"
          />
        </div>
        <a
          v-if="currentImageIndex > 1"
          class="absolute top-1/2 transform -translate-y-1/2 cursor-pointer left-2.5 text-white"
          @click="prevImage"
        >
          &#10094;
        </a>
        <a
          v-if="currentImageIndex < imageCount"
          class="absolute top-1/2 transform -translate-y-1/2 cursor-pointer right-2.5 text-white"
          @click="nextImage"
        >
          &#10095;
        </a>
      </div>
    </div>
    <!-- 게시글 본문 영역 -->
    <div>
      <div class="text-left text-base font-normal flex mb-3">
        강아지... 너무 좋아...<br /><br /><br />
        강아지 최고야<br /><br /><br />
        강아지 좋아요<br />
        강아쥐...........
      </div>
      <!-- like, 이모지 -->
      <div class="flex flex-row">
        <div class="flex items-center">
          <svg
            id="likeButton"
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 24 24"
            stroke-width="2"
            stroke="currentColor"
            class="w-6 h-6"
            @click="handleSvgClick"
          >
            <path
              ref="likePath"
              stroke-linecap="round"
              stroke-linejoin="round"
              d="M21 8.25c0-2.485-2.099-4.5-4.688-4.5-1.935 0-3.597 1.126-4.312 2.733-.715-1.607-2.377-2.733-4.313-2.733C5.1 3.75 3 5.765 3 8.25c0 7.22 9 12 9 12s9-4.78 9-12Z"
            />
          </svg>
          <span @click="handleSvgClick" class="text-sm ml-1">55</span>
          <span class="ml-2">&#128519;</span>
          <!-- 이모티콘 -->
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      currentImageIndex: 1, // 현재 이미지 인덱스
      imageCount: 3, // 이미지 개수
      isLikeClicked: false, // likeButton 클릭 여부를 나타내는 상태 추가
      showMenu: false,
    };
  },
  methods: {
    getImageUrl(index) {
      return require(`../../assets/dog${String(index).padStart(2, "0")}.png`);
    },
    prevImage() {
      this.currentImageIndex = Math.max(1, this.currentImageIndex - 1);
    },
    nextImage() {
      this.currentImageIndex = (this.currentImageIndex % this.imageCount) + 1;
    },
    handleSvgClick() {
      // likeButton 클릭 로직 (색만 바뀜)
      this.isLikeClicked = !this.isLikeClicked;
      const path = this.$refs.likePath;
      if (this.isLikeClicked) {
        path.setAttribute("fill", "red");
        path.setAttribute("stroke", "red");
      } else {
        path.setAttribute("fill", "none");
        path.setAttribute("stroke", "currentColor");
      }
    },
  },
};
</script>

<style scoped></style>
