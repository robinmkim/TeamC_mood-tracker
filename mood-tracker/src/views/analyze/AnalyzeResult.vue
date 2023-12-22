<template>
  <div class="flex h-screen">
    <div class="w-1/5 p-4">side menu</div>

    <div class="flex-1 w-full bg-[#E7F1E5]">
      <!-- 여기서부터 ~~~ -->
      <div class="flex justify-center items-center">

        <div class="flex flex-col w-[700px] h-auto mt-5 bg-slate-200 items-center justify-center">
          <!-- 감정 이모티콘 -->
          <div class="flex w-[500px] h-64 bg-[#ffede6]" id="imoji">

            <div class="flex">♡</div>
            <div class="flex">
              <span>오늘의 기분</span><br />
              <span>아주 최고야</span>
            </div>
          </div>
          <!-- radar chart -->
          <div id="pentagone" class="flex w-[500px] h-64 bg-[#e39a7d]">
            <div>차트</div>
            <dic>내용</dic>
          </div>

        </div>
      </div>

      <!-- 여까지 -->
    </div>

    <div class="w-1/5 p-4">side menu</div>
  </div>
</template>
  
<script>


export default {
  name: "AnalyzeResult",
  
};

</script>
  
<style scoped></style>