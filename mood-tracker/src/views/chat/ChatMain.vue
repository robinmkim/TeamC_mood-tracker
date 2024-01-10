<template>
  <div class="flex h-screen">
    <new-chat
      v-if="showModal"
      :show="showModal"
      :members="members"
      @close="showModal = false"
    ></new-chat>
    <div class="w-1/5">
      <side-bar></side-bar>
    </div>

    <div
      class="flex w-2/5 border-l border-r 1px solid border-gray-200 flex-col"
    >
      <div class="flex items-center align-middle py-4 px-3">
        <h2 class="p-1 font-bold text-2xl">Message</h2>
        <button @click="createChatRoom" class="bg-gray-300 p-2">
          새로운 채팅
        </button>
      </div>
      <div class="overflow-auto">
        <ul class="list-none">
          <li
            v-for="room in rooms"
            :key="room.roomId"
            class="flex rounded-lg bg-gray-100 p-2 m-1 items-center hover:bg-gray-300"
          >
            <div
              class="flex items-center w-4/5"
              @click="loadMessages(room.roomId)"
            >
              <img class="rounded-full h-14 mr-2" :src="``" alt="2" />
              <div class="font-bold text-base">{{ room.roomId }}</div>
            </div>
            <div class="flex w-1/5">
              <div class="flex-col ml-auto">
                <button class="align-top" @click="toggleMenu(index, $event)">
                  <img class="" src="" alt="설정" />
                </button>
                <ul
                  v-if="room.isMenuOpen"
                  class="menu-list"
                  :style="{
                    top: `${menuPosition.top}px`,
                    left: `${menuPosition.left}px`,
                  }"
                >
                  <li><button type="button">대화삭제</button></li>
                  <li><button type="button">신고하기</button></li>
                </ul>
                <div class="text-xs">{{ room.message }}</div>
              </div>
            </div>
          </li>
        </ul>
      </div>
    </div>

    <div class="flex w-2/5 border-r border-gray-200 flex-col">
      <div
        class="flex pl-2 pr-2 pt-3 pb-3 items-center border-b border-gray-200"
      >
        <img class="rounded-full h-12 mr-2" src="" />
        <h2 class="text-lg font-bold">채팅방 1</h2>
      </div>
      <div class="mt-auto overflow-y-auto overflow-x-hidden">
        <div
          v-for="(message, index) in messages"
          :key="index"
          class="flex pl-4 pr-4 py-1 justify-start m-1"
        >
          <div v-if="message.isMine" class="flex flex-row ml-auto">
            <div class="justify-end self-end text-xs mt-1 mr-2">
              <span>{{ message.sendTime }}</span>
            </div>
            <div class="rounded-lg bg-blue-300 p-2">
              <span>{{ message.message }}</span>
            </div>
          </div>
          <div v-else class="flex flex-row mr-auto">
            <div class="rounded-lg bg-gray-300 p-2">
              <span>{{ message.message }}</span>
            </div>
            <div class="justify-end self-end text-xs mt-1 ml-2">
              <span>{{ message.sendTime }}</span>
            </div>
          </div>
        </div>
      </div>
      <div
        id="inputMessage"
        class="flex flex-grow items-center text-center p-1"
      >
        <span class="rounded-lg hover:bg-gray-100">
          <label for="file" class="">
            <img src="" alt="12" />
          </label>
          <input type="file" name="file" id="file" />
        </span>
        <span class="flex-grow mr-0.5">
          <textarea
            v-model="message"
            id="myTextarea"
            class="autoExpand bg-gray-300 block w-full rounded-md border-0 py-1.5 pl-3 pr-3 text-gray-900 ring-1 ring-inset ring-gray-300 placeholder:text-gray-600 focus:ring-2 focus:ring-inset focus:ring-gray-100 sm:text-sm sm:leading-6"
            rows="1"
            placeholder="메세지 입력.."
            @input="autoExpand"
            @keyup.enter="sendMessage"
          >
          </textarea>
        </span>
        <span class="flex items-center w-10">
          <button
            type="submit"
            class="flex rounded-lg hover:bg-gray-100 bg-gray-100 p-1"
          >
            전송
          </button>
        </span>
      </div>
    </div>
  </div>
</template>

<script>
import { reactive } from "vue";
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
import SideBar from "@/components/SideBar";
import axios from "axios";
import NewChat from "@/components/chat/NewChat";

