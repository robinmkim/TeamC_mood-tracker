<template>
  <div class="flex-grow">
    <Pie
      id="my-chart-id"
      v-if="loaded"
      :options="chartOptions"
      :data="chartData"
    />
  </div>
</template>

<script>
import ChartDataLabels from "chartjs-plugin-datalabels";
import { useRoute } from "vue-router";
import { Pie } from "vue-chartjs";
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  ArcElement,
  CategoryScale,
  LinearScale,
} from "chart.js";
import apiClient from "@/utils/apiClient";

ChartJS.register(
  Title,
  Tooltip,
  Legend,
  ArcElement,
  CategoryScale,
  LinearScale,
  ChartDataLabels
);

export default {
  name: "PieChart",
  components: { Pie },
  data: () => ({
    cnt: 0,
    loaded: false,
    labelColorSet: {
      // 감정마다 색상을 고정합니다.
      Angry: "#F67D73",
      Happy: "#FFDDE4",
      Surprise: "#FFE778",
      Neutral: "#D9F3C1",
      Hurt: "#597F61",
      Sad: "#A4BED3",
      Anxiety: "#C9BCE8",
    },
    labelKorSet: {
      Angry: "분노",
      Happy: "기쁨",
      Surprise: "놀람", //당황?
      Neutral: "중립",
      Hurt: "상처",
      Sad: "슬픔",
      Anxiety: "불안",
    },
    chartData: {
      labels: [],
      datasets: [
        {
          label: 0,
          backgroundColor: [],
          data: [],
        },
      ],
    },
    chartOptions: {
      responsive: true,
      plugins: {
        legend: {
          display: true,
          labels: {
            // 범례(Legend)의 폰트 설정
            font: {
              size: 16, // 폰트 크기
              family: "Arial", // 폰트 패밀리
            },
          },
        },
        datalabels: {
          color: "#000",
          font: {
            size: 18, // 데이터 라벨의 폰트 크기
          },
          anchor: "end", // 데이터 라벨의 위치 기준
          align: "start", // 데이터 라벨이 어느 방향으로 떨어질 건지
          offset: 30, // 기준점으로 부터 얼마나 떨어질 건지(픽셀단위)

          formatter: (value, ctx) => {
            if (value > 3) {
              return ctx.chart.data.labels[ctx.dataIndex] + "\n" + value;
            } else {
              return "";
            }
          },
        },
        // 전역 폰트 설정
        font: {
          family: "Arial", // 폰트 패밀리
          size: 16, // 폰트 크기
        },
      },
    },
  }),
  created() {
    this.loaded = false;

    console.log("파이차트 생성");
    const route = useRoute(); // route.path로 보고있는 페이지 경로 가져오기
    const routhPath = route.path;
    const sp_routhPath = routhPath.split("/");
    const ar_id = sp_routhPath[3];
    console.log("지금 보고 있는 페이지는 => ", route.path);
    console.log("불러올 예측결과 아이디는 => ", ar_id);

    this.getSentiData(ar_id);

    // let self = this; // formatter함수 내에서는 this 키워드가 Vue 인스턴스를 가리키지 않기 때문에 따로 변수에 저장하여 사용해야 한다.
    // this.chartOptions.plugins.datalabels.formatter = function (value, ctx) {
    //   if (value > 4) {
    //     return ctx.chart.data.labels[ctx.dataIndex] + "\n" + value;
    //   } else {
    //     if (self.cnt < 1) {
    //       // 4퍼센트 이하인 라벨은 첫번째만 표시
    //       self.cnt = self.cnt + 1;
    //       return ctx.chart.data.labels[ctx.dataIndex] + "\n" + value;
    //     }
    //     return "";
    //   }
    // };
  },
  mounted() {},
  methods: {
    getSentiData(ar_id) {
      this.loaded = false;
      this.cnt = 0; // 데이터를 로딩할 때마다 cnt를 초기화

      apiClient
        .get("/faceresult/detail", {
          params: {
            ar_id: ar_id,
          },
        })
        .then((res) => {
          console.log("받아온 예측결과 데이터 => ", res.data.ar_content);
          const ar_content_json = JSON.parse(res.data.ar_content);

          const entries = Object.entries(ar_content_json);
          entries.sort((a, b) => b[1] - a[1]); // 배열을 각 값(value)에 따라 내림차순으로 정렬
          const sortedObject = entries.reduce((obj, [key, value]) => {
            obj[key.charAt(0).toUpperCase() + key.slice(1)] = value.toFixed(2);
            return obj;
          }, {});

          const dlabels = Object.keys(sortedObject);
          const ddatas = Object.values(sortedObject);

          // 확률이 높은 순서대로 5개 까지만 표시합니다.
          this.chartData.labels = dlabels.slice(0, 5);
          this.chartData.labels = this.chartData.labels.map(
            (label) => this.labelKorSet[label]
          );
          this.chartData.datasets[0].data = ddatas.slice(0, 5);

          for (let i = 0; i < dlabels.length; i++) {
            this.chartData.datasets[0].backgroundColor[i] =
              this.labelColorSet[dlabels[i]];
          }

          console.log("chart 불러옴");
          this.loaded = true;
        })
        .catch((err) => {
          console.log("chart 먼가 안됨", err);
        });
    },
  },
};
</script>
