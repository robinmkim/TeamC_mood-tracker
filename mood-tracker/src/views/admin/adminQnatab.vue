<!-- adminQnatab.vue -->
<template>
  <!-- QnA 게시판 상세 tab -->
  <div>
    <div class="border-b-2 flex h-10">
      <div class="flex w-1/12 items-center justify-center">no</div>
      <div class="flex w-5/12 items-center">질문</div>
      <div class="flex w-2/12 items-center justify-center">작성자</div>
      <div class="flex w-2/12 items-center justify-center">등록일</div>
      <div class="flex w-2/12 items-center justify-center"></div>
    </div>
    <ul class="accordion-item">
      <li v-for="(item, newIndex) in QnAitems" :key="newIndex">
        <ul>
          <li
            class="accordion-title border-b flex h-8 hover:bg-slate-100"
            :class="{ 'bg-slate-50': isAccordionOpen(newIndex) }"
          >
            <div
              class="flex w-1/12 items-center justify-center text-sm text-slate-500 border-b-1"
              @click="toggleAccordion(newIndex)"
            >
              {{ item.number }}
            </div>
            <div
              class="flex w-5/12 items-center text-sm text-slate-500 border-b-1"
              @click="toggleAccordion(newIndex)"
            >
              {{ item.content }}
            </div>
            <div
              class="flex w-2/12 items-center justify-center text-sm text-slate-500 border-b-1"
              @click="toggleAccordion(newIndex)"
            >
              {{ item.user }}
            </div>
            <div
              class="flex w-2/12 items-center justify-center text-sm text-slate-500 border-b-1"
              @click="toggleAccordion(newIndex)"
            >
              {{ item.date }}
            </div>
            <div
              class="flex w-2/12 items-center justify-center text-sm text-slate-500 pr-1 border-b-1"
              @click="toggleAccordion(newIndex)"
            ></div>
          </li>
          <li>
            <div
              v-show="isAccordionOpen && currentAccordionIndex === newIndex"
              class="accordion-content flex border-b flex-col p-2"
            >
              <div
                v-show="!updateShow"
                class="read flex border-b text-sm p-2 pt-0 flex-col w-full text-left font-semibold"
              >
                QnA 질문 제목 {{ newIndex + 1 }}
              </div>
              <div v-show="!updateShow" class="flex text-sm p-2 text-left">
                QnA 질문 내용 {{ newIndex + 1 }} QnA 질문 내용
                {{ newIndex + 1 }} QnA 질문 내용 {{ newIndex + 1 }} QnA 질문
                내용 {{ newIndex + 1 }} QnA 질문 내용 {{ newIndex + 1 }} QnA
                질문 내용 {{ newIndex + 1 }}
              </div>
              <div
                class="flex flex-col justify-center items-center my-1"
                v-show="!updateShow"
              >
                <div
                  class="rounded-full bg-slate-200 h-7 w-20 flex justify-center items-center text-sm"
                  @click="answeringMethod(newIndex)"
                  v-show="!answeringShow"
                >
                  답변하기
                </div>
                <div
                  v-show="answeringShow"
                  class="flex flex-col w-full justify-center items-center p-2"
                >
                  <textarea
                    class="bg-slate-50 text-sm rounded-lg border border-slate-200 focus:outline-slate-400 w-full h-80 resize-none"
                    :value="'질문의 답변을 입력해 주세요' + (newIndex + 1)"
                  ></textarea>
                  <div
                    class="rounded-full bg-slate-200 h-7 w-20 flex justify-center items-center mt-2 text-sm"
                    @click="answeringMethod(newIndex)"
                  >
                    답변하기
                  </div>
                </div>
              </div>
              <div v-show="updateShow === true" class="update">
                <div
                  v-show="updateShow === true"
                  class="accordion-content flex flex-col mt-4 m-2"
                >
                  <input
                    type="text"
                    class="rounded-lg h-10 text-sm bg-slate-50 border placeholder-slate-400 border-slate-200 focus:outline-slate-400 mb-2"
                    :value="'FnA 질문 내용' + (newIndex + 1)"
                  />
                  <textarea
                    class="bg-slate-50 text-sm rounded-lg border border-slate-200 focus:outline-slate-400 w-full h-80 resize-none"
                    :value="'FnA 질문 답변' + (newIndex + 1)"
                  ></textarea>
                  <div class="flex justify-center items-center mt-4">
                    <div
                      class="rounded-full bg-[#ffede6] h-10 w-28 flex justify-center items-center mb-4 text-sm"
                    >
                      수정하기
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </li>
        </ul>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  data() {
    return {
      editor: null,
      currentTab: 0,
      currentSubTab: 0,
      currentAccordionIndex: null,
      currentOpenedAccordionIndex: null,
      updateShow: false,
      answeringShow: false,
      // 아코디언의 열림/닫힘 상태를 저장하는 배열 추가

      QnAitems: [
        {
          number: 1,
          content: "QnA 질문 제목 1",
          user: "user041",
          date: "2023.12.17",
        },
        {
          number: 2,
          content: "QnA 질문 제목 2",
          user: "user071",
          date: "2023.12.18",
        },
        {
          number: 3,
          content: "QnA 질문 제목 3",
          user: "user121",
          date: "2023.12.19",
        },
      ],
    };
  },
  //추가
  methods: {
    formatRegDate(redate) {
      const date = new Date(redate);
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, "0");
      const day = String(date.getDate()).padStart(2, "0");
      return `${year}-${month}-${day}`;
    },
    changeTab(index, tabId) {
      this.currentTab = index;
      this.currentSubTab = 0;
      this.currentAccordionIndex = null;
      console.log(`현재 탭의 id: ${tabId}`);
      console.log(`아코디언 인덱스 초기화: ${this.currentAccordionIndex}`);
    },
    changeSubTab(index) {
      this.currentSubTab = index;
      this.currentAccordionIndex = null;
    },
    isAccordionOpen(index) {
      return this.currentAccordionIndex === index;
    },
    toggleAccordion(index) {
      console.log(`index====>: ${index}`);
      if (this.currentAccordionIndex === index) {
        if (this.updateShow === true) {
          this.updateShow = false;
        } else {
          this.currentAccordionIndex = null;
        }
      } else {
        this.currentAccordionIndex = index;
      }
      this.currentOpenedAccordionIndex = null;
      this.updateShow = false;
      this.answeringShow = false;
      console.log(`기존 아코디언-->: ${this.currentAccordionIndex}`);
      console.log(`지금 열린 아코디언: ${this.currentOpenedAccordionIndex}`);
    },
    openNewAccordion(index) {
      console.log(`index====>: ${index}`);
      if (this.currentOpenedAccordionIndex === index) {
        this.currentOpenedAccordionIndex = null;
      } else {
        this.currentAccordionIndex = null; // 기존 아코디언 초기화
        this.currentOpenedAccordionIndex = index;
      }
      this.updateShow = false;
      console.log(`기존 아코디언-->: ${this.currentAccordionIndex}`);
      console.log(`지금 열린 아코디언: ${this.currentOpenedAccordionIndex}`);
    },
    contentShowMethod(index) {
      if (this.updateShow === true) {
        this.updateShow = false;
        this.currentAccordionIndex = index;
      } else {
        console.log("함수 들어가지 전");
        //this.initializeEditor();
        this.updateShow = true;
      }
      // this.currentAccordionIndex = index;
    },
    answeringMethod() {
      this.answeringShow = true;
    },
  },
  computed: {
    filteredAdminComplaintTabs() {
      return this.adminComplaintTabs;
    },
  },
};
</script>

<style scoped>
/* 컴포넌트에만 스타일 적용 */
</style>
