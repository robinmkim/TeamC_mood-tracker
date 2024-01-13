<template>
  <Bar id="my-chart-id" :options="chartOptions" :data="chartData" />
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
// import axios from "axios";

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

  data() {
    return {
      mid: 1,
      chartData: {
        labels: [],
        datasets: [
          {
            backgroundColor: [
              "#FF6666",
              "#3F9DDA",
              "#715DB2",
              "#FFE886",
              "#779B5D",
              "#7071FF",
              "#33D6FF",
            ],
            data: [],
          },
        ],
      },
      chartOptions: {
        responsive: true,
      },
    };
  },
  methods: {
    fetchData() {
      // Replace the URL with your actual backend endpoint
      // const url = "http://localhost:8081";

      apiClient
        .get(`/mypage/sentiment?mid=${this.mid}`)
        .then((response) => {
          console.log("success");
          const data = response.data;
          //데이터 변환
          const tfData = data.reduce(
            (result, item) => {
              result.labels.push(item.b_sentiment);
              result.datasets[0].data.push(item.cnt);
              return result;
            },
            { labels: [], datasets: [{ data: [] }] }
          );

          this.chartData.labels = tfData.labels;
          this.chartData.datasets[0].data = tfData.datasets[0].data;

          this.updateChart();
          console.log("차트 그렸음");
        })
        .catch((error) => {
          console.log(error);
        });
    },
    updateChart() {
      if (this.$data._chart) {
        this.$data._chart.destroy(); // 기존 차트 파괴
      }
      this.renderChart(this.chartData, this.chartOptions); // 새로운 차트 그리기
    },
  },
  mounted() {
    this.fetchData(); // Fetch data when the component is mounted
    // },
    // created() {
    //   this.fetchData();
  },
};
</script>
