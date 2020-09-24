<template>
  <div style="margin: 10rem 0">
    <v-card class="mx-auto" outlined width="90%" style="margin-bottom: 2rem">
      <div class="d-flex justify-content-between">
        <v-card-title>MyPage</v-card-title>
        <div class="d-flex justify-end">
          <v-card-actions>
            <v-btn @click="goinfo()">회원정보</v-btn>
          </v-card-actions>
        </div>
      </div>
      <!-- <div class="overline mb-4">OVERLINE</div> -->

      <v-list-item three-line>
        <v-list-item-avatar tile size="80" color="grey"></v-list-item-avatar>
        <v-list-item-content>
          <v-row>
            <v-col cols="12" sm="5">
              <v-list-item-title class="headline mb-4"
                >김싸피 님</v-list-item-title
              >
              <v-list-item-title class="mb-1"
                >ssafy2020@ssafy.com</v-list-item-title
              >
              <v-list-item-subtitle style="margin-bottom: 2rem"
                >Greyhound divisely hello coldly
                fonwderfully</v-list-item-subtitle
              >
            </v-col>
            <v-col cols="12" sm="2"></v-col>
            <v-col cols="12" sm="5">
              <v-list-item-subtitle class="mb-3"
                ><v-icon>mdi-bookmark</v-icon> 찜한 수
              </v-list-item-subtitle>
              <v-list-item-subtitle>
                <v-icon>mdi-pencil</v-icon> 카테고리 수</v-list-item-subtitle
              >
            </v-col>
          </v-row>
        </v-list-item-content>
      </v-list-item>
    </v-card>

    <v-card class="mx-auto" width="90%">
      <div>
          <v-col cols="12" sm="8">
            <v-text-field v-model="hint" label="프로필" clearable></v-text-field>
            <v-text-field v-model="hint" label="닉네임" clearable></v-text-field>
            <v-text-field v-model="hint" label="소개" clearable></v-text-field>
            <v-text-field v-model="hint" label="비밀번호" clearable></v-text-field>
            <v-text-field v-model="hint" label="비밀번호 확인" clearable></v-text-field>
          </v-col>
      </div>
    </v-card>
  </div>
</template>

<script>
export default {
  components: {},
  methods: {
    goinfo() {
      this.$router.push("/user/info");
    },
  },
};
</script>

<style>
</style>