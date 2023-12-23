<template>
    <div class="flex min-h-screen">
      <div class="w-1/5">
        <side-bar></side-bar>
      </div>
      <div class="flex-1 border-x px-10 py-32 bg-slate-100">
        question
      </div>
    </div>
  </template>
  
  <script>
  import SideBar from "@/components/SideBar"
  export default {
    name: "PageHome",
    components: {
      SideBar,
    }
  };
  </script>
  
  <style scoped>
  </style>