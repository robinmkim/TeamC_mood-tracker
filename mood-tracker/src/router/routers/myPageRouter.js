import EditProfile from "/src/views/mypage/EditProfile.vue";
import PostDetail from "/src/components/post/PostDetail.vue";
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
