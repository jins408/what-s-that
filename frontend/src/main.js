import Vue from 'vue'
import Vuex from 'vuex'
import App from './App.vue'
import router from './router'
import store from './store'

import VueCookies from 'vue-cookies'
import Vuex from 'vuex'
import vuetify from './plugins/vuetify';

Vue.use(vuetify)
Vue.use(Vuex)

Vue.config.productionTip = false

Vue.use(vuetify)
Vue.use(VueCookies)
Vue.use(Vuex)

new Vue({
  router,
  vuetify,
  store,
  render: h => h(App)
}).$mount('#app')
