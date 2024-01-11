import FaceAnalyze from "/src/views/analyze/FaceAnalyze.vue";
import AnalyzeResult from "/src/views/analyze/AnalyzeResult";

export default [
  {
    path: "/faceanalyze",
    component: FaceAnalyze,
  },
  // {
  //   path: "/faceanalyze/result/:formData",
  //   name: "AnalyzeResult",
  //   component: AnalyzeResult,
  //   props: true,
  // },
  {
    path: "/faceanalyze/result/:lastResultId",
    name: "AnalyzeResult",
    component: AnalyzeResult,
    props: true,
  },
  // {
  //   path: "/faceanalyze/result",
  //   name: "AnalyzeResult",
  //   component: AnalyzeResult,
  // },
];
