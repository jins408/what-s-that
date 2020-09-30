<template>
  <div style="margin: 3rem 0">
    <v-card class="mx-auto" outlined width="90%" style="margin-bottom: 2rem">
      <div class="d-flex justify-content-between">
        <v-card-title>MyPage</v-card-title>
        <div class="d-flex justify-end">
          <v-card-actions>
            <v-btn @click="gomodify()" v-if="!modifycheck">회원정보 수정</v-btn>
            <v-btn @click="signout()" v-if="modifycheck">회원탈퇴</v-btn>
          </v-card-actions>
        </div>
      </div>
      <!-- <div class="overline mb-4">OVERLINE</div> -->

      <v-list-item three-line>
        <img
          v-if="!this.getuser.profileurl"
          style="width: 10rem; hegith: 11rem"
          src="../../assets/bgbg.jpg"
          alt=""
        />
        <img
          v-if="this.getuser.profileurl"
          style="width: 10rem; hegith: 11rem"
          :src="getuser.profileurl"
          alt="안나옴"
        />
        <v-list-item-content>
          <v-row>
            <v-col cols="12" sm="5" class="ml-5">
              <v-list-item-title class="headline mb-4"
                >{{ this.getuser.username }} 님</v-list-item-title
              >
              <v-list-item-title class="mb-1">{{
                this.getuser.email
              }}</v-list-item-title>
              <v-list-item-subtitle style="margin-bottom: 2rem">{{
                this.getuser.introduce
              }}</v-list-item-subtitle>
            </v-col>
            <v-col cols="12" sm="1"></v-col>
            <v-col cols="12" sm="5">
              <v-list-item-subtitle class="mb-3"
                ><v-icon>mdi-bookmark</v-icon> 찜한 수
              </v-list-item-subtitle>
              <v-list-item-subtitle>
                <v-icon>mdi-pencil</v-icon> 카테고리 수</v-list-item-subtitle
              >
            </v-col>
          </v-row>
        </v-list-item-content>
      </v-list-item>
    </v-card>

    <v-card class="mx-auto" width="90%">
      <v-tabs
        background-color="white"
        color="deep-purple accent-4"
        left
        v-if="!modifycheck"
      >
        <v-tab>찜목록</v-tab>
        <v-tab>카테고리</v-tab>

        <v-tab>내정보</v-tab>

        <v-tab-item v-for="n in 3" :key="n">
          <category v-if="n == 2" />
          <v-container fluid>
            <v-row>
              <v-col v-for="i in 6" :key="i" cols="12" md="4">
                <!-- <v-img
                :src="`https://picsum.photos/500/300?image=${i * n * 5 + 10}`"
                :lazy-src="`https://picsum.photos/10/6?image=${i * n * 5 + 10}`"
                aspect-ratio="1"
              ></v-img> -->
              </v-col>
            </v-row>
          </v-container>
        </v-tab-item>
      </v-tabs>
      <v-container v-if="modifycheck">
        <v-row rows="12">
          <v-col cols="12" sm="4">
            <div class="d-flex justify-content-center">
               <input ref="imageInput" type="file" hidden @change="onChangeImages">
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
                <v-btn accept="image/*"
                  @click="onClickImageUpload">이미지 업로드</v-btn>
                <!-- <v-file-input
                  accept="image/*"
                  label="프로필"
                  show-size
                  v-model="file"
                  @click="onClickImageUpload"
                ></v-file-input> -->
            </v-row>
          </v-col>
          <v-col cols="12" sm="6">
            <v-text-field
              v-model="info.username"
              label="이름"
              clearable
            ></v-text-field>
            <v-textarea
              rows="2"
              v-model="info.introduce"
              clearable
              auto-grow
              label="자기소개"
              value="자기소개해주세요"
              counter
              maxlength="200"
              :rules="rules"
            ></v-textarea>
            <!-- <v-text-field v-model="introduce" label="소개" clearable counter maxlength="200" :rules="rules"></v-text-field> -->
            <v-text-field
              v-model="info.password"
              label="비밀번호"
              clearable
            ></v-text-field>
            <v-text-field
              v-model="info.passwordconfirm"
              label="비밀번호 확인"
              clearable
            ></v-text-field>
          </v-col>
        </v-row>
        <div class="d-flex justify-content-end" style="width: 85%">
          <v-btn @click="gomodify()" v-if="modifycheck" class="mr-2"
            >취소</v-btn
          >
          <v-btn @click="completemodify()" v-if="modifycheck">수정 완료</v-btn>
        </div>
      </v-container>
    </v-card>
  </div>
</template>

<script>
import axios from "axios";
import Swal from "sweetalert2";

import store from "../../store";
import category from "../../components/Category.vue";

const baseURL = "http://localhost:8080";

export default {
  components: {
    category,
  },
  data() {
    return {
      rules: [(v) => v.length <= 200 || "Max 200 characters"],
      info: {
        username: "",
        introduce: "",
        password: "",
        passwordconfirm: "",
      },
      getuser: {
        email: "",
        username: "",
        introduce: "",
        profileurl: "",
      },
      file: null,
      modifycheck: false,
      imageUrl: null,
    };
  },
  created() {
    this.getinfo();
  },
  methods: {
    onClickImageUpload() {
      this.$refs.imageInput.click();
    },
    onChangeImages(e) {
      console.log(e.target.files);
      const file = e.target.files[0]; // Get first index in files
      this.imageUrl = URL.createObjectURL(file); // Create File URL
    },
    getinfo() {
      axios
        .get(`${baseURL}/dictionary/account/userinfo`, {
          headers: {
            Authorization: this.$store.state.auth.token,
          },
        })
        .then((res) => {
          this.getuser = res.data.object;
          this.imageUrl = res.data.object.profileurl;
          // console.log(this.getuser)
        })
        .catch((err) => {
          console.log(err);
        });
    },
    signout() {
      Swal.fire({
        width: 350,
        text: "탈퇴하시겠습니까?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "red",
        cancelButtonColor: "gray",
        confirmButtonText:
          '<a style="font-size:1rem; color:white">회원탈퇴</a>',
        cancelButtonText: '<a style="font-size:1rem; color:white">취소</a>',
      }).then((result) => {
        if (result.isConfirmed) {
          Swal.fire({
            width: 350,
            text: "회원탈퇴 되었습니다.",
            icon: "success",
          });
          axios
            .delete(`${baseURL}/dictionary/account/signout`, {
              headers: {
                Authorization: this.$store.state.auth.token,
              },
            })
            .then(() => {
              store.dispatch("AUTH_LOGOUT").then(() => {
                this.$router.push("/");
              });
            })
            .catch((err) => {
              console.log(err);
            });
        }
      });
    },
    gomodify() {
      this.modifycheck = !this.modifycheck;
      this.info.username = "";
      this.info.introduce = "";
      this.info.password = "";
      this.info.passwordconfirm = "";
    },
    completemodify() {
      let formData = new FormData();
      let introduce = this.info.introduce;
      let password = this.info.introduce;
      let username = this.info.username;
      const file = this.$refs.imageInput.files[0];
      console.log(file);
      formData.append("profile", file);
      formData.append("introduce", introduce);
      formData.append("password", password);
      formData.append("username", username);
      for (var pair of formData.entries()) {
        console.log(pair[0] + ", " + pair[1]);
      }

      axios
        .put(`${baseURL}/dictionary/account/modify`, formData, {
          headers: {
            Authorization: this.$store.state.auth.token,
          },
          contentType: "multipart/form-data",
        })
        .then(() => {
          // console.log(res.data)
          alert("수정완료!");
          this.$router.go();
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};
</script>

<style>
</style>