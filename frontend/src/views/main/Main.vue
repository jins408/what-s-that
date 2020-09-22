<template>
  <div>
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
        <v-list-item class="userlist" @click="gojoin">
          <v-list-item-title style="font-size:0.9rem;">Singup</v-list-item-title>
        </v-list-item>
        <v-list-item class="userlist" @click="gologin">
          <v-list-item-title style="font-size:0.9rem;">Login</v-list-item-title>
        </v-list-item>
        <v-list-item class="userlist" @click="gomypage">
          <v-list-item-title style="font-size:0.9rem;">Mypage</v-list-item-title>
        </v-list-item>
        <v-list-item class="userlist" @click="logout">
          <v-list-item-title style="font-size:0.9rem;">Logout</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-menu>
  </div>
    </div>
    <div>
      <p class="text-center" style="margin-top:14rem;">
        <span style="font-size:3rem; font-weight:bold; color:red; text-shadow: 2px 2px 2px gray;">What</span>
        <span style="font-size:2rem; text-shadow: 2px 2px 2px gray;">'s that!?</span>
      </p>
      <v-col cols="12" sm="6" class="mx-auto">
        <v-row>
          <v-text-field prepend-icon="fas fa-search" placeholder="검색해주세요!" filled rounded dense></v-text-field>
          <i class="fas fa-microphone ml-2 mt-2" style="font-size:1.6rem;"></i>
        </v-row>
      </v-col>
      <div class="d-flex justify-content-center" style="margin-top:18rem;">
        <button class="btndown" @click="scroll">
          <i class="fas fa-angle-down" style="font-size:2rem;"></i>
        </button>
      </div>
    </div>

    <div class="mt-2">
      <div style="height:50rem;">
        <a class="ml-5" href="#heritage" style="font-size:1.5rem; margin:0">문화재</a>
        <div class="d-flex justify-content-center" style="margin-top:19rem;">
          <button class="btndown" @click="scroll1">
            <i class="fas fa-angle-down" style="font-size:2rem;"></i>
          </button>
        </div>
      </div>
      <div style="height:50rem;">
        <a class="ml-5" href="#location" style="font-size:1.5rem;">지역</a>
        <div class="d-flex justify-content-center" style="margin-top:19rem;">
          <button class="btndown" @click="scroll2">
            <i class="fas fa-angle-down" style="font-size:2rem;"></i>
          </button>
        </div>
      </div>
      <div style="height:50rem;">
        <a class="ml-5" href="#comment" style="font-size:1.5rem;">최근 댓글</a>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  created() {
    window.addEventListener("scroll", this.updatescroll);
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
      alert('로그아웃 구현하자')
    },
    updatescroll() {
      this.scrollposition = window.scrollY;
      // console.log(this.scrollposition)
    },
    scroll() {
      // var location = document.querySelector("#heritage").offsetTop;
      window.scrollTo({ top: 740, behavior: "smooth" });
    },
    scroll1() {
      window.scrollTo({ top: 1545, behavior: "smooth" });
    },
    scroll2() {
      window.scrollTo({ top: 2345, behavior: "smooth" });
    },
  },
  data() {
    return {
      scrollposition: 0,
    };
  },
};
</script>

<style scoped>
.userbtn{
  padding: 0 10px !important;
}

.userbtn:focus{
  outline: none;
}

.userlist:hover{
  background: rgba(0,0,0,0.2);
  cursor: pointer;
}

.btndown:focus {
  outline: none;
}

.btndown:hover {
  color: red;
}

a {
  text-decoration: none;
  color: red !important;
}
</style>