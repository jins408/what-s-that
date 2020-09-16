import Vue from 'vue'
import VueRouter from 'vue-router'

// Join
import Join from '../views/user/Join.vue'
import Login from '../views/user/Login.vue'
import Mypage from '../views/user/Mypage.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/user/join',
    name: Join,
    component: Join
  },
  {
    path: '/user/login',
    name: Login,
    component: Login
  },
  {
    path: '/user/mypage',
    name: Mypage,
    component: Mypage
  }
  
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
