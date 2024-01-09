<template>
  <Radar id="result" :options="chartOptions" :data="chartData" />
</template>

<script>
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
      chartData: {
        labels: ["좋음", "짜증", "슬픔", "분노", "행복"],
        datasets: [
          {
            label: "Emotion Level",
            backgroundColor: "rgba(173, 216, 230, 0.3)",
            borderColor: "rgba(128, 0, 128, 1)",
            pointBackgroundColor: "rgba(128, 0, 128, 1)",
            pointBorderColor: "#fff",
            pointHoverBackgroundColor: "#fff",
            pointHoverBorderColor: "rgba(128, 0, 128, 1)",
            data: [95, 40, 20, 25, 65],
          },
        ],
      },
      chartOptions: {
        responsive: true,
        scale: {
          ticks: {
            beginAtZero: true,
            max: 100,
          },
        },
      },
    };
  },
};
</script>
