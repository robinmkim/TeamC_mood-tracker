<template>
  <div class=""> <!-- relative 추가하여 프로필과 메뉴와 묶어준다 -->
    <div class="">
      <div class="rounded-lg shadow-lg w-auto mx-2 mt-2 bg-[#FFF5ED] p-4">

        <!-- 사용자 정보 및 이미지 시작 -->
        <div class="mb-12"> <!-- 사용자 정보 좌우에 여백 추가 -->
          <div class="h-24 rounded-t-lg mb-4"></div>
          <img
            src=""
            height="100"
            width="100"
            class="rounded-full -mt-12 border-4 border-white mx-auto"
            alt="User avatar"
            style="aspect-ratio:100/100;object-fit:cover"
          />
          <div class="text-center mt-2 mb-6">
            <h2 class="text-lg font-bold">SOP</h2>
            <p class="text-slate-500">기분?</p>
          </div>
        </div>
        <!-- 사용자 정보 및 이미지 끝 -->

      </div>
    </div>

    <!-- 메뉴 시작 -->
    <nav class="p-4 text-left">
      <router-link to="/postwrite" class="text-lg"><div>게시물 작성</div></router-link>
      <router-link to="/faceanalyze" class="text-lg"><div>얼굴 인식</div></router-link>
      <div class="text-lg">검색</div>
      <div class="text-lg">문의</div>
    </nav>
    <!-- 메뉴 끝 -->

  </div>
</template>

  
  <script>
export default {
  name: "SideBar",

  };

  </script>
  
  <style>
  /* 필요한 스타일 지정 */
  </style>