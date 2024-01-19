<template>
  <div class="relative flex">
    <!-- 본문 -->
    <div class="border-x md:w-[800px] sm:w-[300px]" ref="postScrollContainer">
      <div class="">
        <div class="w-full h-20 bg-slate-200"></div>
        <div class="flex h-32">
          <div class="relative flex">
            <div
              class="border-4 absolute top-[-40px] left-8 flex border-white bg-gray-300 w-32 h-32 rounded-full overflow-hidden"
            >
              <img :src="getPrfileImgUrl()" alt="profile_img" />
            </div>
          </div>
          <div class="flex ml-48 mt-2 w-full items-center">
            <div class="flex flex-col mr-5">
              <span class="text-3xl font-bold">{{ userInfo.m_name }}</span>
              <p class="text-xl">{{ userInfo.m_handle }}</p>
            </div>
            <div class="user-stats">
              <span class="text-lg">팔로워 12</span> |
              <span class="text-lg">팔로잉 26</span>
            </div>
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
              <MyMood v-if="tab.id === 'mood'" />
              <!--내 게시글 목록-->
              <MyPost v-else-if="tab.id === 'post'" />
              <!--달력-->
              <MoodCalander v-else-if="tab.id === 'calander'" />
              <!--좋아요 목록-->
              <MyLike v-else-if="tab.id === 'like'"> </MyLike>
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
      tabs: [
        { name: "나의 Mood", id: "mood" },
        { name: "게시물", id: "post" },
        { name: "Mood 달력", id: "calander" },
        { name: "좋아요", id: "like" },
      ],
      isBoardToggleDropdownOpen: false,
    };
  },

  methods: {
    // 유저 정보
    getMemberInfo() {
      apiClient
        .get(`/member/myInfo`)
        .then((info) => {
          console.log("유저 정보를 불러옵니다");
          info.data.m_handle = "@" + info.data.m_handle;
          this.userInfo = info.data;
        })
        .catch((err) => {
          console.log(err, "유저 정보 못불러옴");
        });
    },
    getPrfileImgUrl() {
      return `http://localhost:8083/${this.userInfo.m_img_path}${this.userInfo.m_img_name}`;
    },

    // 내가 좋아요를 누른 게시글 불러오기

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
  },

  created() {
    // 데이터에 접근이 가능한 첫 번째 라이프 사이클

    this.getMemberInfo();
    this.getPrfileImgUrl();
  },
};
</script>

<style scoped="scoped"></style>
