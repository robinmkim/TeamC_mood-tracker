<template>
  <div class="flex h-full">
    <div class="w-full border-x">
      <div
        class="notiHeader bg-white-500 px-4 flex items-center justify-start h-12"
      >
        <h1 class="notiHeaderMessage text-black font-bold text-2xl">알림</h1>
      </div>

      <div class="border-b border-gray-200">
        <!-- [ST] 탭리스트 -->
        <nav class="flex" role="tablist">
          <div
            v-for="(tab, index) in tabs"
            :key="index"
            class="text-base h-10 flex-1 flex justify-center items-center border-b-4 relative"
            :class="{
              'hover:border-[#e0e0e0] border-transparent': currentTab !== index,
            }"
            @click="changeTab(index, tab.id), (tab.showAlert = false)"
            role="tab"
          >
            <transition name="selTab">
              <div
                class="selBar bg-[#64CCC5] w-full h-1 absolute bottom-[-4px]"
                v-if="currentTab === index"
              ></div>
            </transition>
            <span class="notiTabName align-middle">
              <div v-if="tab.showAlert">
                <div
                  class="notiDisplay absolute mt-[2px] ml-[-10px] z-1 h-2 w-2 rounded-full bg-red-500"
                ></div>
              </div>
              {{ tab.name }}
            </span>
          </div>
        </nav>
        <!-- [ED] 탭리스트 -->
        <div class="flex border-t border-gray-200">
          <div
            class="cursor-pointer pr-1.5 pl-1.5 ml-auto mt-1 mb-1 mr-1 text-sm text-center rounded-md border-2 border-teal-400"
            typeof="button"
            @click="readAllNotice"
          >
            모두 읽음
          </div>
          <div
            class="cursor-pointer pr-1.5 pl-1.5 mt-1 mb-1 mr-1 text-sm text-center rounded-md border-2 border-teal-400"
            typeof="button"
            @click="deleteAllNotice"
          >
            모두 삭제
          </div>
        </div>

        <!-- [ST] 알림 리스트 -->
        <div class="overflow-auto">
          <div v-for="(bean, index) in showList" :key="index" :id="bean.n_id">
            <div class="hover:bg-gray-100 border-t border-gray-200">
              <!-- 알림 follow -->
              <div v-if="bean.n_type == 'follow'">
                <div class="notiItem followNoti flex justify-start p-4">
                  <div
                    class="notiItemImg z-0 h-14 w-14 overflow-hidden relative"
                  >
                    <div
                      v-if="bean.n_state == 1"
                      class="notiDisplay absolute mt-0.5 z-1 h-4 w-4 rounded-full bg-red-500"
                    ></div>
                    <img
                      class="object-contain rounded-full"
                      :src="bean.memberDto.img_byte"
                      alt="프로필 이미지"
                    />
                  </div>
                  <div class="notiItemContent flex-1 flex h-14">
                    <div
                      class="notiItemContent_ justify-center flex flex-col w-3/4 text-left pl-3"
                    >
                      <span
                        class="notiItemContentTime text-sm text-slate-400"
                        >{{ formatTime(bean.regdate) }}</span
                      >
                      <div
                        class="notiItemContentMain w-auto flex items-center cursor-pointer"
                      >
                        <a :href="bean.n_url" @click="readNotice(bean.n_id)">
                          <span class="notiUserName font-bold text-lg">
                            {{ bean.memberDto.m_name }}
                          </span>
                          님이 팔로우 하셨습니다.
                        </a>
                      </div>

                      <!-- <div
                        class="notiItemContentTimePost bg-purple-300 flex items-center"
                      >
                        content
                      </div> -->
                    </div>
                  </div>

                  <div
                    class="flex self-start deleteButton"
                    @click="deleteNotice(bean.n_id)"
                  >
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      x="0px"
                      y="0px"
                      width="20"
                      height="20"
                      viewBox="0 0 50 50"
                    >
                      <path
                        d="M 7.71875 6.28125 L 6.28125 7.71875 L 23.5625 25 L 6.28125 42.28125 L 7.71875 43.71875 L 25 26.4375 L 42.28125 43.71875 L 43.71875 42.28125 L 26.4375 25 L 43.71875 7.71875 L 42.28125 6.28125 L 25 23.5625 Z"
                      ></path>
                    </svg>
                  </div>
                </div>
              </div>
              <!-- [ED] follow -->

              <!-- [ST] 알림 content - comment -->
              <div v-if="bean.n_type == 'comment'">
                <div class="notiItem commentNoti h-24 flex items-center p-4">
                  <div
                    class="notiItemImg z-0 h-14 w-14 overflow-hidden relative"
                  >
                    <div
                      v-if="bean.n_state == 1"
                      class="notiDisplay absolute mt-0.5 z-1 h-4 w-4 rounded-full bg-red-500"
                    ></div>
                    <img
                      class="object-contain rounded-full"
                      :src="bean.memberDto.img_byte"
                      alt="프로필 이미지"
                    />
                  </div>
                  <div class="notiItemContent flex-1 pl-3 justify-start">
                    <span
                      class="notiItemContentTime text-sm text-slate-400 flex"
                      >{{ formatTime(bean.regdate) }}</span
                    >
                    <div class="notiItemContentMain w-auto flex items-center">
                      <a :href="bean.n_url" @click="readNotice(bean.n_id)">
                        <span class="notiUserName font-bold text-lg">
                          {{ bean.memberDto.m_name }}
                        </span>
                        님의 댓글
                      </a>
                    </div>
                    <div
                      class="notiItemContentTimePost flex items-center text-sm mt-2"
                    >
                      {{ bean.n_content }}
                    </div>
                  </div>
                  <div
                    class="flex self-start deleteButton"
                    @click="deleteNotice(bean.n_id)"
                  >
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      x="0px"
                      y="0px"
                      width="20"
                      height="20"
                      viewBox="0 0 50 50"
                    >
                      <path
                        d="M 7.71875 6.28125 L 6.28125 7.71875 L 23.5625 25 L 6.28125 42.28125 L 7.71875 43.71875 L 25 26.4375 L 42.28125 43.71875 L 43.71875 42.28125 L 26.4375 25 L 43.71875 7.71875 L 42.28125 6.28125 L 25 23.5625 Z"
                      ></path>
                    </svg>
                  </div>
                </div>
              </div>
              <!-- [ED] 알림 댓글 comment -->

              <!-- [ST] 알림 대댓글 Reply -->
              <div v-if="bean.n_type == 'reply'">
                <div class="notiItem commentNoti h-24 flex items-center p-4">
                  <div
                    class="notiItemImg z-0 h-14 w-14 overflow-hidden relative"
                  >
                    <div
                      v-if="bean.n_state == 1"
                      class="notiDisplay absolute mt-0.5 z-1 h-4 w-4 rounded-full bg-red-500"
                    ></div>
                    <img
                      class="object-contain rounded-full"
                      :src="bean.memberDto.img_byte"
                      alt="프로필 이미지"
                    />
                  </div>
                  <div class="notiItemContent flex-1 pl-3 justify-start">
                    <span
                      class="notiItemContentTime text-sm text-slate-400 flex"
                      >{{ formatTime(bean.regdate) }}</span
                    >
                    <div class="notiItemContentMain w-auto flex items-center">
                      <a :href="bean.n_url" @click="readNotice(bean.n_id)">
                        <span class="notiUserName font-bold text-lg">
                          {{ bean.memberDto.m_name }}
                        </span>
                        님의 대댓글
                      </a>
                    </div>
                    <div
                      class="notiItemContentTimePost flex items-center text-sm mt-2"
                    >
                      {{ bean.n_content }}
                    </div>
                  </div>
                  <div
                    class="flex self-start deleteButton"
                    @click="deleteNotice(bean.n_id)"
                  >
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      x="0px"
                      y="0px"
                      width="20"
                      height="20"
                      viewBox="0 0 50 50"
                    >
                      <path
                        d="M 7.71875 6.28125 L 6.28125 7.71875 L 23.5625 25 L 6.28125 42.28125 L 7.71875 43.71875 L 25 26.4375 L 42.28125 43.71875 L 43.71875 42.28125 L 26.4375 25 L 43.71875 7.71875 L 42.28125 6.28125 L 25 23.5625 Z"
                      ></path>
                    </svg>
                  </div>
                </div>
              </div>
              <!-- [ED] 알림 대댓글 Reply -->

              <!-- [ST] 알림 게시물 좋아요 boardlike -->
              <div v-if="bean.n_type == 'boardlike'">
                <div class="notiItem likeNoti h-24 flex items-center p-4">
                  <div
                    class="notiItemImg z-0 h-14 w-14 overflow-hidden relative"
                  >
                    <div
                      v-if="bean.n_state == 1"
                      class="notiDisplay absolute mt-0.5 z-1 h-4 w-4 rounded-full bg-red-500"
                    ></div>
                    <img
                      class="object-contain rounded-full"
                      :src="bean.memberDto.img_byte"
                      alt="프로필 이미지"
                    />
                  </div>
                  <div class="notiItemContent flex-1 pl-3 justify-start">
                    <span
                      class="notiItemContentTime text-sm text-slate-400 flex"
                      >{{ formatTime(bean.regdate) }}</span
                    >
                    <div
                      class="notiItemContentMain w-auto flex items-center"
                      @click="readNotice(bean.n_id)"
                    >
                      <a :href="bean.n_url" @click="readNotice(bean.n_id)">
                        <span class="notiUserName font-bold text-lg">
                          {{ bean.memberDto.m_name }}
                        </span>
                        님이 게시글에 좋아요를 남겼습니다!
                      </a>
                    </div>
                    <div
                      class="notiItemContentTimePost flex items-center text-sm text-slate-400 mt-2"
                    >
                      {{ bean.n_content }}
                    </div>
                  </div>
                  <div
                    class="flex self-start deleteButton"
                    @click="deleteNotice(bean.n_id)"
                  >
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      x="0px"
                      y="0px"
                      width="20"
                      height="20"
                      viewBox="0 0 50 50"
                    >
                      <path
                        d="M 7.71875 6.28125 L 6.28125 7.71875 L 23.5625 25 L 6.28125 42.28125 L 7.71875 43.71875 L 25 26.4375 L 42.28125 43.71875 L 43.71875 42.28125 L 26.4375 25 L 43.71875 7.71875 L 42.28125 6.28125 L 25 23.5625 Z"
                      ></path>
                    </svg>
                  </div>
                </div>
              </div>
              <!-- [ED] 알림 게시물 좋아요 boardlike -->

              <!-- [ST] 알림 댓글 좋아요 commentlike -->
              <div v-if="bean.n_type == 'commentlike'">
                <div class="notiItem likeNoti h-24 flex items-center p-4">
                  <div
                    class="notiItemImg z-0 h-14 w-14 overflow-hidden relative"
                  >
                    <div
                      v-if="bean.n_state == 1"
                      class="notiDisplay absolute mt-0.5 z-1 h-4 w-4 rounded-full bg-red-500"
                    ></div>
                    <img
                      class="object-contain rounded-full"
                      :src="bean.memberDto.img_byte"
                      alt="프로필 이미지"
                    />
                  </div>
                  <div class="notiItemContent flex-1 pl-3 justify-start">
                    <span
                      class="notiItemContentTime text-sm text-slate-400 flex"
                      >{{ formatTime(bean.regdate) }}</span
                    >
                    <div
                      class="notiItemContentMain w-auto flex items-center"
                      @click="readNotice(bean.n_id)"
                    >
                      <a :href="bean.n_url" @click="readNotice(bean.n_id)">
                        <span class="notiUserName font-bold text-lg">
                          {{ bean.memberDto.m_name }}
                        </span>
                        님이 댓글에 좋아요를 남겼습니다!
                      </a>
                    </div>
                    <div
                      class="notiItemContentTimePost flex items-center text-sm text-slate-400 mt-2"
                    >
                      {{ bean.n_content }}
                    </div>
                  </div>
                  <div
                    class="flex self-start deleteButton"
                    @click="deleteNotice(bean.n_id)"
                  >
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      x="0px"
                      y="0px"
                      width="20"
                      height="20"
                      viewBox="0 0 50 50"
                    >
                      <path
                        d="M 7.71875 6.28125 L 6.28125 7.71875 L 23.5625 25 L 6.28125 42.28125 L 7.71875 43.71875 L 25 26.4375 L 42.28125 43.71875 L 43.71875 42.28125 L 26.4375 25 L 43.71875 7.71875 L 42.28125 6.28125 L 25 23.5625 Z"
                      ></path>
                    </svg>
                  </div>
                </div>
              </div>
              <!-- [ED] 알림 댓글 좋아요 commentlike -->

              <!-- [ST] 알림 대댓글 좋아요 replylike -->
              <div v-if="bean.n_type == 'replylike'">
                <div class="notiItem likeNoti h-24 flex items-center p-4">
                  <div
                    class="notiItemImg z-0 h-14 w-14 overflow-hidden relative"
                  >
                    <div
                      v-if="bean.n_state == 1"
                      class="notiDisplay absolute mt-0.5 z-1 h-4 w-4 rounded-full bg-red-500"
                    ></div>
                    <img
                      class="object-contain rounded-full"
                      :src="bean.memberDto.img_byte"
                      alt="프로필 이미지"
                    />
                  </div>
                  <div class="notiItemContent flex-1 pl-3 justify-start">
                    <span
                      class="notiItemContentTime text-sm text-slate-400 flex"
                      >{{ formatTime(bean.regdate) }}</span
                    >
                    <div
                      class="notiItemContentMain w-auto flex items-center"
                      @click="readNotice(bean.n_id)"
                    >
                      <a :href="bean.n_url" @click="readNotice(bean.n_id)">
                        <span class="notiUserName font-bold text-lg">
                          {{ bean.memberDto.m_name }}
                        </span>
                        님이 대댓글에 좋아요를 남겼습니다!
                      </a>
                    </div>
                    <div
                      class="notiItemContentTimePost flex items-center text-sm text-slate-400 mt-2"
                    >
                      {{ bean.n_content }}
                    </div>
                  </div>
                  <div
                    class="flex self-start deleteButton"
                    @click="deleteNotice(bean.n_id)"
                  >
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      x="0px"
                      y="0px"
                      width="20"
                      height="20"
                      viewBox="0 0 50 50"
                    >
                      <path
                        d="M 7.71875 6.28125 L 6.28125 7.71875 L 23.5625 25 L 6.28125 42.28125 L 7.71875 43.71875 L 25 26.4375 L 42.28125 43.71875 L 43.71875 42.28125 L 26.4375 25 L 43.71875 7.71875 L 42.28125 6.28125 L 25 23.5625 Z"
                      ></path>
                    </svg>
                  </div>
                </div>
              </div>
              <!-- [ED] 알림 대댓글 좋아요 replylike -->
            </div>
          </div>
        </div>
        <!-- [ED] 알림 리스트 -->
      </div>
    </div>
  </div>
