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
            v-show="processingAnalysis"
            id="submit"
            class="bg-[#64CCC5] px-4 py-2 rounded-full cursor-pointer text-center"
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
import axios from "axios";
import SideBar from "@/components/SideBar";
export default {
  name: "FaceAnalyze",
  components: {
    SideBar,
  },
  data() {
    return {
      image: null,
      memberNum: 1, // 로그인 기능 구현 이후 삭제합니다.
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
      const formData = new FormData();
      formData.append("file1", this.$refs.fileInput.files[0]);
      formData.append("memberNum", this.memberNum); // 임시 유저 회원번호 (로그인 되면 수정 필수)

      axios
        .post("http://192.168.0.13:9000/face/predictFace", formData, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        })
        .then((res) => {
          console.log(res.data);
          if (res.data.error != null) {
            // 이미지를 안 넣었거나, 얼굴이 없거나 하는 경우 error 메세지 alert
            alert(res.data.error);
          } else {
            axios
              .post("http://192.168.0.93:8083/faceresult/lastResultId", {
                memberNum: this.memberNum,
              })
              .then((res) => {
                console.log(res.data);
                const lastResultId = res.data;

                const formData = new FormData();
                formData.append("lastResultId", lastResultId);

                // this.$router.push({
                //   name: "AnalyzeResult",
                //   params: { lastResultId: lastResultId },
                // });
              })
              .catch((error) => {
                console.log("error", error);
              });
          }
        })
        .catch((error) => {
          console.log(error.message);
        });
    },
  },
};
</script>

<style scoped>
/* 추가적인 스타일링을 위해 필요한 경우 여기에 작성하세요 */
</style>
