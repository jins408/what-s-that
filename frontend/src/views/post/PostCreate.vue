<template>
  <div class="container">
    <v-card style="margin-top:5rem;">
      <v-toolbar flat color="primary" dark>
        <v-toolbar-title>문화재 등록</v-toolbar-title>
      </v-toolbar>
      <v-tabs vertical>
        <v-tab class="d-flex justify-content-start">
            <i class="fas fa-user mr-2" style="font-size:1.5rem;"></i>
            <span>
            문화재명
            </span>
        </v-tab>
        <v-tab class="d-flex justify-content-start">
         <i class="fas fa-image mr-2" style="font-size:1.5rem;"></i> 
          이미지 등록
        </v-tab>
        <v-tab class="d-flex justify-content-start">
          <i class="fas fa-file-alt mr-2" style="font-size:1.5rem;"></i>
          내용
        </v-tab>

        <v-tab-item>
          <v-card flat>
            <v-card-text>
              <v-textarea
          rows="1"
          auto-grow
          name="title"
          label="문화재명"
          v-model="culturename"
        ></v-textarea>
            </v-card-text>
          </v-card>
        </v-tab-item>
        <v-tab-item>
          <v-card flat>
            <v-card-text>
        <div class="d-flex justify-content-center">
          <input ref="imageInput" type="file" hidden @change="onChangeImages" />
          <img
            v-if="!imageUrl"
            style="height: 15rem; width: 15rem"
            src="../../assets/bgbg.jpg"
            alt=""
          />
          <img
            v-if="imageUrl"
            style="height: 15rem; width: 15rem"
            :src="imageUrl"
            alt=""
          />
        </div>
        <v-row class="d-flex justify-content-center mt-5">
          <v-btn accept="image/*" @click="onClickImageUpload"
            >이미지 업로드</v-btn
          >
        </v-row>
            </v-card-text>
          </v-card>
        </v-tab-item>
        <v-tab-item>
          <v-card flat>
            <v-card-text>
              <v-textarea outlined rows="10" name="content" label="내용" v-model="content"></v-textarea>
              <div class="d-flex justify-content-end">
                <v-btn @click="regist">등록하기</v-btn>
              </div>
            </v-card-text>
          </v-card>
        </v-tab-item>
      </v-tabs>
    </v-card>
  </div>
</template>

<script>
import axios from "axios";
import Swal from "sweetalert2";

const baseURL = "http://localhost:8080";

export default {
  data() {
    return {
        culturename:"",
        content:"",
        imageUrl: "",
    };
  },
  methods: {
      regist(){
          Swal.fire({
            text: "등록하시겠습니까?",
            icon: 'question',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            cancelButtonText: '취소',
            confirmButtonText: '등록'
            }).then((result) => {
                if (result.isConfirmed) {
                let formData = new FormData();
                let culturename = this.culturename;
                let content = this.content;
                const file = this.$refs.imageInput.files[0];
                console.log(file);
                formData.append("profile", file);
                formData.append("culturename", culturename);
                formData.append("content", content);
                for (var pair of formData.entries()) {
                    console.log(pair[0] + ", " + pair[1]);
                }
                axios
                .post(`${baseURL}/dictionary/culture?content=`+this.content+`&culturename=`+this.culturename, formData)
                .then(()=>{
                    
                        Swal.fire({
                            text: "등록되었습니다",
                            icon: 'success',
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
</style>