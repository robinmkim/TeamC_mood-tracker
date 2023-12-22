<template>
    <div class="flex min-h-screen">
      <div class="w-1/5">
        <side-bar></side-bar>
      </div>
      <div class="flex-1 border-x flex">
        <div class="flex-1">
          <postDetail></postDetail>
        </div>
        <div class="flex-1 bg-slate-100 ">댓글창</div>
      </div>
      <!-- <div class="w-1/5">side menu</div> -->
    </div>
  </template>
  
  <script>
  import SideBar from "@/components/SideBar"
  import postDetail from "@/components/post/postDetail"
  export default {
    components: {
      SideBar,
      postDetail,
    }
  };
  </script>
  
  <style scoped>
  </style>