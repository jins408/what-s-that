<template>
<div style="margin: 10rem 0;">
  <v-card
    class="mx-auto"
    outlined
    width="90%"
    style="margin-bottom: 2rem;"
  >

  <div class="d-flex justify-content-between">
    <v-card-title>MyPage</v-card-title>
        <div class="d-flex justify-end">
            <v-card-actions>
                <v-btn >회원정보</v-btn>
            </v-card-actions>
        </div>
  </div>
    <!-- <div class="overline mb-4">OVERLINE</div> -->

    <v-list-item three-line>
        <v-list-item-avatar
            tile
            size="80"
            color="grey"
        ></v-list-item-avatar>
            <v-list-item-content>
                <v-row>
                <v-col cols="12" sm="5">
                    <v-list-item-title class="headline mb-4">김싸피 님</v-list-item-title>
                    <v-list-item-title class="mb-1">ssafy2020@ssafy.com</v-list-item-title>
                    <v-list-item-subtitle style="margin-bottom: 2rem;">Greyhound divisely hello coldly fonwderfully</v-list-item-subtitle>

                </v-col>
                <v-col cols="12" sm="2"></v-col>
                <v-col cols="12" sm="5">
                    <v-list-item-subtitle class="mb-3" ><v-icon>mdi-bookmark</v-icon> 찜한 수 </v-list-item-subtitle>
                    <v-list-item-subtitle> <v-icon>mdi-pencil</v-icon> 카테고리 수</v-list-item-subtitle>

                </v-col>
                </v-row>
            </v-list-item-content>            
        </v-list-item>
  </v-card >
    
    <v-card
        class="mx-auto" 
        width="90%">
    <v-tabs
      background-color="white"
      color="deep-purple accent-4"
      left
    >
      <v-tab>찜목록</v-tab>
      <v-tab>카테고리</v-tab>
      
      <v-tab>내정보</v-tab>

      <v-tab-item
        v-for="n in 3"
        :key="n"
      >
      <category v-if="n==2" />
        <v-container fluid>
          <v-row>
            <v-col
              v-for="i in 6"
              :key="i"
              cols="12"
              md="4"
            >
              <!-- <v-img
                :src="`https://picsum.photos/500/300?image=${i * n * 5 + 10}`"
                :lazy-src="`https://picsum.photos/10/6?image=${i * n * 5 + 10}`"
                aspect-ratio="1"
              ></v-img> -->
            </v-col>
          </v-row>
        </v-container>
      </v-tab-item>
    </v-tabs>
  </v-card>
  </div>
</template>

<script>
import category from "../../components/Category.vue"

export default {
  components:{
    category,
  }

}
</script>

<style>

</style>