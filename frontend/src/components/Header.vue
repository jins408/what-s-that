<template>
  <div>
    <div class="header" v-if="mainpage">
      <div class="text-center mr-3 mt-2">
        <v-menu offset-y>
          <template v-slot:activator="{ on, attrs }">
            <v-btn
              class="userbtn"
              v-bind="attrs"
              v-on="on"
              style="box-shadow: none"
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
              color="white"
              v-bind="attrs"
              v-on="on"
              style="box-shadow: none"
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

    <div class="text-center" style="margin-top:5rem;" v-if="!mainpage">
      <span @click="gomain()" style="cursor: pointer">
        <span
          style="
            font-size: 3rem;
            font-weight: bold;
            color: red;
            text-shadow: 2px 2px 2px gray;
          "
          >What</span
        >
        <span style="font-size: 2rem; text-shadow: 2px 2px 2px gray"
          >'s that!?</span
        >
      </span>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import store from "../store";
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
      this.$router.push("/user/join");
    },
    gologin() {
      this.$router.push("/");
    },
    gomypage() {
      this.$router.push("/user/mypage");
    },
    logout() {
      store.dispatch("AUTH_LOGOUT").then(() => {
        alert("로그아웃 되었습니다.");
        this.$router.push("/");
      });
    },
    gomain() {
      this.$router.push("/main");
    },
    gowrite(){
      this.$router.push('/postcreate')
    },
    getinfo() {
      axios
        .get(this.$baseurl + `/account/userinfo`, {
          headers: {
            Authorization: this.$store.state.user.token,
          },
        })
        .then((res) => {
          this.isadmin = res.data.object.isadmin;
          console.log(this.isadmin)
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};
</script>

<style scoped>
.header{
  position: absolute;
  right:1%;
  top:0.5%;
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