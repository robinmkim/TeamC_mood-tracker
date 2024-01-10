<template>
  <div class="flex flex-col items-center justify-center h-full p-6">
    <div class="bg-white rounded-lg p-8 h-100 shadow-md w-full max-w-sm">
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
          <div class="flex">
            <input
              id="username"
              type="text"
              placeholder="이름"
              v-model="name"
              class="border-2 border-gray-300 p-2 mb-2 w-full rounded-md focus:border-[#64CCC5] focus:outline-none"
            />
          </div>
          <span v-if="errors.name" class="text-sm text-left text-red-500">{{
            errors.name
          }}</span>
        </div>
        <div class="flex flex-col">
          <div class="flex">
            <input
              id="userid"
              type="email"
              placeholder="이메일"
              v-model="email"
              HEAD
              class="border-2 border-gray-300 p-2 mb-2 w-[240px] rounded-md focus:border-[#64CCC5] focus:outline-none"
            />
            <button
              :disabled="!isButtonActive"
              @click="sendCode"
              class="border-2 rounded-md ml-2 h-[44px] w-[75px] text-slate-500 border-[#64CCC5]"
            >
              {{ timerText }}
            </button>
          </div>
          <span v-if="errors.email" class="text-sm text-left text-red-500">{{
            errors.email
          }}</span>
        </div>
        <div v-if="showCodeInput" class="flex flex-col">
          <div class="flex">
            <input
              id="userid"
              type="text"
              placeholder="인증 번호"
              maxlength="6"
              class="border-2 border-gray-300 p-2 mb-2 w-[240px] rounded-md focus:border-[#64CCC5] focus:outline-none"
            />
            <button
              type="button"
              id="certify"
              @click="verifyCode"
              class="border-2 rounded-md ml-2 h-[44px] w-[75px] text-slate-500 border-[#64CCC5]"
            >
              인증
            </button>
          </div>
          <span v-if="errors.email" class="text-sm text-left text-red-500">{{
            errors.code
          }}</span>
        </div>
        <button
          :disabled="!isVerified"
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
import apiClient from "@/utils/apiClient";
import { ref } from "vue";

const validationSchema = yup.object().shape({
  name: yup.string().required("이름을 입력하세요"),
  email: yup
    .string()
    .required("이메일을 입력하세요")
    .email("이메일 형식이 아닙니다"),
  code: yup
    .string()
    .required("인증번호를 입력하세요")
    .length(6, "인증번호는 6자리여야 합니다"),
});

export default {
  name: "SignupEmail",
  setup() {
    const router = useRouter();
    const showCodeInput = ref(false);
    const isVerified = ref(false);
    const timerText = ref("인증하기");
    const isButtonActive = ref(true);
    const { handleSubmit, errors } = useForm({ validationSchema });
    const { value: name } = useField("name");
    const { value: email } = useField("email");
    const { value: code } = useField("code");

    const onNextClick = handleSubmit(() => {
      goToNextStep();
    });

    const sendCode = () => {
      if (!showCodeInput.value) {
        toggleCodeInput();
      }
      if (isButtonActive.value) {
        // 이메일 인증 로직
        apiClient
          .post("/api/auth/sendCode", { email: email.value })
          .then(() => {
            alert("인증번호가 전송되었습니다");
          })
          .catch((err) => {
            alert(err.response.data.message);
          });
        isButtonActive.value = false;
        startTimer();
      }
    };

    const verifyCode = () => {
      apiClient
        .post("/api/auth/verifyCode", { email: email.value, code: code.value })
        .then(() => {
          alert("인증되었습니다");
          isVerified.value = true;
        })
        .catch((err) => {
          alert(err.response.data.message);
        });
    };

    const toggleCodeInput = () => {
      showCodeInput.value = !showCodeInput.value;
    };
    let timerId;
    const startTimer = () => {
      let timeLeft = 60; // 1분
      timerId = setInterval(() => {
        if (timeLeft <= 0) {
          clearInterval(timerId);
          timerText.value = "인증하기";
          isButtonActive.value = true;
        } else {
          timeLeft--;
          const minutes = Math.floor(timeLeft / 60);
          const seconds = timeLeft % 60;
          timerText.value = `${minutes}:${seconds < 10 ? "0" : ""}${seconds}`;
        }
      }, 1000);
    };

    const goToNextStep = () => {
      router.push({
        name: "SignupPassword",
        state: {
          userInfo: { m_name: name.value, m_email: email.value },
        },
      });
    };

    return {
      name,
      email,
      code,
      errors,
      showCodeInput,
      isButtonActive,
      isVerified,
      timerText,
      sendCode,
      verifyCode,
      onNextClick,
    };
  },
};
</script>

<style scoped></style>
