import { createWebHistory, createRouter } from "vue-router";
//경로가 아닌 라이브러리 이름이 들어가면 라이브러리에서 가지고 오겠다.
import PageHome from "/src/views/home/PageHome.vue";
import PostWrite from "/src/views/post/PostWrite.vue";
import chatRouter from "./routers/chatRouter";
import myPageRouter from "./routers/myPageRouter";
import notiRouter from "./routers/notiRouter";
import faceRouter from "./routers/faceRouter";

import postDetailRouter from "./routers/postRouter";
import questionRouter from "./routers/questionRouter";
import adminRouter from "./routers/adminRouter";


const routes = [
  {
    path: "/",
    component: PageHome,
  },
  {
    path: "/postwrite",
    component: PostWrite,
  },
  ...chatRouter,
  ...myPageRouter,
  ...notiRouter,
  ...faceRouter,
  ...postDetailRouter,
  ...questionRouter,
  ...adminRouter,

];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
