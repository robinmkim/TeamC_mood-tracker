import SignupPassword from "@/views/auth/SignupPassword";
import SignupUsername from "@/views/auth/SignupUsername";
import SignupBday from "@/views/auth/SignupBday";
import ProfileImg from "@/views/auth/SignupProfileImg";
import SignupSuccess from "@/views/auth/SignupSuccess";

export default [
  {
    path: "/password",
    name: "SignupPassword",
    component: SignupPassword,
    props: true,
  },
  {
    path: "/username",
    name: "SignupUsername",
    component: SignupUsername,
  },
  {
    path: "/birth",
    name: "SignupBday",
    component: SignupBday,
  },
  {
    path: "/profileImg",
    name: "SignupProfileImg",
    component: ProfileImg,
  },
  {
    path: "/success",
    name: "SignupSuccess",
    component: SignupSuccess,
  },
];
