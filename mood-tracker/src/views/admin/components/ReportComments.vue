<!-- ReportComments.vue -->
<template>
  <div>
    <div class="border-b-2 flex h-10">
      <div class="flex w-1/12 items-center justify-center">no</div>
      <div class="flex w-5/12 items-center">신고사유</div>
      <div class="flex w-2/12 items-center justify-center">신고자</div>
      <div class="flex w-2/12 items-center justify-center">신고날짜</div>
      <div class="flex w-2/12 items-center justify-center">처리</div>
    </div>
    <ul class="accordion-item">
      <li v-for="(item, newIndex) in CommentsList" :key="newIndex">
        <ul>
          <li
            class="accordion-title border-b flex h-8 hover:bg-slate-100"
            :class="{ 'bg-slate-50': isAccordionOpen(newIndex) }"
            @click="handleAccordionClick(newIndex, item.bc_id)"
          >
            <div
              class="flex w-1/12 items-center justify-center text-sm text-slate-500 border-b-1"
            >
              {{ item.report_id }}
            </div>
            <div
              class="flex w-5/12 items-center text-sm text-slate-500 border-b-1"
            >
              {{ item.report_type }}
            </div>
            <div
              class="flex w-2/12 items-center justify-center text-sm text-slate-500 border-b-1"
            >
              {{ item.report_bid }}
            </div>
            <div
              class="flex w-2/12 items-center justify-center text-sm text-slate-500 border-b-1"
            >
              {{ formatRegDate(item.regdate) }}
            </div>
            <div
              class="flex w-2/12 items-center justify-center text-sm text-slate-500 pr-1 border-b-1"
            >
              <div
                class="rounded-full mr-1 bg-slate-200 h-7 w-20 flex justify-center items-center"
                @click="reportCmtDelete(item.bc_id)"
              >
                삭제
              </div>
            </div>
          </li>
          <li>
            <div
              v-show="isAccordionOpen && currentAccordionIndex === newIndex"
              class="accordion-content bg-red-50 flex"
            >
              <div
                v-show="!updateShow"
                class="read flex border-b text-sm p-2 pt-0 flex-col w-full text-left font-semibold"
              >
                <div v-for="(boardItem, index) in boardData" :key="index">
                  <div v-if="item.bc_id === boardItem.b_id">
                    {{ boardItem.cm_content || "No content available" }}
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
import { apiClient2 as apiClient } from "@/utils/apiClient";

export default {
  data() {
    return {
      editor: null,
      currentAccordionIndex: null,
      currentOpenedAccordionIndex: null,
      updateShow: false,
      answeringShow: false,
      // 아코디언의 열림/닫힘 상태를 저장하는 배열 추가
      // 리스트 준비되면 Postitems -> ReportList변경하기
      CommentsList: [],
      boardData: [],
      // 페이징,검색
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
    };
  },

  //추가
  mounted() {
    this.reportPage();
  },

  methods: {
    reportPage(n) {
      if (this.page !== n) {
        this.page = n - 1;
      }
      this.repoertCmtGetList();
    },
    reportCmtGetDetail(b_c_id) {
      console.log("b_c_id :", b_c_id);
      apiClient
        .get(`cteam/admin/reports/comments/detail/${b_c_id}`)
        .then((res) => {
          const responseData = res.data;

          if (Array.isArray(responseData) && responseData.length > 0) {
            this.boardData = responseData.map((item) => {
              const [b_id, cm_content] = item;
              return {
                b_id,
                cm_content,
              };
            });
            console.log("board 응답 데이터: ", this.boardData);
          } else {
            console.error(
              "Received data has unexpected structure:",
              responseData
            );
          }
        })
        .catch((error) => {
          console.error("Error fetching detail:", error);
        });
    },

    repoertCmtGetList() {
      apiClient
        .get("cteam/admin/reports/comments/list", {
          headers: {},
        })
        .then((res) => {
          this.CommentsList = res.data;
          console.log("commentList 응답 데이터: ", this.CommentsList);
        })
        .catch((err) => {
          if (err.message.indexOf("Network Error") > -1) {
            alert("네트워크에 문제가 있습니다.\n잠시 후 다시 시도해주세요.");
          } else {
            console.error("데이터를 가져오는 중 오류 발생:", err);
          }
        });
    },

    reportCmtDelete(b_c_id) {
      // 삭제할 데이터
      apiClient
        .delete(`cteam/admin/reports/comments/delete/${b_c_id}`)
        .then((response) => {
          console.log("데이터 삭제 성공", response);
          alert("신고 댓글 삭제성공");
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
    handleAccordionClick(index, bc_id) {
      // 클릭시 상세 호출
      this.reportCmtGetDetail(bc_id);

      this.toggleAccordion(index);
    },
  },
};
</script>

<style scoped>
/* 컴포넌트에만 스타일 적용 */
</style>
