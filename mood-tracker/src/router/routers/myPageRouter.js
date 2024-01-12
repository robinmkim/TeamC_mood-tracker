import MypageMain from "/src/views/mypage/MypageMain.vue";
import EditProfile from "/src/views/mypage/EditProfile.vue";
import PostDetail from "/src/components/post/PostDetail.vue";

export default [
  {
    path: "/",
    component: MypageMain,
  },
  {
    path: "/mypage/edit",
    component: EditProfile,
  },
  {
    path: "/mypage/post",
    component: PostDetail,
  },
];
