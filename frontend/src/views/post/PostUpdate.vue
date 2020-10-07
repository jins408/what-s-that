<template>
<div>
   <div class="postupdatebg">
     <div class="darkcover-img-bg"></div>
     <!-- 웹버전 -->
    <div class="d-none d-sm-block">
    <div class=" postupdatebg_title_web" >내용 수정</div>
    </div>
    <!-- 모바일버전 -->
    <div class="d-block d-sm-none d-md-none">
    <div class=" postupdatebg_title_mobile" >내용 수정</div>
    </div>
   </div>

   <div class="container" style="margin-top:5rem; margin-bottom:5rem;">
      <v-card>
        <br>
        <div class="d-flex justify-content-between">
          <v-text-field
            label="이름"
            name="culturename"
            v-model="this.post.culturename"
            prepend-icon="mdi-label"
            type="text"
            class="mx-10 mb-5"
            id="culturename"
          ></v-text-field>

          <v-text-field
            label="English Name"
            name="ename"
            v-model="this.post.ename"
            prepend-icon="mdi-label"
            type="text"
            class="mx-10 mb-5"
            id="ename"
          ></v-text-field>
        </div>

        <div class="mx-10">
          <v-file-input
            v-model="thumbnail"
            ref="Thumbnail"
            id="Thumbnail"
            accept="image/png, image/jpeg, image/bmp"
            placeholder="Pick an Thumbnail"
            prepend-icon="mdi-camera"
            label="Thumbnail"
            class="mb-5"
          ></v-file-input>
        </div>

        <div class="d-flex justify-content-between">
        <v-text-field
          label="유형"
          name="category"
          v-model="this.post.category"
          prepend-icon="mdi-label"
          type="text"
          class="mx-10 mb-5"
          id="category"
        ></v-text-field>

        <v-text-field
          label="시대"
          name="generation"
          v-model="this.post.generation"
          prepend-icon="mdi-label"
          type="text"
          class="mx-10 mb-5"
          id="generation"
        ></v-text-field>
      </div>

      <v-text-field
        label="연도"
        name="constructionperiod"
        v-model="this.post.constructionperiod"
        prepend-icon="mdi-label"
        type="text"
        class="mx-10 mb-5"
        id="constructionperiod"
      ></v-text-field>

      <v-text-field
        label="위치"
        name="location"
        v-model="this.post.location"
        prepend-icon="mdi-label"
        type="text"
        class="mx-10 mb-5"
        id="location"
      ></v-text-field>

      <div class="d-flex justify-content-between">
        <v-text-field
          label="위도"
          name="lat"
          v-model="this.post.lat"
          prepend-icon="mdi-label"
          type="text"
          class="mx-10 mb-5"
          id="lat"
        ></v-text-field>
        <v-text-field
          label="경도"
          name="lng"
          v-model="this.post.lng"
          prepend-icon="mdi-label"
          type="text"
          class="mx-10 mb-5"
          id="lng"
        ></v-text-field>
      </div>



        <editor height="500px" id="editor" ref="toastuiEditor" > </editor> 
        
        <div id="editor"></div>

        <div style="text-align:right">
        
          <v-btn class="ma-2" color="rgb(240, 162, 46)" @click="modifypost()">
            수정하기
          </v-btn>
        </div>
      </v-card>
   </div>
</div>
</template>

<script>
import "@toast-ui/editor/dist/toastui-editor.css";
import 'codemirror/lib/codemirror.css';
import { Editor } from '@toast-ui/vue-editor'
import Swal from 'sweetalert2'
import axios from "axios";

