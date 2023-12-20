<!-- ProfileHome.vue -->
<template>
  <div class="relative"> <!-- relative 추가하여 프로필과 메뉴와 묶어준다 -->
    <div class="absolute top-0 left-0 mt-4 ml-4 z-20">
      <div class="rounded-lg shadow-lg w-64 bg-[#88AB8E] p-4">

        <!-- 사용자 정보 및 이미지 시작 -->
        <div class="mb-12"> <!-- 사용자 정보 좌우에 여백 추가 -->
          <div class="h-24 rounded-t-lg mb-4"></div>
          <img
            src="@/assets/Pororo.png"
            height="100"
            width="100"
            class="rounded-full -mt-12 border-4 border-white mx-auto"
            alt="User avatar"
            style="aspect-ratio:100/100;object-fit:cover"
          />
          <div class="text-center mt-2 mb-6">
            <h2 class="text-lg font-semibold">SOP</h2>
            <p class="text-gray-500">기분?</p>
          </div>
        </div>
        <!-- 사용자 정보 및 이미지 끝 -->

      </div>
    </div>

    <!-- 메뉴 시작 -->
    <nav class="fixed bottom-40 p-4 text-left">
      <ul>
        <li v-for="(item, index) in list" :key="index" class="mt-2">
          <a :href="`#content${index + 1}`" class="text flex items-center">
          </a>
        </li>
      </ul>
    </nav>
    <!-- 메뉴 끝 -->

  </div>
</template>

  
  <script>
export default {
  name: "ProfileHome",
  data() {
    return {
      list: ['얼굴인식', '검색', '환경설정', '북마크'],
    };
  },
};
  </script>
  
  <style>
  /* 필요한 스타일 지정 */
  </style>