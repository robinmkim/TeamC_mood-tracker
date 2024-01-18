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
  font-family: Arita-dotum-Medium;
}
@font-face {
  font-family: "Arita-dotum-Medium";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_one@1.0/Arita-dotum-Medium.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}
</style>
