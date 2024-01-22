import FaceAnalyze from "/src/views/analyze/FaceAnalyze.vue";
import AnalyzeResult from "/src/views/analyze/AnalyzeResult";
import SideWidget from "/src/components/SideWidget.vue";

export default [
  {
    path: "/faceanalyze",
    components: {
      default: FaceAnalyze,
      widget: SideWidget,
    },
  },
  {
    path: "/faceanalyze/result/:lastResultId",
    name: "AnalyzeResult",
    components: {
      default: AnalyzeResult,
      widget: SideWidget,
    },
    props: true,
  },
];
