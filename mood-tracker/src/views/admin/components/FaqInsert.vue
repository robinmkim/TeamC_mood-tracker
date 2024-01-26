<template>
  <div>
    <input
      type="text"
      class="rounded-lg h-10 text-sm bg-slate-50 border placeholder-slate-400 border-slate-200 focus:outline-slate-400 mb-2 w-full"
      placeholder=" Faq 질문을 입력해 주십시오."
      v-model="faqTitle"
    />
    <textarea
      class="bg-slate-50 text-sm rounded-lg border border-slate-200 focus:outline-slate-400 w-full h-80 resize-none"
      placeholder=" Faq 질문의 답변을 입력해 주십시오."
      v-model="faqContent"
    ></textarea>
    <div class="flex justify-center items-center mt-4">
      <div
        class="rounded-full bg-[#ffede6] h-10 w-28 flex justify-center items-center mb-4 text-sm"
        @click="faqInsert()"
      >
        등록하기
      </div>
    </div>
  </div>
</template>

<script>
import { apiClient2 as apiClient } from "@/utils/apiClient";
export default {
  data() {
    return {
      editor: null,
      currentAccordionIndex: null,
      Faqitems: [],
      faq_title: "",
      faq_content: "",
    };
  },
  //추가
  methods: {
    async faqInsert() {
      console.log("실행실행");
      const apiUrl = "/cteam/admin/insert"; // 기본적으로 baseURL이 적용됩니다.
      const requestData = {
        faq_title: this.faqTitle,
        faq_content: this.faqContent,
      };

      try {
        const response = await apiClient.post(apiUrl, requestData);
        console.log(response.data);
        alert("FAQ 등록 성공");
        this.insertOpenSuccessPopup();
      } catch (error) {
        console.error(error);
      }
    },

    async save() {
      const content = this.$refs.editor.getContent();
      console.log(content);
    },

    insertOpenSuccessPopup() {
      this.$router.go(0);
    },

    isAccordionOpen(index) {
      return this.currentAccordionIndex === index;
    },
  },
};
</script>

<style scoped>
/* 컴포넌트에만 스타일 적용 */
</style>
