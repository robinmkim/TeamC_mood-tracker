<!-- FaqList.vue -->
<template>
  <div>
    <div class="border-b-2 flex h-10">
      <div class="flex w-1/12 items-center justify-center">no</div>
      <div class="flex w-5/12 items-center">질문</div>
      <div class="flex w-2/12 items-center justify-center">등록일</div>
      <div class="flex w-2/12 items-center justify-center">
        <div
          class="rounded-full mr-1 bg-slate-200 h-7 w-20 flex justify-center items-center text-sm"
          @click.stop="openNewAccordion(index)"
        >
          등록하기
        </div>
      </div>
    </div>
    <div
      v-show="
        isAccordionOpen(newIndex) || currentOpenedAccordionIndex === index
      "
      class="accordion-content flex flex-col mt-4 m-2 border-b"
    >
      <!-- 새로운 Faq 등록 div-->
      <faqInsert></faqInsert>
    </div>
    <ul class="accordion-item">
      <li v-for="(item, newIndex) in Faqitems" :key="newIndex">
        <ul>
          <li
            class="accordion-title border-b flex h-8 hover:bg-slate-100"
            :class="{ 'bg-slate-50': isAccordionOpen(newIndex) }"
            @click="toggleAccordion(newIndex)"
          >
            <div
              class="flex w-1/12 items-center justify-center text-sm text-slate-500 border-b-1"
            >
              {{ newIndex + 1 }}
            </div>
            <div
              class="flex w-5/12 items-center text-sm text-slate-500 border-b-1"
            >
              {{ item.faq_title }}
            </div>
            <div
              class="flex w-2/12 items-center justify-center text-sm text-slate-500 border-b-1"
            >
              {{ formatRegDate(item.faq_regdate) }}
            </div>
            <div
              class="flex w-2/12 items-center justify-center text-sm text-slate-500 pr-1 border-b-1"
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
                Faq 질문 제목 {{ item.faq_title }}
              </div>
              <div v-show="!updateShow" class="flex text-sm p-2 text-left">
                Faq 질문 내용 : {{ item.faq_content }}
              </div>
              <div
                class="flex justify-center items-center my-1"
                v-show="!updateShow"
              >
                <div
                  class="rounded-full mr-1 bg-slate-200 h-7 w-20 flex justify-center items-center text-sm"
                  @click="contentShowMethod(newIndex)"
                >
                  수정
                </div>
                <div
                  class="rounded-full bg-red-600 h-7 w-20 flex justify-center items-center text-white text-sm"
                  @click="faqdelete(item.qid)"
                >
                  삭제
                </div>
              </div>
              <div v-show="updateShow === true" class="update">
                <div
                  v-show="updateShow === true"
                  class="accordion-content flex flex-col mt-4 m-2"
                >
                  <!-- <div>
                                  <div id="editor"></div>
                                </div> -->

                  <input
                    type="text"
                    class="rounded-lg h-10 text-sm bg-slate-50 border placeholder-slate-400 border-slate-200 focus:outline-slate-400 mb-2"
                    :value="item.faq_title"
                    @input="editedTitle = $event.target.value"
                  />
                  <textarea
                    class="bg-slate-50 text-sm rounded-lg border border-slate-200 focus:outline-slate-400 w-full h-80 resize-none"
                    :value="item.faq_content"
                    @input="editedContent = $event.target.value"
                  ></textarea>

                  <div class="flex justify-center items-center mt-4">
                    <div
                      class="rounded-full bg-[#ffede6] h-10 w-28 flex justify-center items-center mb-4 text-sm"
                      @click="updateFaq(item.qid)"
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
import faqInsert from "./FaqInsert.vue";
import apiClient from "@/utils/apiClient";

