<template>
  <div>
    <form @submit.prevent="submitForm">
      <div class="flex flex-col">
        <div
          class="h-auto px-2 pt-2 border-b"
          @drop.prevent="handleFileDrop"
          @dragover.prevent
        >
          <div class="flex items-center border-b">
            <div class="w-10 rounded-full overflow-hidden">
              <img src="@/assets/logo.png" />
            </div>
            <span class="ml-2">{{ memberHandle }}</span>
            <div class="cursor-pointer ml-2" @click="isExpanded = !isExpanded">
              {{ selectedEmoji }}
            </div>
            <div v-if="isExpanded" class="cursor-pointer">
              <span
                v-for="(emotion, emoji) in emotionMap"
                :key="emoji"
                @click="selectEmoji(emoji)"
                class="ml-1"
              >
                {{ emoji }}
              </span>
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
import axios from "axios";
import apiClient from "@/utils/apiClient";
export default {
  props: ["resultId"],
  data() {
    return {
      memberHandle: "",
      text: "",
      files: [],
      isExpanded: false,
      selectedEmoji: "😆", // 기본 이모지
      emotionMap: {
        "😆": "happy",
        "😡": "angry",
        "😬": "anxiety",
        "🤕": "hurt",
        "😐": "neutral",
        "😢": "sad",
        "😨": "surprise",
      },
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
  mounted() {
    // userName을 가져옵니다.
    apiClient.get("/member/userInfo/memberHandle").then((res) => {
      console.log("memberHandle = ", res.data);
      this.memberHandle = res.data;
    });
    // 스프링 : emotion결과를 가져와 selectedEmoji에 맞춰줍니다..
    apiClient
      .get("/faceresult/detail", {
        params: {
          ar_id: this.$props.resultId,
        },
      })
      .then((res) => {
        console.log(res.data);
        const reversedEmotionMap = {};
        for (const key in this.emotionMap) {
          const value = this.emotionMap[key];
          reversedEmotionMap[value] = key;
        }
        this.selectedEmoji = reversedEmotionMap[res.data.ar_content_max];
        this.emotion = res.data.ar_content_max;
      });
    // resultId로  합성이미지의 파일이름(ar_generated_img)를 가져옵니다.
    //스프링
    apiClient
      .get("/faceresult/detail", {
        params: {
          ar_id: this.$props.resultId,
        },
      })
      .then((res) => {
        console.log(res.data.ar_generated_img);
        //장고로 부터 FileResponse로 이미지파일을 반환받아 blob형태로 읽고
        axios
          .get("http://localhost:9000/face/downloadGeneratedImage", {
            params: {
              imageName: res.data.ar_generated_img,
            },
            responseType: "blob",
          })
          .then((res2) => {
            const resBlob = res2.data;
            const fileReader = new FileReader();
            // axios요청이 비동기적으로 처리되는 탓에 Blob 데이터를 읽어오기 전에
            // this.processFiles([file])를 호출하는 문제를 해결하기 위해
            // resBlob데이터를 읽는 readAsArrayBuffer를 명시한다.
            fileReader.readAsArrayBuffer(resBlob); // Blob 데이터를 읽기 시작
            fileReader.onload = () => {
              // blob테이터를 FileReader를 사용해 읽고, 파일 객체를 생성합니다.
              const file = new File([resBlob], res.data.ar_generated_img, {
                type: "image/jpeg",
              });
              // 이미지 파일 객체를 업로드합니다.
              this.processFiles([file]);
            };
          })
          .catch((error) => {
            console.log(error);
          });
      })
      .catch((error) => {
        console.log(error);
      });
  },
  methods: {
    adjustHeight(e) {
      // textarea높이 자동 조절
      const element = e.target;
      element.style.height = "auto";
      element.style.height = element.scrollHeight + "px";
    },

    handleFileDrop(e) {
      const files = e.dataTransfer.files;
      this.processFiles(files);
    },

    handleFileChange(e) {
      const files = e.target.files;
      this.processFiles(files);
    },

    processFiles(files) {
      console.log("this.files = ", this.files);
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
      this.$refs.fileInput.click();
    },

    selectEmoji(emoji) {
      this.selectedEmoji = emoji;
      this.emotion = this.emotionMap[emoji];
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
