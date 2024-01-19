import questionPage from "/src/views/question/questionPage.vue";
import SideWidget from "/src/components/SideWidget.vue";

export default [
  {
    path: "/question",
    components: {
      default: questionPage,
      widget: SideWidget,
    },
  },
];
