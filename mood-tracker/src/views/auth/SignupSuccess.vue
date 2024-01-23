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
      <div class="flex flex-col justify-around items-start h-auto">
        <!--
        <div
          class="rounded-full overflow-hidden w-32 h-32 border-[3px] border-[#64CCC5]"
        >
          <img
            v-if="userInfo.profileImg"
            :src="userInfo.profileImg"
            alt="Profile"
            class="w-full h-full object-cover rounded-full"
          />
        </div>
        -->
        <span>email: {{ userInfo.m_email }}</span>
        <span>password: {{ userInfo.m_pwd }}</span>
        <span>user name: {{ userInfo.m_handle }}</span>
        <span>birthday: {{ userInfo.m_bdate }}</span>
        <span>gender: {{ userInfo.m_gender }}</span>
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
        path: "/login",
      });
    };
    return { userInfo, goToLogin };
  },
};
</script>
<style lang=""></style>
