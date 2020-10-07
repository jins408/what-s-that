<template>
  <div class="main-bg" style="height:100%;">
    <div class="img-cover"></div>
    <div class="login-form">
    <div class="mt-3 mr-5 text-end">
      <v-btn rounded color="error" dark @click="join">간편회원가입</v-btn>
    </div>
  <div style="margin-top : 130px;"> 
    <v-container >
          <h3 class="text-center mb-3">로그인</h3>
          <v-form>
            <v-row >
              <v-col class="mx-auto"  cols="12" sm="4">
                      <v-text-field
                        label="아이디(이메일)입력해주세요."
                        background-color="white"
                        filled
                        rounded
                        v-model="email"
                      ></v-text-field>         
                      <v-text-field
                        type="password"
                        label="영문, 숫자 혼용 8자 이상"
                        background-color="white"
                        filled
                        rounded
                        v-model="password"              
                      ></v-text-field>
                      <v-btn rounded color="primary" style="width:100%; height:19%; font-size: 1rem" @click="Login()">로그인</v-btn>

                      <div class=" d-flex justify-content-between">
                        <findpw v-model="showFindpwForm"></findpw>
                        <v-btn text style="color: white;" @click.stop="showFindpwForm=true">pw찾기</v-btn>
                        <v-btn text style="color: white;" @click="main">비회원으로 이용하기</v-btn>
                      </div>
                      <!-- <p style="border-style: solid">solid</p> -->
                      <hr class="mt-3" style="border: 1px solid white">
                      <div class="d-flex justify-end">
                        <h5 style="color: white; margin-right:43%; font-size: 1rem " >카카오로 로그인하기</h5>
                        <img src="../../assets/kakao.png" style="width:50px; height:50px;"  @click="kakaoLogin">
                      </div>
                      
                </v-col>
            </v-row>
          </v-form>
      </v-container>
  </div>
  </div>
  </div>

</template>

<script src="https://developers.kakao.com/sdk/js/kakao.min.js"></script>
<script>Kakao.init('9d9efefa15409fa1a5cb74b4a63fb06c');</script>

<script>
import axios from "axios";
import Swal from "sweetalert2";
import findpw from "../../components/Findpw.vue"


import { AUTH_REQUEST } from "../../store/actions/auth";
import { USER_SUCCESS } from "../../store/actions/user";
// import auth from '../../store/modules/auth';


export default {
    name:"Login",

    components:{
      findpw
    },

    created(){
      
      },

  methods:{
    Login(){
      var reg = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
      if(!this.email.match(reg)){
        Swal.fire({
          position: 'top',
          icon: 'error',
          title: '이메일 양식을 확인해주세요.',
          showConfirmButton: false,
          timer: 1500
        })
        // alert("이메일 양식을 확인해주세요")
      }
      else{
        const {email, password} = this;
          this.$store
            .dispatch(AUTH_REQUEST, {email, password})
            .then(() =>{
              this.main();
            })
            .catch((error)=>{
              console.log(error)
              // alert("로그인실패")
            })

          }
      },
      join(){
        scroll(0,0);
        this.$router.push( '/user/join' )
      },
      main(){
        scroll(0,0)
        this.$router.push("/main")
        this.$router.go()
      },
      kakaoLogin(){
        const kakaovue = this;
        Kakao.Auth.login({
          success: function(respones){
            axios.get(kakaovue.$baseurl + `/user/kakaologin?access_token=${respones.access_token}`)
              .then(res =>{
                Swal.fire({
                position: 'top',
                icon: 'success',
                title: '카카오로그인 성공',
                showConfirmButton: false,
                timer: 1500
              })
                // alert("카카오로그인 성공")
                kakaovue.$store.commit(USER_SUCCESS, res.data.object)
                location.href="http://j3b202.p.ssafy.io/main"
                // location.href="http://localhost:8080/main"
              })
            },
            fail: function(error){
              Swal.fire({
          position: 'top',
          icon: 'error',
          title: '카카오로그인 실패.',
          showConfirmButton: false,
          timer: 1500
        })
              // alert("카카오로그인 실패")
              console.log(error)
            }
          })
      }
        
  },
      
  data: () =>{
        return{
          email: "",
          password: "",
          showFindpwForm: false
        }
    }
  }
</script>

<style scoped>
.main-bg{
    background: url('../../assets/bgbg.jpg') no-repeat;
    /* opacity: 0.8; */
    width: 100%;
    background-size: cover;
    background-position: center;
    color: #ffffff;

}
.img-cover{
   position: absolute;
   height: 100%;
   width: 100%;
   background-color: rgba(0, 0, 0, 0.4);                                                                 
   z-index:1;
}

.login-form{
  position: relative;
   z-index: 2;
}


</style>