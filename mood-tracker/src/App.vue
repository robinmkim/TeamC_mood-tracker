<template>
  <page-header class="fixed top-0 z-50" />
  <router-view />
</template>

<script>
import PageHeader from "./components/PageHeader.vue";

export default {
  name: "App",
  components: { PageHeader },
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
