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
          class="flex justify-center overflow-auto"
        >
          <div
            v-if="message.isMine"
            class="flex flex-col items-center w-2/3 mb-2"
          >
            <div class="flex flex-row ml-auto mt-3 break-all text-right">
              <div class="rounded-lg bg-gray-300 p-2 max-w-52">
                <span>{{ message.message }}</span>
              </div>
            </div>
          </div>
          <div
            v-else
            class="flex flex-col justify-center items-center w-2/3 mb-2"
          >
            <div class="flex flex-col mr-auto mt-3 break-all text-left">
              <div class="rounded-lg bg-gray-300 p-2 w-64 text-wrap">
                <span v-html="formatMessage(message.message)"></span>
              </div>
              <div class="rounded-lg bg-gray-300 mt-3 p-2 w-52 text-wrap">
                아래 제목을 누르면 해당 노래의 YouTube 영상으로 이동합니다<br />
                <a
                  v-for="(item, index) in message.musicTitle"
                  :href="message.musicLink[index]"
                  target="_blank"
                  :key="index"
                  >{{ index }}. {{ item }}<br
                /></a>
              </div>
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
        <button
          @click="sendPrompt"
          :disabled="isLoading"
          class="border rounded-xl px-2 py-1"
        >
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
      isLoding: false,
      prompt: "",
      messages: [],
    };
  },
  methods: {
    formatMessage(msg) {
      return msg.replace(/(\n)/g, "<br>");
    },
    sendPrompt() {
      this.messages.push({
        isMine: true,
        message: this.prompt,
        musicLink: "",
      });
      this.isLoading = true;
      axios
        .post("http://127.0.0.1:8000/recommend-songs/", {
          prompt: this.prompt,
        })
        .then((res) => {
          console.log(res.data);
          this.messages.push({
            isMine: false,
            message: res.data.result.replace(/(\d\.)/g, "\n$1"),
            musicTitle: res.data.song_titles,
            musicLink: res.data.song_urls,
          });
          this.isLoading = false;
        })
        .catch((err) => {
          console.log(err);
          this.isLoading = false;
        });
      this.prompt = "";
    },
  },
};
</script>
<style lang=""></style>
