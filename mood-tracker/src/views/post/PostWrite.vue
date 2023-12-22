<template>
  <div>
    <form>
      <div class="flex flex-col">
        <div
          class="h-auto px-2 pt-2 border-b"
          @drop.prevent="handleFileDrop"
          @dragover.prevent
        >
          <div>
            <div class="flex items-center border-b">
              <div class="w-10 rounded-full overflow-hidden">
                <img src="@/assets/logo.png" class="" />
              </div>
              <span class="ml-2">ickhigh</span>

              <div class="cursor-pointer ml-2" @click="toggleExpand">
                {{ selectedEmoji }}
              </div>
              <div v-if="isExpanded" class="cursor-pointer">
                <span
                  v-for="emoji in emojis"
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
                @input="adjustHeight"
              ></textarea>
            </div>
            <div class="flex">
              <div v-for="(file, index) in files" :key="index" class="py-1">
                <img :src="file.preview" class="w-24 border" />
              </div>
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
export default {
  data() {
    return {
      text: "",
      file: null,
      files: [],
      isExpanded: false,
      selectedEmoji: "😊", // 기본 이모지
      emojis: ["😀", "😂", "🤣", "😍", "😎", "😊"],
    };
  },
  name: "PostWrite",
  methods: {
    adjustHeight(e) {
      const element = e.target;
      element.style.height = "auto";
      element.style.height = element.scrollHeight + "px";
    },
    handleFileDrop(e) {
      const files = e.dataTransfer.files;
      Array.from(files).forEach((file) => {
        this.readFile(file);
      });
    },

    readFile(file) {
      const reader = new FileReader();
      reader.onload = (e) => {
        this.files.push({
          name: file.name,
          preview: e.target.result,
        });
      };
      reader.readAsDataURL(file);
    },

    triggerFileInput() {
      this.$refs.fileInput.click();
    },
    handleFileChange(event) {
      const selectedFiles = event.target.files;
      Array.from(selectedFiles).forEach((file) => {
        this.readFile(file);
      });
    },
    toggleExpand() {
      this.isExpanded = !this.isExpanded;
    },
    selectEmoji(emoji) {
      this.selectedEmoji = emoji;
      this.isExpanded = false;
    },
  },
  components: {},
};
</script>
