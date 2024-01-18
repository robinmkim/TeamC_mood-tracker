import Vuex from "vuex";

export const store = new Vuex.Store({
  state: {
    alertNewChat: false, // 새 채팅메세지
  },
  getters: {},
  mutations: {
    showAlertNewChat: function (state) {
      state.alertNewChat = true;
    },
    hideAlertNewChat: function (state) {
      state.alertNewChat = false;
    },
  },
  actions: {},
});
