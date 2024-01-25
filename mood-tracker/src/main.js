import { createApp } from "vue";
import "tailwindcss/tailwind.css";
import App from "./App.vue";
import axios from "axios";
import Vuex from "vuex";
import { store } from "./store";
// import router from "./router";
store.dispatch("initializeStore");
import router from "./router/index";
const app = createApp(App);

app.config.globalProperties.$axios = axios; //전역변수로 설정 컴포넌트에서 this.$axios 호출할 수 있게 설정
app.config.globalProperties.$serverUrl = "//localhost:8083"; //api server

app.use(router).use(Vuex).use(store).mount("#app");
