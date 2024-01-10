<template>
  <div class="flex min-h-screen">
    <div class="w-1/5">
      <div class="flex mt-3 mb-3 items-center">
        <div
          class="notiItemImg z-0 h-14 w-14 overflow-hidden relative rounded-full bg-slate-200"
        ></div>
        <div class="ml-3 font-bold">ADMIN</div>
      </div>
      <nav class="flex flex-col" role="tablist">
        <div
          v-for="(tab, index) in tabs"
          :key="index"
          class="text-base h-10 flex items-center border-r-4"
          :class="{
            'border-[#64CCC5]': currentTab === index,
            'hover:border-[#e0e0e0] border-transparent': currentTab !== index,
          }"
          @click="changeTab(index, tab.id)"
          role="tab"
        >
          <div class="notiTabName text-left">
            {{ tab.name }}
          </div>
        </div>
      </nav>
    </div>
    <div class="flex-1 border-x">
      <div class="">
        <div
          v-for="(tab, index) in tabs"
          :key="index"
          :id="tab.id"
          v-show="currentTab === index"
          role="tabpanel"
        >
          <!-- 신고게시판 tab -->
          <div v-if="currentTab === 0">
            <div class="border m-10">
              <nav class="flex" role="tablist">
                <div
                  v-for="(
                    adminComplaintTab, adminComplaintTabIndex
                  ) in filteredAdminComplaintTabs"
                  :key="adminComplaintTabIndex"
                  class="text-base h-10 flex-1 flex justify-center items-center border-b-4 py-1.5"
                  :class="{
                    'border-[#64CCC5]':
                      currentSubTab === adminComplaintTabIndex,
                    'hover:border-[#e0e0e0] border-transparent':
                      currentSubTab !== adminComplaintTabIndex,
                  }"
                  @click="changeSubTab(adminComplaintTabIndex)"
                  role="tab"
                >
                  <div class="notiTabName text-left">
                    {{ adminComplaintTab.name }}
                  </div>
                </div>
              </nav>
              <!-- 게시글 신고 tab -->
              <div v-if="currentSubTab === 0">
                <div class="border-b-2 flex h-10">
                  <div class="flex w-1/12 items-center justify-center">no</div>
                  <div class="flex w-5/12 items-center">신고사유</div>
                  <div class="flex w-2/12 items-center justify-center">
                    신고자
                  </div>
                  <div class="flex w-2/12 items-center justify-center">
                    신고날짜
                  </div>
                  <div class="flex w-2/12 items-center justify-center">
                    처리
                  </div>
                </div>
                <ul class="accordion-item">
                  <li v-for="(item, index) in Postitems" :key="index">
                    <ul>
                      <li
                        class="accordion-title border-b flex h-8 hover:bg-slate-100"
                        @click="toggleAccordion(index)"
                      >
                        <div
                          class="flex w-1/12 items-center justify-center text-sm text-slate-500 border-b-1"
                        >
                          {{ item.number }}
                        </div>
                        <div
                          class="flex w-5/12 items-center text-sm text-slate-500 border-b-1"
                        >
                          {{ item.content }}
                        </div>
                        <div
                          class="flex w-2/12 items-center justify-center text-sm text-slate-500 border-b-1"
                        >
                          {{ item.user }}
                        </div>
                        <div
                          class="flex w-2/12 items-center justify-center text-sm text-slate-500 border-b-1"
                        >
                          {{ item.date }}
                        </div>
                        <div
                          class="flex w-2/12 items-center justify-center text-sm text-slate-500 pr-1 border-b-1"
                        >
                          <div
                            class="rounded-full mr-1 bg-slate-200 h-7 w-20 flex justify-center items-center"
                          >
                            삭제
                          </div>
                          <div
                            class="rounded-full bg-red-600 h-7 w-20 flex justify-center items-center text-white"
                          >
                            정지
                          </div>
                        </div>
                      </li>
                      <li>
                        <div
                          v-show="
                            isAccordionOpen && currentAccordionIndex === index
                          "
                          class="accordion-content bg-red-50 flex"
                        >
                          <PostDetailPageVue></PostDetailPageVue>
                        </div>
                      </li>
                    </ul>
                  </li>
                </ul>
              </div>
              <!-- 유저 신고 tab -->
              <div v-else-if="currentSubTab === 1">
                <div class="border-b-2 flex h-10">
                  <div class="flex w-1/12 items-center justify-center">no</div>
                  <div class="flex w-5/12 items-center">신고사유</div>
                  <div class="flex w-2/12 items-center justify-center">
                    신고자
                  </div>
                  <div class="flex w-2/12 items-center justify-center">
                    신고날짜
                  </div>
                  <div class="flex w-2/12 items-center justify-center">
                    처리
                  </div>
                </div>
                <ul class="accordion-item">
                  <li v-for="(item, index) in Useritems" :key="index">
                    <ul>
                      <li
                        class="accordion-title border-b flex h-8 hover:bg-slate-100"
                        @click="toggleAccordion(index)"
                      >
                        <div
                          class="flex w-1/12 items-center justify-center text-sm text-slate-500 border-b-1"
                        >
                          {{ item.number }}
                        </div>
                        <div
                          class="flex w-5/12 items-center text-sm text-slate-500 border-b-1"
                        >
                          {{ item.content }}
                        </div>
                        <div
                          class="flex w-2/12 items-center justify-center text-sm text-slate-500 border-b-1"
                        >
                          {{ item.user }}
                        </div>
                        <div
                          class="flex w-2/12 items-center justify-center text-sm text-slate-500 border-b-1"
                        >
                          {{ item.date }}
                        </div>
                        <div
                          class="flex w-2/12 items-center justify-center text-sm text-slate-500 pr-1 border-b-1"
                        >
                          <div
                            class="rounded-full mr-1 bg-slate-200 h-7 w-20 flex justify-center items-center"
                          >
                            삭제
                          </div>
                          <div
                            class="rounded-full bg-red-600 h-7 w-20 flex justify-center items-center text-white"
                          >
                            정지
                          </div>
                        </div>
                      </li>
                      <li>
                        <div
                          v-show="
                            isAccordionOpen && currentAccordionIndex === index
                          "
                          class="accordion-content bg-red-50 flex"
                        >
                          <MypageMain></MypageMain>
                        </div>
                      </li>
                    </ul>
                  </li>
                </ul>
              </div>
            </div>
          </div>

          <!-- QnA 게시판 tab -->
          <div v-if="currentTab === 1">
            <div class="border m-10">
              <nav class="flex" role="tablist">
                <div
                  v-for="(adminQnATab, adminQnATabIndex) in adminQnATabs"
                  :key="adminQnATabIndex"
                  class="text-base h-10 flex-1 flex justify-center items-center border-b-4 py-1.5"
                  :class="{
                    'border-[#64CCC5]': currentSubTab === adminQnATabIndex,
                    'hover:border-[#e0e0e0] border-transparent':
                      currentSubTab !== adminQnATabIndex,
                  }"
                  @click="changeSubTab(adminQnATabIndex)"
                  role="tab"
                >
                  <div class="notiTabName text-left">
                    {{ adminQnATab.name }}
                  </div>
                </div>
              </nav>
              <!-- FnA 신고 tab -->
              <div v-if="currentSubTab === 0">
                <FaqList></FaqList>
              </div>
              <!-- QnA 신고 tab -->
              <div v-else-if="currentSubTab === 1">
                <div class="border-b-2 flex h-10">
                  <div class="flex w-1/12 items-center justify-center">no</div>
                  <div class="flex w-5/12 items-center">질문</div>
                  <div class="flex w-2/12 items-center justify-center">
                    작성자
                  </div>
                  <div class="flex w-2/12 items-center justify-center">
                    등록일
                  </div>
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
                          v-show="
                            isAccordionOpen &&
                            currentAccordionIndex === newIndex
                          "
                          class="accordion-content flex border-b flex-col p-2"
                        >
                          <div
                            v-show="!updateShow"
                            class="read flex border-b text-sm p-2 pt-0 flex-col w-full text-left font-semibold"
                          >
                            QnA 질문 제목 {{ newIndex + 1 }}
                          </div>
                          <div
                            v-show="!updateShow"
                            class="flex text-sm p-2 text-left"
                          >
                            QnA 질문 내용 {{ newIndex + 1 }} QnA 질문 내용
                            {{ newIndex + 1 }} QnA 질문 내용
                            {{ newIndex + 1 }} QnA 질문 내용
                            {{ newIndex + 1 }} QnA 질문 내용
                            {{ newIndex + 1 }} QnA 질문 내용 {{ newIndex + 1 }}
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
                                :value="
                                  '질문의 답변을 입력해 주세요' + (newIndex + 1)
                                "
                              ></textarea>
                              <div
                                class="rounded-full bg-slate-200 h-7 w-20 flex justify-center items-center mt-2 text-sm"
                                @click="answeringMethod(newIndex)"
                              >
                                답변하기
                              </div>
                            </div>
                            <!-- <div
                                class="rounded-full bg-red-600 h-7 w-20 flex justify-center items-center text-white"
                              >
                                삭제
                              </div> -->
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
                              <div
                                class="flex justify-center items-center mt-4"
                              >
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
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// FnAitems 에서 작성자 테스트를 위해 user삭제
import PostDetailPageVue from "../post/PostDetailPage.vue";
import MypageMain from "../mypage/MypageMain.vue";
import FaqList from "../../components/admin/FaqList.vue";
// import Editor from "@toast-ui/editor";
// import "@toast-ui/editor/dist/toastui-editor.css";

