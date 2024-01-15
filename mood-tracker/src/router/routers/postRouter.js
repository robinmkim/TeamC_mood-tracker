import postDetail from "/src/views/post/PostDetailPage.vue";
import postUpdate from "/src/views/post/PostUpdate.vue";

export default [
  {
    path: "/postDetail",
    name: "postDetail",
    component: postDetail,
  },
  {
    path: "/jh_post/update",
    name: "postUpdate",
    component: postUpdate,
  },
];
