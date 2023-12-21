<template>
  <div class="flex min-h-screen">
    <div class="w-1/5">
      <side-bar></side-bar>
    </div>
    <div class="flex-1 border-x">
      <home-post></home-post>
    </div>
    <div class="w-1/5">side menu</div>
  </div>
</template>

<script>
import SideBar from "@/components/SideBar"
import HomePost from "@/components/home/HomePost.vue";
export default {
  name: "PageHome",
  components: {
    SideBar,
    HomePost,
  }
};
</script>

<style scoped>
</style>