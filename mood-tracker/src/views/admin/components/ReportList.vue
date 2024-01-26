<!-- ReportList.vue -->
<template>
  <div>
    <!-- 리스트 헤더 -->
    <div class="border-b-2 flex h-10">
      <div class="flex w-1/12 items-center justify-center">no</div>
      <div class="flex w-7/12 items-center">신고사유</div>

      <div class="flex w-2/12 items-center justify-center">신고날짜</div>
      <div class="flex w-2/12 items-center justify-center">처리</div>
    </div>
    <!-- 리스트 내용 -->
    <ul class="accordion-item">
      <li v-for="(item, newIndex) in ReportList" :key="newIndex">
        <ul>
          <li
            class="accordion-title border-b flex h-8 hover:bg-slate-100"
            :class="{ 'bg-slate-50': isAccordionOpen(newIndex) }"
            @click="handleAccordionClick(newIndex, item.bc_id)"
          >
            <!-- 각 열의 데이터 -->
            <div
              class="flex w-1/12 items-center justify-center text-sm text-slate-500 border-b-1"
            >
              {{ item.report_id }}
            </div>
            <div
              class="flex w-7/12 items-center text-sm text-slate-500 border-b-1"
            >
              {{ item.report_type }}
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
                @click="reportDelete(item.bc_id)"
              >
                삭제
              </div>
            </div>
          </li>
          <!-- 아코디언 내용 -->
          <li>
            <div
              v-show="isAccordionOpen && currentAccordionIndex === newIndex"
              class="accordion-content bg-red-50 flex"
            >
              <div
                v-show="!updateShow"
                class="read flex border-b text-sm p-2 pt-0 flex-col w-full text-left font-semibold"
              >
                <!-- 게시글 이미지 영역 -->
                <div
                  class="postImage relative mb-3"
                  v-if="board.mediaList.length > 0"
                >
                  {{ board.mediaList.length }}
                  <div class="flex items-center justify-center relative">
                    <!-- 이미지 목록 순회 -->
                    <div
                      v-for="(media, mediaindex) in board.mediaList"
                      :key="mediaindex"
                      class="h-60 overflow-hidden relative rounded-lg flex items-center justify-center"
                    >
                      <img
                        :src="getImageUrl(media)"
                        alt="Post image"
                        class="items-center rounded-lg"
                      />
                    </div>
                    <!-- 이미지 이전/다음 버튼 -->
                    <a
                      v-if="currentImageIndex > 1"
                      class="absolute top-1/2 transform -translate-y-1/2 cursor-pointer left-2.5 text-black"
                      @click="prevImage"
                    >
                      &#10094;
                    </a>
                    <a
                      v-if="currentImageIndex < board.mediaList.length"
                      class="absolute top-1/2 transform -translate-y-1/2 cursor-pointer right-2.5 text-black"
                      @click="nextImage"
                    >
                      &#10095;
                    </a>
                  </div>

                  {{ board.b_content }}
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
        @click="reportPage(1)"
        class="first w3-button w3-bar-item w3-border"
        >&lt;&lt;</a
      >
      <a
        href="javascript:;"
        v-if="paging.start_page > 10"
        @click="reportPage(`${paging.start_page}`)"
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
            @click="reportPage(`${n}`)"
            :key="q_id"
          >
            {{ n }}
          </a>
        </template>
      </template>
      <a
        href="javascript:;"
        v-if="paging.total_page_cnt > paging.end_page"
        @click="reportPage(`${paging.end_page + 1}`)"
        class="next w3-button w3-bar-item w3-border"
        >&gt;</a
      >
      <a
        href="javascript:;"
        @click="reportPage(`${paging.total_page_cnt}`)"
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
      <option value="report_Type">내용</option>
    </select>
    &nbsp;
    <input type="text" v-model="search_value" @keyup.enter="reportPage()" />
    &nbsp;
    <button @click="reportPage()">검색</button>
  </div>
</template>
<script>
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
      currentImageIndex: 1,
      board: {
        b_id: null,
        b_content: "",
        mediaList: [],
      },
      ReportList: [],
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
  computed: {
    imageCount() {
      // mediaList 배열의 길이를 반환합니다.
      return this.board.mediaList.length;
    },
  },
  //추가
  mounted() {
    this.reportPage();
  },

  methods: {
    // 검색 함수 추가
    reportPage(n) {
      if (this.page !== n) {
        this.page = n - 1;
      }
      this.reportGetList();
    },
    reportGetList() {
      this.requestBody = {
        // 데이터 전송
        sk: this.search_key,
        sv: this.search_value,
        page: this.page,
        size: this.size,
      };
      apiClient
        .get("cteam/admin/reports/list", {
          params: this.requestBody,
          headers: {},
        })
        .then((res) => {
          if (res.data.result_code === "OK") {
            this.ReportList = res.data.data;
            this.paging = res.data.pagination;
            this.q_id =
              this.paging.total_list_cnt -
              (this.paging.page - 1) * this.paging.page_size;
            console.log("ReportList : ", this.ReportList);
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
    getImageUrl(media) {
      // md_path와 md_name을 결합하여 이미지의 전체 경로를 반환합니다.
      return `http://localhost:8083/${media.md_path}${media.md_name}`;
    },
    prevImage() {
      // 이전 이미지로 이동합니다.
      if (this.currentImageIndex > 1) {
        this.currentImageIndex -= 1;
      }
    },
    nextImage() {
      // 다음 이미지로 이동합니다.
      if (this.currentImageIndex < this.imageCount) {
        this.currentImageIndex += 1;
      }
    },
    reportGetDetail(b_c_id) {
      console.log("상세 b_c_id :", b_c_id);
      apiClient
        .get(`cteam/admin/reports/detail/${b_c_id}`)
        .then((res) => {
          // API로부터 받은 데이터를 변수에 할당
          const responseData = res.data;

          // 받은 데이터가 기대한 구조를 가지고 있는지 확인
          if (Array.isArray(responseData) && responseData.length > 0) {
            // 모든 게시글을 저장할 배열 생성
            const boards = [];

            // responseData의 각 항목을 순회
            for (let j = 0; j < responseData.length; j++) {
              // responseData의 각 항목에서 필요한 데이터 추출
              const [b_id, b_content, md_id, md_name, md_path, md_type] =
                responseData[j];

              // mediaList 객체 생성
              const mediaList = [
                {
                  md_id,
                  md_name,
                  md_path,
                  md_type,
                },
              ];

              // board 객체 생성
              const board = {
                b_id,
                b_content,
                mediaList,
              };

              // board 객체를 배열에 추가
              boards.push(board);
            }

            // 이제 boards 배열에는 여러 게시글이 들어 있음
            console.log("Boards:", boards);

            // 첫 번째 게시글을 this.board에 할당
            this.board = boards[0];
          } else {
            // 구조가 예상과 다를 경우 처리
            console.error(
              "Received data has unexpected structure:",
              responseData
            );
          }

          // 디버깅을 위한 로그 출력
          console.log("M_id", this.board.mediaList[0].md_id);
          console.log("Detail 응답 데이터: ", this.board);
          console.log("boards 응답 데이터: ", this.boards);
        })
        .catch((error) => {
          console.error("Error fetching detail:", error);
        });
    },

    reportDelete(b_c_id) {
      // 삭제할 데이터
      apiClient
        .delete(`cteam/admin/reports/delete/${b_c_id}`)
        .then((response) => {
          console.log("데이터 삭제 성공", response);
          alert("신고 게시글 삭제성공");
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
      this.reportGetDetail(bc_id);

      this.toggleAccordion(index);
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
