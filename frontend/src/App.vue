<template>
  <v-app>
    <Header v-if="!loginpage" />
    <router-view />
    <Footer v-if="!loginpage" />
  </v-app>
</template>

<script>
import Header from "./components/Header.vue";
import LoginVue from "./views/user/Login.vue";
import Footer from "./components/Footer.vue";

import "es6-promise/auto";

export default {
  name: "App",

  computed: {
    loginpage() {
      return this.$route.name == LoginVue;
    },
  },

  components: {
    Header,
    Footer,
  },

  data: () => ({
    //
  }),
};
</script>

<style>
#app {
  /* font-family: '국립박물관문화재단클래식B'; */
  font-family: "MapoGoldenPier";
}
@font-face {
  font-family: "국립박물관문화재단클래식B";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_twelve@1.0/국립박물관문화재단클래식B.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}
@font-face {
  font-family: "MapoGoldenPier";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/MapoGoldenPierA.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}
</style>