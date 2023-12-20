<template>

  <div class="flex h-screen">
    <div class="w-1/5 bg-sky-200">side menu</div>

    <div class="flex-1">
      <div class="notiHeader bg-white-500 px-4 flex items-center justify-start" style="flex: 1; height: 45px;">
        <h1 class="notiHeaderMessage text-black font-bold text-2xl">알림</h1>
      </div>

      <div class="">
        <nav class="flex" role="tablist">
          <div
            v-for="(tab, index) in tabs"
            :key="index"
            class="text-sm h-10 flex-1 flex justify-center items-center"
            :class="{
              'border-b-4 border-solid border-[#B2CFAF]': currentTab === index ,
              'hover:border-b-4 border-[#e0e0e0] ': currentTab !== index,
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
                <div class="notiItem bg-red-500 flex justify-start p-4 border-b border-gray-200 " style="height: 80px;">
                  <div class="notiItemImg bg-blue-500" style="height: 50px; width: 50px; overflow: hidden;">
                    <img class="object-contain h-full w-full rounded-full" src="..\..\assets\notiProfileImage01.jpg">
                  </div>
                  <div class="notiItemContent flex-1 flex bg-purple-400" style="height: 50px;">
                    <div class="notiItemContent_ items-stretch flex w-3/4 bg-purple-600">
                      <div class="notiItemContentTime bg-purple-100 flex items-center">
                          <span class="notiItemContentTimeText text-[10px]">37초전</span>
                      </div>
                      <div class="notiItemContentMain bg-purple-200 flex items-center">
                          5
                      </div>
                      <div class="notiItemContentTimePost bg-purple-300 flex items-center">
                          9
                      </div>
                    </div>
                    <div class="flex w-1/4 bg-rose-400">
                      2
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

    <div class="w-1/5 bg-sky-200">side menu</div>
  </div>
</template>

  
<script>
  
  
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
  };



  </script>
  <style scoped>

  </style>