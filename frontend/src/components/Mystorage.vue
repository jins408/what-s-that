<template>
  <div class="container">
    <v-col class="pt-5">
      <v-row cols="12" sm="12">
        <v-card
          class="mx-5 my-3"
          max-width="250"
          v-for="(mystorage, index) in mystorages"
          :key="index"
          style="cursor: pointer"
        >
          <v-img
            class="white--text align-end"
            height="200px"
            src="https://cdn.vuetifyjs.com/images/cards/docks.jpg"
            @click="gopostdetail(mystorage.postno)"
          >
            <div class="d-flex justify-content-end">
              <v-card-title>{{ mystorage.culturename }}</v-card-title>
            </div>
          </v-img>
          <v-card-actions class="d-flex justify-content-center">
            <v-btn color="red" style="font-weight:bold; width:50%" text @click="gopostdetail(mystorage.postno)"> 상세보기 </v-btn>
            <v-btn color="red" style="font-weight:bold; width:50%" text @click="delmystorage(mystorage.postno)"> 취소 </v-btn>
          </v-card-actions>
        </v-card>
      </v-row>
    </v-col>
  </div>
</template>

<script>
import axios from "axios";

const baseURL = "http://localhost:8080";

export default {
    props:{
        mystorages:Array,
    },
  methods: {
    delmystorage(postno){
        axios
        .delete(`${baseURL}/dictionary/culture/favorite/${postno}`,{
          headers: {
            Authorization: this.$store.state.user.token,
          },
          })
          .then(()=>{
              alert('삭제완료!')
              location.reload()
          })
          .catch((err)=>{
              console.log(err)
          })
    },
    gopostdetail(postno) {
      this.$router.push({
        name: "PostDetail",
        params: { ID: postno },
      });
    },
  },
};
</script>

<style>
</style>