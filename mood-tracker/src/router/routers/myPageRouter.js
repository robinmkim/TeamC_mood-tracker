import MypageMain from "/src/views/mypage/MypageMain.vue";
import EditProfile from "/src/views/mypage/EditProfile.vue";


export default [
  {
    path: "/mypage",
    component: MypageMain,
  },
  {
    path: "/mypage/edit",
    component: EditProfile,
  },

];
