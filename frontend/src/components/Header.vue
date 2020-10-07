<template>
<div>
  <div class="d-flex justify-content-end">
    <div class="header" v-if="mainpage">
      <div class="text-center mr-3 mt-2">
        <v-menu offset-y>
          <template v-slot:activator="{ on, attrs }">
            <v-btn
              class="userbtn mt-2"
              v-bind="attrs"
              v-on="on"
              style="box-shadow: none; font-family: '국립박물관문화재단클래식B';"
              ><i
                class="far fa-user-circle mr-2"
                style="font-size: 1.3rem"
                v-bind="attrs"
                v-on="on"
              ></i>
              User
            </v-btn>
          </template>
          <v-list class="text-center p-0 userbtnmenu">
            <v-list-item class="userlist" @click="gojoin" v-if="!isLoggedIn">
              <v-list-item-title style="font-size: 0.9rem; color:white;"
                >Singup</v-list-item-title
              >
            </v-list-item>
            <v-list-item class="userlist" @click="gologin" v-if="!isLoggedIn">
              <v-list-item-title style="font-size: 0.9rem; color:white;"
                >Login</v-list-item-title
              >
            </v-list-item>
            <v-list-item class="userlist" @click="gowrite" v-if="isLoggedIn && isadmin == 1">
              <v-list-item-title style="font-size: 0.9rem; color:white;"
                >Write</v-list-item-title
              >
            </v-list-item>
            <v-list-item class="userlist" @click="gomypage" v-if="isLoggedIn">
              <v-list-item-title style="font-size: 0.9rem; color:white;"
                >Mypage</v-list-item-title
              >
            </v-list-item>
            <v-list-item class="userlist" @click="logout" v-if="isLoggedIn">
              <v-list-item-title style="font-size: 0.9rem; color:white;"
                >Logout</v-list-item-title
              >
            </v-list-item>
          </v-list>
        </v-menu>
      </div>
    </div>
    <div class="header" v-if="!mainpage">
      <div class="text-center mr-3 mt-2">
        <v-menu offset-y>
          <template v-slot:activator="{ on, attrs }">
            <v-btn
              class="mt-2"
              color="white"
              v-bind="attrs"
              v-on="on"
              style="box-shadow: none; font-family: '국립박물관문화재단클래식B';"
              ><i
                class="far fa-user-circle mr-2"
                style="font-size: 1.3rem"
                v-bind="attrs"
                v-on="on"
              ></i>
              User
            </v-btn>
          </template>
          <v-list class="text-center p-0">
            <v-list-item class="userlist" @click="gojoin" v-if="!isLoggedIn">
              <v-list-item-title style="font-size: 0.9rem"
                >Singup</v-list-item-title
              >
            </v-list-item>
            <v-list-item class="userlist" @click="gologin" v-if="!isLoggedIn">
              <v-list-item-title style="font-size: 0.9rem"
                >Login</v-list-item-title
              >
            </v-list-item>
            <v-list-item class="userlist" @click="gowrite" v-if="isLoggedIn && isadmin == 1">
              <v-list-item-title style="font-size: 0.9rem;"
                >Write</v-list-item-title
              >
            </v-list-item>
            <v-list-item class="userlist" @click="gomypage" v-if="isLoggedIn">
              <v-list-item-title style="font-size: 0.9rem"
                >Mypage</v-list-item-title
              >
            </v-list-item>
            <v-list-item class="userlist" @click="logout" v-if="isLoggedIn">
              <v-list-item-title style="font-size: 0.9rem"
                >Logout</v-list-item-title
              >
            </v-list-item>
          </v-list>
        </v-menu>
      </div>
    </div>
  </div>

    <!-- 웹버전 -->
    <div class="d-none d-sm-block">
    <div class="text-center" v-if="!mainpage">
      <span @click="gomain()" style="cursor: pointer">
        <span
          style="
            font-size: 3rem;
            font-weight: bold;
            color: red;
            text-shadow: 2px 2px 2px gray;
            font-family: '국립박물관문화재단클래식B';
          "
          >What</span
        >
        <span style="font-size: 2rem; text-shadow: 2px 2px 2px gray; font-family: '국립박물관문화재단클래식B';"
          >'s that!?</span
        >
      </span>
    </div>
    </div>

    <!-- 모바일버전 -->
    <div class="d-block d-sm-none d-md-none">
      <div class="ml-5" v-if="!mainpage">
      <span @click="gomain()" style="cursor: pointer">
        <span
          style="
            font-size: 3rem;
            font-weight: bold;
            color: red;
            text-shadow: 2px 2px 2px gray;
            font-family: '국립박물관문화재단클래식B';
          "
          >What</span
        >
        <span style="font-size: 2rem; text-shadow: 2px 2px 2px gray; font-family: '국립박물관문화재단클래식B';"
          >'s that!?</span
        >
      </span>
    </div>
    </div>
  </div>
</template>



<script>
import axios from "axios";
import store from "../store";
import Swal from "sweetalert2";
import Main from "../views/main/Main.vue";
import { mapGetters } from "vuex";

// import { mapState} from "vuex";

export default {
  data() {
    return {
      checkmain: false,
      isadmin:"",
    };
  },

  computed: {
    ...mapGetters ({
      isLoggedIn : 'isAuthenticated'
    }),
    mainpage() {
      return this.$route.name == Main;
    },
  },
  created(){
    this.getinfo()
  },
  methods: {
    gojoin() {
      scroll(0, 0);
      this.$router.push("/user/join").catch(()=>{
        this.$router.go();
      });
    },
    gologin() {
      scroll(0, 0);
      this.$router.push("/").catch(()=>{
        this.$router.go();
      });
    },
    gomypage() {
      scroll(0, 0);
      this.$router.push("/user/mypage").catch(()=>{
        this.$router.go();
      });
    },
    logout() {
      scroll(0, 0);
      store.dispatch("AUTH_LOGOUT").then(() => {
        Swal.fire({
              position: 'top-end',
              icon: 'success',
              title: '로그아웃 되었습니다.',
              showConfirmButton: false,
              timer:1500
          })
          setTimeout(()=>{
            this.$router.push("/");
          },1500)
        // alert("로그아웃 되었습니다.");
        // this.$router.push("/");
      });
    },
    gomain() {
      scroll(0, 0);
      this.$router.push("/main").catch(()=>{
        this.$router.go();
      });
    },
    gowrite(){
      scroll(0, 0);
      this.$router.push('/postcreate').catch(()=>{
        this.$router.go();
      });
    },
    getinfo() {
      if(this.$store.state.user.token){
        axios
          .get(this.$baseurl + `/account/userinfo`, {
            headers: {
              Authorization: this.$store.state.user.token,
            },
          })
          .then((res) => {
            this.isadmin = res.data.object.isadmin;
            // console.log(this.isadmin)
          })
          .catch((err) => {
            console.log(err);
          });
      }
    },
  },
};
</script>

<style scoped>
.header{
  position: absolute;
  z-index: 5;
}

.userbtn{
  background: none !important;
  color: white;
}

.userbtnmenu{
  background: none !important;
}
</style>