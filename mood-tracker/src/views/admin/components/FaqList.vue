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
    <!-- Faq List div-->
    <ul class="accordion-item">
      <li v-for="(item, newIndex) in FaqList" :key="newIndex">
        <ul>
          <li
            class="accordion-title border-b flex h-8 hover:bg-slate-100"
            :class="{ 'bg-slate-50': isAccordionOpen(newIndex) }"
            @click="toggleAccordion(newIndex)"
          >
            <div
              class="flex w-1/12 items-center justify-center text-sm text-slate-500 border-b-1"
            >
              {{ item.qid }}
            </div>
            <div
              class="flex w-5/12 items-center text-sm text-slate-500 border-b-1"
            >
              {{ item.faq_title }}
            </div>
            <div
              class="flex w-2/12 items-center justify-center text-sm text-slate-500 border-b-1"
            >
              {{ formatRegDate(item.regdate) }}
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
  <!-- 페이징 폼 -->
  <div
    class="pagination w3-bar w3-padding-16 w3-small"
    v-if="paging.total_list_cnt > 0"
  >
    <span class="pg">
      <a
        href="javascript:;"
        @click="faqPage(1)"
        class="first w3-button w3-bar-item w3-border"
        >&lt;&lt;</a
      >
      <a
        href="javascript:;"
        v-if="paging.start_page > 10"
        @click="faqPage(`${paging.start_page}`)"
        class="prev w3-button w3-bar-item w3-border"
        >&lt;</a
      >
      <template v-for="(n, q_id) in paginavigation()">
        <template v-if="paging.page == n">
          <strong
            class="w3-button w3-bar-item w3-border bg-[#64CCC5] text-white mr-2 ml-2"
            :key="q_id"
          >
            {{ n }}
          </strong>
        </template>
        <template v-else>
          <a
            class="w3-button w3-bar-item w3-border hover:bg-gray-300 mr-2 ml-2"
            href="javascript:;"
            @click="faqPage(`${n}`)"
            :key="q_id"
          >
            {{ n }}
          </a>
        </template>
      </template>
      <a
        href="javascript:;"
        v-if="paging.total_page_cnt > paging.end_page"
        @click="faqPage(`${paging.end_page + 1}`)"
        class="next w3-button w3-bar-item w3-border"
        >&gt;</a
      >
      <a
        href="javascript:;"
        @click="faqPage(`${paging.total_page_cnt}`)"
        class="last w3-button w3-bar-item w3-border"
        >&gt;&gt;</a
      >
    </span>
  </div>
  <!-- 검색 폼 -->
  <div>
    <!-- 여기에 나머지 템플릿 내용을 추가하세요 -->
    <select v-model="search_key">
      <option value="">- 선택 -</option>
      <option value="faq_title">제목</option>
      <option value="faq_content">내용</option>
    </select>
    &nbsp;
    <input type="text" v-model="search_value" @keyup.enter="faqPage()" />
    &nbsp;
    <button @click="faqPage()">검색</button>
  </div>
</template>
<script>
import FaqInsert from "@/views/admin/components/FaqInsert.vue";
import apiClient from "@/utils/apiClient";

export default {
  components: { FaqInsert },
  data() {
    return {
      editor: null,
      currentAccordionIndex: null,
      currentOpenedAccordionIndex: null,
      updateShow: false,
      answeringShow: false,
      // 아코디언의 열림/닫힘 상태를 저장하는 배열 추가
      idx: null,
      FaqList: {},
      requestBody: {}, //리스트 페이지 데이터전송
      q_id: "", //게시판 숫자처리
      paging: {
        block: 0,
        end_page: 0,
        next_block: 0,
        page: 0,
        page_size: 0,
        prev_block: 0,
        start_index: 0,
        start_page: 0,
        total_block_cnt: 0,
        total_list_cnt: 0,
        total_page_cnt: 0,
      }, //페이징 데이터
      page: this.$route.query.page ? this.$route.query.page : 0,
      size: this.$route.query.size ? this.$route.query.size : 10,
      // 검색
      search_key: this.$route.query.sk ? this.$route.query.sk : "",
      search_value: this.$route.query.sv ? this.$route.query.sv : "",
      paginavigation: function () {
        //페이징 처리 for문 커스텀
        let pageNumber = []; //;
        let start_page = this.paging.start_page;
        let end_page = this.paging.end_page;
        for (let i = start_page; i <= end_page; i++) pageNumber.push(i);
        return pageNumber;
      },
      editedTitle: "",
      editedContent: "",
    };
  },
  //추가
  mounted() {
    this.faqPage();
  },

  methods: {
    // 검색 함수 추가
    faqPage(n) {
      if (this.page !== n) {
        this.page = n - 1;
      }
      this.faqGetList();
    },
    faqGetList() {
      this.requestBody = {
        // 데이터 전송
        sk: this.search_key,
        sv: this.search_value,
        page: this.page,
        size: this.size,
      };
      apiClient
        .get("cteam/admin/listfaq", {
          params: this.requestBody,
          headers: {},
        })
        .then((res) => {
          if (res.data.result_code === "OK") {
            this.FaqList = res.data.data;
            this.paging = res.data.pagination;
            this.q_id =
              this.paging.total_list_cnt -
              (this.paging.page - 1) * this.paging.page_size;
            console.log("list : ", this.FaqList);
            console.log("paging : ", this.paging);
            console.log("q_id : ", this.q_id);
          }
        })
        .catch((err) => {
          if (err.message.indexOf("Network Error") > -1) {
            alert("네트워크에 문제가 있습니다.\n잠시 후 다시 시도해주세요.");
          } else {
            console.error("데이터를 가져오는 중 오류 발생:", err);
          }
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
        const response = await apiClient.patch(
          `/cteam/admin/updateFaq/${qid}`,
          requestData
        );

        console.log("FAQ 업데이트 성공", response);
        alert("FAQ 업데이트 성공");
        this.successPopup();

        //초기화
        this.editedTitle = "";
        this.editedContent = "";
      } catch (error) {
        console.error("오류발생", error);
      }
    },
    faqdelete(qid) {
      // 삭제할 데이터
      apiClient
        .delete(`/cteam/admin/deleteFaq/${qid}`)
        .then((response) => {
          console.log("데이터 삭제 성공", response);
          alert("FAQ 삭제성공");
          this.successPopup();
        })
        .catch((error) => {
          console.error("데이터 삭제 실패", error);
        });
    },
    successPopup() {
      this.$router.go(0);
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
    // answeringMethod() {
    //   this.answeringShow = true;
    // },
  },
};
</script>

<style scoped>
/* 컴포넌트에만 스타일 적용 */
</style>
