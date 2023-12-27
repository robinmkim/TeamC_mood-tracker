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
      chartData: {
        labels: [
          "Angry",
          "Disgust",
          "Fear",
          "Happy",
          "Neutral",
          "Sad",
          "Surprise",
        ],
        datasets: [
          {
            backgroundColor: [
              "#f87979",
              "#45aaf2",
              "#fed330",
              "#4b7bec",
              "#fed330",
              "#4b7bec",
              "#45aaf2",
            ],
            data: [5, 2, 1, 10, 7, 3, 2],
          },
        ],
      },
      chartOptions: {
        responsive: true,
      },
    };
  },
};
</script>
