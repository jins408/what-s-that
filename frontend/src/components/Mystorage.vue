<template>
  <div class="container">
    <v-col class="pt-5">
      <v-row cols="12" sm="12">
        <v-card
          class="mx-5 my-3 favoritecard"
          max-width="250"
          v-for="(mystorage, index) in mystorages"
          :key="index"
          style="cursor: pointer"
        >
          <v-card-actions class="d-flex justify-content-between">
            <span class="ml-2" style="font-size:1.2rem; font-weight:bold" @click="gopostdetail(mystorage.postno)">{{ mystorage.culturename }}</span>
            <i class="fas fa-times mr-2" style="color:red;" @click="delmystorage(mystorage.postno)"></i>
            <!-- <v-btn color="red" style="font-weight:bold; width:50%" text @click="gopostdetail(mystorage.postno)"> 상세보기 </v-btn> -->
            <!-- <v-btn color="red" style="font-weight:bold; width:50%" text @click="delmystorage(mystorage.postno)"> 취소 </v-btn> -->
          </v-card-actions>
          <v-img
            class="white--text align-end favoiteimg"
            height="250px"
            :src="mystorage.imageUrl"
            @click="gopostdetail(mystorage.postno)"
          >
          </v-img>
          <p class="favoriteshow" @click="gopostdetail(mystorage.postno)"><i class="fas fa-map-marker-alt mr-2"></i>상세보기</p>
        </v-card>
      </v-row>
    </v-col>
  </div>
</template>

<script>
import axios from "axios";
import Swal from 'sweetalert2'

export default {
    props:{
        mystorages:Array,
    },
  methods: {
    delmystorage(postno){
        axios
        .delete(this.$baseurl + `/culture/favorite/${postno}`,{
          headers: {
            Authorization: this.$store.state.user.token,
          },
          })
          .then(()=>{
              Swal.fire({
              position: 'top-end',
              icon: 'success',
              title: '삭제 완료!',
              showConfirmButton: false,
              timer:1500
          })
          setTimeout(()=>{
            location.reload();
          },1500)
          })
          .catch((err)=>{
              console.log(err)
          })
    },
    gopostdetail(postno) {
      scroll(0,0);
      this.$router.push({
        name: "PostDetail",
        params: { ID: postno },
      });
    },
  },
};
</script>

<style>
.favoritecard:hover .favoiteimg{
  filter: brightness(40%);
  transition: all 0.4s  0s
}

.favoritecard:hover p{
  visibility: visible;
}

.favoriteshow{
  font-size:1.5rem;
  color:white;
  position: absolute;
  top:50%;
  left:25%;
  visibility: hidden;
}
</style>