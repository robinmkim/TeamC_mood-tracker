<template lang="">
  <div class="">
    <div class="flex flex-start w-full mt-2">
      <span class="text-2xl font-semi-bold ml-3">chatSongti</span>
    </div>
    <div class="flex flex-col justify-end h-full border">
      <!-- 여기에 h-full 추가 -->
      <div class="flex-grow">
        <div
          v-for="(message, index) in messages"
          :key="index"
          class="flex justify-center"
        >
          <div
            v-if="message.isMine"
            class="flex flex-col justify-center items-center w-2/3 mb-2"
          >
            <div class="flex justify-end w-full">
              <span class="text-xs text-gray-400">{{ message.message }}</span>
            </div>
          </div>
          <div
            v-else
            class="flex flex-col justify-center items-center w-2/3 mb-2"
          >
            <div class="flex justify-start w-full">
              <span class="text-xs text-gray-400">{{ message.message }}</span>
            </div>
            <div class="flex justify-start w-full">
              <a class="text-xs text-gray-400">{{ message.musicLink }}</a>
            </div>
          </div>
        </div>
      </div>
      <!-- h-auto 대신 flex-grow 사용 -->
      <div class="flex justify-center mb-2">
        <input
          v-model="prompt"
          class="w-2/3 border rounded-md mr-2"
          type="text"
        />
        <button @click="sendPrompt" class="border rounded-xl px-2 py-1">
          전송
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "ChatBot",
  data() {
    return {
      isLoding: true,
      prompt: "",
      messages: [
        {
          isMine: true, // 또는 false
          message: "",
          musicLink: "",
        },
      ],
    };
  },
  methods: {
    sendPrompt() {
      this.messages.push({
        isMine: true,
        message: this.prompt,
        musicLink: "",
      });
      axios
        .post("http://127.0.0.1:8000/recommend-songs/", {
          prompt: this.prompt,
        })
        .then((res) => {
          console.log(res.data);
          this.messages.push({
            isMine: false,
            message: res.data.result,
            musicLink: res.data.song_urls,
          });
        });
      this.prompt = "";
    },
  },
};
</script>
<style lang=""></style>