</template>

<script>
import apiClient from "@/utils/apiClient";
import { EventBus } from "@/utils/EventBus.js";
import { watch, ref } from "vue";
// import axios from "axios";

export default {
  name: "NotiPage",
  // components: {},
  // data() {
  //   return {};
  // },
  setup() {
    const currentTab = ref(0);
    const showList = ref(null);
    const tabs = ref([
      { name: "전체", id: "notiTabsAll", showAlert: false },
      { name: "팔로잉", id: "notiTabsFollow", showAlert: false },
      { name: "답글", id: "notiTabsReply", showAlert: false },
      { name: "좋아요", id: "notiTabsLike", showAlert: false },
    ]);

    watch(
      () => EventBus.newAlertNoticeEvent,
      (newValue) => {
        if (newValue) {
          // Header로 부터 새 알림이 왔다고 전달받으면 -> 탭 부분에 알림 아이콘을 표시한다.
          const alertType = newValue.parseMessage.type;

          // 팔로우 알림 -> 전체탭, 팔로우탭이 아니면 아이콘 표시
          if (alertType == "follow") {
            console.log(">>> NotiPage :: 받은 알림 type", alertType);
            if (currentTab.value != 0 && currentTab.value != 1) {
              console.log("동작확인");
              tabs.value[1].showAlert = true;
            }
          }
          // 답글 알림 -> 전체탭, 답글탭이 아니면 아이콘 표시
          else if (alertType == "comment" || alertType == "reply") {
            if (currentTab.value != 0 && currentTab.value != 2) {
              tabs.value[2].showAlert = true;
            }
          }
          // 좋아요 알림 -> 전체탭, 좋아요탭이 아니면 아이콘 표시
          else if (
            alertType == "boardlike" ||
            alertType == "commentlike" ||
            alertType == "replylike"
          ) {
            if (currentTab.value != 0 && currentTab.value != 3) {
              tabs.value[3].showAlert = true;
            }
          }
          // 알림 리스트를 리로딩
          loadNoticeList();
        }
      }
    );
    function loadNoticeList() {
      if (currentTab.value == 0) {
        loadNoticeListAll();
      } else if (currentTab.value == 1) {
        loadNoticeListFollow();
      } else if (currentTab.value == 2) {
        loadNoticeListReply();
      } else if (currentTab.value == 3) {
        loadNoticeListLike();
      }
    }
    function loadNoticeListAll() {
      apiClient.get("/notification/select/all").then((res) => {
        showList.value = res.data;
      });
    }
    function loadNoticeListFollow() {
      apiClient.get("/notification/select/follow").then((res) => {
        showList.value = res.data;
      });
    }
    function loadNoticeListReply() {
      apiClient.get("/notification/select/comment").then((res) => {
        showList.value = res.data;
      });
    }
    function loadNoticeListLike() {
      apiClient.get("/notification/select/like").then((res) => {
        showList.value = res.data;
      });
    }
    return {
      currentTab,
      showList,
      loadNoticeListAll,
      loadNoticeListFollow,
      loadNoticeListReply,
      loadNoticeListLike,
      loadNoticeList,
      tabs,
    };
  },
  mounted() {
    this.loadNoticeListAll(); // 첫 화면은 '전체' 탭 이기 때문
  },
  methods: {
    changeTab(index, tabId) {
      // 전체-팔로잉-답글-좋아요 탭으로 이동
      this.currentTab = index;
      console.log(`현재 탭의 id: ${tabId}`);
      this.loadNoticeList(); // 탭 이동과 함께 알림 리스트도 다시 받아옵니다.
    },
    readNotice(n_id) {
      // 알림 읽음 처리
      apiClient
        .patch("/notification/read", {
          n_id: n_id,
        })
        .then((res) => {
          console.log(n_id, "를 읽었습니다. =", res.data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    deleteNotice(n_id) {
      // 알림을 1개씩 삭제
      let userReturn = confirm("알림을 영구적으로 삭제하시겠습니까?");
      if (userReturn == true) {
        apiClient
          .delete("/notification/delete", {
            data: {
              n_id: n_id,
            },
          })
          .then((res) => {
            if (res.data == 1) {
              console.log(n_id, " 삭제 완료");
              this.loadNoticeList();
            } else {
              console.log(n_id, " 삭제 실패");
            }
          })
          .catch((error) => {
            console.log(error);
          });
      }
    },
    readAllNotice() {
      apiClient
        .patch("/notification/readAll")
        .then(() => {
          console.log("알림을 모두 읽었습니다.");
        })
        .catch((error) => {
          console.log(error);
        });
    },
    deleteAllNotice() {
      // (전체/팔로우/답글/좋아요)알림을 모두 삭제
      const currentTabName = this.tabs[this.currentTab].name;
      let userReturn = confirm(
        currentTabName + " 알림을 모두 삭제하시겠습니까?"
      );
      if (userReturn == true) {
        let userReturn2 = confirm(
          "삭제한 알림은 복구할 수 없습니다. \n그래도 삭제하시겠습니까?"
        );
        if (userReturn2 == true) {
          if (this.currentTab == 0) {
            // 전체
            apiClient
              .delete("/notification/deleteAll/all")
              .then((res) => {
                if (res.data == 1) {
                  console.log(" 삭제 완료");
                  this.loadNoticeListAll();
                } else {
                  console.log(" 삭제 실패");
                }
              })
              .catch((error) => {
                console.log(error);
              });
          } else if (this.currentTab == 1) {
            // 팔로우
            apiClient
              .delete("/notification/deleteAll/follow")
              .then((res) => {
                if (res.data == 1) {
                  console.log(" 삭제 완료");
                  this.loadNoticeListFollow();
                } else {
                  console.log(" 삭제 실패");
                }
              })
              .catch((error) => {
                console.log(error);
              });
          } else if (this.currentTab == 2) {
            // 답글
            apiClient
              .delete("/notification/deleteAll/comment")
              .then((res) => {
                if (res.data == 1) {
                  console.log(" 삭제 완료");
                  this.loadNoticeListReply();
                } else {
                  console.log(" 삭제 실패");
                }
              })
              .catch((error) => {
                console.log(error);
              });
          } else if (this.currentTab == 3) {
            // 좋아요
            apiClient
              .delete("/notification/deleteAll/like")
              .then((res) => {
                if (res.data == 1) {
                  console.log(" 삭제 완료");
                  this.loadNoticeListLike();
                } else {
                  console.log(" 삭제 실패");
                }
              })
              .catch((error) => {
                console.log(error);
              });
          }
        }
      }
    },
    formatTime(dateString) {
      const now = new Date();
      const postDate = new Date(dateString);
      const diffInSeconds = Math.floor((now - postDate) / 1000);
      if (diffInSeconds < 60) {
        return `${diffInSeconds}초 전`;
      }
      const diffInMinutes = Math.floor(diffInSeconds / 60);
      if (diffInMinutes < 60) {
        return `${diffInMinutes}분 전`;
      }
      const diffInHours = Math.floor(diffInMinutes / 60);
      if (diffInHours < 24) {
        return `${diffInHours}시간 전`;
      }
      return postDate.toLocaleDateString("ko-KR");
    },
  },
};
</script>
<style scoped>
.selTab-enter-active {
  animation: bounce-in 0.5s;
}
/* .selTab-leave-active {
  animation: bounce-in 0.5s reverse;
} */
@keyframes bounce-in {
  0% {
    transform: scale(0);
    width: 100%;
  }
  50% {
    transform: scale(1.25);
    width: 90%;
  }
  100% {
    transform: scale(1);
    width: 100%;
  }
}
.deleteButton {
  position: relative;
  left: 14px;
  top: -10px;
  opacity: 0.3;
  transition: opacity 0.2s ease;
}
.deleteButton:hover {
  opacity: 1;
}
</style>
