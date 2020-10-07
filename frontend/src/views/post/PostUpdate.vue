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
        <v-text-field
          label="name"
          name="name"
          v-model="name"
          prepend-icon="mdi-label"
          type="text"
          class="mx-10 mb-5"
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
            class="mb-5"
          ></v-file-input>
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
import axios from "axios";
import Swal from "sweetalert2";

export default {
    created(){
        this.postno = this.$route.params.ID;
    },
  data() {
    return {
        culturename:"",
        content:"",
        imageUrl: "",
        postno:"",
    };
  },
  methods: {
      modify(){
          Swal.fire({
            text: "수정하시겠습니까?",
            icon: 'question',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            cancelButtonText: '취소',
            confirmButtonText: '수정'
            }).then((result) => {
                if (result.isConfirmed) {
                let formData = new FormData();
                let culturename = this.culturename;
                let content = this.content;
                // let postno = this.postno
                const file = this.$refs.imageInput.files[0];
                console.log(file);
                formData.append("image", file);
                formData.append("culturename", culturename);
                formData.append("content", content);
                // formData.append("postno", postno);
                for (var pair of formData.entries()) {
                    console.log(pair[0] + ", " + pair[1]);
                }
                axios
                .put(this.$baseurl + `/culture/admin/${this.postno}`, formData,{
                  headers: {
                    Authorization: this.$store.state.user.token,
                  },
                })
                .then(()=>{
                        Swal.fire({
                            text: "수정되었습니다",
                            icon: 'success',
                        })
                        this.$router.push({
                           name: "PostDetail",
                           params: { ID: this.postno },
                        })
                })
                .catch((err)=>{
                    console.log(err)
                })
                    }
            })
      },
    onClickImageUpload() {
      this.$refs.imageInput.click();
    },
    onChangeImages(e) {
      console.log(e.target.files);
      const file = e.target.files[0]; // Get first index in files
      this.imageUrl = URL.createObjectURL(file); // Create File URL
    },
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