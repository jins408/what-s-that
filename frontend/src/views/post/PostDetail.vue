<template>
  <div>
  <div class="postdetail_bg">
    <!-- 웹버전 -->
    <div class="d-none d-sm-block">
    <div class="post_title_web" >문화정보</div>
    </div>
    <!-- 모바일버전 -->
    <div class="d-block d-sm-none d-md-none">  
    <div class=" post_title_mobile" >문화정보</div>
    </div>
    </div>

    <v-container>
          <v-row class="mb-6" no-gutters>
            <v-col cols="12" sm="12" md="6">
            <v-card class="pa-2 mt-5" outlined tile color="#BDBDBD" >
              <img
                :src="this.post.imageUrl"
                alt="여긴 이미지"
                style="width: 97%; height: 27rem; padding-left: 16px; margin-top:1.5rem; margin-bottom:1.5rem;"
              />

            </v-card>
            </v-col>
            
   
          <v-col cols="12" sm="12" md="6">
          <v-card class="pa-2 mt-5" outlined tile style="height: 31.13rem;" color="#EEEEEE">
          <div class="d-flex justify-content-between mx-5 mt-5">
            <span style="font-size:1.8rem; font-weight: bold;">
              {{ this.post.culturename }}
            </span>
              <i
                class="far fa-bookmark mt-1"
                style="font-size: 1.8rem;"
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
            <hr class="posthr">

          <div class="pa-5">
            <div class="d-flex justify-content-start mb-5">
            <h6 style="font-weight: bold; color:#BF360C; margin-right:3rem; ">유형 </h6>
            {{ this.post.category}}
            </div>    
            
            <div class="d-flex justify-content-start mb-5">
            <h6 style="font-weight: bold; color:#BF360C; margin-right:3rem;">시대</h6>
            {{ this.post.generation}}
            </div>

            <div class="d-flex justify-content-start mb-5">
            <h6 style="font-weight: bold; color:#BF360C; margin-right:3rem;">연도</h6>
            {{ this.post.constructionperiod}}
            </div>

            <div class="d-flex justify-content-start mb-5">
            <h6 style="font-weight: bold; color:#BF360C; margin-right:3rem;">위치</h6>
            {{ this.post.location}}
            </div>
          
          </div>
           </v-card>
          </v-col>
        </v-row>
    </v-container>
    
    <div class="container">
      <div class="d-flex justify-content-start">
      <h5 style=" font-weight: bold; color:#BF360C">상세 내용 </h5>
      <i class="fas fa-microphone ml-3" style="font-size:1.6rem;" @click="audiotest"></i>
      </div>
      <div>
      <Viewer v-if="this.post.content != null" :initialValue="this.post.content" />  
      </div>
    </div>

    <v-container>
    <hr class="commenthr">
    <h5 style=" font-weight: bold; color:#BF360C">위치 </h5>
    <div id="map" style="width:100%;height:400px;"></div>

    <hr class="commenthr">
    <comment :commentData="commentData"></comment>
    <div
      v-if="isadmin == 1"
      class="d-flex justify-content-end"
      style="margin: 2rem auto"
    >
      <v-btn @click="deleted" class="mr-2" color="error">삭제하기</v-btn>
      <v-btn @click="gomodify" color="info">수정하기</v-btn>
    </div>
    </v-container>
  

  </div>
</template>
  <script type="text/javascript"
    src="http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${process.env.VUE_APP_KAKAO_MAP_API_KEY}"></script>
    
<script>
import axios from "axios";
import Swal from "sweetalert2";
import "codemirror/lib/codemirror.css";
import "@toast-ui/editor/dist/toastui-editor.css";
import { Viewer } from "@toast-ui/vue-editor";

import comment from "../../components/Comment.vue";

export default {
  components: {
    comment,
    Viewer
  },

  created(){
     this.commentData.userno=this.$store.state.user.userno
     this.commentData.postno = this.$route.params.ID;
    //  console.log(this.commentData.postno)
    //  console.log(this.$store.state.user.token)
     this.bmarkList()
     this.getdetail()
     this.getinfo()
  },

  mounted() {
    window.kakao && window.kakao.maps ? location.reload() : this.addScript();
  },

  methods: {
    getinfo() {
      axios
        .get(this.$baseurl + `/account/userinfo`, {
          headers: {
            Authorization: this.$store.state.user.token,
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
      })
      .catch((err)=>{
        console.log(err)
      })
    },
    bookmark(){
      if(!this.$store.state.user.token){
        Swal.fire({
        position: 'top-end',
        icon: 'error',
        title: '로그인해주세요!',
        width:'400px',
        height:'200px',
        showConfirmButton: false,
        timer: 1500
        })
      }else{

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
        .then(() =>{
          this.ismark = true;
          Swal.fire({
          position: 'top-end',
          icon: 'success',
          title: '찜등록!',
          width:'400px',
          height:'200px',
          showConfirmButton: false,
          timer: 1500
          })
          // location.reload();
        })
        .catch((error)=>{
          console.log(error)
        })
      }
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
              Swal.fire({
              position: 'top-end',
              icon: 'success',
              title: '찜해제!',
              width:'400px',
              height:'200px',
              showConfirmButton: false,
              timer: 1500
              })
              this.ismark = false
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
          .delete(`${this.$baseurl}/culture/admin/${this.commentData.postno}`, {
            headers: {
            Authorization: this.$store.state.user.token,
           },
          })
          .then(()=>{
            Swal.fire({
              text:"삭제완료",
              icon:"success"
            })
            scroll(0,0)
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
    initMap() {
      let lng = this.post.lng;
      let lat = this.post.lat;
      var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
      mapOption = { 
          center: new kakao.maps.LatLng(lng, lat), // 지도의 중심좌표
          level: 2 // 지도의 확대 레벨
      };
      var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
      var markerPosition  = new kakao.maps.LatLng(lng, lat);
      var marker = new kakao.maps.Marker({
         position: markerPosition
      }); 
      map.relayout();
      marker.setMap(map);
    },
    addScript() {
      const script = document.createElement('script');
      script.onload = () => kakao.maps.load(this.initMap);
      script.src = `http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${process.env.VUE_APP_KAKAO_MAP_API_KEY}`;
      document.head.appendChild(script);
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

<style>
.postdetail_bg{
    background: url('../../assets/postdetail_bg.jpg') no-repeat;
    width: 100%;
    height: 18rem;
    background-size: cover;
    background-position: center;
}
.post_title_web{
  position: absolute;
  left: 44%;
  top: 11rem;
  color: white;
  font-size: 2.7rem;
  font-weight: bold;
}

.post_title_mobile{
  position: absolute;
  left: 22%;
  top: 11rem;
  color: white;
  font-size: 2.7rem;
  font-weight: bold;
}

.posthr{
  margin-top: 0.3rem;
  margin-bottom: 0;
  margin-left: 1.3rem;
  width: 95%;
}
.commenthr{
  margin-top: 0.5rem;
  margin-bottom: 2rem;
  width: 100%;
}

</style>