import Vuex from "vuex";

const key = "myStore";

function loadState() {
  const storedState = localStorage.getItem(key);
  if (storedState) {
    return JSON.parse(storedState);
  }
  return {
    alertNewChat: false,
    alertNewNotice: false,
    totalCommentCount : 0,
  };
}

export const store = new Vuex.Store({
  state: loadState(),
  getters: {
    getCommentCount: (state) => state.comments.length,
    getReplyCount: (state) => state.replies.length,
    getTotalCount: (state) => state.comments.length + state.replies.length,
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
  },
  actions: {
    initializeStore({ commit }) {
      // localStore에서 변수값을 불러와 초기화
      const storedValue = localStorage.getItem(key);
      if (storedValue) {
        commit("setVariable", JSON.parse(storedValue));
      }
    },
  },
});
