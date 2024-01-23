<!-- QnaList.vue -->
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
              v-show="isAccordionOpen && currentAccordionIndex === index"
              class="accordion-content bg-red-50 flex"
            >
              <MypageMain></MypageMain>
            </div>
          </li>
        </ul>
      </li>
    </ul>
  </div>
</template>
<script>
import MypageMain from "@/views/mypage/MypageMain.vue";
import apiClient from "@/utils/apiClient";

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

      idx: null,
      ReportList: [],
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
  components: {
    MypageMain,
  },
  //추가
  mounted() {
    // this.faqPage();
    this.repoertGetList();
  },

  methods: {
    goToDetailPage(item) {
      // 신고자인지, 신고게시물인지 확인하고수정하기
      this.$router.push(`/PostDetail/${item.report_id}`);
    },
    repoertGetList() {
      apiClient
        .get("/report/list", {
          headers: {},
        })
        .then((res) => {
          this.ReportList = res.data;
          console.log("list 응답 데이터: ", this.ReportList);
        })
        .catch((err) => {
          if (err.message.indexOf("Network Error") > -1) {
            alert("네트워크에 문제가 있습니다.\n잠시 후 다시 시도해주세요.");
          } else {
            console.error("데이터를 가져오는 중 오류 발생:", err);
          }
        });
    },

    // // 검색 함수 추가
    // faqPage(n) {
    //   if (this.page !== n) {
    //     this.page = n - 1;
    //   }
    //   this.faqGetList();
    // },
    // repoertGetList() {
    //   this.requestBody = {
    //     // 데이터 전송
    //     sk: this.search_key,
    //     sv: this.search_value,
    //     page: this.page,
    //     size: this.size,
    //   };
    //   apiClient
    //     .get("/admin/list", {
    //       params: this.requestBody,
    //       headers: {},
    //     })
    //     .then((res) => {
    //       if (res.data.result_code === "OK") {
    //         this.ReportList = res.data.data;
    //         this.paging = res.data.pagination;
    //         this.q_id =
    //           this.paging.total_list_cnt -
    //           (this.paging.page - 1) * this.paging.page_size;
    //         console.log("list : ", this.ReportList);
    //         console.log("paging : ", this.paging);
    //         console.log("q_id : ", this.q_id);
    //       }
    //     })
    //     .catch((err) => {
    //       if (err.message.indexOf("Network Error") > -1) {
    //         alert("네트워크에 문제가 있습니다.\n잠시 후 다시 시도해주세요.");
    //       } else {
    //         console.error("데이터를 가져오는 중 오류 발생:", err);
    //       }
    //     });
    // },

    reportDelete(qid) {
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

    // answeringMethod() {
    //   this.answeringShow = true;
    // },
  },
};
</script>

<style scoped>
/* 컴포넌트에만 스타일 적용 */
</style>
