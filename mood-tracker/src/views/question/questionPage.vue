<template>
  <div class="flex h-full">
    <div class="flex-1 border-x px-10 py-32">
      <div class="flex flex-col text-left">
        <span
          class="pl-3 pr-10 inline-block w-36 border-b-2 mb-4 placeholder: border-b-slate-300"
        >
          질문하기
        </span>
        <input
          type="text"
          class="rounded-lg h-10 bg-slate-50 border placeholder-slate-400 border-slate-200 focus:outline-slate-400 mb-2"
          placeholder=" 질문 제목을 입력해 주십시오."
          v-model="qTitle"
        />
        <textarea
          class="bg-slate-50 rounded-lg border border-slate-200 focus:outline-slate-400 w-full h-80 resize-none"
          placeholder=" 질문 내용을 입력해 주십시오."
          v-model="qContent"
        ></textarea>
        <div class="flex justify-center items-center mt-4">
          <div
            class="rounded-full bg-[#ffede6] h-10 w-28 flex justify-center items-center"
            @click="questionInsert()"
          >
            등록하기
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import apiClient from "@/utils/apiClient";
export default {
  props: ["resultId"],
  data() {
    return {
      // memberHandle: "",
      q_title: "",
      q_content: "",
    };
  },
  name: "PostWrite",
  mounted() {
    // // userName을 가져옵니다.
    // apiClient.get("/member/userInfo/memberHandle").then((res) => {
    //   console.log("memberHandle = ", res.data);
    //   this.memberHandle = res.data;
    // });
  },
  methods: {
    adjustHeight(e) {
      // textarea높이 자동 조절
      const element = e.target;
      element.style.height = "auto";
      element.style.height = element.scrollHeight + "px";
    },
    async questionInsert() {
      const apiUrl = "/question/add";
      const requestData = {
        q_title: this.qTitle,
        q_content: this.qContent,
      };

      try {
        const response = await apiClient.post(apiUrl, requestData);
        console.log(response.data);
        alert("Question 등록 성공");
        this.insertOpenSuccessPopup();
      } catch (error) {
        console.error(error);
      }
    },
    insertOpenSuccessPopup() {
      this.$router.go(0);
    },
  },
  components: {},
};
</script>

<style scoped></style>
