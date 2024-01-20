<template>
  <div class="bg-[#64CCC5] w-full h-16 py-3 flex justify-end items-center">
    <div id="title" class="absolute left-1/2 transform -translate-x-1/2">
      <router-link to="/">
        <span class="text-2xl font-[400]">Songtiment</span>
      </router-link>
    </div>
    <div id="menu" class="flex mr-4">
      <div class="relative">
        <button @click="toggleDropdown">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 24 24"
            stroke-width="1.5"
            stroke="currentColor"
            class="w-8 h-8 px-1"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              d="M15.75 6a3.75 3.75 0 11-7.5 0 3.75 3.75 0 017.5 0zM4.501 20.118a7.5 7.5 0 0114.998 0A17.933 17.933 0 0112 21.75c-2.676 0-5.216-.584-7.499-1.632z"
            />
          </svg>
        </button>
        <div
          v-show="isDropdownOpen"
          class="absolute flex flex-col bg-white shadow-md mt-2 rounded-md py-2 w-32 right-[1px]"
        >
          <router-link to="/" @click="toggleDropdown"
            ><span class="border-b">Home</span></router-link
          >
          <router-link to="/login" @click="toggleDropdown"
            ><span class="border-b">Log In</span></router-link
          >
          <router-link to="/timeline" @click="toggleDropdown"
            ><span class="border-b">타임라인</span></router-link
          >
          <span
            class="border-b"
            @click="
              () => {
                toggleDropdown();
                logout();
              }
            "
            >로그아웃</span
          >
        </div>
      </div>
      <router-link to="/chat" @click="clickChatIcon">
        <!--  Alert 채팅  -->
        <div v-if="this.$store.state.alertNewChat">
          <div
            class="notiDisplay absolute mt-[2px] ml-[3px] z-1 h-3 w-3 rounded-full bg-red-500"
          ></div>
          <div
            class="notiDisplay absolute mt-[2px] ml-[3px] z-1 h-3 w-3 rounded-full bg-red-500 opacity-50 animate-ping"
          ></div>
        </div>
        <svg
          xmlns="http://www.w3.org/2000/svg"
          fill="none"
          viewBox="0 0 24 24"
          stroke-width="1.5"
          stroke="currentColor"
          class="w-8 h-8 px-1"
        >
          <path
            stroke-linecap="round"
            stroke-linejoin="round"
            d="M21.75 6.75v10.5a2.25 2.25 0 01-2.25 2.25h-15a2.25 2.25 0 01-2.25-2.25V6.75m19.5 0A2.25 2.25 0 0019.5 4.5h-15a2.25 2.25 0 00-2.25 2.25m19.5 0v.243a2.25 2.25 0 01-1.07 1.916l-7.5 4.615a2.25 2.25 0 01-2.36 0L3.32 8.91a2.25 2.25 0 01-1.07-1.916V6.75"
          />
        </svg>
      </router-link>
      <router-link to="/noti" @click="clickNoticeIcon">
        <!--  Alert 알림  -->
        <div v-if="this.$store.state.alertNewNotice">
          <div
            class="notiDisplay absolute mt-[2px] ml-[3px] z-1 h-3 w-3 rounded-full bg-red-500"
          ></div>
          <div
            class="notiDisplay absolute mt-[2px] ml-[3px] z-1 h-3 w-3 rounded-full bg-red-500 opacity-50 animate-ping"
          ></div>
        </div>
        <svg
          xmlns="http://www.w3.org/2000/svg"
          fill="none"
          viewBox="0 0 24 24"
          stroke-width="1.5"
          stroke="currentColor"
          class="w-8 h-8 px-1"
        >
          <path
            stroke-linecap="round"
            stroke-linejoin="round"
            d="M14.857 17.082a23.848 23.848 0 005.454-1.31A8.967 8.967 0 0118 9.75v-.7V9A6 6 0 006 9v.75a8.967 8.967 0 01-2.312 6.022c1.733.64 3.56 1.085 5.455 1.31m5.714 0a24.255 24.255 0 01-5.714 0m5.714 0a3 3 0 11-5.714 0"
          />
        </svg>
      </router-link>
    </div>
  </div>
