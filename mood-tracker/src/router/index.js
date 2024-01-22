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
import authRouter from "./routers/authRouter";
import postRouter_yh from "./routers/postRouter_yh";
import searchPage from "./routers/searchPage";
import MypageMain from "/src/views/mypage/MypageMain";
import SideWidget from "/src/components/SideWidget";
import DefaultLayout from "@/layouts/DefaultLayout.vue";
import EmptyLayout from "@/layouts/EmptyLayout.vue";
import LogIn from "@/views/auth/LogIn";
import SignupEmail from "@/views/auth/SignupEmail";
import AdminPage from "@/views/admin/AdminPage";
import apiClient from "@/utils/apiClient";
import ErrorPage from "@/views/error/ErrorPage";
import chatbotRouter from "./routers/chatbotRouter";

const routes = [
  {
    path: "/",
    component: DefaultLayout,
    children: [
      {
        path: "",
        components: {
          default: MypageMain,
          widget: SideWidget,
        },
      },
      {
        path: "/error",
        components: {
          default: ErrorPage,
          widget: SideWidget,
        },
      },
      {
        path: "/:memberId",
        components: {
          default: MypageMain,
          widget: SideWidget,
        },
        beforeEnter: (to, from, next) => {
          apiClient
          .get(`/member/info/${to.params.memberId}`)
          .then((res) => {
            if (res.data !== "") {
              next();
            } else {
              next("/error");
              // next();
            }
          })
          .catch((err) => {
            console.log(err);
            next("/error");
          }
          );
        },
      },
      {
        path: "/timeline",
        components: {
          default: PageHome,
          widget: SideWidget,
        },
      },
      {
        path: "/postwrite",
        component: PostWrite,
        widget: SideWidget,
      },
      ...postRouter_yh,
      ...searchPage,
      ...chatRouter,
      ...myPageRouter,
      ...notiRouter,
      ...faceRouter,
      ...postDetailRouter,
      ...questionRouter,
      ...chatbotRouter,
    ],
  },
  {
    path: "/login",
    component: EmptyLayout,
    children: [
      {
        path: "",
        component: LogIn,
      },
    ],
  },
  {
    path: "/admin",
    component: EmptyLayout,
    children: [
      {
        path: "",
        component: AdminPage,
      },
    ],
  },
  {
    path: "/signup",
    component: EmptyLayout,
    children: [
      {
        path: "",
        component: SignupEmail,
      },
      ...authRouter,
    ],
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach((to, from, next) => {
  const publicPages = ["/login"];
  const authRequired = !publicPages.includes(to.path);
  const loggedIn = localStorage.getItem("jwtToken");

  if (authRequired && !loggedIn) {
    return next("/login");
  }
  next();
});

export default router;
