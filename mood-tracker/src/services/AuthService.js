import apiClient from "@/utils/apiClient";
import router from "@/router";

class AuthService {
  login = async (user) => {
    return await apiClient.post("/auth/login", {
      username: user.username,
      password: user.password,
    });
  };
  logout() {
    window.localStorage.removeItem("jwtToken");
    router.push({ name: "Login" });
  }
}

export default new AuthService();
