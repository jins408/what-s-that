<template>
  <div>
    <div
      class="d-flex justify-content-start"
      style="width: 90%; margin: auto; padding-top: 5rem"
    >
      <div class="ml-5 w-100">
        <p style="font-size: 1.5rem; font-weight: 540; text-align: center">
          {{ this.post.culturename }}
        </p>
        <div class="d-flex justify-content-end mb-5">
          <i
            class="far fa-bookmark"
            style="font-size: 1.5rem"
            v-if="!ismark"
            @click="bookmark"
          ></i>
          <i
            class="fas fa-bookmark"
            style="font-size: 1.5rem"
            v-if="ismark"
            @click="bookmarkdelete"
          ></i>
        </div>
      </div>
    </div>


    <v-card width="90%" class="mx-auto mt-5">
      <v-tabs background-color="white" color="red" left>
        <v-tab>사진</v-tab>
        <v-tab>내용</v-tab>

        <v-tab-item v-for="n in 2" :key="n">
          <v-container fluid>
            <v-row >
              <v-col v-if="n == 1" class="d-flex justify-content-center">
                <img
                  src="../../assets/bgbg.jpg"
                  alt="여긴 이미지"
                  style="width: 26rem; height: 24rem; padding-left: 12px"
                />
              </v-col>
              <v-col v-if="n == 2" cols="12" md="4">
                {{post.content}}
              </v-col>
            </v-row>
          </v-container>
        </v-tab-item>
      </v-tabs>
    </v-card>
    <comment :commentData="commentData"></comment>
    <div
      class="d-flex justify-content-end"
      style="width: 90%; margin: 2rem auto"
    >
      <v-btn @click="deleted">삭제하기</v-btn>
      <v-btn @click="gomodify">수정하기</v-btn>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import Swal from "sweetalert2";

import comment from "../../components/Comment.vue";

export default {
  components: {
    comment,
  },

  created(){
     this.commentData.userno=this.$store.state.user.userno
     this.commentData.postno = this.$route.params.ID;
    //  console.log(this.commentData.postno)
    //  console.log(this.$store.state.user.token)
     this.bmarkList()
     this.getdetail()
  },

  methods: {
    getdetail(){
      axios
      .get(`${this.$baseurl}/culture/detail/${this.commentData.postno}`)
      .then((res)=>{
        this.post = res.data.object
        console.log(this.post)
      })
      .catch((err)=>{
        console.log(err)
      })
    },
    bookmark(){
      axios({
        method: 'POST',
        url: this.$baseurl + `/culture/regfavorite`,
        data: {
          postno: this.commentData.postno
        },
         headers: {
            Authorization: this.$store.state.user.token,
          },
      })
      .then((response) =>{
        console.log(response)
        this.ismark = true;
        alert("찜등록!")
        // location.reload();
      })
      .catch((error)=>{
        console.log(error)
      })
    },
    bmarkList(){
      axios.get(`${this.$baseurl}/culture/favorite`, {
          headers: {
            Authorization: this.$store.state.user.token,
          },
        })
        .then((response) => {
          this.bookmarkdata = response.data.object;
          for(var i=0; i<this.bookmarkdata.length; i++){
            if(this.bookmarkdata[i].postno == this.commentData.postno){
              this.ismark = true;
            }
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    bookmarkdelete(){
       axios
        .delete(`${this.$baseurl}/culture/favorite/${this.commentData.postno}`,{
          headers: {
            Authorization: this.$store.state.user.token,
          },
          })
          .then(()=>{
              alert('찜해제!')
              this.ismark = false
              // location.reload()
          })
          .catch((err)=>{
              console.log(err)
          })
    },
    gomodify(){
      this.$router.push({
            name: "PostUpdate",
            params: { ID: this.commentData.postno },
          });
    },
    deleted(){
      Swal.fire({
        text: "삭제하시겠습니까?",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: '삭제',
        cancelButtonText: '취소'
      }).then((result) => {
        if (result.isConfirmed) {
          axios
          .delete(`${this.$baseurl}/culture/${this.commentData.postno}`)
          .then(()=>{
            Swal.fire({
              text:"삭제완료",
              icon:"success"
            })
            this.$router.push('/main')
          }).catch((err)=>{
            console.log(err)
          })
        }
    })
    },
    
  },

  data() {
    return {
      comment: null,
      bookmarkdata:"",
      ismark:false,
      commentData: {
          reply:"",
          userno:"",
          postno:"",
      },
      post:[],
      
    }
  }

};
</script>

<style>
</style>