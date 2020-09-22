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
      <p class="text-center mb-5">
        <span style="font-size:3rem; font-weight:bold; color:red; text-shadow: 2px 2px 2px gray;">What</span>
        <span style="font-size:2rem; text-shadow: 2px 2px 2px gray;">'s that!?</span>
      </p>

          <div class="d-flex justify-content-start" style="width:90%; margin:auto; padding-top:5rem;">
            <img src="../../assets/bgbg.jpg" alt="여긴 이미지" style="width:26rem; height:24rem; padding-left:12px;">
            <div class="ml-5 w-100">
                <p style="font-size:1.5rem; font-weight:540; text-align:center">경주 불국사</p>
                <p>유형</p>
                <p>시대</p>
                <p>건립시기/연도</p>
                <p>규모(면적)</p>
                <p>소새지</p>
                <p>소유자</p>
                <p>관리자</p>
                <p>문화재 지정번호</p>
                <p>문화재 지정일</p>
            </div>
          </div>
          <v-card width="90%" class="mx-auto">
    <v-tabs
      background-color="white"
      color="red"
      left
    >
      <v-tab>정의</v-tab>
      <v-tab>내용</v-tab>
      <v-tab>Abstract</v-tab>

      <v-tab-item
        v-for="n in 3"
        :key="n"
      >
        <v-container fluid>
          <v-row>
            <v-col
              v-for="i in 6"
              :key="i"
              cols="12"
              md="4"
            >
              <v-img
                :src="`https://picsum.photos/500/300?image=${i * n * 5 + 10}`"
                :lazy-src="`https://picsum.photos/10/6?image=${i * n * 5 + 10}`"
                aspect-ratio="1"
              ></v-img>
            </v-col>
          </v-row>
        </v-container>
      </v-tab-item>
    </v-tabs>
  </v-card>
      </div>
</template>

<script>
export default {
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
    }

}
</script>

<style>

</style>