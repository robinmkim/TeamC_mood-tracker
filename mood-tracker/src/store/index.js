import Vuex from "vuex";
import apiClient from "@/utils/apiClient";
import { jwtDecode } from "jwt-decode";

const key = "myStore";

function loadState() {
  const storedState = localStorage.getItem(key);

  let state = {
    alertNewChat: false,
    alertNewNotice: false,
    totalCommentCount : 0,
    userInfo: null,
  }

  if (storedState) {
    const parsedState = JSON.parse(storedState);
    state.alertNewChat = parsedState.alertNewChat;
    state.alertNewNotice = parsedState.alertNewNotice;
    state.totalCommentCount = parsedState.totalCommentCount;
    state.userInfo = parsedState.userInfo;
  }
  return state;
}

export const store = new Vuex.Store({
  state: loadState(),
  getters: {
    getCommentCount: (state) => state.comments.length,
    getReplyCount: (state) => state.replies.length,
    getTotalCount: (state) => state.comments.length + state.replies.length,
    getUserInfo: (state) => state.userInfo,
  },
  mutations: {
    setVariable(state, value) {
      state.alertNewChat = value.alertNewChat;
      state.alertNewNotice = value.alertNewNotice;
      localStorage.setItem(key, JSON.stringify(value));
    },
    showAlertNewChat: function (state) {
      state.alertNewChat = true;
      localStorage.setItem(key, JSON.stringify(state));
    },
    hideAlertNewChat: function (state) {
      state.alertNewChat = false;
      localStorage.setItem(key, JSON.stringify(state));
    },
    showAlertNewNotice: function (state) {
      state.alertNewNotice = true;
      localStorage.setItem(key, JSON.stringify(state));
    },
    hideAlertNewNotice: function (state) {
      state.alertNewNotice = false;
      localStorage.setItem(key, JSON.stringify(state));
    },
    setTotalCommentCount(state, count) {
      state.totalCommentCount = count;
    },
    setUserInfo(state, userInfo) {
      state.userInfo = userInfo;
    },
    updateUserInfo(state, userInfo) {
      state.userInfo = userInfo;
    },
    initializeUserInfo(state, userInfo) {
      state.userInfo = userInfo;
    }
  },
  actions: {
    initializeStore({ commit }) {
      // localStore에서 변수값을 불러와 초기화
      const storedValue = localStorage.getItem(key);
      if (storedValue) {
        commit("setVariable", JSON.parse(storedValue));
      }
    },
    async initializeUserInfo({ commit, state }) {
      try {
        const token = localStorage.getItem("jwtToken");
        const decoded = jwtDecode(token);
        const memberId = decoded.m_id;

        const response = await apiClient.get(`/member/info/${memberId}`);
        const userInfo = response.data;

        commit("initializeUserInfo", userInfo);
        state.userInfo = JSON.stringify(userInfo);
        localStorage.setItem("myStore", JSON.stringify(state));
      } catch (error) {
        console.error(error);
      }
    },
    async updateUserName({ state }, newName) {
      try{
        const response = await apiClient.put(`/member/profile/name`, {
          m_name: newName,
        });
  
        const userInfo = JSON.parse(state.userInfo);
        userInfo.m_name = newName;
        state.userInfo = JSON.stringify(userInfo);
        localStorage.setItem("myStore", JSON.stringify(state));

        console.log(response);
      } catch(error) {
        console.log(error);
      }
    },
    async updateUserHandle({ state }, newHandle) {
      try{
        const response = await apiClient.put(`/member/profile/handle`, {
          m_handle: newHandle,
        });
  
        const userInfo = JSON.parse(state.userInfo);
        userInfo.m_handle = newHandle;
        state.userInfo = JSON.stringify(userInfo);
        localStorage.setItem("myStore", JSON.stringify(state));
  
        console.log(response);
      } catch(error) {
        console.log(error);
      }
    },
    async updateUserBio({ state }, newBio) {
      try{
        const response = await apiClient.put(`/member/profile/bio`, {
          m_bio: newBio,
        });
  
        const userInfo = JSON.parse(state.userInfo);
        userInfo.m_bio = newBio;
        state.userInfo = JSON.stringify(userInfo);
        localStorage.setItem("myStore", JSON.stringify(state));
  
        console.log(response);
      } catch(error) {
        console.log(error);
      }
    },
    async updateUserGender({ state }, newGender) {
      try{
        const response = await apiClient.put(`/member/profile/gender`, {
          m_gender: newGender,
        });
  
        const userInfo = JSON.parse(state.userInfo);
        userInfo.m_gender = newGender;
        state.userInfo = JSON.stringify(userInfo);
        localStorage.setItem("myStore", JSON.stringify(state));
  
        console.log(response);
      } catch(error) {
        console.log(error);
      }
    },
    async updateUserProfileImage({ state }, newImageName) {
      try{
        const response = await apiClient.put(`/member/profile/image`, {
          m_img_name: newImageName,
        });
  
        const userInfo = JSON.parse(state.userInfo);
        userInfo.m_gender = newImageName;
        state.userInfo = JSON.stringify(userInfo);
        localStorage.setItem("myStore", JSON.stringify(state));
  
        console.log(response);
      } catch(error) {
        console.log(error);
      }
    }
  },
});
