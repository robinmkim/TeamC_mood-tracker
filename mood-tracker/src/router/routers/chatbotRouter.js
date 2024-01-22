import ChatBot from "@/views/chatbot/ChatBot.vue";
import SideWidget from "@/components/SideWidget.vue";

export default [
  {
    path: "/musicrecommand",
    components: {
      default: ChatBot,
      widget: SideWidget,
    },
  },
];
