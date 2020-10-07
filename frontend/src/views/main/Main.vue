<template>
  <div>
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
            <!-- <i
              class="fas fa-camera ml-2 mt-4"
              style="font-size: 1.6rem; cursor: pointer"
              accept="image/*"
              @click="onClickImageUpload"
            ></i> -->
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
      console.log(e.target.files);
      const file = e.target.files[0]; // Get first index in files
      this.imageUrl = URL.createObjectURL(file); // Create File URL
      let formData = new FormData();
      formData.append("image", file);
      for (var pair of formData.entries()) {
        console.log(pair[0] + ", " + pair[1]);
      }
      Swal.fire({
        title: '사진 인식 중...'
      })
      Swal.showLoading()
      axios
        .post(this.$baseurl + "/culture/image", formData)
        .then((res) => {
          console.dir(res.data.object);
          Swal.close()
          this.$router.push({
            name: "PostDetail",
            params: { ID: res.data.object },
          });
          
        })
        .catch((err) => {
          console.log(err);
          Swal.close();
          Swal.fire({
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
      // console.log(this.scrollposition)
    },
    gosearchview() {
      var ll = document.querySelector("#searchview").offsetTop;
      window.scrollTo({ top: ll, behavior: "smooth" });
    },
    // scroll() {
    //   var ll = document.querySelector("#heritage").offsetTop;
    //   window.scrollTo({top:ll-10, behavior:'smooth'});
    // },
    // scroll1() {
    //   var ll = document.querySelector("#location").offsetTop;
    //   window.scrollTo({top:ll-10, behavior:'smooth'});
    // },
    // scroll2() {
    //   var ll = document.querySelector("#comment").offsetTop;
    //   window.scrollTo({top:ll-10, behavior:'smooth'});
    // },
  },
  data() {
    return {
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