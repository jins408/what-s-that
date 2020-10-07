<template>
  <div class="container" style="min-height: 45rem">
    <div>
      <p class="text-center" style="margin-top: 2rem">
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
        <span style="font-size: 3rem; text-shadow: 2px 2px 2px gray; font-family: '국립박물관문화재단클래식B';"
          >'s that!?</span
        >
      </p>
        <p class="text-right mr-2" style="font-size: 1rem; text-shadow: 2px 2px 2px gray; cursor:pointer; font-family: '국립박물관문화재단클래식B';" @click="list()">전체 리스트보기</p>
      <v-row>
        <v-card
          class="mx-5 my-3 postcard"
          max-width="250"
          v-for="(item, index) in culture"
          :key="index"
          style="cursor: pointer"
          @click="gopostdetail(item.culturename)"
        >
          <v-img
            class="white--text align-end cultureimg"
            height="200px"
            :src="item.imageUrl"
          >
          </v-img>
          <v-card-actions class="d-flex justify-content-center">
            <p
              class="text-center my-1"
              color="black"
              style="font-weight:bold; font-size:1.1rem; width: 100%"
              text
            >
              {{ item.culturename }}
            </p>
          </v-card-actions>
          <p class="show"><i class="fas fa-map-marker-alt mr-2"></i>상세보기</p>
        </v-card>
      </v-row>

      <!-- <span style="font-size:2rem; color:gray;">{{index+1}}. </span>
                <span style="font-size:2rem; color:gray; margin-left:3rem;" >{{item}}</span> -->
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  props: {
    culture: Array,
  },
  data() {
    return {
      post: [],
    };
  },
  methods: {
    list(){
      axios.get(this.$baseurl + `/culture/search?culturename=${""}`)
        .then((res)=>{
          this.culture = res.data.object
        })
        .catch((err)=>{
          console.log(err)
        })
    },
    gopostdetail(culturename) {
      axios
        .get(this.$baseurl + `/culture/find?culturename=${culturename}`)
        .then((res) => {
          this.post = res.data.object[0];
          // console.log(this.post);
          scroll(0, 0);
          this.$router.push({
            name: "PostDetail",
            params: { ID: this.post.postno },
          });
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};
</script>

<style>
.postcard:hover .cultureimg{
  filter: brightness(40%);
  transition: all 0.4s  0s
}

.postcard:hover p{
  visibility: visible;
}

.show{
  font-size:1.5rem;
  color:white;
  position: absolute;
  top:36%;
  left:25%;
  visibility: hidden;
}
</style>