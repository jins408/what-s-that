<template>
  <div class="my-auto"> 
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
                      <div class="d-flex justify-end">
                        <v-btn text>비회원으로 이용하기</v-btn>
                      </div>
                      <div class="mt-4 text-center">
                        <v-btn rounded color="primary" dark class="mr-3">로그인</v-btn>
                        <v-btn rounded color="error" dark>회원가입</v-btn>
                      </div>
                </v-col>
            </v-row>
          </v-form>
      </v-container>
  </div>
</template>

<script>
export default {

}
</script>

<style>

</style>