</template>

<script>
import { jwtDecode } from "jwt-decode";
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
import { EventBus } from "./../utils/EventBus.js";
import { watch, ref } from "vue";
import { useStore } from "vuex";
import apiClient from "@/utils/apiClient.js";
import { useRoute } from "vue-router";
export default {
  name: "PageHeader",
  data() {
    return {
      isDropdownOpen: false,
    };
  },
  setup() {
    //
    const receivedMessage = ref("");
    const stompClient = ref(null);
    const subscriptionId = ref(null);
    const memberId = ref(null);
    const store = useStore(); // vuex store 가져오기
    const route = useRoute(); // route.path로 보고있는 페이지 경로 가져오기
    //
    watch(
      () => EventBus.myLoginEvent,
      (newValue) => {
        if (newValue) {
          receivedMessage.value = newValue.message;
          // Login.vue로부터 (로그인성공)이벤트버스를 전달받으면 Header에서 웹소켓 연결합니다.
          if (newValue.message == "login") {
            console.log(">>>>>> RECEIVED EVENTBUS ==> ", newValue.message);
            console.log(">>>>>> HEADER :: WEBSOCKET CONNECTIng");
            connect();
            getUnreadNotice(); // 안 읽은 알림의 개수를 가져오고 알림아이콘을 표시한다.
          } else {
            console.log(
              ">>>>>> HEADER :: UNIDENTIFIED MESSAGE => ",
              newValue.message
            );
          }
        }
      }
    );
    watch(
      () => EventBus.myChatEvent,
      (newValue) => {
        console.log("ch.채팅 => ", newValue.message);
      }
    );
    //
    function getUnreadNotice() {
      apiClient.get("/notification/select/unread").then((res) => {
        console.log(">>>>>> HEADER :: UNREAD NOTICE COUNT = ", res.data);
        const unreadNoticeNumber = res.data;
        //unreadNoticeNumber가 1 이상이면 알림 아이콘을 띄웁니다.
        if (unreadNoticeNumber > 0) {
          console.log(">>>>>> HEADER :: UNREAD => SHOW ALERT NOTICE ICON !!");
          showAlertNoticeIcon();
        }
      });
    }
    function disconnect() {
      // 로그아웃 시 사용 ( 웹소켓 연결 해제 )
      console.log(">>>>>> HEADER :: WEBSOCKET DIS-CONNECTIED");
      console.log(">>>>>> HEADER :: UNSUB => ", subscriptionId.value);
      if (stompClient.value) {
        stompClient.value.unsubscribe(subscriptionId.value, {});
      }
    }
    function connect() {
      console.log("SUB");
      const token = localStorage.getItem("jwtToken");
      if (token != null) {
        const decoded = jwtDecode(token);
        memberId.value = decoded.m_id;
      }
      console.log("token = ", token);
      console.log("CHECK MEMBERID>VALUE", memberId.value);

      const socket = new SockJS("http://192.168.0.43:8083/ws");
      stompClient.value = Stomp.over(socket);

      stompClient.value.connect(
        {},
        (frame) => {
          console.log(">>>>>> HEADER :: WEBSOCKET CONNECTIED");
          console.log(frame);
          if (memberId.value != null) {
            console.log("memberId === ", memberId.value);

            subscriptionId.value = stompClient.value.subscribe(
              `/topic/notiChat/` + memberId.value,
              onMessageReceived
            ).id;
            console.log(">>>>>> HEADER :: SUB => ", subscriptionId.value);
          }
        },
        (error) => {
          console.log(">>>>>> HEADER :: CONNECT ERROR :: ", error);
          // 연결 오류 시 3초 마다 다시 시도
          setTimeout(function () {
            connect();
          }, 3000);
        }
      );
    }
    function initialConnentWS() {
      console.log("SUB");
      const token = localStorage.getItem("jwtToken");
      if (token != null) {
        const decoded = jwtDecode(token);
        memberId.value = decoded.m_id;
      }
      console.log("token = ", token);
      console.log("CHECK MEMBERID>VALUE", memberId.value);

      const socket = new SockJS("http://192.168.0.43:8083/ws");
      stompClient.value = Stomp.over(socket);

      stompClient.value.connect(
        {},
        (frame) => {
          console.log(">>>>>> HEADER :: WEBSOCKET CONNECTIED");
          console.log(frame);
          if (memberId.value != null) {
            console.log("memberId === ", memberId.value);

            subscriptionId.value = stompClient.value.subscribe(
              `/topic/notiChat/` + memberId.value,
              onMessageReceived
            ).id;
            console.log(">>>>>> HEADER :: SUB => ", subscriptionId.value);
          }
        },
        (error) => {
          console.log(">>>>>> HEADER :: CONNECT ERROR :: ", error);
        }
      );
    }
    //
    function onMessageReceived(payload) {
      console.log(">>> HEADER :: MESSAGE RECEIVED");
      let parseMessage = JSON.parse(payload.body);
      console.log(parseMessage);
      console.log("type ==========>", parseMessage.type);
      if (parseMessage.type == "chat") {
        console.log("지금 보고 있는 페이지는 => ", route.path);
        if (route.path != "/chat") {
          // 채팅페이지를 보고있으면 아이콘을 띄우지 않습니다.
          console.log("알림 보낸 사람 = ", parseMessage.m_id_from);
          showAlertChatIcon();
        } else {
          EventBus.newAlertChatEvent = { message: "newAlertChat" };
        }
      } else {
        console.log("지금 보고 있는 페이지는 => ", route.path);
        if (route.path != "/noti") {
          // 알림페이지를 보고있으면 아이콘을 띄우지 않습니다.
          console.log("알림 보낸 사람 = ", parseMessage.m_id_from);
          showAlertNoticeIcon();
        } else {
          EventBus.newAlertNoticeEvent = {
            message: "newAlertNotice",
            parseMessage: parseMessage,
          };
        }
      }
    }
    function showAlertNoticeIcon() {
      console.log("SHOW ALERT NOTICE ICON !!");
      store.commit("showAlertNewNotice"); //store.js의 showAlertNewChat함수 호출
    }
    function hideAlertNoticeIcon() {
      console.log("HIDE ALERT NOTICE ICON!!");
      store.commit("hideAlertNewNotice");
    }
    function showAlertChatIcon() {
      console.log("SHOW ALERT CHAT ICON!!");
      store.commit("showAlertNewChat");
    }
    function hideAlertChatIcon() {
      console.log("HIDE ALERT CHAT ICON!!");
      store.commit("hideAlertNewChat");
    }
    //
    return {
      receivedMessage,
      stompClient,
      subscriptionId,
      memberId,
      connect,
      disconnect,
      onMessageReceived,
      showAlertNoticeIcon,
      hideAlertNoticeIcon,
      showAlertChatIcon,
      hideAlertChatIcon,
      initialConnentWS,
    };
  },
  created() {
    this.initialConnentWS();
  },
  methods: {
    toggleDropdown() {
      this.isDropdownOpen = !this.isDropdownOpen;
    },
    logout() {
      localStorage.removeItem("jwtToken");
      this.$router.push({ path: "/login" });
      //
      this.hideAlertNoticeIcon();
      this.hideAlertChatIcon();
      this.disconnect(); // 알림 웹소켓 연결 해제
    },
    clickChatIcon() {
      this.hideAlertChatIcon();
    },
    clickNoticeIcon() {
      this.hideAlertNoticeIcon();
    },
  },
};
</script>
<style scoped></style>
