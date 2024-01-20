<template>
  <div v-if="isLoading" class="loading-container">
    <div class="loading">
      <Fade-loader />
    </div>
  </div>

  <div class="flex h-screen">
    <div class="flex-1 w-full bg-[#E7F1E5]">
      <!-- 여기서부터 ~~~ -->
      <div class="flex flex-col items-center justify-center">
        <div class="p-5 mt-5 w-[700px] bg-slate-200">
          <span
            >얼굴을 분석하여 결과에 따라 음악을 추천합니다 <br />(ง ᵕᴗᵕ)ว
          </span>
        </div>

        <!-- 클릭하여 파일 업로드 -->
        <div class="flex mt-5">
          <span class="p-5 w-[700px] h-100 item-center text-center">
            <p>⬇⬇ 선택 또는 드래그 하여 사진 업로드 ⬇⬇</p>

            <!-- <input
              type="file"
              ref="fileInput"
              class="hidden"
              @change="handleFileSelect"
              accept=".jpg, .jpeg, .png"
            /> -->
          </span>
        </div>
        <!-- 드래그하여 파일 업로드-->
        <div
          class="flex m-1 w-[550px] h-[450px] bg-white box-content text-center"
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
            v-if="!isLoading"
            id="submit"
            class="bg-[#64CCC5] px-4 py-2 rounded-full cursor-pointer text-center hover:bg-[#6fe3db] hover:scale-110 duration-300"
            @click="goToResult"
            >분석하기</span
          >
          <span
            v-if="isLoading"
            id="submit"
            class="bg-[#64CCC5] px-4 py-2 rounded-full cursor-pointer text-center"
            >분석 중...</span
          >
        </div>
      </div>
      <!-- 여까지 -->
    </div>
  </div>
</template>

<script>
import FadeLoader from "vue-spinner/src/FadeLoader.vue";
import { jwtDecode } from "jwt-decode";
import axios from "axios";
import apiClient from "./../../utils/apiClient";
export default {
  name: "FaceAnalyze",
  components: {
    FadeLoader,
  },
  data() {
    return {
      image: null,
      isLoading: false,
      // memberNum: 1, // 로그인 기능 구현 이후 삭제합니다.
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

    async goToResult() {
      // 분석하기 -> 분석중
      this.isLoading = true;
      // [ST] jwtToken decode해서 m_id를 추출한다.
      const token = localStorage.getItem("jwtToken");
      console.log(token);
      const decoded = jwtDecode(token);
      console.log(decoded);
      console.log(decoded.m_id);
      // [ED] jwtToken decode

      const formData = new FormData();
      formData.append("file1", this.$refs.fileInput.files[0]);
      formData.append("m_id", decoded.m_id); // 임시 유저 회원번호 (로그인 되면 수정 필수)
      // formData.append("token", token); // decode는 장고에서.

      //장고로 토큰, file1을 보낸다. 토큰decode는 장고에서 하도록 수정할 것
      // const {data} = await this.$axios({

      // })

      await axios
        .post("http://localhost:9000/face/predictFace", formData, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        })
        .then((res) => {
          console.log(res.data);
          if (res.data.error != null) {
            // 이미지를 안 넣었거나, 얼굴이 없거나 하는 경우 error 메세지 alert
            this.isLoading = false;
            alert(res.data.error);
          } else {
            apiClient
              .get("/faceresult/lastResultId")
              .then((res) => {
                console.log(res.data);
                const lastResultId = res.data;

                const formData = new FormData();
                formData.append("lastResultId", lastResultId);

                this.$router.push({
                  name: "AnalyzeResult",
                  params: { lastResultId: lastResultId },
                });
              })
              .catch((error) => {
                console.log("error", error);
              });
          }
        })
        .catch((error) => {
          console.log(error.message);
          this.isLoading = false;
        });
      // 분석중 -> 분석하기
      this.isLoading = false;
    },
  },
};
</script>

<style scoped>
/* 추가적인 스타일링을 위해 필요한 경우 여기에 작성하세요 */
.loading {
  z-index: 2;
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  box-shadow: rgba(0, 0, 0, 0.1) 0 0 0 9999px;
}
</style>
