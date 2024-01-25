import NotiPage from "/src/views/noti/NotifcationPage.vue";
import SideWidget from "/src/components/SideWidget.vue";

export default [
  {
    path: "/noti",
    name: "NotiList",
    components: {
      default: NotiPage,
      widget: SideWidget,
    },
  },
];
