import { createApp } from "vue";
import "tailwindcss/tailwind.css";
import App from "./App.vue";
import axios from "axios";
// import router from "./router";

import router from "./router/index";
const app = createApp(App);

app.config.globalProperties.$axios = axios; //전역변수로 설정 컴포넌트에서 this.$axios 호출할 수 있게 설정
// 이전 api
app.config.globalProperties.$serverUrl = "//localhost:8081"; //api server
// const serverUrl = '//localhost:8081';

app.config.globalProperties.$serverUrl = "http://192.168.0.50:8083/cteam"; //api server

app.use(router).mount("#app");
