<template lang="">
  <div>
    <!-- 모달 부분 -->
    <PostModal
      :isOpen="isModalOpen"
      :day="modalData.mday"
      :month="modalData.mmonth"
      :ByDateList="ByDateList"
      @close="isModalOpen = false"
    />
    <div class="flex-grow">
      <div>
        <div>
          <div class="my-2">
            <div class="text-xs mb-[-5px] text-slate-500">{{ this.year }}</div>
            <button @click="prevMonth" class="px-2">◀</button>
            <span class="text-lg">{{ currentMonth }}월</span>
            <button @click="nextMonth" class="px-2">▶</button>
          </div>
        </div>
        <table class="w-full">
          <thead class="w-full shadow flex-auto">
            <th
              v-for="week in weekend"
              :key="week"
              class="p-2 h-10 xl:text-sm text-xs flex-col items-center text-center"
            >
              <span
                class="w-20 xl:block lg:block md:block sm:block hidden ="
                :class="{
                  'text-red-400': week === 'Sunday',
                }"
                >{{ week }}</span
              >
              <span class="xl:hidden lg:hidden md:hidden sm:hidden block">{{
                week.slice(0, 3)
              }}</span>
            </th>
          </thead>
          <tbody class="w-full">
            <tr
              tr
              v-for="(date, idx) in dates"
              :key="idx"
              class="text-center h-12"
            >
              <td
                v-for="(day, secondIdx) in date"
                :key="secondIdx"
                class="p-1 transition cursor-pointer duration-500 ease"
                :class="{
                  'hover:bg-transparent': day === null,
                }"
                @click="day !== null ? setSelectedDate(day) : () => {}"
              >
                <div
                  class="flex flex-col h-24 mx-auto w-full overflow-hidden hover:bg-teal-50 rounded-lg"
                  :class="{
                    'hover:bg-transparent': day === null,
                  }"
                >
                  <div
                    class="bottom flex flex-grow h-30 py-2 w-full cursor-pointer item-center justify-center"
                  >
                    <!-- {{ getTopSentiment(day) }} -->
                    <img
                      v-show="this.sentimentList[day - 1]"
                      :src="`http://localhost:8083/images/${
                        this.sentimentList[day - 1]
                      }.png`"
                      alt="Post image"
                      class="items-center rounded-lg w-12 h-12"
                    />
                    <!-- {{ getTopSentiment(day) }} -->
                  </div>
                  <div class="top h-5 w-full">
                    <span
                      class="text-gray-500 h-5 w-5"
                      :class="{ 'text-red-400': secondIdx === 0 }"
                      >{{ day }}</span
                    >
                  </div>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>
<script>
import PostModal from "@/components/PostModal.vue";
import apiClient from "@/utils/apiClient";
export default {
  name: "MoodCalander",
  components: {
    PostModal,
  },
  data() {
    return {
      weekend: [
        "Sunday",
        "Monday",
        "Tuesday",
        "Wednesday",
        "Thursday",
        "Friday",
        "Saturday",
      ],
      dates: [],
      currentYear: 0,
      currentMonth: 0,
      year: 0,
      month: 0,
      lastMonthDates: 0,
      nextMonthDates: 0,
      modalData: {
        mday: 0,
        mmonth: 0,
      },
      isModalOpen: false,
      selectedDate: null,
      ByDateList: [],
      sentimentList: [],
    };
  },
  methods: {
    //달력에서 불러올 게시글 리스트
    getListByDate() {
      if (this.isLoading) {
        console.log("calander 로딩중");
        return; // 이미 로딩 중이면 요청을 하지 않음
      }
      this.isLoading = true;
      apiClient
        .get(`/mypage/reglist?regdate=${this.selectedDate}`)
        .then((res) => {
          console.log("캘린더 포스트 넘어옴");
          this.ByDateList = [...this.ByDateList, ...res.data];
        })
        .catch((err) => {
          console.log(err, "calander 뭔가 안됨");
        })
        .finally(() => {
          this.isLoading = false; // 로딩 완료
        });
    },
    calendarData() {
      // 현재 월의 첫번째 날, 마지막 날, 지난달의 마지망 날 등을 계산하여 달력에 표시할 데이터를 생성
      const [monthFirstDay, monthLastDate, lastMonthLastDate] =
        this.getFirstDayLastDate(this.year, this.month);
      this.dates = this.getMonthOfDays(
        monthFirstDay,
        monthLastDate,
        lastMonthLastDate
      );
    },
    getFirstDayLastDate(year, month) {
      const firstDay = new Date(year, month - 1, 1).getDay(); // 이번 달 시작 요일
      const lastDate = new Date(year, month, 0).getDate(); // 이번 달 마지막 날짜
      let lastYear = year;
      let lastMonth = month - 1;
      if (month === 1) {
        lastMonth = 12;
        lastYear -= 1;
      }
      const prevLastDate = new Date(lastYear, lastMonth, 0).getDate(); // 지난 달 마지막 날짜
      return [firstDay, lastDate, prevLastDate];
    },
    getMonthOfDays(monthFirstDay, monthLastDate, prevMonthLastDate) {
      let day = 1;
      let prevDay = prevMonthLastDate - monthFirstDay + 1;
      const dates = [];
      let weekOfDays = [];

      // 이전 달의 마지막 날짜 이후부터 현재 월의 마지막 날짜까지만 추가
      while (prevDay <= prevMonthLastDate) {
        weekOfDays.push(prevDay);
        prevDay += 1;
      }

      while (day <= monthLastDate) {
        if (day === 1) {
          // 1일이 어느 요일인지에 따라 테이블에 그리기 위한 이번 달의 날짜들을 구할 필요가 있다.
          weekOfDays = [];
          for (let i = 0; i < monthFirstDay; i++) {
            // 이전 달의 마지막 날짜 이후부터 현재 월의 첫 날짜까지는 비워둡니다.
            weekOfDays.push(null);
          }
        }
        weekOfDays.push(day);
        if (weekOfDays.length === 7) {
          // 일주일 채우면
          dates.push(weekOfDays);
          weekOfDays = []; // 초기화
        }
        day += 1;
      }

      // 남은 날짜 추가
      const len = weekOfDays.length;
      if (len > 0 && len < 7) {
        for (let k = 1; k <= 7 - len; k += 1) {
          weekOfDays.push(null); // 다음 달의 시작부터 나머지 요일을 채웁니다.
        }
        dates.push(weekOfDays); // 남은 날짜 추가
      }

      return dates;
    },

    prevMonth() {
      if (this.month === 1) {
        this.year -= 1;
        this.month = 12;
      } else {
        this.month -= 1;
      }
      this.currentMonth = this.month; // 수정된 부분
      this.calendarData();
      this.getTopSentiment();
    },
    nextMonth() {
      if (this.month === 12) {
        this.year += 1;
        this.month = 1;
      } else {
        this.month += 1;
      }
      this.currentMonth = this.month; // 수정된 부분
      this.calendarData();
      this.getTopSentiment();
    },
    formatDate(year, month, day) {
      // 년, 월, 일을 받아서 'yy/mm/dd' 형식으로 포맷팅하여 반환
      // 각 자리수가 한 자리 수인 경우 앞에 0을 붙여줍니다.
      const formattedYear = year.toString().padStart(2, "0");
      const formattedMonth = (month + 1).toString().padStart(2, "0");
      const formattedDay = day.toString().padStart(2, "0");

      // 'yy/mm/dd' 형식으로 반환합니다.
      return `${formattedYear}${formattedMonth}${formattedDay}`;
    },
    setSelectedDate(day) {
      const formattedDate = this.formatDate(this.year, this.month - 1, day);
      this.ByDateList = [];
      this.modalData.mday = day;
      this.modalData.mmonth = this.currentMonth;
      this.getByRegList(formattedDate);
      this.isModalOpen = true;
    },
    getImageUrl() {
      return `http://localhost:8083/images/${this.board.b_sentiment}.png`;
    },
    getTopSentiment() {
      const lastDate = new Date(this.year, this.month, 0).getDate();
      const firstDay = 1;
      console.log("year: " + this.year);
      console.log("month: " + this.month);

      console.log("monthFirstDay: " + 1);
      console.log("lastDate: " + lastDate);
      // sentimentList

      apiClient
        .get(
          `/mypage/getTopSentiment?year=${this.year}&month=${this.month}&firstDay=${firstDay}&lastDate=${lastDate}&m_id=5`
        )
        .then((response) => {
          this.sentimentList = response.data;
          console.log(this.sentimentList);
        })
        .catch((error) => {
          console.error("Error fetching the board data:", error);
        });

      // console.log("lastMonthLastDate: " + lastMonthLastDate);

      // if (day !== null) {
      //   const formattedDate = this.formatDate(this.year, this.month - 1, day);
      //   var img_url;
      //   apiClient
      //     .get(`/mypage/getTopSentiment?regdate=${formattedDate}&m_id=5`)
      //     .then((response) => {
      //       if (!response.data) {
      //         img_url = `http://localhost:8083/images/Calendar_${response.data}.png`;
      //       } else {
      //         img_url = "http://localhost:8083/images/nullPostDayImage.png";
      //       }
      //     })
      //     .catch((error) => {
      //       console.error("Error fetching the board data:", error);
      //       // return `http://localhost:8083/images/nullPostDayImage.png`;
      //     });
      //   console.log(img_url);
      //   return img_url;
      // } else {
      //   return "!"; // 또는 다른 기본값을 반환할 수 있습니다.
      // }
      // this.ByDateList = [];
      // this.modalData.mday = day;
      // this.modalData.mmonth = this.currentMonth;
      // this.getByRegList(formattedDate);
    },
    getByRegList(formattedDate) {
      console.log("컨트롤러에 전달할 날짜:", formattedDate);

      if (this.isLoading) {
        console.log("calander 로딩중");
        return; // 이미 로딩 중이면 요청을 하지 않음
      }
      this.isLoading = true;
      apiClient
        .get(`/mypage/reglist?regdate=${formattedDate}`)
        .then((res) => {
          console.log("캘린더 포스트 넘어옴");

          this.ByDateList = [...this.ByDateList, ...res.data];
          console.log(formattedDate, "data list: ", this.ByDateList);
        })
        .catch((err) => {
          console.log(err, "calander 뭔가 안됨");
        })
        .finally(() => {
          this.isLoading = false; // 로딩 완료
        });
    },
  },
  created() {
    const date = new Date();
    this.year = date.getFullYear();
    this.month = date.getMonth() + 1;
    this.currentMonth = this.month;
    this.calendarData();
    this.getTopSentiment();
  },
};
</script>
<style lang=""></style>
