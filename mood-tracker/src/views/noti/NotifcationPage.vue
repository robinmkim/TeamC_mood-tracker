<template>

  <div class="flex h-screen">
    <div class="w-1/5">
      <side-bar></side-bar>
    </div>

    <div class="flex-1 border-x-2">
      <div class="notiHeader bg-white-500 px-4 flex items-center justify-start" style="flex: 1; height: 45px;">
        <h1 class="notiHeaderMessage text-black font-bold text-2xl">알림</h1>
      </div>

      <div class="">
        <nav class="flex" role="tablist">
          <div
            v-for="(tab, index) in tabs"
            :key="index"
            class="text-base h-10 flex-1 flex justify-center items-center border-b-4"
            :class="{
              'border-[#64CCC5]': currentTab === index ,
              'hover:border-[#e0e0e0] border-transparent': currentTab !== index,
            }"
            @click="changeTab(index, tab.id)"
            role="tab"
          >
            <span class="notiTabName align-middle">
              {{ tab.name }}
            </span>
          </div>
        </nav>

        <div class="mt-3">
          <div v-for="(tab, index) in tabs" :key="index" :id="tab.id" v-show="currentTab === index" role="tabpanel">
            
            <!-- 전체 tab -->
            <div v-if="tab.id === 'notiTabsAll'">
              <div id="bar-with-underline-1">
                <!-- 알림 content - follow -->
                <div class="notiItem followNoti flex justify-start p-4 mt-[-12px] border-b border-gray-200 " >
                  <div class="notiItemImg z-0 h-14 w-14 overflow-hidden relative">
                    <!-- notiDisplay: 확인하지 않은 알림 표시 -->
                    <div class="notiDisplay absolute mt-0.5 z-1 h-4 w-4 rounded-full bg-red-500"></div>
                    <img class="object-contain rounded-full" src="..\..\assets\notiProfileImage01.jpg">
                  </div>
                  <div class="notiItemContent flex-1 flex h-14">
                    <div class="notiItemContent_ justify-center flex flex-col w-3/4 text-left pl-3">
                      <span class="notiItemContentTime text-sm text-slate-400 ">37초전</span>
                      <div class="notiItemContentMain w-auto flex items-center">
                        <span class="notiUserName font-bold text-lg">
                         UserName
                        </span>
                        님이 팔로우 하셨습니다.
                      </div>
                      <div class="notiItemContentTimePost bg-purple-300 flex items-center">
                          
                      </div>
                    </div>
                    <div class="notiItemContentButton flex w-1/4 justify-center items-center">
                      <div class="notiItemContentButtonYes rounded-full mr-3 bg-[#ffede6] h-10 w-20 flex justify-center items-center">
                        수락
                      </div>
                      <div class="notiItemContentButtonNo rounded-full bg-slate-200 h-10 w-20 flex justify-center items-center">
                        거절
                      </div>
                    </div>
                  </div>
                </div>

                <!-- 알림 content - like -->
                <div class="notiItem likeNoti h-28 flex items-center p-4 mt-[-12px] border-b border-gray-200">
                  <div class="notiItemImg z-0 h-14 w-14 overflow-hidden relative">
                    <div class="notiDisplay absolute mt-0.5 z-1 h-4 w-4 rounded-full bg-red-500"></div>
                    <img class="object-contain rounded-full" src="..\..\assets\notiProfileImage01.jpg">
                  </div>
                  <div class="notiItemContent flex-1 pl-3 justify-start ">
                    <span class="notiItemContentTime text-sm text-slate-400 flex">37초전</span>
                    <div class="notiItemContentMain w-auto flex items-center">
                      <span class="notiUserName font-bold text-lg">
                        UserName
                      </span>
                      님이 게시글에 좋아요를 남겼습니다!
                    </div>
                    <div class="notiItemContentTimePost flex items-center text-sm text-slate-400 mt-2">
                      님들아 나 사진 예쁜거 찍었음 봐줭
                    </div>
                  </div>
                </div>

                <!-- 알림 content - comment -->
                <div class="notiItem commentNoti h-28 flex items-center p-4 mt-[-12px] border-b border-gray-200">
                  <div class="notiItemImg z-0 h-14 w-14 overflow-hidden relative">
                    <img class="object-contain rounded-full" src="..\..\assets\notiProfileImage01.jpg">
                  </div>
                  <div class="notiItemContent flex-1 pl-3 justify-start ">
                    <span class="notiItemContentTime text-sm text-slate-400 flex">37초전</span>
                    <div class="notiItemContentMain w-auto flex items-center">
                      <span class="notiUserName font-bold text-lg">
                        UserName
                      </span>
                      님의 댓글
                    </div>
                    <div class="notiItemContentTimePost flex items-center text-sm mt-2">
                      정말 예쁜 것 같아요!
                    </div>
                  </div>
                </div>
                
              </div>
            </div>

            <!-- 팔로잉 tab -->
            <div v-else-if="tab.id === 'notiTabsFollow'">
              
            </div>

            <!-- 답글 tab -->
            <div v-else-if="tab.id === 'notiTabsReply'">
              
            </div>

          </div>
        </div>
      </div>



    </div>

    <div class="w-1/5">side menu</div>
  </div>
</template>

  
<script>
import SideBar from "@/components/SideBar"
  
  export default {

    data() {
      return {
        currentTab: 0,
        tabs: [
          { name: '전체', id: 'notiTabsAll' },
          { name: '팔로잉', id: 'notiTabsFollow' },
          { name: '답글', id: 'notiTabsReply' },
        ],
      };
    },
    methods: {
      changeTab(index, tabId) {
        this.currentTab = index;
        console.log(`현재 탭의 id: ${tabId}`);
      },
    },
    name: "NotiPage",
    components: {
      SideBar,
    }
  };



  </script>
  <style scoped>

  </style>