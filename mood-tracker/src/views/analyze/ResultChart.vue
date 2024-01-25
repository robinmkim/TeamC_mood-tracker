<template>
  <Radar id="result" v-if="loaded" :options="chartOptions" :data="chartData" />
</template>

<script>
import apiClient from "@/utils/apiClient";
import { useRoute } from "vue-router";
import { Radar } from "vue-chartjs";
import {
  Chart as ChartJS,
  RadialLinearScale,
  PointElement,
  LineElement,
  Filler,
  Tooltip,
} from "chart.js";

ChartJS.register(RadialLinearScale, PointElement, LineElement, Filler, Tooltip);

export default {
  name: "ResultChart",
  components: { Radar },
  data() {
    return {
      loaded: false,
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
            //rgba(100, 204, 197, 1) <- 해더컬러
            label: "Emotion Level",
            backgroundColor: "rgba(173, 216, 230, 0.3)",
            borderColor: "rgba(0, 153, 153, 1)",
            pointBackgroundColor: "rgba(0, 153, 153, 1)",
            pointBorderColor: "#fff",
            pointRadius: 5,
            pointHoverRadius: 10,
            pointHoverBackgroundColor: "#fff",
            pointHoverBorderColor: "rgba(0, 153, 153, 1)",
            data: [],
          },
        ],
      },
      chartOptions: {
        responsive: true,
        scales: {
          r: {
            suggestedMin: -10,
            suggestedMax: 40,
            pointLabels: {
              display: true, // 데이터 포인트 레이블
              font: {
                size: 16,
              },
            },
            ticks: {
              beginAtZero: true,
              display: true, // 축 레이블
              max: 100,
              remin: 50,
              font: {
                size: 14,
              },
            },
            angleLines: {
              display: true, // 각도선
            },
          },
        },
        plugins: {
          datalabels: {
            display: false,
          },
          tooltip: {
            enabled: true,
          },
          legend: {
            display: false,
          },
        },
      },
    };
  },
  created() {
    this.loaded = false;

    console.log("레이다 차트 생성");
    const route = useRoute(); // route.path로 보고있는 페이지 경로 가져오기
    const routhPath = route.path;
    const sp_routhPath = routhPath.split("/");
    const ar_id = sp_routhPath[3];
    console.log("지금 보고 있는 페이지는 => ", route.path);
    console.log("불러올 예측결과 아이디는 => ", ar_id);

    this.getSentiData(ar_id);
  },
  methods: {
    getSentiData(ar_id) {
      this.loaded = false;

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
