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
        <v-carousel-item
          v-for="(item, i) in items"
          :key="i"
          :src="item.src"
        >
            <v-row
              class="fill-height"
              align="center"
              justify="center"
            >
            </v-row>
        </v-carousel-item>
      </v-carousel>
            <div class="display-3 what" style="width:100%;">
              <p class="text-center" style="margin-top:14rem;">
                <span style="font-size:5rem; font-weight:bold; color:red; text-shadow: 2px 2px 2px gray;">What</span>
                <span style="font-size:3rem; text-shadow: 2px 2px 2px gray;">'s that!?</span>
              </p>
              <v-col cols="12" sm="6" class="mx-auto">
                <v-row>
                  <v-text-field prepend-icon="fas fa-search" label="검색해주세요!" v-model="culturename" filled rounded @keypress.enter="search" background-color="white" ></v-text-field>
                  <!-- <i class="fas fa-microphone ml-2 mt-2" style="font-size:1.6rem;"></i> -->
                </v-row>
              </v-col>
              <div class="d-flex justify-content-center" style="margin-top:16rem;">
                <!-- <button class="btndown" @click="scroll">
                  <i class="fas fa-angle-down" style="font-size:2rem;"></i>
                </button> -->
              </div>
            </div>
    </div>

    <div v-if="this.searchok == true" class="container mt-2" data-offset="0">
      <searchview  :culture="culture" id="searchview" />
    </div>
  </div>
</template>

<script>
import axios from "axios";
import searchview from "../../components/Searchview.vue";

export default {
  components:{
    searchview,
  },
  created() {
    window.addEventListener("scroll", this.updatescroll);
  },
  methods: {
    search(){
      if(this.culturename == ""){
        this.searchok = false
      }else{
        axios.get(this.$baseurl + `/culture/search?culturename=${this.culturename}`)
        .then((res)=>{
          // if(this.searchok == false){
          //   this.scroll();
          //   }else{
          //   this.gosearchview();
          // }
          this.searchok = true;
          this.culture = res.data.object
          // console.log(this.culture)
          setTimeout(() => {
            this.gosearchview()
          }, 100);
        })
        .catch((err)=>{
          console.log(err)
        })
      }
    },
    updatescroll() {
      this.scrollposition = window.scrollY;
      // console.log(this.scrollposition)
    },
    gosearchview(){
      var ll = document.querySelector("#searchview").offsetTop;
      window.scrollTo({top:ll, behavior:'smooth'});
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
      culturename :'',
      scrollposition: 0,
      culture:[],
      searchok:false,
        items: [
          {
            src:  require('../../assets/mainbg1.jpg'),
          },
          {
            src:  require('../../assets/mainbg2.jpg'),
          },
          {
            src:  require('../../assets/mainbg3.jpg'),
          },

        ],
    };
  },
};
</script>

<style scoped>
.userbtn{
  padding: 0 10px !important;
}

.userbtn:focus{
  outline: none;
}

.userlist:hover{
  background: rgba(0,0,0,0.2);
  cursor: pointer;
}

.btndown:focus {
  outline: none;
}

.btndown:hover {
  color: red;
}

.what{
  position: absolute;
  top: 10px;
}

a {
  text-decoration: none;
  color: red !important;
}

</style>