<template>
  <div class="flex h-screen" @click="closeToggleMenu">
    <new-chat
      v-if="showModal"
      :show="showModal"
      :members="members"
      @close="showModal = false"
      @start-chat="startNewChat"
    ></new-chat>

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
            v-for="(room, index) in rooms"
            :key="room.roomId"
            class="flex rounded-lg bg-gray-100 p-2 m-1 items-center hover:bg-gray-300"
          >
            <div class="flex flex-col items-start w-4/5 my-2 " @click="loadMessages(room.roomId, room.otherMemberName, room.otherMemberId)">
              <!-- <img class="rounded-full h-14 mr-2" :src="``" alt="profileImg" /> -->
              <div class="font-bold text-base">{{ room.otherMemberName }}</div>
              <div class="text-base">{{ room.message }}</div>
            </div>
            <div class="flex w-1/5">
              <div class="flex-col ml-auto">
                <button class="align-top" @click.stop="toggleMenu(index, $event)">
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    fill="none"
                    viewBox="0 0 24 24"
                    stroke-width="1.5"
                    stroke="currentColor"
                    class="w-6 h-6"
                  >
                    <path
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      d="M12 6.75a.75.75 0 1 1 0-1.5.75.75 0 0 1 0 1.5ZM12 12.75a.75.75 0 1 1 0-1.5.75.75 0 0 1 0 1.5ZM12 18.75a.75.75 0 1 1 0-1.5.75.75 0 0 1 0 1.5Z"
                    />
                  </svg>
                </button>
                <ul
                  v-if="room.isMenuOpen"
                  class="menu-list"
                  :style="{
                    top: `${menuPosition.top}px`,
                    left: `${menuPosition.left}px`,
                  }"
                >
                  <li><button @click="deleteChatRoom(room.roomId)" type="button">대화삭제</button></li>
                  <li><button @click="reportChatRoom" type="button">신고하기</button></li>
                </ul>
              </div>
            </div>
          </li>
        </ul>
      </div>
    </div>

    <div class="flex w-3/5 border-r border-gray-200 flex-col">
      <div
        class="flex pl-2 pr-2 pt-3 pb-3 items-center border-b border-gray-200"
      >
        <img class="rounded-full h-12 mr-2" src="" />
        <input class="text-lg font-bold" v-model="chattingMember.name" />
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
import { jwtDecode } from "jwt-decode";
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
import apiClient from "@/utils/apiClient";
import NewChat from "@/components/chat/NewChat";

