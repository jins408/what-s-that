<template>
  <div>
    <div
      class="d-flex justify-content-start"
      style="width: 90%; margin: auto; padding-top: 5rem"
    >
      <img
        src="../../assets/bgbg.jpg"
        alt="여긴 이미지"
        style="width: 26rem; height: 24rem; padding-left: 12px"
      />
      <div class="ml-5 w-100">
        <p style="font-size: 1.5rem; font-weight: 540; text-align: center">
          경주 불국사
        </p>
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
   
    <comment id="comment"></comment>

    <v-card width="90%" class="mx-auto mt-5">
      <v-tabs background-color="white" color="red" left>
        <v-tab>정의</v-tab>
        <v-tab>내용</v-tab>
        <v-tab>Abstract</v-tab>

        <v-tab-item v-for="n in 3" :key="n">
          <v-container fluid>
            <v-row>
              <v-col v-for="i in 6" :key="i" cols="12" md="4">
                <v-img
                  :src="`https://picsum.photos/500/300?image=${i * n * 5 + 10}`"
                  :lazy-src="`https://picsum.photos/10/6?image=${
                    i * n * 5 + 10
                  }`"
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
import comment from "../../components/Comment.vue";

export default {
  components: {
    comment,
  },

  data() {
    return {
      comment: null,
      
    }
  }

};
</script>

<style>
</style>