export default {
  data() {
    return {
      currentTab: 0,
      currentSubTab: 0,
      currentAccordionIndex: null,
      currentOpenedAccordionIndex: null,
      updateShow: false,
      answeringShow: false,
      // 아코디언의 열림/닫힘 상태를 저장하는 배열 추가
      tabs: [
        { name: "신고게시판", id: "adminComplaint" },
        { name: "QnA게시판", id: "adminQnA" },
      ],
      adminComplaintTabs: [
        { name: "게시글", id: "adminComplaintPost" },
        { name: "유저", id: "adminComplaintUser" },
      ],
      adminQnATabs: [
        { name: "FnA", id: "adminQnAPost" },
        { name: "QnA", id: "adminQnAUser" },
      ],
      Postitems: [
        {
          number: 1,
          content: "타인의 생명을 위협하는 내용의 게시물",
          user: "user04",
          date: "2023.12.17",
        },
        {
          number: 2,
          content: "욕설 및 비방 글",
          user: "user07",
          date: "2023.12.18",
        },
        {
          number: 3,
          content: "도배 및 홍보 게시물",
          user: "user12",
          date: "2023.12.19",
        },
      ],
      Useritems: [
        {
          number: 1,
          content: "타인의 생명을 위협하는 내용의 게시물1",
          user: "user041",
          date: "2023.12.17",
        },
        {
          number: 2,
          content: "욕설 및 비방 글1",
          user: "user071",
          date: "2023.12.18",
        },
        {
          number: 3,
          content: "도배 및 홍보 게시물1",
          user: "user121",
          date: "2023.12.19",
        },
      ],
      FnAitems: [
        {
          number: 1,
          content: "FnA 질문 제목 1",
          user: "user041",
          date: "2023.12.17",
        },
        {
          number: 2,
          content: "FnA 질문 제목 2",
          user: "user071",
          date: "2023.12.18",
        },
        {
          number: 3,
          content: "FnA 질문 제목 3",
          user: "user121",
          date: "2023.12.19",
        },
      ],
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
      FaqList: {},
      editedTitle: "",
      editedContent: "",
    };
  },
  //추가
  components: { PostDetailPageVue, MypageMain, FaqList },
  methods: {
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

<style scoped></style>
