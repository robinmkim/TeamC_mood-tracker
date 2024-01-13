<template>
  <div class="flex flex-col items-center justify-center h-full p-6">
    <div class="bg-white rounded-lg p-8 h-[450px] shadow-md w-full max-w-sm">
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
        <span class="text-xl font-semibold">비밀번호 설정</span>
      </div>
      <form class="flex flex-col space-y-4" @submit.prevent="onNextClick">
        <div class="flex flex-col">
          <label for="password" class="text-left mb-2">비밀번호</label>
          <input
            id="password"
            type="password"
            placeholder="비밀번호"
            v-model="password"
            @input="validatePassword"
            class="border-2 border-gray-300 p-2 mb-2 rounded-md focus:border-[#64CCC5] focus:outline-none"
          />
          <span
            v-if="errors.password"
            class="text-sm h-3 text-left text-red-500"
            >{{ errors.password }}</span
          >
        </div>
        <div class="flex flex-col">
          <label for="password" class="text-left">비밀번호 확인</label>
          <input
            id="confirmPassword"
            type="password"
            placeholder="비밀번호 확인"
            v-model="confirmPassword"
            @input="validateConfirmPassword"
            class="border-2 border-gray-300 p-2 mb-2 rounded-md focus:border-[#64CCC5] focus:outline-none"
          />
          <span
            v-if="errors.confirmPassword"
            class="text-sm text-left h-3 text-red-500"
            >{{ errors.confirmPassword }}</span
          >
        </div>
        <button
          type="submit"
          class="bg-[#64CCC5] text-white p-2 rounded-lg shadow hover:bg-[#3f827e]"
        >
          다음
        </button>
      </form>
    </div>
  </div>
</template>
<script>
import { useForm, useField } from "vee-validate";
import * as yup from "yup";
import { useRouter } from "vue-router";
import { ref, onMounted } from "vue";

const validationSchema = yup.object().shape({
  password: yup
    .string()
    .required("비밀번호를 입력해주세요")
    .min(8, "비밀번호는 8자 이상이어야 합니다")
    .matches(/[A-Z]/, "대문자를 최소 1개 포함해야 합니다")
    .matches(/[\W_]+/, "특수문자를 최소 1개 포함해야 합니다"),
  confirmPassword: yup
    .string()
    .oneOf([yup.ref("password"), null], "비밀번호가 일치하지 않습니다")
    .required("비밀번호 확인은 필수입니다."),
});
export default {
  name: "SignupPassword",

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

    const { handleSubmit, errors } = useForm({ validationSchema });
    const { value: password } = useField("password");
    const { value: confirmPassword } = useField("confirmPassword");

    const onNextClick = handleSubmit(() => {
      goToNextStep();
    });

    const goToNextStep = () => {
      router.push({
        name: "SignupUsername",
        state: {
          userInfo: { ...userInfo.value, m_pwd: password.value },
        },
      });
    };

    return {
      password,
      confirmPassword,
      errors,
      onNextClick,
    };
  },
};
</script>
<style lang=""></style>
