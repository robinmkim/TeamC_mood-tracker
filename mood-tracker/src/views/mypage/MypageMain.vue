<template>
  <div class="flex h-screen">
    <!-- 왼쪽 사이드 메뉴 -->
    <div class="w-1/5 p-4">
      <p class="text-xl font-semibold">사이드 메뉴</p>
      <!-- 왼쪽 사이드 메뉴의 내용을 여기에 추가하세요 -->
    </div>

    <!-- 메인 콘텐츠 영역 -->
    <div class="flex-1">
      <!-- 자기 소개 부분 -->
      <div class="flex w-full h-120">
        <div class="profil-img flex items-center justify-center">
          <img
            class="w-20 h-20 rounded-full"
            src="..\..\assets\notiProfileImage01.jpg"
            alt="이미지 설명"
          />
        </div>
        <div class="w-full flex-col ml-4">
          <div class="flex h-16 flex-col-reverse">
            <div class="flex items-center">
              <div class="font-semibold mr-1 text-lg">Name</div>
              <div class="text-sm text-gray-500 mr-2">
                your.email@example.com
              </div>
              <div class="flex-grow"></div>
              <div
                class="bg-[#ffede6] text-[000000] rounded-md mr-5 p-0.5 text-sm cursor-pointer  "
                @click="goToEdit"
              >
                프로필 편집
              </div>
            </div>
          </div>

          <div class="relative h-5 w-100">
            <div class="inset-y-0 left-0 w-30 text-left text-sm">
              지금은 마이페이지를 만들고 있다.
            </div>
          </div>

          <div class="flex justify-between">
            <div class="flex items-center">
              <div class="text-gray-700 rounded-md mr-4">팔로우 : ??</div>
            </div>
            <div class="flex-grow"></div>

            <div class="flex items-center">
              <div class="text-gray-700 px-4 rounded-md ml-4">팔로잉 : ??</div>
            </div>
            <div class="flex-grow"></div>
            <div class="flex-grow"></div>
          </div>
        </div>
      </div>

      <div class="">
        <nav class="flex" role="tablist">
          <div
            v-for="(tab, index) in tabs"
            :key="index"
            class="text-base h-10 flex-1 flex justify-center items-center border-b-4"
            :class="{
              'border-[#64CCC5]': currentTab === index,
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
          <div
            v-for="(tab, index) in tabs"
            :key="index"
            :id="tab.id"
            v-show="currentTab === index"
            role="tabpanel"
          >
            <!-- 전체 tab -->
            <div v-if="tab.id === 'notiTabsAll'">
              <div id="bar-with-underline-1">
                <div
                  class="notiItem bg-red-500 flex justify-start p-4 border-b border-gray-200"
                  style="height: 80px"
                >
                  <div
                    class="notiItemImg bg-blue-500"
                    style="height: 50px; width: 50px; overflow: hidden"
                  >
                    <img
                      class="object-contain h-full w-full rounded-full"
                      src="..\..\assets\notiProfileImage01.jpg"
                    />
                  </div>
                  <div
                    class="notiItemContent flex-1 flex bg-purple-400"
                    style="height: 50px"
                  >
                    <div
                      class="notiItemContent_ items-stretch flex w-3/4 bg-purple-600"
                    >
                      <div
                        class="notiItemContentTime bg-purple-100 flex items-center"
                      >
                        <span class="notiItemContentTimeText text-[10px]"
                          >37초전</span
                        >
                      </div>
                      <div
                        class="notiItemContentMain bg-purple-200 flex items-center"
                      >
                        5
                      </div>
                      <div
                        class="notiItemContentTimePost bg-purple-300 flex items-center"
                      >
                        9
                      </div>
                    </div>
                    <div class="flex w-1/4 bg-rose-400">2</div>
                  </div>
                </div>
              </div>
            </div>

            <!-- 팔로잉 tab -->
            <div v-else-if="tab.id === 'notiTabsFollow'"></div>

            <!-- 답글 tab -->
            <div v-else-if="tab.id === 'notiTabsReply'"></div>
          </div>
        </div>
      </div>
    </div>

    <!-- 오른쪽 사이드 메뉴 -->
    <div class=" ">
      <p class="text-xl font-semibold">주우진</p>

      <!-- 간단한 테일윈드 달력 스타일 -->
      <div class="p-4 border rounded bg-white shadow-md">
        <div class="text-lg font-bold mb-4">{{ currentMonth }}</div>
        <div class="flex justify-between mb-2">
          <div class="text-sm font-semibold text-gray-600">일</div>
          <div class="text-sm font-semibold text-gray-600">월</div>
          <div class="text-sm font-semibold text-gray-600">화</div>
          <div class="text-sm font-semibold text-gray-600">수</div>
          <div class="text-sm font-semibold text-gray-600">목</div>
          <div class="text-sm font-semibold text-gray-600">금</div>
          <div class="text-sm font-semibold text-gray-600">토</div>
        </div>
        <div class="grid grid-cols-7 gap-2">
          <div v-for="day in days" :key="day.id" class="text-center">
            <div
              v-if="day.value"
              class="cursor-pointer p-2 rounded-full hover:bg-gray-200"
            >
              {{ day.value }}
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      currentTab: 0,
      tabs: [
        {
          name: "전체",
          id: "notiTabsAll",
        },
        {
          name: "팔로잉",
          id: "notiTabsFollow",
        },
        {
          name: "답글",
          id: "notiTabsReply",
        },
      ],
      currentMonth: "2023년 12월",
      days: [],
    };
  },
  mounted() {
    this.generateCalendar();
  },
  methods: {
    changeTab(index, tabId) {
      this.currentTab = index;
      console.log(`현재 탭의 id: ${tabId}`);
    },
    generateCalendar() {
      this.days = [];
      const currentDate = new Date();
      const currentMonth = currentDate.getMonth();

      //const currentDate = new Date();
      const firstDayOfMonth = new Date(2023, 11, 1); // 2023년 12월 1일

      // 월의 첫째 날을 설정합니다.
      firstDayOfMonth.setDate(1);

      // 월의 첫째 날의 요일을 가져옵니다. (0: 일요일, 1: 월요일, ..., 6: 토요일)
      const startingDay = firstDayOfMonth.getDay();

      // 첫째 날의 요일을 기준으로 달력의 시작 날짜를 설정합니다.
      firstDayOfMonth.setDate(1 - startingDay);

      // 42일(6주) 동안의 날짜를 생성합니다.
      for (let i = 0; i < 42; i++) {
        const day = new Date(firstDayOfMonth);
        day.setDate(firstDayOfMonth.getDate() + i);

        const isStartOfMonth = day.getMonth() === currentMonth;

        this.days.push({
          id: i,
          value: day.getDate(),
          isStartOfMonth: isStartOfMonth,
        });
      }
    },
      // 편집버트 클릭
    goToEdit() {
        console.log('goToEdit 메소드 호출됨');
        this.$router.push("/mypage/edit");
        },

  },

  
  name: "PageHome",

};
</script>

<style scoped="scoped"></style>
