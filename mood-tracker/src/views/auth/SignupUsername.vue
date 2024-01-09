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
        <span class="text-xl font-semibold">닉네임 설정</span>
      </div>
      <form class="flex flex-col space-y-4" @submit.prevent="onNextClick">
        <div class="flex flex-col">
          <div class="flex">
            <input
              id="username"
              type="text"
              placeholder="닉네임"
              v-model="username"
              class="border-2 border-gray-300 p-2 mb-2 w-[240px] rounded-md focus:border-[#64CCC5] focus:outline-none"
            />
            <button
              id="dupcheck"
              type="button"
              class="border-2 rounded-md ml-2 h-[44px] w-[75px] text-slate-500 border-[#64CCC5]"
            >
              중복확인
            </button>
          </div>
          <!--
          <span class="text-sm h-3 text-left text-red-500"
            >중복확인문구넣을자리</span
          >
          -->
          <span class="text-sm h-3 text-left text-red-500">{{
            errors.username
          }}</span>
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
  username: yup
    .string()
    .required("사용자 이름을 입력해주세요")
    .min(4, "사용자 이름은 4자 이상이어야 합니다")
    .matches(
      /^[가-힣a-z0-9_]+$/,
      "사용자 이름은 한글, 영어 소문자, 숫자, 밑줄(_)만 가능합니다"
    )
    .matches(/^\S*$/, "사용자 이름에 공백이 포함될 수 없습니다"),
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
    const { value: username } = useField("username");

    const onNextClick = handleSubmit((values) => {
      console.log(values);
      goToNextStep();
    });

    const goToNextStep = () => {
      router.push({
        name: "SignupBday",
        state: {
          userInfo: {
            userId: userInfo.value.userId,
            password: userInfo.value.password,
            userName: username.value,
          },
        },
      });
    };

    return {
      username,
      errors,
      onNextClick,
    };
  },
};
</script>
<style lang=""></style>
