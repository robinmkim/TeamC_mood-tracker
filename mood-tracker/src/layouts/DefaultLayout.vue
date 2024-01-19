<template lang="">
  <div class="flex justify-center mt-16">
    <!-- 왼쪽 side menu -->
    <side-bar />

    <!-- 기본 뷰 -->
    <router-view class="md:w-[700px] sm:w-[300px]" />

    <!-- 오른쪽 widget 뷰 -->
    <router-view name="widget" class="" />
  </div>
</template>
<script>
import SideBar from "@/components/SideBar.vue";

export default {
  components: { SideBar },
};
</script>
<style lang=""></style>