export default {
  components :{
    Editor,
  },
  created(){
      this.postno = this.$route.params.ID;
      this.getdetail();
  },
  data: () => {
    return {
      dialog: false,
      thumbnail: null,
      editorHtml : "",
      editorText : "",
      veditorText : "",
      post:[],
    };
  },
  methods: {
    getdetail(){
      axios
      .get(`${this.$baseurl}/culture/detail/${this.postno}`)
      .then((res)=>{
        this.post = res.data.object
      })
      .catch((err)=>{
        console.log(err)
      })
    },
    modifypost() {
      if (this.checkInput()) {
        const formData = new FormData();
        formData.append("image", this.thumbnail);
        formData.append("culturename", this.post.culturename.trim());
        formData.append("ename", this.post.ename.trim());
        formData.append("category", this.post.category.trim());
        formData.append("generation", this.post.generation.trim());
        formData.append("constructionperiod", this.post.constructionperiod.trim());
        formData.append("location", this.post.location.trim());
        formData.append("lng", this.post.lng.trim());
        formData.append("lat", this.post.lat.trim());
        formData.append(
          "content",
          this.$refs.toastuiEditor.invoke("getMarkdown")
        );
        axios
          .put(this.$baseurl + `/culture/admin/${this.postno}`, formData,{
            headers: {
              Authorization: this.$store.state.user.token,
            },
          })
          .then(() => {
               Swal.fire({
                position: 'center',
                icon: 'success',
                title: '게시글이 수정되었습니다.',
                showConfirmButton: false,
                timer: 1500
                })
              scroll(0,0)
              this.$router.push({
                name: "PostDetail",
                params: { ID: this.postno },
            })
          })
          .catch(() => {
            Swal.fire({
                    icon: 'error',
                    title: '수정 실패',
                    text: '게시글 수정에 실패하였습니다.',
                });
          });
        this.dialog = false;
      }
    },
    checkInput() {
      let name = this.post.culturename.trim();
      let ename = this.post.ename.trim();
      let category = this.post.category.trim();
      let generation = this.post.generation.trim();
      let constructionperiod = this.post.constructionperiod.trim();
      let location = this.post.location.trim();
      let lng = this.post.lng.trim();
      let lat = this.post.lat.trim();
      let thumbnail = this.thumbnail;
      let content = this.$refs.toastuiEditor.invoke("getMarkdown"); // content를 저장하는 액션 처리 }
     if (name == "") {
        Swal.fire({
          position: 'top',
          icon: 'info',
          title: '이름을 입력해주세요',
          showConfirmButton: false,
          timer: 1500
        })
        document.getElementById("name").focus();
        return false;
      }
      if (ename == "") {
        Swal.fire({
          position: 'top',
          icon: 'info',
          title: '영어 이름을 입력해주세요',
          showConfirmButton: false,
          timer: 1500
        })
        document.getElementById("ename").focus();
        return false;
      }
      if (category == "") {
         Swal.fire({
          position: 'top',
          icon: 'info',
          title: '유형을 입력해주세요',
          showConfirmButton: false,
          timer: 1500
        })
        document.getElementById("category").focus();
        return false;
      }
      if (generation == "") {
         Swal.fire({
          position: 'top',
          icon: 'info',
          title: '시대를 입력해주세요',
          showConfirmButton: false,
          timer: 1500
        })
        document.getElementById("generation").focus();
        return false;
      }
      if (constructionperiod == "") {
         Swal.fire({
          position: 'top',
          icon: 'info',
          title: '연도를 입력해주세요',
          showConfirmButton: false,
          timer: 1500
        })
        document.getElementById("constructionperiod").focus();
        return false;
      }
      if (location == "") {
         Swal.fire({
          position: 'top',
          icon: 'info',
          title: '위치를 입력해주세요',
          showConfirmButton: false,
          timer: 1500
        })
        document.getElementById("location").focus();
        return false;
      }
      if (lng == "") {
         Swal.fire({
          position: 'top',
          icon: 'info',
          title: '경도를 입력해주세요',
          showConfirmButton: false,
          timer: 1500
        })
        document.getElementById("lng").focus();
        return false;
      }
      if (lat == "") {
         Swal.fire({
          position: 'top',
          icon: 'info',
          title: '위도를 입력해주세요',
          showConfirmButton: false,
          timer: 1500
        })
        document.getElementById("lat").focus();
        return false;
      }
      if (content == "") {
         Swal.fire({
          position: 'top',
          icon: 'info',
          title: '내용을 입력해주세요',
          showConfirmButton: false,
          timer: 1500
        })
        document.getElementById("editor").focus();
        return false;
      }
      if (thumbnail == null) {
         Swal.fire({
          position: 'top',
          icon: 'info',
          title: '썸네일을 등록해주세요',
          showConfirmButton: false,
          timer: 1500
        })
        return false;
      }
      return true;
    }
  },
};
</script>

<style>
.postupdatebg{
    background: url('../../assets/login_bg.jpg') no-repeat;
    width: 100%;
    height: 18rem;
    background-size: cover;
    background-position: center;
}

.darkcover-img-bg{
    background-color: rgba(0, 0, 0, 0.3);
    /*살짝 투명한 검정으로 배경색*/
    width: 100%;
    height: 18rem;
    position: absolute;
}

.postupdatebg_title_web{
  position: absolute;
  left: 44%;
  top: 11rem;
  color: white;
  font-size: 2.7rem;
  font-weight: bold;
}

.postupdatebg_title_mobile{
  position: absolute;
  left: 25%;
  top: 11rem;
  color: white;
  font-size: 2.7rem;
  font-weight: bold;
}
</style>