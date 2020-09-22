import Vue from 'vue'
import Vuex from 'vuex'
import store from './store'
import App from './App.vue'
import router from './router'
import vuetify from './plugins/vuetify';

Vue.use(vuetify)
Vue.use(Vuex)

Vue.config.productionTip = false

new Vue({
  router,
  vuetify,
  store,
  render: h => h(App)
}).$mount('#app')
