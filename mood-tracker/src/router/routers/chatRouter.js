import ChatMain from "/src/views/chat/ChatMain.vue";
import SideWidget from "/src/components/SideWidget.vue";

export default [
  {
    path: "/chat",
    components: {
      default: ChatMain,
      widget: SideWidget,
    },
  },
];
