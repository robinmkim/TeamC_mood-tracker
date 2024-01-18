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
          <button @click="prevMonth" class="p-2">◀</button>
          <span class="text-lg">{{ currentMonth }}월</span>
          <button @click="nextMonth" class="p-2">▶</button>
        </div>
        <table class="w-full">
          <thead class="w-full">
            <th
              v-for="week in weekend"
              :key="week"
              class="p-2 border-r w-auto h-10 xl:text-sm text-xs"
            >
              <span class="xl:block lg:block md:block sm:block hidden">{{
                week
              }}</span>
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
              @click="isModalOpen = true"
            >
              <td
                v-for="(day, secondIdx) in date"
                :key="secondIdx"
                class="border p-1 h-24 w-96 transition cursor-pointer duration-500 ease hover:bg-gray-300"
                @click="setSelectedDate(day)"
              >
                <div class="flex flex-col h-24 mx-auto w-full overflow-hidden">
                  <div class="top h-5 w-full">
                    <span class="text-gray-500">{{ day }}</span>
                  </div>
                  <div
                    class="bottom flex-grow h-30 py-2 w-full cursor-pointer item-center justify-center"
                  ></div>
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
      while (day <= monthLastDate) {
        if (day === 1) {
          // 1일이 어느 요일인지에 따라 테이블에 그리기 위한 지난 셀의 날짜들을 구할 필요가 있다.
          for (let j = 0; j < monthFirstDay; j += 1) {
            weekOfDays.push(prevDay);
            prevDay += 1;
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
      const len = weekOfDays.length;
      if (len > 0 && len < 7) {
        for (let k = 1; k <= 7 - len; k += 1) {
          weekOfDays.push(k);
        }
      }
      if (weekOfDays.length > 0) dates.push(weekOfDays); // 남은 날짜 추가
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
    },
    formatDate(year, month, day) {
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
  },
};
</script>
<style lang=""></style>
