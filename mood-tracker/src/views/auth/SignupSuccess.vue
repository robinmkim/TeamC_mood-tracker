<template>
  <div class="flex flex-col items-center justify-center h-full p-6">
    <div class="bg-white rounded-lg p-8 h-96 shadow-md w-full max-w-sm">
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
        <span class="text-xl font-semibold">회원가입 완료</span>
      </div>
      <div class="flex flex-col justify-around items-start h-48">
        <span>email: {{ userInfo.userId }}</span>
        <span>password: {{ userInfo.password }}</span>
        <span>user name: {{ userInfo.userName }}</span>
        <span>birthday: {{ userInfo.birthday }}</span>
        <span>gender: {{ userInfo.gender }}</span>
      </div>
      <button
        @click="goToLogin"
        class="bg-[#64CCC5] text-white p-2 rounded-lg shadow w-full hover:bg-[#3f827e]"
      >
        로그인 화면으로
      </button>
    </div>
  </div>
</template>
<script>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
export default {
  name: "SignupSuccess",
  setup() {
    const router = useRouter();
    const userInfo = ref({});
    onMounted(() => {
      const state = history.state;
      console.log(state.userInfo);
      if (state) {
        userInfo.value = state.userInfo;
      }
    });
    const goToLogin = () => {
      router.push({
        name: "LogIn",
      });
    };
    return { userInfo, goToLogin };
  },
};
</script>
<style lang=""></style>
