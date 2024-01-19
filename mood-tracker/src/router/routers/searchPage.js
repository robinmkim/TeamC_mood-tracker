import searchPage from "/src/views/search/SearchPage.vue";
import SideWidget from "/src/components/SideWidget.vue";

export default [
  {
    path: "/search",
    components: {
      default: searchPage,
      widget: SideWidget,
    },
  },
];
