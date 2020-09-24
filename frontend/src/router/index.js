import Vue from 'vue'
import VueRouter from 'vue-router'
import Vuex from 'vuex'

// import Store from '../store/index'
// User
import Join from '../views/user/Join.vue'
import Login from '../views/user/Login.vue'
import Mypage from '../views/user/Mypage.vue'
import Info from '../views/user/Info.vue'

// Main
import Main from '../views/main/Main.vue'

// Post
import PostDetail from '../views/post/PostDetail.vue'

Vue.use(VueRouter)
Vue.use(Vuex)

const routes = [
  // user
  {
    path: '/user/join',
    name: Join,
    component: Join
  },
  {
    path: '/',
    name: Login,
    component: Login
  },

  {
    path: '/user/mypage',
    name: Mypage,
    component: Mypage
  },
  {
    path: '/user/info',
    name: Info,
    component: Info
  },
  

  // main
  {
    path: '/main',
    name: Main,
    component: Main
  },

  // post
  {
    path: '/postdetail',
    name: PostDetail,
    component: PostDetail
  },

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
