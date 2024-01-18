<template>
  <div class="bg-[#64CCC5] w-full py-3 flex justify-end items-center relative">
    <div id="title" class="absolute left-1/2 transform -translate-x-1/2">
      <router-link to="/">
        <span>Inside Out</span>
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
          <router-link to="/"
            ><span class="border-b" @click="toggleDropdown"
              >Home</span
            ></router-link
          >
          <router-link to="/login"
            ><span class="border-b" @click="toggleDropdown"
              >Log In</span
            ></router-link
          >
          <router-link to="/timeline"
            ><span class="border-b" @click="toggleDropdown"
              >타임라인</span
            ></router-link
          >
          <span class="border-b" @click="logout">로그아웃</span>
        </div>
      </div>
      <router-link to="/chat">
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
      <router-link to="/noti">
        <!-- notiDisplay: 확인하지 않은 알림 표시 -->
        <div v-if="alertNoticeIcon">
          <div
            class="notiDisplay absolute mt-[2px] ml-[3px] z-1 h-3 w-3 rounded-full bg-red-500"
          ></div>
          <div
            class="notiDisplay absolute mt-[2px] ml-[3px] z-1 h-3 w-3 rounded-full bg-red-500 opacity-50 animate-ping"
          ></div>
        </div>
        <!-- {{ alertIcon }} -->
        <!-- {{ this.$store.state.alertNewChat }} -->
        <!-- <div>받은 메시지: {{ receivedMessage }}</div> -->
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
// import { mapMutations } from "vuex";
// import EventBus from "@/eventBus/eventBus";
import { EventBus } from "./../utils/EventBus.js";
import { watch, ref } from "vue";
export default {
  name: "PageHeader",
  data() {
    return {
      isDropdownOpen: false,
      // stompClient: null,
      // subscriptionId: null,
      // memberId: null,
    };
  },
  setup() {
    //
    const receivedMessage = ref("");
    const stompClient = ref(null);
    const subscriptionId = ref(null);
    const memberId = ref(null);
    const alertNoticeIcon = ref(false);

    const sendEventLogout = () => {
      EventBus.myLoginEvent = { message: "logout" };
    };
    const sendEventNewChat = () => {
      EventBus.myChatEvent = { message: "newChat" };
    };

    watch(
      () => EventBus.myLoginEvent,
      (newValue) => {
        if (newValue) {
          receivedMessage.value = newValue.message;
          //newValue.message읽어서 로그인/로그아웃 구분
          // 하고 ws구독/구독해제 작업
          if (newValue.message == "logout") {
            console.log(">>>>>> HEADER :: WEBSOCKET DIS-CONNECTIED");
            disconnect();
          } else if (newValue.message == "login") {
            console.log(">>>>>> HEADER :: WEBSOCKET CONNECTIng");
            connect();
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
    function disconnect() {
      console.log("UNSUB => ", subscriptionId.value);
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

      const socket = new SockJS("http://localhost:8083/ws");
      stompClient.value = Stomp.over(socket);

      stompClient.value.connect(
        {},
        (frame) => {
          console.log(">>> HEADER :: WEBSOCKET CONNECTIED");
          console.log(frame);
          if (memberId.value != null) {
            console.log("memberId === ", memberId.value);

            subscriptionId.value = stompClient.value.subscribe(
              `/topic/notiChat/` + memberId.value,
              onMessageReceived
            ).id;
            console.log(
              ">>>>>> CONNECT :: subscriptionId.value ==> ",
              subscriptionId.value
            );
          }
        },
        (error) => {
          console.log("CONNECT ERROR :: ", error);
          connect();
        }
      );
    }
    //
    function onMessageReceived(payload) {
      console.log(">>> HEADER :: MESSAGE RECEIVED");
      let parseMessage = JSON.parse(payload.body);
      console.log(parseMessage);
      console.log("type ==========> ", parseMessage.type);
      if (parseMessage.type == "chat") {
        sendEventNewChat(); //  새 채팅이 왔다고 Sidebar에 전달합니다.
      } else {
        showAlertNoticeIcon(); // 알림아이콘 뱃지를 보여줍니다.
      }
    }
    function showAlertNoticeIcon() {
      console.log("SHOW ALERT ICON!!");
      alertNoticeIcon.value = true;
    }
    function hideAlertNoticeIcon() {
      console.log("SHOW ALERT ICON!!");
      alertNoticeIcon.value = false;
    }
    //
    return {
      sendEventLogout,
      receivedMessage,
      stompClient,
      subscriptionId,
      memberId,
      connect,
      onMessageReceived,
      alertNoticeIcon,
      showAlertNoticeIcon,
      hideAlertNoticeIcon,
    };
  },
  created() {
    this.connect();
    // const token = localStorage.getItem("jwtToken");
    // if (token != null) {
    //   const decoded = jwtDecode(token);
    //   this.memberId = decoded.m_id;
    // }
  },
  methods: {
    // ...mapMutations(["showAlertNewChat", "hideAlertNewChat"]),
    toggleDropdown() {
      this.isDropdownOpen = !this.isDropdownOpen;
    },
    logout() {
      localStorage.removeItem("jwtToken");
      this.$router.push({ path: "/login" });
      //
      this.hideAlertNoticeIcon();
      this.sendEventLogout();
    },
  },
};
</script>
<style scoped></style>
