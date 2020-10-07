import Vue from 'vue'
import VueRouter from 'vue-router'
import Vuex from 'vuex'

// import Store from '../store/index'
// User
import Join from '../views/user/Join.vue'
import Login from '../views/user/Login.vue'
import Mypage from '../views/user/Mypage.vue'

// Main
import Main from '../views/main/Main.vue'

// Post
import PostDetail from '../views/post/PostDetail.vue'
import PostCreate from '../views/post/PostCreate.vue'
import PostUpdate from '../views/post/PostUpdate.vue'

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


  // main
  {
    path: '/main',
    name: Main,
    component: Main
  },

  // post
  {
    path: '/postdetail/:ID',
    name: 'PostDetail',
    component: PostDetail
  },
  {
    path: '/postcreate',
    name: 'PostCreate',
    component: PostCreate
  },
  {
    path: '/postupdate/:ID',
    name: 'PostUpdate',
    component: PostUpdate
  },

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router