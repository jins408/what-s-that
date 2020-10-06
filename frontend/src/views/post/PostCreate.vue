<template>
 <div class="mt-16">
    <v-card>
      <v-text-field
        label="name"
        name="name"
        v-model="name"
        prepend-icon="mdi-label"
        type="text"
        class="mx-10"
        id="name"
      ></v-text-field>

      <div class="mx-10">
        <v-file-input
          v-model="thumbnail"
          ref="Thumbnail"
          id="Thumbnail"
          accept="image/png, image/jpeg, image/bmp"
          placeholder="Pick an Thumbnail"
          prepend-icon="mdi-camera"
          label="Thumbnail"
        ></v-file-input>
      </div>



      <editor height="1000px" id="editor" ref="toastuiEditor" > </editor> 
      
      <div id="editor"></div>

      <div style="text-align:right">
      
        <v-btn class="ma-2" color="rgb(240, 162, 46)" @click="registpost()">
          등록하기
        </v-btn>
      </div>
    </v-card>
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
            this.$router.go(-1);
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
      let thumbnail = this.thumbnail;
      let content = this.$refs.toastuiEditor.invoke("getMarkdown"); // content를 저장하는 액션 처리 }
      if (name == "") {
        alert("제목을 입력해주세요");
        document.getElementById("name").focus();
        return false;
      }
      if (content == "") {
        alert("내용을 입력해주세요");
        document.getElementById("editor").focus();
        return false;
      }
      if (thumbnail == null) {
        alert("썸네일을 등록해주세요");
        return false;
      }

      return true;
    }
  }
};
</script>

<style scoped>
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
   margin:  0 auto;
    
}
</style>