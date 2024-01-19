<template>
  <div class="flex h-screen">
    <div class="w-1/5">
      <side-bar></side-bar>
    </div>

    <div class="flex-1 border-x-2">
      <div
        class="notiHeader bg-white-500 px-4 flex items-center justify-start"
        style="flex: 1; height: 45px"
      >
        <h1 class="notiHeaderMessage text-black font-bold text-2xl">알림</h1>
      </div>

      <div class="">
        <!-- [ST] 탭리스트 -->
        <nav class="flex" role="tablist">
          <div
            v-for="(tab, index) in tabs"
            :key="index"
            class="text-base h-10 flex-1 flex justify-center items-center border-b-4 relative"
            :class="{
              // 'border-[#64CCC5]': currentTab === index,
              'hover:border-[#e0e0e0] border-transparent': currentTab !== index,
            }"
            @click="changeTab(index, tab.id)"
            role="tab"
          >
            <transition name="selTab">
              <div
                class="selBar bg-[#64CCC5] w-full h-1 absolute bottom-[-4px]"
                v-if="currentTab === index"
              ></div>
            </transition>
            <span class="notiTabName align-middle">
              {{ tab.name }}
            </span>
          </div>
        </nav>
        <!-- [ED] 탭리스트 -->

        <!-- [ST] 알림 리스트 -->

        <div class="mt-3 overflow-auto">
          <div v-for="(bean, index) in showList" :key="index" :id="bean.n_id">
            <div>
              <!-- 알림 follow -->
              <div v-if="bean.n_type == 'follow'">
                <div
                  class="notiItem followNoti flex justify-start p-4 mt-[-12px] border-b border-gray-200"
                >
                  <div
                    class="notiItemImg z-0 h-14 w-14 overflow-hidden relative"
                  >
                    <!-- notiDisplay: 확인하지 않은 알림 표시 -->
                    <div
                      v-if="bean.n_state == 1"
                      class="notiDisplay absolute mt-0.5 z-1 h-4 w-4 rounded-full bg-red-500"
                    ></div>
                    <!-- <div v-if="bean.n_state == 1">
                      <div
                        class="notiDisplay absolute mt-[2px] ml-[3px] z-1 h-3 w-3 rounded-full bg-red-500"
                      ></div>
                      <div
                        class="notiDisplay absolute mt-[2px] ml-[3px] z-1 h-3 w-3 rounded-full bg-red-500 opacity-50 animate-ping"
                      ></div>
                    </div> -->

                    <!-- 프로필이미지 -->
                    <!-- <img
                      class="object-contain rounded-full"
                      :src="getProfileImage(bean.m_id_from)"
                    /> -->
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
                        >{{ bean.regdate }}</span
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

                    <div
                      class="notiItemContentButton flex w-1/4 justify-center items-center"
                    >
                      <div
                        class="notiItemContentButtonYes rounded-full mr-3 bg-[#ffede6] h-10 w-20 flex justify-center items-center hover:bg-[#fadcd0] duration-300"
                      >
                        수락
                      </div>
                      <div
                        class="notiItemContentButtonNo rounded-full bg-slate-200 h-10 w-20 flex justify-center items-center hover:bg-slate-300 duration-300"
                      >
                        거절
                      </div>
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

              <!-- [ST] 알림 comment -->
              <div v-if="bean.n_type == 'comment'">
                <!-- [ST] 알림 content - comment -->
                <div
                  class="notiItem commentNoti h-28 flex items-center p-4 mt-[-12px] border-b border-gray-200"
                >
                  <div
                    class="notiItemImg z-0 h-14 w-14 overflow-hidden relative"
                  >
                    <div
                      v-if="bean.n_state == 1"
                      class="notiDisplay absolute mt-0.5 z-1 h-4 w-4 rounded-full bg-red-500"
                    ></div>
                    <!-- <div v-if="bean.n_state == 1">
                      <div
                        class="notiDisplay absolute mt-[2px] ml-[3px] z-1 h-3 w-3 rounded-full bg-red-500"
                      ></div>
                      <div
                        class="notiDisplay absolute mt-[2px] ml-[3px] z-1 h-3 w-3 rounded-full bg-red-500 opacity-50 animate-ping"
                      ></div>
                    </div> -->
                    <img
                      class="object-contain rounded-full"
                      :src="bean.memberDto.img_byte"
                      alt="프로필 이미지"
                    />
                  </div>
                  <div class="notiItemContent flex-1 pl-3 justify-start">
                    <span
                      class="notiItemContentTime text-sm text-slate-400 flex"
                      >{{ bean.regdate }}</span
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
                <div
                  class="notiItem commentNoti h-28 flex items-center p-4 mt-[-12px] border-b border-gray-200"
                >
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
                      >{{ bean.regdate }}</span
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

              <!-- [ST] 알림 like -->
              <div v-if="bean.n_type == 'like'">
                <div
                  class="notiItem likeNoti h-28 flex items-center p-4 mt-[-12px] border-b border-gray-200"
                >
                  <div
                    class="notiItemImg z-0 h-14 w-14 overflow-hidden relative"
                  >
                    <div
                      v-if="bean.n_state == 1"
                      class="notiDisplay absolute mt-0.5 z-1 h-4 w-4 rounded-full bg-red-500"
                    ></div>
                    <!-- <div v-if="bean.n_state == 1">
                      <div
                        class="notiDisplay absolute mt-[2px] ml-[3px] z-1 h-3 w-3 rounded-full bg-red-500"
                      ></div>
                      <div
                        class="notiDisplay absolute mt-[2px] ml-[3px] z-1 h-3 w-3 rounded-full bg-red-500 opacity-50 animate-ping"
                      ></div>
                    </div> -->
                    <img
                      class="object-contain rounded-full"
                      :src="bean.memberDto.img_byte"
                      alt="프로필 이미지"
                    />
                  </div>
                  <div class="notiItemContent flex-1 pl-3 justify-start">
                    <span
                      class="notiItemContentTime text-sm text-slate-400 flex"
                      >{{ bean.regdate }}</span
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
              <!-- [ED] 알림 like -->
            </div>
          </div>
        </div>
        <!-- [ED] 알림 리스트 -->
      </div>
    </div>

    <div class="w-1/5">side menu</div>
  </div>
