import { createWebHistory, createRouter } from "vue-router";
//경로가 아닌 라이브러리 이름이 들어가면 라이브러리에서 가지고 오겠다.
import PageHome from "/src/views/home/PageHome.vue";
import FaceAnalyze from "/src/views/analyze/FaceAnalyze.vue"
import AnalyzeResult from "/src/views/analyze/AnalyzeResult"

const routes = [
  {
    path: "/",
    component: PageHome,
  },
  {
    path:"/FaceAnalyze",
    component: FaceAnalyze,
  },
  {
    path:"/AnalyzeResult",
    component: AnalyzeResult,
  },
];

const face = createRouter({
  history: createWebHistory(),
  routes,
});

export default face;
