import { createWebHistory, createRouter } from "vue-router";
import PageHome from "/src/views/home/PageHome.vue";
import ProfileHome from "/src/views/sidemenu/ProfileHome.vue";

const routes = [
    {
        path: "/",
        component: PageHome,
    },
    {
      path: "/ProfileHome",
      component: ProfileHome,
    },
  ];

  const profilehome = createRouter({
    history: createWebHistory(),
    routes,
  });

  export default profilehome;