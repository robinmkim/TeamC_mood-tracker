<template>
  <div class="flex flex-col h-screen">
    <PageHeader />
    <div class="flex-grow overflow-auto w-3/4 mx-auto">
      <router-view />
    </div>
    <PageFooter />
  </div>
</template>

<script>
import PageHeader from "./components/PageHeader.vue";
import PageFooter from "./components/PageFooter.vue";

export default {
  name: "App",
  components: { PageHeader, PageFooter },
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>
