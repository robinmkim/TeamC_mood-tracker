<template>
  <div
    class="flex flex-col items-center justify-center h-full p-6 mt-[170px] w-96"
  >
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
        <span class="text-xl font-semibold">Profile Image</span>
      </div>
      <form class="flex flex-col space-y-4" @submit.prevent="onNextClick">
        <div class="flex items-center justify-center">
          <!-- 미리보기를 위한 원형 이미지 컨테이너 -->
          <label for="profileImg" class="cursor-pointer">
            <div
              @click="openFileInput"
              class="rounded-full overflow-hidden w-[160px] h-[160px] border-[3px] border-[#64CCC5]"
            >
              <input
                type="file"
                ref="fileInput"
                class="hidden"
                @change="handleFileSelect"
                accept=".jpg, .jpeg, .png"
              />
              <div
                @click="openFileInput"
                class="w-full h-full flex items-center justify-center"
                v-if="profileImg"
              >
                <img
                  :src="profileImg"
                  alt="Uploaded"
                  class="w-full h-full object-cover"
                  width="100"
                  height="100"
                />
              </div>
              <div
                v-else
                class="w-full h-full flex items-center justify-center"
              >
                <span class="text-gray-500 text-3xl">+</span>
              </div>
            </div>
          </label>
        </div>
        <div class="flex flex-col">
          <div class="flex">
            <input
              id="username"
              type="text"
              placeholder="간단한 자기 소개를 적어주세요"
              v-model="bio"
              class="border-2 border-gray-300 p-2 mb-2 w-full rounded-md focus:border-[#64CCC5] focus:outline-none"
            />
          </div>
          <span v-if="errors.bio" class="text-sm text-left text-red-500">{{
            errors.bio
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
import { useRouter } from "vue-router";
import { ref, onMounted } from "vue";
import * as yup from "yup";
import apiClient from "@/utils/apiClient";

const validationSchema = yup.object().shape({
  bio: yup.string(),
});

export default {
  name: "SignupProfileImg",
  setup() {
    const fileInput = ref(null);
    const profileImg = ref(null);
    const userInfo = ref({});
    const router = useRouter();

    onMounted(() => {
      const state = history.state;
      if (state) {
        userInfo.value = state.userInfo;
      }
    });

    const { handleSubmit, errors } = useForm({ validationSchema });
    const { value: bio } = useField("bio");

    const handleFileSelect = () => {
      const file = fileInput.value.files[0];
      handleImageUpload(file);
    };

    const handleImageUpload = (file) => {
      if (file) {
        const reader = new FileReader();
        reader.onload = () => {
          profileImg.value = reader.result;
        };
        reader.readAsDataURL(file);
      }
    };

    const openFileInput = () => {
      fileInput.value.click();
    };

    const submitData = () => {
      let formData = new FormData();
      formData.append("m_profile", fileInput.value.files[0]);
      formData.append("m_bio", bio.value); // 'fileInput' ref에서 파일 추가

      // userInfo의 다른 데이터를 FormData에 추가
      for (const key in userInfo.value) {
        formData.append(key, userInfo.value[key]);
      }

      apiClient
        .post("/auth/signUp", formData, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        })
        .then(() => {
          alert("회원가입이 완료되었습니다.");
          router.push({
            path: "/login",
          });
        })
        .catch((error) => {
          console.error("Error during data submission:", error);
        });
    };

    const onNextClick = () => {
      handleSubmit(() => {
        submitData();
      })();
    };

    return {
      fileInput,
      profileImg,
      bio,
      errors,
      onNextClick,
      handleFileSelect,
      openFileInput,
    };
  },
};
</script>

<style scoped></style>