</template>

<script>
import apiClient from "./../../utils/apiClient";
import SideBar from "@/components/SideBar";
// import axios from "axios";

export default {
  name: "NotiPage",
  components: {
    SideBar,
  },
  data() {
    return {
      currentTab: 0,
      tabs: [
        { name: "전체", id: "notiTabsAll" },
        { name: "팔로잉", id: "notiTabsFollow" },
        { name: "답글", id: "notiTabsReply" },
        { name: "좋아요", id: "notiTabsLike" },
      ],
      showList: null,
    };
  },
  created() {
    console.log("mounted");
    this.loadNoticeListAll(); // 첫 화면은 '전체' 탭 이기 때문
    this.checkUnreadNotice(); // 안읽은 메세지 개수 체크 -> Header에 전달
  },
  methods: {
    checkUnreadNotice() {
      //안읽은 메세지가 1 이상이면 header아이콘 on, 0이면 off
      apiClient.get("notification/select/unread").then((res) => {
        console.log(">>>>>> NOTICE :: CHECK UNREAD NOTICE => ", res.data);
        if (res.data > 0) {
          console.log(">>>>>> NOTICE :: UNREAD NOTICE > 0");
        }
      });
    },
    loadNoticeListAll() {
      apiClient.get("/notification/select/all").then((res) => {
        console.log(res.data);
        this.showList = res.data;
      });
    },
    loadNoticeListFollow() {
      apiClient.get("/notification/select/follow").then((res) => {
        console.log(res.data);
        this.showList = res.data;
      });
    },
    loadNoticeListReply() {
      apiClient.get("/notification/select/comment").then((res) => {
        console.log(res.data);
        this.showList = res.data;
      });
    },
    loadNoticeListLike() {
      apiClient.get("/notification/select/like").then((res) => {
        console.log(res.data);
        this.showList = res.data;
      });
    },
    changeTab(index, tabId) {
      // 전체-팔로잉-답글-좋아요 탭으로 이동
      this.currentTab = index;
      console.log(`현재 탭의 id: ${tabId}`);
      this.loadNoticeList(tabId); // 탭 이동과 함께 알림 리스트도 다시 받아옵니다.
    },
    loadNoticeList(tabId) {
      if (tabId == "notiTabsAll") {
        console.log("ALL 조회");
        this.loadNoticeListAll();
        this.checkUnreadNotice(); // 안읽은 메세지 개수 체크 -> Header에 전달
      } else if (tabId == "notiTabsFollow") {
        console.log("Follow 조회");
        this.loadNoticeListFollow();
        this.checkUnreadNotice(); // 안읽은 메세지 개수 체크 -> Header에 전달
      } else if (tabId == "notiTabsReply") {
        console.log("Reply 조회");
        this.loadNoticeListReply();
        this.checkUnreadNotice(); // 안읽은 메세지 개수 체크 -> Header에 전달
      } else if (tabId == "notiTabsLike") {
        console.log("Like 조회");
        this.loadNoticeListLike();
        this.checkUnreadNotice(); // 안읽은 메세지 개수 체크 -> Header에 전달
      }
    },
    readNotice(n_id) {
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
      // this.$router.push("postDetail");
    },
    deleteNotice(n_id) {
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
              if (this.currentTab == 0) {
                this.loadNoticeListAll();
              } else if (this.currentTab == 1) {
                this.loadNoticeListFollow();
              } else if (this.currentTab == 2) {
                this.loadNoticeListReply();
              } else if (this.currentTab == 3) {
                this.loadNoticeListLike();
              }
            } else {
              console.log(n_id, " 삭제 실패");
            }
          })
          .catch((error) => {
            console.log(error);
          });
      }
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
  left: 16px;
  opacity: 0.2;
  transition: opacity 0.2s ease;
}
.deleteButton:hover {
  opacity: 1;
}
</style>
