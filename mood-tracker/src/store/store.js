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
  };
}

export const store = new Vuex.Store({
  state: loadState(),
  getters: {},
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
