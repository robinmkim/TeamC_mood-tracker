import followPage from "/src/views/follow/FollowPage.vue";
import SideWidget from "/src/components/SideWidget.vue";

export default [
  {
    path: "/follow",
    components: {
      default: followPage,
      widget: SideWidget,
    },
  },
];
