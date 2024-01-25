<template lang="">
  <div class="flex justify-center min-h-full">
    <!-- 왼쪽 side menu -->
    <side-bar class="mt-14" />

    <!-- 기본 뷰 -->
    <router-view class="mt-14 md:w-[700px] sm:w-[300px]" />

    <!-- 오른쪽 widget 뷰 -->
    <router-view name="widget" class="mt-14" />
  </div>
</template>
<script>
import SideBar from "@/components/SideBar.vue";

export default {
  components: { SideBar },
};
</script>
<style lang=""></style>
