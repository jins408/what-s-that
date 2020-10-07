<template>
  <div>
    <div
      class="d-flex justify-content-start"
    >
      <div class="w-100">
        <img
              src="../../assets/bgbg.jpg"
              alt="여긴 이미지"
              style="width: 100%; height: 18rem;"
            />
        <p class="culturetitle">
          {{ this.post.culturename }}
        </p>
        <div class="d-flex justify-content-end mb-5">
          <i class="fas fa-microphone mr-3" style="font-size:1.6rem;" @click="audiotest"></i>
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
        <div id="map" style="width:500px;height:400px;"></div>
        <div>            
            {{post.content}}
        </div>
      </div>
    </div>


    <!-- <v-card width="90%" class="mx-auto mt-5">
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
                <i class="fas fa-microphone ml-2 mt-2" style="font-size:1.6rem;" @click="audiotest"></i>
                {{post.content}}
              </v-col>
            </v-row>
          </v-container>
        </v-tab-item>
      </v-tabs>
    </v-card> -->
    <comment :commentData="commentData"></comment>
    <div
      v-if="isadmin == 1"
      class="d-flex justify-content-end"
      style="width: 90%; margin: 2rem auto"
    >
      <v-btn @click="deleted">삭제하기</v-btn>
      <v-btn @click="gomodify">수정하기</v-btn>
    </div>
  </div>
</template>

<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=a1a9c20764f1b8ba5af778823c5a36ae"></script>
<script>
import axios from "axios";
import Swal from "sweetalert2";

import comment from "../../components/Comment.vue";

export default {
  components: {
    comment,
  },

  created(){
    this.commentData.postno = this.$route.params.ID;
    this.bmarkList();
    this.getdetail();
    this.getinfo();
    // var roadviewContainer = document.getElementById('roadview'); //로드뷰를 표시할 div
    // var roadview = new kakao.maps.Roadview(roadviewContainer); //로드뷰 객체
    // var roadviewClient = new kakao.maps.RoadviewClient(); //좌표로부터 로드뷰 파노ID를 가져올 로드뷰 helper객체

    // var position = new kakao.maps.LatLng(33.450701, 126.570667);

    // // 특정 위치의좌표와 가까운 로드뷰의 panoId를 추출하여 로드뷰를 띄운다.
    // roadviewClient.getNearestPanoId(position, 50, function(panoId) {
    //     roadview.setPanoId(panoId, position); //panoId와 중심좌표를 통해 로드뷰 실행
    // });
  },

  methods: {
    getinfo() {
      axios
        .get(this.$baseurl + `/account/userinfo`, {
          headers: {
            Authorization: this.$store.state.auth.token,
          },
        })
        .then((res) => {
          this.isadmin = res.data.object.isadmin;
          // console.log(this.isadmin)
        })
        .catch((err) => {
          console.log(err);
        });
    },
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
    audiotest(){
      if(!this.audio){
        this.audio = new Audio("http://j3b202.p.ssafy.io:8088/dictionary/images/audio/"+this.commentData.postno +".mp3");
      } 
      if(!this.isPlaying){
        this.audio.play();
        this.isPlaying = "true";
      }else{
        this.audio.pause();
        this.audio.currentTime = 0;
        this.isPlaying = "";
      }
    },
    
  },

  data() {
    return {
      isadmin:"",
      comment: null,
      bookmarkdata:"",
      ismark:false,
      commentData: {
          reply:"",
          userno:"",
          postno:"",
      },
      post:[],
      audio : false
    }
  }

};
</script>

<style scoped>
.culturetitle{
  position: absolute;
  top:12rem;
  left:45%;
  font-size: 3rem; 
  font-weight: bold;
  color:white;
}
</style>