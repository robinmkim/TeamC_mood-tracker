<template>
  <div class="flex h-full">
    <div class="flex-1 border-x px-10 py-32">
      <div class="flex flex-col text-left">
        <span
          class="pl-3 pr-10 inline-block w-36 border-b-2 mb-4 placeholder: border-b-slate-300"
        >
          문의하기
        </span>
        <input
          type="text"
          class="rounded-lg h-10 bg-slate-50 border placeholder-slate-400 border-slate-200 focus:outline-slate-400 mb-2"
          placeholder=" 문의 제목을 입력해 주십시오."
        />
        <textarea
          class="bg-slate-50 rounded-lg border border-slate-200 focus:outline-slate-400 w-full h-80 resize-none"
          placeholder=" 문의 내용을 입력해 주십시오."
        ></textarea>
        <div class="flex justify-center items-center mt-4">
          <div
            class="rounded-full bg-[#ffede6] h-10 w-28 flex justify-center items-center"
          >
            등록하기
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  components: {},
};
</script>

<style scoped></style>
