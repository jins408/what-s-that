<template>
  <div class="main-bg" style="height:100%;">
    <div class="img-cover"></div>
    <div class="login-form">
    <div class="mt-3 mr-5 text-end">
      <v-btn rounded color="error" dark @click="join">간편회원가입</v-btn>
    </div>
  <div style="margin-top : 130px;"> 
    <v-container >
          <h2 class="text-center mb-3">로그인</h2>
          <v-form>
            <v-row >
              <v-col class="mx-auto"  cols="12" sm="4">
                      <v-text-field
                        placeholder="아이디(이메일)입력해주세요."
                        background-color="white"
                        filled
                        rounded                    
                      ></v-text-field>         
                      <v-text-field
                        type="password"
                        placeholder="영문, 숫자 혼용 8자 이상"
                        background-color="white"
                        filled
                        rounded              
                      ></v-text-field>
                      <v-btn rounded color="primary" style="width:100%; height:19%;">로그인</v-btn>

                      <div class=" d-flex justify-end">
                        <v-btn text style="color: white;">비회원으로 이용하기</v-btn>
                      </div>
                      <hr class="mt-5">
                      <div class="d-flex justify-end">
                        <h5 style="color: white; margin-right:50%;" class="mt-1">카카오로 로그인하기</h5>
                        <img src="../../assets/kakao.png" style="width:50px; height:50px;" class="mt-1" alt="">
                      </div>
                      
                </v-col>
            </v-row>
          </v-form>
      </v-container>
  </div>
  </div>
  </div>

</template>

<script>
export default {
    name:"Login",
    computed:{

  },
  methods:{
      join(){
        this.$router.push( '/user/join' )
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