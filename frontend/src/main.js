import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

import VueCookies from 'vue-cookies'
import Vuex from 'vuex'
import vuetify from './plugins/vuetify';

Vue.config.productionTip = false
Vue.use(vuetify)
Vue.use(VueCookies)
Vue.use(Vuex)

Vue.prototype.$baseurl = "http://j3b202.p.ssafy.io:8088/dictionary";
// Vue.prototype.$baseurl = "http://localhost:8088/dictionary";

new Vue({
  router,
  vuetify,
  store,
  render: h => h(App)
}).$mount('#app')
