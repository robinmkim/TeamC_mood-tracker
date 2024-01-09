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
        <span class="text-xl font-semibold">E-Mail</span>
      </div>
      <form class="flex flex-col space-y-4" @submit.prevent="onNextClick">
        <div class="flex flex-col">
          <input
            id="userid"
            type="email"
            placeholder="사용자 아이디 또는 이메일"
            v-model="id"
            class="border-2 border-gray-300 p-2 mb-2 rounded-md focus:border-[#64CCC5] focus:outline-none"
          />
          <span class="text-sm text-left text-red-500">{{ idError }}</span>
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
import { useRouter } from "vue-router"; // router를 임포트

export default {
  name: "SignupEmail",
  setup() {
    const { value: id, errorMessage: idError } = useField(
      "id",
      yup
        .string()
        .required("이메일을 입력하세요")
        .email("이메일 형식이 아닙니다")
    );

    const router = useRouter();

    const { handleSubmit } = useForm();

    const onNextClick = () => {
      handleSubmit(() => {
        goToNextStep();
      })();
    };

    const goToNextStep = () => {
      router.push({
        name: "SignupPassword",
        state: {
          userInfo: { userId: id.value },
        },
      });
    };

    return {
      id,
      idError,
      onNextClick,
    };
  },
};
</script>

<style scoped></style>
