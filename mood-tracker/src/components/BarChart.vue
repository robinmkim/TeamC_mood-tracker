<template>
  <div class="flex-grow">
    <Bar
      id="my-chart-id"
      v-if="loaded"
      :options="chartOptions"
      :data="chartData"
    />
  </div>
</template>

<script>
import { Bar } from "vue-chartjs";
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale,
} from "chart.js";
import apiClient from "@/utils/apiClient";

ChartJS.register(
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale
);

export default {
  name: "BarChart",
  components: { Bar },
  props: {
    m_id: {
      type: Number,
      required: true,
    },
  },
  data: () => ({
    loaded: false,
    chartData: {
      labels: [
        "Angry",
        "Happy",
        "Surprise",
        "Neutral",
        "Hurt",
        "Sad",
        "Anxiety",
      ],
      datasets: [
        {
          label: 0,
          backgroundColor: [
            "#F67D73",
            "#FFDDE4",
            "#FFE778",
            "#D9F3C1",
            "#597F61",
            "#A4BED3",
            "#C9BCE8",
          ],
          data: [],
        },
      ],
    },

    chartOptions: {
      responsive: true,
      plugins: {
        legend: {
          display: false,
        },
        datalabels: {
          display: false,
        },
      },
    },
  }),

  methods: {
    getSentiData() {
      this.loaded = false;

      apiClient
        .get(`/mypage/sentiment?m_id=${this.m_id}`)
        .then((res) => {
          this.chartData;
          const datas = res.data;
          // 데이터 설정

          const dlabels = datas.map((item) => item.B_SENTIMENT);
          const ddatas = datas.map((item) => item.CNT);

          for (let j = 0; j < dlabels.length; j++) {
            const dlabel = dlabels[j];
            for (let i = 0; i < this.chartData.labels.length; i++) {
              const label = this.chartData.labels[i].toLowerCase();
              if (label === dlabel) {
                this.chartData.datasets[0].data[i] = ddatas[j];
              }
            }
          }
          console.log("chart 불러옴");
          this.loaded = true;
        })
        .catch((err) => {
          console.log("chart 먼가 안됨", err);
        });
    },
  },
  created() {
    this.loaded = false;
    this.getSentiData();
  },
};
</script>
