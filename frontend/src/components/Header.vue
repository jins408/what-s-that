<template>
  <div class="d-flex justify-content-end">
      <div class="text-center mr-3 mt-2">
    <v-menu offset-y>
      <template v-slot:activator="{ on, attrs }">
        <v-btn
          class="userbtn"
          color="white"
          v-bind="attrs"
          v-on="on"
          style="box-shadow:none;"
        ><i class="far fa-user-circle mr-2" style="font-size:1.3rem;" v-bind="attrs" v-on="on"></i>
          User
        </v-btn>
      </template>
      <v-list class="text-center p-0">
        <v-list-item class="userlist" @click="gojoin" v-if="!isLoggedIn"  >
          <v-list-item-title style="font-size:0.9rem;">Singup</v-list-item-title>
        </v-list-item>
        <v-list-item class="userlist" @click="gologin" v-if="!isLoggedIn"  >
          <v-list-item-title style="font-size:0.9rem;">Login</v-list-item-title>
        </v-list-item>
        <v-list-item class="userlist"  @click="gomypage" v-if="isLoggedIn" >
          <v-list-item-title style="font-size:0.9rem;">Mypage</v-list-item-title>
        </v-list-item>
        <v-list-item class="userlist"  @click="logout" v-if="isLoggedIn"  >
          <v-list-item-title style="font-size:0.9rem;">Logout</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-menu>
  </div>
    </div>
</template>

<script>
import { mapState } from 'vuex'
import store from "../store"

// import { mapState} from "vuex";

export default {

    computed:{
      ...mapState({
        isLoggedIn: state => state.user.isLoggedIn,
      })   
    },
    methods: {
    gojoin(){
      this.$router.push('/user/join')
    },
    gologin(){
      this.$router.push('/')
    },
    gomypage(){
      this.$router.push('/user/mypage')
    },
    logout(){
      store.dispatch("AUTH_LOGOUT")
      .then(() =>{
        alert('로그아웃 되었습니다.')
        this.$router.push("/")
      })
    },
    }

}
</script>

<style>

</style>