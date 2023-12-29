<template>
  <div class="flex flex-col items-center justify-center h-full p-6">
    <div class="bg-white rounded-lg p-8 shadow-md w-full max-w-sm">
      <div class="flex flex-col items-center mb-6">
        <!-- 로고나 아이콘을 넣을 수 있는 공간 -->
        <svg
          class="w-12 h-12 text-gray-500 mb-2"
          fill="none"
          viewBox="0 0 24 24"
          stroke="currentColor"
        >
          <!-- SVG content here -->
        </svg>
        <span class="text-xl font-semibold">로그인</span>
      </div>
      <form class="flex flex-col space-y-4">
        <label id="userid" class="text-left">ID</label>
        <input
          id="userid"
          type="text"
          placeholder="사용자 아이디 또는 이메일"
          class="border-2 border-gray-300 p-2 rounded-md focus:border-blue-500 focus:outline-none"
        />
        <label id="password" class="text-left">PW</label>
        <input
          id="password"
          type="password"
          placeholder="비밀번호"
          class="border-2 border-gray-300 p-2 rounded-md focus:border-blue-500 focus:outline-none"
        />
        <button
          type="submit"
          class="bg-[#64CCC5] text-white p-2 rounded-lg shadow hover:bg-[#3f827e]"
        >
          로그인
        </button>
      </form>
      <div class="flex flex-col items-center justify-between mt-3">
        <div class="flex w-full items-center space-x-2 mb-3">
          <div class="bg-gray-200 h-0.5 flex-grow"></div>
          <div class="text-gray-400 text-sm px-2">또는</div>
          <div class="bg-gray-200 h-0.5 flex-grow"></div>
        </div>
        <button
          type="button"
          class="bg-[#64CCC5] w-full text-white p-2 rounded-lg shadow hover:bg-[#3f827e]"
        >
          계정 만들기
        </button>
        <button type="button" class="mt-4">비밀번호를 잊으셨나요?</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "LogIn",
  components: {},
};
</script>

<style scoped></style>
