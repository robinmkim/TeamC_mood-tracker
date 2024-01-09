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
              <div @click="openFileInput" class="flex">
                <img
                  v-if="profileImg"
                  :src="profileImg"
                  alt="Uploaded"
                  class="w-full h-full object-cover"
                />
              </div>

              <div class="w-full h-full flex items-center justify-center">
                <span class="text-gray-500 text-3xl">+</span>
              </div>
            </div>
          </label>
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
import { useRouter } from "vue-router"; // router를 임포트
import { ref, onMounted } from "vue";

export default {
  name: "SignupProfileImg",
  methods: {
    handleFileSelect() {
      const fileInput = this.$refs.fileInput;
      const file = fileInput.files[0];
      this.handleImageUpload(file);
    },
    handleImageUpload(file) {
      if (file) {
        const reader = new FileReader();
        reader.onload = () => {
          this.profileImg = reader.result;
        };
        reader.readAsDataURL(file);
      }
    },
    openFileInput() {
      const fileInput = this.$refs.fileInput;
      fileInput.click();
    },
  },
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
    const { handleSubmit } = useForm();
    const { value: profileImg } = useField("profileImg");
    const onNextClick = () => {
      handleSubmit(() => {
        goToNextStep();
      })();
    };

    // 다음단계로 이동
    const goToNextStep = () => {
      router.push({
        name: "SignupSuccess",
        state: {
          userInfo: {
            ...userInfo.value,
            profileImg: profileImg.value,
          },
        },
      });
    };

    return {
      onNextClick,
      profileImg,
    };
  },
};
</script>

<style scoped></style>
