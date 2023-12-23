<template>
    <div class="flex min-h-screen">
      <div class="w-1/5">
        <side-bar></side-bar>
      </div>
      <div class="flex-1 border-x">
        admin
      </div>
      <div class="w-1/5">side menu</div>
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