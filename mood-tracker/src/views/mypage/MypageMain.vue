<template>
  <div class="relative flex">
    <!-- 본문 -->
    <div class="border-x md:w-[800px] sm:w-[300px]" ref="postScrollContainer">
      <div class="">
        <div
          class="w-full h-20"
          :style="{ backgroundColor: backgroundColor }"
        ></div>
        <div class="flex h-32">
          <div class="relative flex">
            <div
              class="border-4 absolute top-[-40px] left-8 flex bg-slate-200 border-white w-32 h-32 rounded-full overflow-hidden"
            >
              <img :src="getPrfileImgUrl()" alt="profile_img" />
            </div>
          </div>
          <div class="flex-grow flex-col ml-48 mt-2 justify-start items-center">
            <div class="flex flex-col border-b-2 border-slate-200 w-full">
              <div class="flex my-2">
                <div>
                  <span class="text-3xl font-bold mr-3">{{
                    userInfo.m_name
                  }}</span>
                </div>
                <span class="mt-2 text-lg text-slate-400">
                  {{ userInfo.m_handle }}
                </span>
              </div>
              <div class="flex items-center w-full">
                <router-link
                  :to="{
                    path: '/follow',
                    query: {
                      searchType: 'follower',
                      m_id: userInfo.m_id,
                    },
                  }"
                  class="text-lg mr-3"
                >
                  팔로워 {{ followerCnt }}
                </router-link>
                <router-link
                  :to="{
                    path: '/follow',
                    query: {
                      searchType: 'following',
                      m_id: userInfo.m_id,
                    },
                  }"
                  class="text-lg"
                >
                  팔로잉 {{ followingCnt }}
                </router-link>
                <span v-if="isVisible">
                  <button
                    @click="follow"
                    class="m-2 h-[30px] w-[80px] rounded-lg bg-blue-500 hover:bg-blue-700 items-center justify-center text-white font-bold"
                    v-if="followed === true"
                  >
                    팔로우
                  </button>
                  <button
                    @click="follow"
                    class="m-2 h-[30px] w-[80px] rounded-lg bg-blue-500 hover:bg-blue-700 items-center justify-center text-white font-bold"
                    v-if="followed === false"
                  >
                    언팔로우
                  </button>
                </span>
              </div>
            </div>
            <div class="flex w-full mt-2 mr-auto">
              <div>
                <span class="mr-auto">{{ userInfo.m_bio }}</span>
              </div>
            </div>
          </div>
          <div class="flex" v-if="isVisible === false">
            <router-link
              to="/mypage/edit"
              class="m-2 h-[30px] w-[100px] border-2 rounded-lg border-slate-500 items-center justify-center"
            >
              <div class="text-slate-500">프로필 편집</div>
            </router-link>
          </div>
        </div>

        <div class="h-full">
          <nav class="flex" role="tablist">
            <div
              v-for="(tab, index) in tabs"
              :key="index"
              class="text-base h-10 flex-1 flex flex-grow justify-center items-center border-b-4"
              :class="{
                'border-[#64CCC5]': currentTab === index,
                'hover:border-[#e0e0e0] border-transparent':
                  currentTab !== index,
              }"
              @click="changeTab(index, tab.id)"
              role="tab"
            >
              <span class="notiTabName align-middle">
                {{ tab.name }}
              </span>
            </div>
          </nav>

          <div class="mt-3" ref="scrollContainer">
            <div
              v-for="(tab, index) in tabs"
              :key="index"
              :id="tab.id"
              v-show="currentTab === index"
              role="tabpanel"
            >
              <MyMood
                v-if="tab.id === 'mood'"
                :m_id="m_id"
                @handleMainSenti="handleMainSenti"
              />
              <!--내 게시글 목록-->
              <MyPost v-else-if="tab.id === 'post'" :m_id="m_id" />
              <!--달력-->
              <MoodCalander v-else-if="tab.id === 'calander'" :m_id="m_id" />
              <!--좋아요 목록-->
              <MyLike v-else-if="tab.id === 'like'" :m_id="m_id" />
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- <div class="h-full">
      <side-bar />
    </div> -->
  </div>
</template>

<script>
// import SideBar from "@/components/SideBar.vue";
import apiClient from "@/utils/apiClient";
import MyMood from "./Components/MyMood.vue";
import MoodCalander from "./Components/MoodCalander.vue";
import MyPost from "./Components/MyPost.vue";
import MyLike from "./Components/MyLike.vue";
import { jwtDecode } from "jwt-decode";

