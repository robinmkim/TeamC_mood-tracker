<template>
  <div class="flex min-h-screen">
    <div class="w-1/5">
      <side-bar></side-bar>
    </div>
    <div class="flex-1 border-x">
      <post-write />
      <post-detail />
      <hr />
      <post-detail />
      <hr />
      <post-detail />
      <hr />
      <post-detail />
      <hr />
      <post-detail />
    </div>
    <div class="w-1/5">side menu</div>
  </div>
</template>

<script>
import SideBar from "@/components/SideBar";
import PostWrite from "../post/PostWrite.vue";
import PostDetail from "@/components/post/PostDetail.vue";
export default {
  name: "PageHome",
  components: {
    SideBar,
    PostWrite,
    PostDetail,
  },
};
</script>

<style scoped></style>
