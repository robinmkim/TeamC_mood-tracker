import LogIn from "@/views/auth/LogIn";
import SignupEmail from "@/views/auth/SignupEmail";
import SignupPassword from "@/views/auth/SignupPassword";
import SignupUsername from "@/views/auth/SignupUsername";
import SignupBday from "@/views/auth/SignupBday";
import ProfileImg from "@/views/auth/SignupProfileImg";
import SignupSuccess from "@/views/auth/SignupSuccess";

export default [
  {
    path: "/login",
    name: "LogIn",
    component: LogIn,
  },
  {
    path: "/singup/email",
    name: "SignupEmail",
    component: SignupEmail,
  },
  {
    path: "/singup/password",
    name: "SignupPassword",
    component: SignupPassword,
    props: true,
  },
  {
    path: "/singup/username",
    name: "SignupUsername",
    component: SignupUsername,
  },
  {
    path: "/singup/birth",
    name: "SignupBday",
    component: SignupBday,
  },
  {
    path: "/singup/profileImg",
    name: "SignupProfileImg",
    component: ProfileImg
    ,
  },
  {
    path: "/singup/success",
    name: "SignupSuccess",
    component: SignupSuccess,
  },
];
