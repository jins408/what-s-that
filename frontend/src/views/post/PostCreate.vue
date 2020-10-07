<template>
 <div>
   <div class="postcreatebg">
     <div class="darkcover-img-bg"></div>
     <!-- 웹버전 -->
    <div class="d-none d-sm-block">
    <div class=" postcreatebg_title_web" >문화재 등록</div>
    </div>
    <!-- 모바일버전 -->
    <div class="d-block d-sm-none d-md-none">
    <div class=" postcreatebg_title_mobile" >문화재 등록</div>
    </div>
   </div>

   <div class="container" style="margin-top:5rem; margin-bottom:5rem;">
    <v-card>
      <br>
      <div class="d-flex justify-content-between">
        <v-text-field
          label="이름"
          name="name"
          v-model="name"
          prepend-icon="mdi-label"
          type="text"
          class="mx-10 mb-5"
          id="name"
        ></v-text-field>

        <v-text-field
          label="English Name"
          name="ename"
          v-model="ename"
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
          v-model="category"
          prepend-icon="mdi-label"
          type="text"
          class="mx-10 mb-5"
          id="category"
        ></v-text-field>

        <v-text-field
          label="시대"
          name="generation"
          v-model="generation"
          prepend-icon="mdi-label"
          type="text"
          class="mx-10 mb-5"
          id="generation"
        ></v-text-field>
      </div>

      <v-text-field
        label="연도"
        name="constructionperiod"
        v-model="constructionperiod"
        prepend-icon="mdi-label"
        type="text"
        class="mx-10 mb-5"
        id="constructionperiod"
      ></v-text-field>

      <v-text-field
        label="위치"
        name="location"
        v-model="location"
        prepend-icon="mdi-label"
        type="text"
        class="mx-10 mb-5"
        id="location"
      ></v-text-field>

      <div class="d-flex justify-content-between">
        <v-text-field
          label="위도"
          name="lat"
          v-model="lat"
          prepend-icon="mdi-label"
          type="text"
          class="mx-10 mb-5"
          id="lat"
        ></v-text-field>
        <v-text-field
          label="경도"
          name="lng"
          v-model="lng"
          prepend-icon="mdi-label"
          type="text"
          class="mx-10 mb-5"
          id="lng"
        ></v-text-field>
      </div>


      <editor height="500px" id="editor" ref="toastuiEditor" > </editor> 
      
      <div id="editor"></div>

      <div style="text-align:right">
      
        <v-btn class="ma-2" color="rgb(240, 162, 46)" @click="registpost()">
          등록하기
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
  data: () => {
    return {
      dialog: false,
      name: "",
      ename:"",
      category:"",
      generation:"",
      constructionperiod:"",
      location:"",
      lng:"",
      lat:"",
      thumbnail: null,
      editorHtml : "",
      editorText : "",
      veditorText : "",
    };
  },
  components :{
    Editor,
  },


  methods: {
    registpost() {
      if (this.checkInput()) {
        const formData = new FormData();
        formData.append("image", this.thumbnail);
        formData.append("culturename", this.name.trim());
        formData.append("ename", this.ename.trim());
        formData.append("category", this.category.trim());
        formData.append("generation", this.generation.trim());
        formData.append("constructionperiod", this.constructionperiod.trim());
        formData.append("location", this.location.trim());
        formData.append("lng", this.lng.trim());
        formData.append("lat", this.lat.trim());
        formData.append(
          "content",
          this.$refs.toastuiEditor.invoke("getMarkdown")
        );
        // let content = this.$refs.toastuiEditor.invoke("getMarkdown"); // content를 저장하는 액션 처리
        axios({
          method: "POST",
          url: this.$baseurl + "/culture/admin",
          data: formData,
          headers: {
             Authorization: this.$store.state.user.token,
          }
        })
          .then(() => {
               Swal.fire({
                position: 'center',
                icon: 'success',
                title: '게시글이 등록되었습니다.',
                showConfirmButton: false,
                timer: 1500
                })
            scroll(0,0);
            this.$router.push('/main');
          })
          .catch(() => {
            Swal.fire({
                    icon: 'error',
                    title: '등록 실패',
                    text: '게시글 등록에 실패하였습니다.',
                });
          });
        this.dialog = false;
      }
    },
    checkInput() {
      let name = this.name.trim();
      let ename = this.ename.trim();
      let category = this.category.trim();
      let generation = this.generation.trim();
      let constructionperiod = this.constructionperiod.trim();
      let location = this.location.trim();
      let lng = this.lng.trim();
      let lat = this.lat.trim();
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
  }
};
</script>

<style scoped>
.postcreatebg{
  background: url('../../assets/create_bg.jpg') no-repeat;
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

.postcreatebg_title_web{
  position: absolute;
  left: 42%;
  top: 11rem;
  color: white;
  font-size: 2.7rem;
  font-weight: bold;
}

.postcreatebg_title_mobile{
  position: absolute;
  left: 20%;
  top: 11rem;
  color: white;
  font-size: 2.7rem;
  font-weight: bold;
}

#box {
  width: 90%;
  border: 1px solid #b0bec5;
  height: 100px;
  margin: 10px;
  padding: 10px;
  border-radius: 10px;
}
#name{
  position: relative;
  top:30px;
  text-align: center;
  font-size: 30px;
  font-weight: bold;
}
#titleback{
    width:300px;
    background-size: 300px;
    height: 100px;
    color :  rgb(115, 50, 20);
    opacity: 0.8;
   /* background-image:url('../../assets/back.png'); */
   margin:  0 auto;
    
}
</style>