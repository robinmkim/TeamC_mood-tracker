<template>
  <div class="h-full overflow-y-auto">
    <form @submit.prevent="submitForm">
      <div class="flex flex-col">
        <div
          class="h-auto px-2 pt-2 border-b"
          @drop.prevent="handleFileDrop"
          @dragover.prevent
        >
          <div class="flex items-center border-b h-10">
            <div class="w-10 rounded-full overflow-hidden">
              <img
                class="object-contain rounded-full"
                :src="getUserImageUrl()"
                alt="user icon"
              />
            </div>
            <span class="ml-2">{{ user.m_name }}</span>
            <img
              class="cursor-pointer ml-2"
              @click="isExpanded = !isExpanded"
              :src="`http://localhost:8083/images/${selectedEmoji}.png`"
              width="25"
              height="25"
            />
            <div v-if="isExpanded" class="cursor-pointer flex flex-col my-1">
              <div class="flex">
                <img
                  v-for="(sentiment, index) in sentimentList"
                  :key="index"
                  :src="`http://localhost:8083/images/${sentiment}.png`"
                  alt="sentiment"
                  class="inline-block ml-2"
                  width="20"
                  height="20"
                  @click="selectEmoji(sentiment, index)"
                />
              </div>
            </div>
          </div>
          <div class="border-b mb-1">
            <textarea
              class="bg-inherit w-full h-auto resize-none focus:outline-none"
              placeholder="오늘의 기분을 알려주세요 :)"
              v-model="text"
              @input="adjustHeight"
            ></textarea>
          </div>
          <div class="flex">
            <div v-for="(file, index) in files" :key="index" class="py-1">
              <img :src="file.preview" class="w-24 border" />
            </div>
          </div>
        </div>
        <div class="flex justify-between px-3 py-2 border-b">
          <input
            type="file"
            ref="fileInput"
            multiple
            style="display: none"
            @change="handleFileChange"
          />
          <svg
            @click="triggerFileInput"
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 24 24"
            stroke-width="1.5"
            stroke="currentColor"
            class="w-6 h-6 cursor-pointer"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              d="M2.25 15.75l5.159-5.159a2.25 2.25 0 013.182 0l5.159 5.159m-1.5-1.5l1.409-1.409a2.25 2.25 0 013.182 0l2.909 2.909m-18 3.75h16.5a1.5 1.5 0 001.5-1.5V6a1.5 1.5 0 00-1.5-1.5H3.75A1.5 1.5 0 002.25 6v12a1.5 1.5 0 001.5 1.5zm10.5-11.25h.008v.008h-.008V8.25zm.375 0a.375.375 0 11-.75 0 .375.375 0 01.75 0z"
            />
          </svg>

          <input
            class="bg-[#ffede6] w-16 h-8 rounded-md p-0.5"
            type="submit"
            value="입력"
          />
        </div>
      </div>
    </form>
  </div>
</template>
<script>
import apiClient from "@/utils/apiClient";
import { jwtDecode } from "jwt-decode";
export default {
  data() {
    return {
      text: "",
      files: [],
      isExpanded: false,
      selectedEmoji: "happy", // 기본 이모지
      sentimentList: [
        "happy",
        "angry",
        "anxiety",
        "hurt",
        "neutral",
        "sad",
        "surprise",
      ],
      emotion: "happy",
      user: {
        m_name: null,
        m_hanble: null,
        m_img_name: "",
        m_img_path: "",
      },
    };
  },
  name: "PostWrite",
  created() {
    this.getUsetInfo();
  },
  props: {
    updateParentData: {
      type: Function,
    },
  },
  methods: {
    getUsetInfo() {
      // jwtToken을 decode해서 m_id를 추출한다.
      const token = localStorage.getItem("jwtToken");
      const decoded = jwtDecode(token);
      const m_id = decoded.m_id;
      apiClient
        .get(`/member/userInfo/${m_id}`)
        .then((response) => {
          this.user = response.data;
        })
        .catch((error) => {
          console.error("Error fetching the board data:", error);
        });
    },
    getUserImageUrl() {
      if (this.user && this.user.m_img_path) {
        return `http://localhost:8083/${this.user.m_img_path}${this.user.m_img_name}`;
      } else {
        // 여기에 기본 이미지 URL 또는 다른 처리를 추가하세요.
        return "http://images/Logo.png";
      }
    },
    adjustHeight(e) {
      // textarea높이 자동 조절
      const element = e.target;
      element.style.height = "auto";
      element.style.height = element.scrollHeight + "px";
    },

    handleFileDrop(e) {
      // 파일 드롭
      const files = e.dataTransfer.files;
      this.processFiles(files);
    },

    handleFileChange(e) {
      // 파일이 선택되었을 때
      const files = e.target.files;
      this.processFiles(files);
    },

    processFiles(files) {
      // 파일 목록을 받아 각 파일을 처리, files배열에 추가
      //FileReader 를 생성해서 메타데이터와 실제 파일 객체 저장
      Array.from(files).forEach((file) => {
        const reader = new FileReader();
        reader.onload = (e) => {
          // 파일의 메타데이터와 실제 파일 객체 저장
          this.files.push({
            name: file.name,
            preview: e.target.result,
            file: file, // 실제 파일 객체 추가
          });
        };
        reader.readAsDataURL(file);
      });
    },

    triggerFileInput() {
      // 파일 input 클릭하게 만듦
      this.$refs.fileInput.click();
    },

    selectEmoji(sentiment, index) {
      this.selectedEmoji = sentiment;
      console.log(this.sentimentList[index]);
      this.emotion = this.sentimentList[index];
      this.isExpanded = false;
    },

    submitForm() {
      const formData = new FormData();
      formData.append("m_id", 1);
      formData.append("b_content", this.text);

      this.files.forEach((fileObj) => {
        formData.append("mediaList", fileObj.file); // 실제 파일 객체를 사용
      });
      formData.append("b_sentiment", this.emotion);
      for (let [key, value] of formData.entries()) {
        console.log(`${key}: ${value}`);
      }
      apiClient
        .post("/post/add", formData, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        })
        .then(() => {
          console.log("success");
          this.$emit("update-parent-data");
        })
        .catch((error) => {
          console.log("formData" + formData);

          console.log(error);
        });
    },
  },

  components: {},
};
</script>