export default {
  name: "ChatMain",
  components: {
    NewChat,
  },
  data() {
    return {
      menuPosition: { top: 0, left: 0 },
      rooms: [],
      // 현재 선택한 채팅방의 ID
      roomId: null,
      stompClient: null,
      // 채팅 입력창에 입력한 메시지
      message: "",
      // 현재 로그인한 사용자의 ID
      memberId: null,
      // 현재 채팅방의 메시지
      // 최초 렌더링 시 서버에서 가져온 메시지 목록으로 초기화
      messages: [],
      // 현재 로그인한 사용자가 구독 중인 채팅방 목록
      // 중복 구독 방지를 위해 roomId 저장
      subscribedRooms: [],
      showModal: false,
      members: [],
      subscriptionId: null,
      chattingMember: {"name": "", "id": ""},
    };
  },
  async created() {
    const token = localStorage.getItem("jwtToken");
    const decoded = jwtDecode(token);
    this.memberId = decoded.m_id;

    this.connect();
    this.loadChatRooms();
  },
  methods: {
    connect() {
      const socket = new SockJS("http://localhost:8083/ws");
      this.stompClient = Stomp.over(socket);
      this.stompClient.connect(
        {},
        (frame) => {
          console.log(frame);
          this.subscribe();
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
      const parseMessage = JSON.parse(payload.body);
      console.log(parseMessage);
      const roomId = parseMessage.roomId;
      
      // 현재 채팅방 목록에 해당 채팅방이 존재하는지 확인
      // 존재하면 해당 채팅방의 메시지를 업데이트
      const foundRoom = this.rooms.findIndex((room) => room.roomId === roomId);
      console.log("fofoff", foundRoom);
      console.log("fofoff", parseMessage.memberName);
      if (foundRoom == -1) {
        // 현재 채팅방 목록에 존재하지 않으면 새로운 chats에 넣기 위한 객체 생성
        const newRoom = {
          roomId: roomId,
          otherMemberId: parseMessage.memberId,
          otherMemberName: parseMessage.memberName,
          message: parseMessage.message,
          isMenuOpen: false,
          menuPosition: { top: 0, left: 0 },
          newMessage: true,
        };
        this.rooms.splice(0, 0, newRoom);
      }
      else {
        // 현재 채팅방 목록에 존재하면 해당 채팅방의 메시지를 업데이트
        this.rooms[foundRoom] = reactive({
          ...this.rooms[foundRoom],
          "message": parseMessage.message,
          "newMessage": true,
        });
      }
      // 현재 보고있는 방인지 확인
      if (this.roomId === roomId) {
        // 현재 보고있는 방이면 메시지 목록에 추가
        // 내 메시지와 다른 사람의 메시지 구분을 위해 isMine 값 추가
        this.rooms[foundRoom] = reactive({
          ...this.rooms[foundRoom],
          "newMessage": false,
        });
        if (parseMessage.memberId === this.memberId) parseMessage.isMine = true;
        else parseMessage.isMine = false;
        // 날짜 포맷
        const hours = parseMessage.sendTime.substring(11, 13);
        const minutes = parseMessage.sendTime.substring(14, 16);
        const period = hours >= 12 ? "오후" : "오전";
        parseMessage.sendTime = `${period} ${hours}:${minutes}`;
        this.messages.push(parseMessage);
      }
    },
    subscribe () {
      this.stompClient.subscribe(
        `/topic/chat/${this.memberId}`,
        this.onMessageReceived
      );
    },
    sendMessage() {
      if (!this.message.trim()) {
        return;
      }
      if (!this.roomId) {
        this.message = "";
        return;
      }
      this.message = this.message.trim();
      let chatMessage = {
        roomId: this.roomId,
        memberId: String(this.memberId),
        otherMemberId: this.chattingMember.id,
        message: this.message,
      };
      // 서버로 메시지 전송
      apiClient
        .post(`/send`, chatMessage)
        .then(response => {
          console.log(response.data);
          this.message = "";
        })
        .catch((error) => {
          console.log(error);
        });

      // if (this.stompClient) {
      //   // webstomp-client의 send() 메서드를 사용하여 서버로 메시지 전송
      //   // send(destination, body, headers)
      //   this.stompClient.send(
      //     "/pub/chat/send",
      //     JSON.stringify(chatMessage),
      //     {}
      //   );
      //   this.message = "";
      // }
    },
    autoExpand(event) {
      event.target.style.height = "auto";
      event.target.style.height = event.target.scrollHeight + "px";
    },
    toggleMenu(roomId, event) {
      // 모든 채팅방의 메뉴 상태를 닫음
      this.rooms.forEach((chat, i) => {
        if (i !== roomId && chat.isMenuOpen) {
          this.rooms[i] = reactive({
            ...chat,
            isMenuOpen: false,
          });
        }
      });
      // 선택한 채팅방의 메뉴 상태를 토글
      const rect = event.target.getBoundingClientRect();
      this.rooms[roomId] = reactive({
        ...this.rooms[roomId],
        menuPosition: {
          top: rect.bottom + window.scrollY + 5,
          left: rect.left + window.scrollX - 50,
        },
        isMenuOpen: true,
      });

      // 메뉴 상태 업데이트 후 컴포넌트의 일반 상태를 업데이트
      this.menuPosition = {
        top: rect.bottom + window.scrollY + 5,
        left: rect.left + window.scrollX - 50,
      };
    },
    deleteChatRoom(roomId) {
      apiClient
        .post(`/rooms/exit`, { "roomId": roomId })
        .then((response) => {
          const deletedRoomId = response.data;
          // 채팅방 목록에서 해당 채팅방 삭제
          const indexToRemove = this.rooms.findIndex(room => room.roomId === deletedRoomId);
          if (indexToRemove !== -1) {
            // rooms 배열에서 해당 인덱스의 요소를 제거
            this.rooms.splice(indexToRemove, 1);
          }
          // 채팅방 삭제 후 첫 번째 채팅방으로 이동
          this.roomId = null;
          this.messages = [];
        })
        .catch((error) => {
          console.log(error);
        });
    },
    // reportChatRoom() {

    // },
    createChatRoom() {
      this.getMemberList();
      this.showModal = true;
    },
    getMemberList() {
      apiClient
        .get("/follow/myfollow")
        .then((res) => {
          this.members = res.data;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    loadChatRooms() {
      // 현재 로그인한 사용자의 채팅방 목록 조회
      apiClient
        .get(`/rooms`)
        .then((response) => {
          console.log(response.data);
          this.rooms = "";
          this.rooms = response.data;
          for (let i = 0; i < this.rooms.length; i++) {
            this.rooms[i].isMenuOpen = false;
            this.rooms[i].menuPosition = { top: 0, left: 0 };
            this.rooms[i].newMessage = false;
          }
          console.log(this.rooms);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    // 채팅방 클릭 시 해당 방에 해당하는 채팅 목록을 불러오는 메서드
    loadMessages(roomId, otherMemberName, otherMemberId) {
      // this.createNewSubscribe(roomId);
      this.roomId = roomId;
      this.chattingMember.name = otherMemberName;
      this.chattingMember.id = otherMemberId;
      apiClient
        .get(`/rooms/${roomId}/messages`)
        .then((response) => {
          console.log(response.data);
          // 서버에서 roomId에 해당하는 채팅방의 메시지 목록을 가져옴
          // 가져온 메시지 목록으로 this.messages 초기화
          if (response.data === null) {
            this.messages = [];
            return;
          }
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
        })
        .catch((error) => {
          console.log(error);
        });
    },
    startNewChat(eventData) {
      const newChatMemberId = eventData.memberId;
      const newChatMemberName = eventData.memberName;
      
      const foundRoom = this.rooms.find(
        (room) => room.otherMemberId === newChatMemberId
      );
      // 두 사용자 간 채팅방이 존재하는지 확인
      // 채팅방이 존재하면 해당 채팅방으로 이동
      if (foundRoom !== undefined) {
        this.loadMessages(foundRoom.roomId, newChatMemberName, newChatMemberId);
        this.showModal = false;
        return;
      }
      else {
        const user = {
          myMemberId: this.memberId,
          otherMemberId: newChatMemberId,
        };

        // 이전에 대화했던 채팅방이 존재하는지 확인
        apiClient
          .post(`/rooms/newRoom`, user)
          .then((response) => {
            // 채팅방이 존재하면 해당 채팅방으로 이동
            // 기존에 채팅방을 나갔다가 다시 들어오는 경우
            const res = response.data;
            const roomData = {
              roomId: res.roomId,
              otherMemberId: res.newMemberId,
              otherMemberName: res.newMemberName,
              message: "",
              isMenuOpen: false,
              menuPosition: { top: 0, left: 0 },
            };
            this.rooms.splice(0, 0, roomData);
            // 새로운 채팅방을 구독
            // this.createNewSubscribe(res.roomId);
            // 나갔다가 들어온 경우 아무 메시지도 없으므로 this.messages 초기화
            this.messages = [];
          this.showModal = false;
          })
          .catch((error) => {
            console.log(error);
          });
      }
    },
    // createNewChatRoom(user) {
    //   apiClient
    //     .post("/rooms/new", user)
    //     .then((response) => {
    //       // 새로운 채팅방의 ID를 가져와서 채팅방 목록에 추가
    //       const res = response.data;
    //       const roomData = {
    //         roomId: res.roomId,
    //         otherMemberId: res.newMemberId,
    //         otherMemberName: res.newMemberName,
    //         message: "",
    //         isMenuOpen: false,
    //         menuPosition: { top: 0, left: 0 },
    //       };
    //       this.rooms.splice(0, 0, roomData);

    //       // 새로운 채팅방을 구독
    //       this.createNewSubscribe(res.roomId);
    //       // 새로운 채팅방은 메시지가 존재하지 않으므로 this.messages 초기화
    //       this.messages = [];
    //       // 새로운 채팅방 정보로 화면을 업데이트
    //       this.chattingMember.name = res.newMemberName;
    //       this.chattingMember.id = res.roomId;
    //     })
    //     .catch((error) => {
    //       console.log(error);
    //     });
    // },
    createNewSubscribe(roomId) {
      // 기존 방 구독 해제
      // subscribe 시 생성된 subscriptionId를 사용하여 구독 해제
      this.stompClient.unsubscribe(`${this.subscriptionId}`, {});
      // 새로운 방 구독
      // 현재 구독 중인 방(subscriptionId) 값 수정
      this.subscriptionId = this.stompClient.subscribe(
        `/topic/${roomId}`,
        this.onMessageReceived
      ).id;
      this.roomId = roomId;
    },
    closeToggleMenu() {
      this.rooms.forEach((room, i) => {
        if (room.isMenuOpen) {
          this.rooms[i] = reactive({
            ...room,
            isMenuOpen: false,
          });
        }
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
