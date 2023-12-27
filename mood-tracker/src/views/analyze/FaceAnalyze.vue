<template>
  <div class="flex h-screen">
    <div class="w-1/5">
      <side-bar></side-bar>
    </div>

    <div class="flex-1 w-full bg-[#E7F1E5]">
      <!-- 여기서부터 ~~~ -->
      <div class="flex flex-col items-center justify-center">
        <div class="p-5 mt-5 w-[700px] bg-slate-200">
          <span
            >얼굴을 분석하여 결과에 따라 음악을 추천합니다 <br />(ง ᵕᴗᵕ)ว
          </span>
        </div>

        <!-- 클릭하여 파일 업로드 -->
        <div class="flex mt-5 mb-2 bg-slate-200">
          <span
            @click="openFileInput"
            class="cursor-pointer p-5 w-[700px] h-100 item-center text-center"
          >
            <p class="box-content">선택 또는 드래그 하여 사진 업로드</p>

            <input
              type="file"
              ref="fileInput"
              class="hidden"
              @change="handleFileSelect"
              accept=".jpg, .jpeg, .png"
            />
          </span>
        </div>
        <!-- 드래그하여 파일 업로드-->
        <div
          class="flex m-2 w-[700px] h-[450px] bg-slate-200 box-content text-center"
          @dragover.prevent="handleDragOver"
          @drop="handleDrop"
        >
          <div
            class="flex flex-col items-center justify-center w-full h-full cursor-pointer"
            @click="openFileInput"
          >
            <p v-if="!image" class="cursor-pointer">---</p>
            <input
              type="file"
              ref="fileInput"
              class="hidden"
              @change="handleFileSelect"
              accept=".jpg, .jpeg, .png"
            />

            <!-- 이미지 미리보기-->
            <div @click="openFileInput" class="flex">
              <img
                v-if="image"
                :src="image"
                alt="Uploaded"
                class="flex object-contain w-auto h-[450px]"
              />
            </div>
          </div>
        </div>

        <div class="flex justify-center mt-3">
          <span
            id="submit"
            class="bg-[#64CCC5] w-24 h-18 rounded-full cursor-pointer"
            @click="goToResult"
            >분석하기</span
          >
        </div>
      </div>
      <!-- 여까지 -->
    </div>

    <div class="w-1/5 p-4">side menu</div>
  </div>
</template>

<script>
import SideBar from "@/components/SideBar";
export default {
  name: "FaceAnalyze",
  components: {
    SideBar,
  },
  data() {
    return {
      image: null,
    };
  },
  methods: {
    handleDragOver(event) {
      event.dataTransfer.dropEffect = "copy";
    },
    handleDrop(event) {
      event.preventDefault();
      const file = event.dataTransfer.files[0];
      this.handleImageUpload(file);
    },
    handleFileSelect() {
      const fileInput = this.$refs.fileInput;
      const file = fileInput.files[0];
      this.handleImageUpload(file);
    },
    handleImageUpload(file) {
      if (file) {
        const reader = new FileReader();
        reader.onload = () => {
          this.image = reader.result;
        };
        reader.readAsDataURL(file);
      }
    },
    openFileInput() {
      const fileInput = this.$refs.fileInput;
      fileInput.click();
    },

    goToResult() {
      this.$router.push("/faceanalyze/result");
    },
  },
};
</script>

<style scoped>
/* 추가적인 스타일링을 위해 필요한 경우 여기에 작성하세요 */
</style>
