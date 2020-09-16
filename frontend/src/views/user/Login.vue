<template>
  <div>
    <div class="mt-3 mr-5 text-end">
      <v-btn rounded color="error" dark>간편회원가입</v-btn>
    </div>
  <div style="margin-top : 130px;"> 
    <v-container >
          <h2 class="text-center mb-3">로그인</h2>
          <v-form>
            <v-row >
              <v-col class="mx-auto"  cols="12" sm="4">
                      <v-text-field
                        placeholder="아이디(이메일)입력해주세요."
                        filled
                        rounded                    
                      ></v-text-field>         
                      <v-text-field
                        type="password"
                        placeholder="영문, 숫자 혼용 8자 이상"
                        filled
                        rounded              
                      ></v-text-field>
                      <v-btn rounded color="primary" style="width:100%; height:19%;">로그인</v-btn>

                      <div class=" d-flex justify-end">
                        <v-btn text>비회원으로 이용하기</v-btn>
                      </div>
                      <hr class="mt-5">
                      <div class="d-flex justify-end">
                        <h5 style="color: gray; margin-right:50%;" class="mt-1">카카오로 로그인하기</h5>
                        <img src="../../assets/kakao.png" style="width:50px; height:50px;" class="mt-1" alt="">
                      </div>
                </v-col>
            </v-row>
          </v-form>
      </v-container>
  </div>
  </div>
</template>

<script>
export default {
  
  }
</script>

<style>

</style>