<template>
  <div class="flex min-h-screen">
    <div class="w-1/5">
      <side-bar></side-bar>
    </div>
    <div class="flex-1 border-x">
      <post-write/>
      <home-post></home-post>
    </div>
    <div class="w-1/5">side menu</div>
  </div>
</template>

<script>
import SideBar from "@/components/SideBar"
import HomePost from "@/components/home/HomePost.vue";
import PostWrite from "../post/PostWrite.vue";
export default {
  name: "PageHome",
  components: {
    SideBar,
    HomePost,
    PostWrite,
  }
};
</script>

<style scoped>
</style>