export default {
  name: "ChatMain",
  components: {
    SideBar,
    NewChat,
  },
  data() {
    return {
      menuPosition: { top: 0, left: 0 },
      rooms: [],
      // 현재 선택한 채팅방의 ID
      roomId: 1,
      stompClient: null,
      // 채팅 입력창에 입력한 메시지
      message: "",
      // 현재 로그인한 사용자의 ID
      memberId: 1,
      // 현재 채팅방의 메시지
      // 최초 렌더링 시 서버에서 가져온 메시지 목록으로 초기화
      messages: [],
      // 현재 로그인한 사용자가 구독 중인 채팅방 목록
      // 중복 구독 방지를 위해 roomId 저장
      subscribedRooms: [],
      showModal: false,
      members: [],
      subscriptionId: null,
    };
  },
  async created() {
    this.connect();
    this.loadChatRooms();
  },
  methods: {
    autoExpand(event) {
      event.target.style.height = "auto";
      event.target.style.height = event.target.scrollHeight + "px";
    },
    toggleMenu(index, event) {
      // 모든 채팅방의 메뉴 상태를 닫음
      this.chatList.forEach((chat, i) => {
        if (i !== index && chat.isMenuOpen) {
          this.chatList[i] = reactive({
            ...chat,
            isMenuOpen: false,
          });
        }
      });

      // 선택한 채팅방의 메뉴 상태를 토글
      const rect = event.target.getBoundingClientRect();
      this.chatList[index] = reactive({
        ...this.chatList[index],
        menuPosition: {
          top: rect.bottom + window.scrollY + 5,
          left: rect.left + window.scrollX - 50,
        },
        isMenuOpen: !this.chatList[index].isMenuOpen,
      });

      // 메뉴 상태 업데이트 후 컴포넌트의 일반 상태를 업데이트
      this.menuPosition = {
        top: rect.bottom + window.scrollY + 5,
        left: rect.left + window.scrollX - 50,
      };
    },
    createChatRoom() {
      this.getMemberList();
      this.showModal = true;
    },
    getMemberList() {
      axios
        .get("http://192.168.0.214:8083/member/list")
        .then((res) => {
          this.members = res.data;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    connect() {
      const socket = new SockJS("http://192.168.0.214:8083/ws");
      this.stompClient = Stomp.over(socket);

      this.stompClient.connect(
        {},
        (frame) => {
          console.log(frame);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    onerror(error) {
      console.error("Connection error: " + error);
    },
    onMessageReceived(payload) {
      // 내 메시지와 다른 사람의 메시지 구분을 위해 isMine 값 추가
      let parseMessage = JSON.parse(payload.body);
      if (parseMessage.memberId === this.memberId) parseMessage.isMine = true;
      else parseMessage.isMine = false;

      // 날짜 포맷
      const hours = parseMessage.sendTime.substring(11, 13);
      const minutes = parseMessage.sendTime.substring(14, 16);
      const period = hours >= 12 ? "오후" : "오전";
      parseMessage.sendTime = `${period} ${hours}:${minutes}`;
      this.messages.push(parseMessage);
    },
    sendMessage() {
      if (!this.message.trim()) {
        return;
      }
      this.message = this.message.trim();
      let chatMessage = {
        roomId: this.roomId,
        memberId: this.memberId,
        message: this.message,
      };
      if (this.stompClient) {
        // webstomp-client의 send() 메서드를 사용하여 서버로 메시지 전송
        // send(destination, body, headers)
        this.stompClient.send(
          "/pub/chat/send",
          JSON.stringify(chatMessage),
          {}
        );
        this.message = "";
      }
    },
    loadChatRooms() {
      // 현재 로그인한 사용자의 채팅방 목록 조회
      axios
        .get(`http://192.168.0.214:8083/rooms/${this.memberId}`)
        .then((response) => {
          this.rooms = "";
          this.rooms = response.data;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    // 채팅방 클릭 시 해당 방에 해당하는 채팅 목록을 불러오는 메서드
    loadMessages(roomId) {
      // 기존 방 구독 해제
      // subscribe 시 생성된 subscriptionId를 사용하여 구독 해제
      this.stompClient.unsubscribe(`${this.subscriptionId}`, {});
      // this.subscribedRooms = this.subscribedRooms.filter(room => room !== this.roomId)

      //  새로운 방 구독
      // if(!this.subscribedRooms.includes(roomId)) {
      //     this.subscribedRooms.push(roomId);
      this.subscriptionId = this.stompClient.subscribe(
        `/topic/${roomId}`,
        this.onMessageReceived
      ).id;
      // }
      this.roomId = roomId;
      axios
        .get(`http://192.168.0.214:8083/rooms/${roomId}/messages`)
        .then((response) => {
          // 서버에서 roomId에 해당하는 채팅방의 메시지 목록을 가져옴
          // 가져온 메시지 목록으로 this.messages 초기화
          this.messages = [];
          this.messages = response.data;

          // 가져온 메시지들을 내 메시지와 상대 메시지를 구분하기 위해 isMine 값 추가
          for (let i = 0; i < this.messages.length; i++) {
            if (this.messages[i].memberId === this.memberId) {
              this.messages[i].isMine = true;
            } else {
              this.messages[i].isMine = false;
            }
            // 날짜 포맷
            const hours = this.messages[i].sendTime.substring(11, 13);
            const minutes = this.messages[i].sendTime.substring(14, 16);
            const period = hours >= 12 ? "오후" : "오전";

            this.messages[i].sendTime = `${period} ${hours}:${minutes}`;
          }
          // console.log(response.data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>

<style scoped>
.menu-list {
  list-style-type: none;
  padding: 0;
  margin: 0;
  position: absolute;
  background-color: #f9f9f9;
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
  z-index: 1;
  top: 0; /* 버튼과의 상대적인 위치 조절 */
  left: 100%;
}

.menu-list li {
  padding: 6px;
  cursor: pointer;
  border-bottom: 1px solid #ddd;
}

.menu-list li:last-child {
  border-bottom: none;
}

#file {
  display: none;
}

#inputMessage {
  min-height: 1em;
  height: auto;
  overflow: auto;
}
</style>
