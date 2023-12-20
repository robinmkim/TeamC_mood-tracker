<template>
  <div class="flex min-h-screen">
    <div class="w-1/5  bg-sky-200">
      <profile-home></profile-home>
    </div>
    <div class="flex-1">
      <home-post></home-post>
    </div>
    <div class="w-1/5 bg-sky-200">side menu</div>
  </div>
</template>

<script>
import ProfileHome from "@/components/SideBar"
import HomePost from "@/components/home/HomePost.vue";
export default {
  name: "PageHome",
  components: {
    ProfileHome,
    HomePost,
  }
};
</script>

<style scoped>
</style>