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
      <form class="flex flex-col space-y-4" @submit.prevent="onSubmit">
        <div class="flex flex-col">
          <label for="userid" class="text-left mb-2">ID</label>
          <input
            id="userid"
            type="email"
            placeholder="사용자 아이디 또는 이메일"
            v-model="id"
            class="border-2 border-gray-300 p-2 mb-2 rounded-md focus:border-[#64CCC5] focus:outline-none"
          />
          <span class="text-sm text-left text-red-500">{{ idError }}</span>
        </div>
        <div class="flex flex-col">
          <label for="password" class="text-left mb-2">PW</label>
          <input
            id="password"
            type="password"
            placeholder="비밀번호"
            v-model="password"
            class="border-2 border-gray-300 p-2 mb-2 rounded-md focus:border-[#64CCC5] focus:outline-none"
          />
          <span class="text-sm text-left text-red-500">{{
            passwordError
          }}</span>
        </div>
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
        <router-link to="/singup/email" class="w-full">
          <button
            type="button"
            class="bg-[#64CCC5] w-full text-white p-2 rounded-lg shadow hover:bg-[#3f827e]"
          >
            계정 만들기
          </button>
        </router-link>
        <button type="button" class="mt-4">비밀번호를 잊으셨나요?</button>
      </div>
    </div>
  </div>
</template>

<script>
import { useForm, useField } from "vee-validate";
import * as yup from "yup";

export default {
  name: "LogIn",
  setup() {
    // 각 필드를 useField를 사용하여 등록
    const { value: id, errorMessage: idError } = useField(
      "id",
      yup
        .string()
        .required("이메일을 입력하세요")
        .email("이메일 형식이 아닙니다")
    );
    const { value: password, errorMessage: passwordError } = useField(
      "password",
      yup
        .string()
        .required("비밀번호를 입력하세요")
        .min(10, "비밀번호는 최소 10자 입니다")
    );

    const { handleSubmit } = useForm();

    // 폼 제출 핸들러
    const onSubmit = handleSubmit(() => {
      console.log("Form values: ", { id: id.value, password: password.value });
    });

    return {
      id,
      password,
      idError,
      passwordError,
      onSubmit,
    };
  },
};
</script>

<style scoped></style>
