<template>
  <div>
    <form @submit.prevent="submitForm">
      <div class="flex flex-col">
        <div
          class="h-auto px-2 pt-2 border-b"
          @drop.prevent="handleFileDrop"
          @dragover.prevent
        >
          <div class="flex items-center border-b h-10">
            <div class="w-9 h-9 roickhighunded-full overflow-hidden">
              <img
                class="postDetailUserImg object-contain rounded-full"
                :src="getUserImageUrl()"
                alt="user icon"
                style="aspect-ratio: 100/100; object-fit: cover"
              />
            </div>
            <div class="userHandle ml-2 text-lg text-slate-500">
              @{{ this.user ? this.user.m_handle : "No Handle" }}
            </div>
            <!-- <span class="ml-2 text-lg">{{ user.m_name }}</span> -->
            <img
              class="cursor-pointer ml-2"
              @click="isExpanded = !isExpanded"
              :src="`http://localhost:8083/images/${emotion}.png`"
              width="20"
              height="20"
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
            <div v-if="board.mediaList.length > 0" class="flex mt-2">
              <div
                v-for="(media, index) in board.mediaList"
                :key="index"
                class="relative flex flex-col mr-3"
              >
                <img
                  :src="getImageUrl(media)"
                  alt="Post image"
                  class="w-24 border relative z-10"
                />
                <button type="button" @click="deleteOldMedia(index)">
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    fill="none"
                    viewBox="0 0 24 24"
                    stroke-width="1.5"
                    stroke="currentColor"
                    class="w-4 h-4 absolute top-[-12px] right-[-12px] z-10"
                  >
                    <path
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      d="M6 18 18 6M6 6l12 12"
                    />
                  </svg>
                </button>
              </div>
            </div>

            <div
              v-for="(file, index) in files"
              :key="index"
              class="py-1 relative flex flex-col mt-2 mr-3"
            >
              <img :src="file.preview" class="w-24 border relative z-9" />
              <button type="button" @click="deleteNewMedia(index)">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke-width="1.5"
                  stroke="currentColor"
                  class="w-4 h-4 absolute top-[-12px] right-[-12px] z-10"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    d="M6 18 18 6M6 6l12 12"
                  />
                </svg>
              </button>
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
            value="수정"
            type="submit"
          />
        </div>
      </div>
    </form>
  </div>
</template>
<script>
import apiClient from "@/utils/apiClient";
export default {
  data() {
    return {
      b_id: this.$route.query.b_id,
      currentImageIndex: 1,
      text: "",
      files: [],
      isExpanded: false,
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
      board: {
        b_id: null,
        m_id: null,
        b_content: "",
        b_sentiment: "",
        regdate: "",
        mediaList: [],
      },
      user: {
        m_name: null,
        m_hanble: null,
        m_img_name: "",
        m_img_path: "",
      },
    };
  },
  created() {
    this.loadBoardData();
  },
  computed: {
    imageCount() {
      return this.board.mediaList.length;
    },
  },
  name: "PostUpdate",
  methods: {
    deleteOldMedia(index) {
      this.board.mediaList.splice(index, 1);
    },
    deleteNewMedia(index) {
      this.files.splice(index);
    },
    getImageUrl(media) {
      // md_path와 md_name을 결합하여 이미지의 전체 경로를 반환합니다.
      return `http://localhost:8083/${media.md_path}${media.md_name}`;
    },
    getUserImageUrl() {
      return `http://localhost:8083/${this.user.m_img_path}${this.user.m_img_name}`;
    },
    getUserInfo() {
      apiClient
        .get(`/member/info/${this.board.m_id}`)
        .then((response) => {
          this.user = response.data;
        })
        .catch((error) => {
          console.error("Error fetching the board data:", error);
        });
    },
    async loadBoardData() {
      try {
        const response = await apiClient.get(`/post/get/${this.b_id}`);
        this.board = response.data;
        this.text = this.board.b_content;
        this.emotion = this.board.b_sentiment;
        // this.selectedEmoji = this.sentimentEmoji();
        this.getUserInfo();
        console.log("----board--mediaList--", this.board.mediaList);
        this.loadMediaList(); // 기존 미디어 파일을 불러옵니다.
      } catch (error) {
        console.error("Error fetching the board data:", error);
      }
    },
    adjustHeight(e) {
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

    selectEmoji(sentiment, index) {
      this.selectedEmoji = sentiment;
      console.log(this.sentimentList[index]);
      this.emotion = this.sentimentList[index];
      this.isExpanded = false;
    },

    submitForm() {
      const formData = new FormData();

      const mb_idList = [];
      this.board.mediaList.forEach((media) => {
        mb_idList.push(media.md_id);
      });
      if (mb_idList === null) {
        formData.append("mb_idList", ["isNull"]);
      } else {
        formData.append("mb_idList", mb_idList);
      }

      // formData.append("m_id", 1);
      formData.append("b_id", this.b_id);
      formData.append("b_content", this.text);
      this.files.forEach((fileObj) => {
        formData.append("mediaList", fileObj.file); // 실제 파일 객체를 사용
      });
      formData.append("b_sentiment", this.emotion);

      for (let [key, value] of formData.entries()) {
        console.log(`${key}: ${value}`);
      }

      apiClient
        .post("/post/update", formData, {
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
