import { createWebHistory, createRouter } from "vue-router";
//경로가 아닌 라이브러리 이름이 들어가면 라이브러리에서 가지고 오겠다.
import PageHome from "/src/views/home/PageHome.vue";
import ChatMain from "/src/views/chat/ChatMain.vue";

const routes = [
  {
    path: "/",
    component: PageHome,
  },
  {
    path: "/chat",
    component: ChatMain,
  },
];

const router1 = createRouter({
  history: createWebHistory(),
  routes,
});

export default router1;