export default {
  components: { faqInsert },
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

      Faqitems: [],
      idx: null,
      FaqList: {},
      editedTitle: "",
      editedContent: "",
    };
  },
  //추가
  mounted() {
    this.FaqGetList();
  },
  methods: {
    faqInsert() {
      console.log("실행실행");
      let apiUrl = this.$serverUrl + "/admin/insert";
      // 서버로 데이터 전송
      // 서버로 보낼 데이터 객체 생성
      const requestData = {
        faq_title: this.faqTitle,
        faq_content: this.faqContent,
      };

      //INSERT
      console.log(apiUrl);
      this.$axios
        .post(apiUrl, requestData)
        .then((res) => {
          console.log(res.data);
          this.insertOpenSuccessPopup();
        })
        .catch((err) => {
          console.error(err);
        });
    },

    async save() {
      var content = this.$refs.editor.getContent();
      console.log(content);
    },
    FaqGetList() {
      console.log("aaaa");
      apiClient
        .get("cteam/admin/listfaq")
        .then((res) => {
          console.log("bbbb");
          this.FaqList = res.data;
          console.log("list : ", this.FaqList);
        })
        .catch((err) => {
          if (err.message.indexOf("Network Error") > -1) {
            alert("네트워크에 문제가 있습니다.\n잠시 후 다시 시도해주세요.");
          } else {
            console.error("데이터를 가져오는 중 오류 발생:", err);
          }
        });

      // this.$axios
      //   .get(this.$serverUrl + "/admin")
      //   .then((res) => {
      //     //this.list = res.data  //서버에서 데이터를 목록으로 보내므로 바로 할당하여 사용할 수 있다.
      //     this.Faqitems = res.data;
      //     console.log(`Faqitems ==========`, this.Faqitems);
      //   })
      //   .catch((err) => {
      //     if (err.message.indexOf("Network Error") > -1) {
      //       alert("네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.");
      //     }
      //   });
    },
    FaqView(idx) {
      this.requestBody.idx = idx;
      console.log(`requestBody ==========`, this.requestBody.idx);
      this.$router.push({
        path: "./detail",
        query: this.requestBody,
      });
    },
    async updateFaq(qid) {
      console.log("faqId : " + qid);
      //FAQ 수정하기 버튼을 클릭했을 때 동작
      try {
        const requestData = {
          faq_title: this.editedTitle,
          faq_content: this.editedContent,
        };

        // 서버로 데이터를 전송하는 HTTP PATCH 요청
        const response = this.$axios.patch(
          `${this.$serverUrl}/admin/${qid}`,
          requestData
        );

        console.log("FAQ 업데이트 성공", response);
        this.updateOpenSuccessPopup();

        //초기화
        this.editedTitle = "";
        this.editedContent = "";
      } catch (error) {
        console.error("오류발생", error);
      }
    },
    faqdelete(qid) {
      // 삭제할 데이터
      this.$axios
        .delete(`${this.$serverUrl}/admin/${qid}`)
        .then((response) => {
          console.log("데이터 삭제 성공", response);
          this.deleteOpenSuccessPopup();
        })
        .catch((error) => {
          console.error("데이터 삭제 실패", error);
        });
    },

    deleteOpenSuccessPopup() {
      // 팝업 창을 띄우기 위한 코드
      alert("FAQ 삭제성공"); // 브라우저 기본 팝업 사용
      window.location.href = "http://192.168.0.32:8081/admin";
    },

    updateOpenSuccessPopup() {
      // 팝업 창을 띄우기 위한 코드
      alert("FAQ 업데이트 성공"); // 브라우저 기본 팝업 사용
      window.location.href = "http://192.168.0.32:8081/admin";
    },

    insertOpenSuccessPopup() {
      // 팝업 창을 띄우기 위한 코드
      alert("FAQ 등록 성공"); // 브라우저 기본 팝업 사용
      window.location.href = "http://192.168.0.32:8081/admin";
    },

    formatRegDate(redate) {
      const date = new Date(redate);
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, "0");
      const day = String(date.getDate()).padStart(2, "0");
      return `${year}-${month}-${day}`;
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
