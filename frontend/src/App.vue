<template>
  <v-app>
    <Header v-if="!loginpage" />
    <router-view />
  </v-app>
</template>

<script>
import Header from './components/Header.vue'
import LoginVue from './views/user/Login.vue';

export default {
  name: 'App',

  computed:{
    loginpage(){
      return this.$route.name == LoginVue
    }
  },

  components: {
    Header,
  },

  data: () => ({
    //
  }),
};
</script>
