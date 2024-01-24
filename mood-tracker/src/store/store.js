import Vuex from "vuex";
import apiClient from "@/utils/apiClient";
import { jwtDecode } from "jwt-decode";

const key = "myStore";

function loadState() {
  const storedState = localStorage.getItem(key);
  if (storedState) {
    const parsedState = JSON.parse(storedState);
    return {
      alertNewChat: parsedState.alertNewChat,
      alertNewNotice: parsedState.alertNewNotice,
      totalCommentCount : parsedState.totalCommentCount,
      userInfo: null,
    };
  }
  return {
    alertNewChat: false,
    alertNewNotice: false,
    totalCommentCount : 0,
    userInfo: null,
  };
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
    async initializeUserInfo({ commit }) {
      try {
        const token = localStorage.getItem("jwtToken");
        const decoded = jwtDecode(token);
        const memberId = decoded.m_id;

        const response = await apiClient.get(`/member/info/${memberId}`);
        const userInfo = response.data;

        commit("initializeUserInfo", userInfo);
      } catch (error) {
        console.error(error);
      }
    },
    async updateUserName({ commit, state }, newName) {
      try{
        const response = await apiClient.put(`/member/profile/name`, {
          m_name: newName,
        });
  
        const updateUserInfo = {...state.userInfo, m_name: newName};
        commit("updateUserInfo", updateUserInfo);
  
        console.log(response);
      } catch(error) {
        console.log(error);
      }
    },
    async updateUserHandle({ commit, state }, newHandle) {
      try{
        const response = await apiClient.put(`/member/profile/handle`, {
          m_handle: newHandle,
        });
  
        const updateUserInfo = {...state.userInfo, m_handle: newHandle};
        commit("updateUserInfo", updateUserInfo);
  
        console.log(response);
      } catch(error) {
        console.log(error);
      }
    },
    async updateUserBio({ commit, state }, newBio) {
      try{
        const response = await apiClient.put(`/member/profile/bio`, {
          m_bio: newBio,
        });
  
        const updateUserInfo = {...state.userInfo, m_bio: newBio};
        commit("updateUserInfo", updateUserInfo);
  
        console.log(response);
      } catch(error) {
        console.log(error);
      }
    },
    async updateUserGender({ commit, state }, newGender) {
      try{
        const response = await apiClient.put(`/member/profile/gender`, {
          m_gender: newGender,
        });
  
        const updateUserInfo = {...state.userInfo, m_gender: newGender};
        commit("updateUserInfo", updateUserInfo);
  
        console.log(response);
      } catch(error) {
        console.log(error);
      }
    },
    async updateUserProfileImage({ commit, state }, newImageName) {
      try{
        const response = await apiClient.put(`/member/profile/image`, {
          m_img_name: newImageName,
        });
  
        const updateUserInfo = {...state.userInfo, m_img_name: newImageName};
        commit("updateUserInfo", updateUserInfo);
  
        console.log(response);
      } catch(error) {
        console.log(error);
      }
    }
  },
});
