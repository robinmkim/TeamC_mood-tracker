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
        <div class="mt-3">
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

                    <!-- 프로필이미지 -->
                    <img
                      class="object-contain rounded-full"
                      src="..\..\assets\notiProfileImage01.jpg"
                    />
                  </div>
                  <div class="notiItemContent flex-1 flex h-14">
                    <div
                      class="notiItemContent_ justify-center flex flex-col w-3/4 text-left pl-3"
                    >
                      <span
                        class="notiItemContentTime text-sm text-slate-400"
                        >{{ bean.n_date }}</span
                      >
                      <div class="notiItemContentMain w-auto flex items-center">
                        <a :href="bean.n_url">
                          <span class="notiUserName font-bold text-lg">
                            {{ bean.n_user_from }}
                          </span>
                          님이 팔로우 하셨습니다.
                        </a>
                      </div>

                      <div
                        class="notiItemContentTimePost bg-purple-300 flex items-center"
                      ></div>
                    </div>
                    <div
                      class="notiItemContentButton flex w-1/4 justify-center items-center"
                    >
                      <div
                        class="notiItemContentButtonYes rounded-full mr-3 bg-[#ffede6] h-10 w-20 flex justify-center items-center"
                      >
                        수락
                      </div>
                      <div
                        class="notiItemContentButtonNo rounded-full bg-slate-200 h-10 w-20 flex justify-center items-center"
                      >
                        거절
                      </div>
                    </div>
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
                      class="absolute mt-0.5 z-1 h-4 w-4 rounded-full bg-red-500"
                    ></div>
                    <img
                      class="object-contain rounded-full"
                      src="..\..\assets\notiProfileImage01.jpg"
                    />
                  </div>
                  <div class="notiItemContent flex-1 pl-3 justify-start">
                    <span
                      class="notiItemContentTime text-sm text-slate-400 flex"
                      >{{ bean.n_date }}</span
                    >
                    <div class="notiItemContentMain w-auto flex items-center">
                      <a :href="bean.n_url">
                        <span class="notiUserName font-bold text-lg">
                          {{ bean.n_user_from }}
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
                </div>
              </div>
              <!-- [ED] 알림  comment -->

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
                    <img
                      class="object-contain rounded-full"
                      src="..\..\assets\notiProfileImage01.jpg"
                    />
                  </div>
                  <div class="notiItemContent flex-1 pl-3 justify-start">
                    <span
                      class="notiItemContentTime text-sm text-slate-400 flex"
                      >{{ bean.n_date }}</span
                    >
                    <div class="notiItemContentMain w-auto flex items-center">
                      <a :href="bean.n_url">
                        <span class="notiUserName font-bold text-lg">
                          {{ bean.n_user_from }}
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
import SideBar from "@/components/SideBar";
import axios from "axios";

export default {
  data() {
    return {
      currentTab: 0,
      tabs: [
        { name: "전체", id: "notiTabsAll" },
        { name: "팔로잉", id: "notiTabsFollow" },
        { name: "답글", id: "notiTabsReply" },
        { name: "좋아요", id: "notiTabsLike" },
      ],
      notiTabsAllList: null,
      notiTabsFollowList: null,
      notiTabsReplyList: null,
      notiTabsLikeList: null,

      showList: null,
      userNum: 1, //로그인 기능 붙으면 이건 삭제
    };
  },
  mounted() {
    console.log("mounted");
    axios
      .get("http://192.168.0.93:8083/notification/select/all", {
        params: {
          n_user: this.userNum,
        },
      })
      .then((res) => {
        console.log(res.data);
        this.showList = res.data;
      });
  },
  methods: {
    changeTab(index, tabId) {
      this.currentTab = index;
      console.log(`현재 탭의 id: ${tabId}`);
      if (tabId == "notiTabsAll") {
        console.log("ALL 조회");
        axios
          .get("http://192.168.0.93:8083/notification/select/all", {
            params: {
              n_user: this.userNum,
            },
          })
          .then((res) => {
            console.log(res.data);
            this.showList = res.data;
          });
      } else if (tabId == "notiTabsFollow") {
        console.log("Follow 조회");
        axios
          .get("http://192.168.0.93:8083/notification/select/follow", {
            params: {
              n_user: this.userNum,
            },
          })
          .then((res) => {
            console.log(res.data);
            this.showList = res.data;
          });
      } else if (tabId == "notiTabsReply") {
        console.log("Reply 조회");
        axios
          .get("http://192.168.0.93:8083/notification/select/comment", {
            params: {
              n_user: this.userNum,
            },
          })
          .then((res) => {
            console.log(res.data);
            this.showList = res.data;
          });
      } else if (tabId == "notiTabsLike") {
        console.log("Like 조회");
        axios
          .get("http://192.168.0.93:8083/notification/select/like", {
            params: {
              n_user: this.userNum,
            },
          })
          .then((res) => {
            console.log(res.data);
            this.showList = res.data;
          });
      }
    },
  },
  name: "NotiPage",
  components: {
    SideBar,
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
</style>