export default {
  name: "MypageMain",
  components: {
    // SideBar,
    MyMood,
    MoodCalander,
    MyPost,
    MyLike,
  },
  data() {
    return {
      userInfo: {},
      isLoading: false,
      emotionMap: {
        happy: "😆",
        angry: "😡",
        anxiety: "😬",
        hurt: "🤕",
        neutral: "😐",
        sad: "😢",
        surprise: "😨",
      },
      currentTab: 0,
      tabs: [],
      isBoardToggleDropdownOpen: false,
      followerCnt: 0,
      followingCnt: 0,
      isVisible: false,
      followed: false,
      m_id: null,
      mainSenti: "",
      backgroundColor: "",
    };
  },

  methods: {
    // 유저 정보
    getMemberInfo() {
      this.m_id = this.$route.path.replace("/", "");
      // 파라미터로 받은 m_id가 비어있으면 내 정보를 가져옴

      if (this.m_id === "") {
        const token = localStorage.getItem("jwtToken");
        const decoded = jwtDecode(token);
        this.m_id = decoded.m_id;
      }

      apiClient
        .get(`/member/info/${this.m_id}`)
        .then((res) => {
          res.data.m_handle = "@" + res.data.m_handle;
          this.userInfo = res.data;
        })
        .catch((err) => {
          console.log(err, "유저 정보 못불러옴");
        });
    },
    getPrfileImgUrl() {
      return `http://localhost:8083/${this.userInfo.m_img_path}${this.userInfo.m_img_name}`;
    },
    checkMemberId() {
      const pathMemberId = Number(this.$route.path.replace("/", ""));
      // 파라미터로 받은 memberId가 비어있으면 내 정보를 가져옴
      const token = localStorage.getItem("jwtToken");
      const decoded = jwtDecode(token);
      const loginMemberId = decoded.m_id;

      if (pathMemberId === loginMemberId || pathMemberId === 0) {
        this.tabs = [
          { name: "기분", id: "mood" },
          { name: "게시글", id: "post" },
          { name: "달력", id: "calander" },
          { name: "좋아요", id: "like" },
        ];
      } else {
        this.tabs = [
          { name: "기분", id: "mood" },
          { name: "게시글", id: "post" },
          { name: "달력", id: "calander" },
        ];
        this.isVisible = true;
      }
    },
    getFollowCnt() {
      let memberId = this.$route.path.replace("/", "");
      // 파라미터로 받은 memberId가 비어있으면 내 정보를 가져옴

      if (memberId === "") {
        const token = localStorage.getItem("jwtToken");
        const decoded = jwtDecode(token);
        memberId = decoded.m_id;
      }

      apiClient
        .get(`/follow/followcnt/${memberId}`)
        .then((res) => {
          this.followingCnt = res.data.followedCnt;
          this.followerCnt = res.data.followerCnt;
        })
        .catch((err) => {
          console.log(err, "팔로워 수 못불러옴");
        });
    },
    changeTab(index, tabId) {
      this.currentTab = index;
      // 탭이 변경되면
      if (tabId === "post") {
        console.log(`현재 탭의 id: ${tabId}`);
      } else if (tabId === "like") {
        console.log(`현재 탭의 id: ${tabId}`);
      } else {
        console.log(`현재 탭의 id: ${tabId}`);
      }
    },
    follow() {
      const followInfo = {
        followedId: this.userInfo.m_id,
      };
      apiClient
        .post("/follow", followInfo)
        .then((res) => {
          if (res.data === "Follow Success") {
            this.followerCnt = this.followerCnt + 1;
            this.followed = false;
          } else {
            this.followerCnt = this.followerCnt - 1;
            this.followed = true;
          }
        })
        .catch((err) => {
          console.log("팔로우 실패", err);
        });
    },
    checkFollow() {
      const memberId = this.$route.path.replace("/", "");

      const token = localStorage.getItem("jwtToken");
      const decoded = jwtDecode(token);
      const loginMemberId = decoded.m_id;
      if (this.m_id == "" || this.m_id == loginMemberId) {
        return;
      } else {
        apiClient
          .get(`/follow/check/${memberId}`)
          .then((res) => {
            console.log(res.data, "팔로우 체크 성공");
            if (res.data === "Not Followed") {
              this.followed = true;
            } else {
              this.followed = false;
            }
          })
          .catch((err) => {
            console.log(err, "팔로우 체크 실패");
          });
      }
    },
    handleMainSenti(senti) {
      this.mainSenti = senti;
      console.log("마이무드에서 넘어온", senti, "그걸 담은", this.mainSenti);
      this.backgroundColor = this.getBackgroundColor(this.mainSenti);
    },
    getBackgroundColor(mainSenti) {
      switch (mainSenti) {
        case "angry":
          return "#F67D73";
        case "happy":
          return "#FFDDE4";
        case "surprise":
          return "#FFE778";
        case "neutral":
          return "#D9F3C1";
        case "hurt":
          return "#597F61";
        case "sad":
          return "#A4BED3";
        case "anxiety":
          return "#C9BCE8";
        default:
          return ""; // 기본값 설정
      }
    },
  },
  created() {
    this.getMemberInfo();
    this.getPrfileImgUrl();
    this.checkMemberId();
    this.getFollowCnt();
    this.checkFollow();
  },
};
</script>

<style scoped="scoped"></style>
