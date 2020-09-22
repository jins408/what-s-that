import Vue from 'vue'
import VueRouter from 'vue-router'

// Join
import Join from '../views/user/Join.vue'
import Login from '../views/user/Login.vue'
import Mypage from '../views/user/Mypage.vue'

// Main
import Main from '../views/main/Main.vue'

// Post
import PostDetail from '../views/post/PostDetail.vue'

Vue.use(VueRouter)

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
