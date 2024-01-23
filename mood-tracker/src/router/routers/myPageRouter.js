import EditProfile from "/src/views/mypage/EditProfile.vue";
import PostDetail from "@/views/post/components/PostDetail";
import SideWidget from "/src/components/SideWidget.vue";

export default [
  {
    path: "/mypage/edit",
    component: EditProfile,
  },
  {
    path: "/mypage/post",
    components: {
      default: PostDetail,
      widget: SideWidget,
    },
  },
];
