<template>
  <div>
         <v-row justify="center">
    <v-dialog
      v-model="dialog"
      persistent
      max-width="1100"
    >
      <v-card id="card"> 
        <v-card-title class="headline">
          사용가이드
        </v-card-title>
        <v-card-text><v-img src="../../assets/guideline.png"></v-img></v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
         
          <v-btn
            color="green darken-1"
            text
            @click="dialog = false"
          >
            닫기
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-row>
    <div>
      <v-carousel
        hide-delimiters
        cycle
        height="755"
        hide-delimiter-background
        show-arrows-on-hover
      >
        <v-carousel-item v-for="(item, i) in items" :key="i" :src="item.src">
          <v-row class="fill-height" align="center" justify="center"> </v-row>
        </v-carousel-item>
      </v-carousel>
      <div class="display-3 what" style="width: 100%">
        <p class="text-center" style="margin-top: 14rem">
          <span
            style="
              font-size: 5rem;
              font-weight: bold;
              color: red;
              text-shadow: 2px 2px 2px gray;
              font-family: '국립박물관문화재단클래식B';
            "
            >What</span
          >
          <span
            style="
              font-size: 3rem;
              text-shadow: 2px 2px 2px gray;
              font-family: '국립박물관문화재단클래식B';
            "
            >'s that!?</span
          >
        </p>
        <v-col cols="12" sm="6" class="mx-auto">
          <v-row>
            <v-text-field
              append-icon="fas fa-camera"
              prepend-icon="fas fa-search"
              label="검색해주세요!"
              v-model="culturename"
              filled
              rounded
              @click:append="onClickImageUpload"
              @keypress.enter="search"
              background-color="white"
              style="font-family: '국립박물관문화재단클래식B'"
            ></v-text-field>
            <input
              ref="imageInput"
              type="file"
              hidden
              @change="onChangeImages"
            />
          </v-row>
        </v-col>
        <div class="d-flex justify-content-center" style="margin-top: 16rem">
          <button class="btndown" @click="gosearchview">
            <i
              class="fas fa-angle-down"
              style="font-size: 2rem; color: white"
            ></i>
          </button>
        </div>
      </div>
    </div>

    <div class="container mt-2" data-offset="0">
      <searchview :culture="culture" id="searchview" />
    </div>
  </div>
</template>

<script>
import axios from "axios";
import Swal from "sweetalert2";
import searchview from "../../components/Searchview.vue";

export default {
  components: {
    searchview,
  },
  created() {
    window.addEventListener("scroll", this.updatescroll);
    this.getdetail();
  },
  methods: {
    getdetail() {
      axios
        .get(this.$baseurl + `/culture/search?culturename=${this.culturename}`)
        .then((res) => {
          this.culture = res.data.object;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    onClickImageUpload() {
      this.$refs.imageInput.click();
    },
    onChangeImages(e) {
      const file = e.target.files[0]; // Get first index in files
      this.imageUrl = URL.createObjectURL(file); // Create File URL
      let formData = new FormData();
      formData.append("image", file);
      Swal.fire({
        title: '사진 인식 중...'
      })
      Swal.showLoading()
      axios
        .post(this.$baseurl + "/culture/image", formData)
        .then((res) => {
          Swal.close()
          scroll(0, 0);
          this.$router.push({
            name: "PostDetail",
            params: { ID: res.data.object },
          });
        })
        .catch(() => {
          Swal.close();
          Swal.fire({
            icon: "error",
            title: '인식 실패'
          });

        });
    },
    search() {
      axios
        .get(this.$baseurl + `/culture/search?culturename=${this.culturename}`)
        .then((res) => {
          this.culture = res.data.object;
          setTimeout(() => {
            this.gosearchview();
          }, 50);
          this.culturename = "";
        })
        .catch((err) => {
          console.log(err);
        });
    },

    updatescroll() {
      this.scrollposition = window.scrollY;
    },
    gosearchview() {
      var ll = document.querySelector("#searchview").offsetTop;
      window.scrollTo({ top: ll, behavior: "smooth" });
    },
  },
  data() {
    return {
      dialog: true,
      imageUrl: "",
      culturename: "",
      scrollposition: 0,
      culture: [],
      items: [
        {
          src: require("../../assets/mainbg1.jpg"),
        },
        {
          src: require("../../assets/mainbg2.jpg"),
        },
        {
          src: require("../../assets/mainbg3.jpg"),
        },
      ],
    };
  },
};
</script>

<style scoped>
.userbtn {
  padding: 0 10px !important;
}

.userbtn:focus {
  outline: none;
}

.userlist:hover {
  background: rgba(0, 0, 0, 0.2);
  cursor: pointer;
}

.btndown:focus {
  outline: none;
}

.btndown:hover {
  color: red;
}

.what {
  position: absolute;
  top: 10px;
}

a {
  text-decoration: none;
  color: red !important;
}
</style>