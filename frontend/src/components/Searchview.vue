<template>
  <div class="container" style="min-height: 45rem">
    <div>
      <p class="text-center" style="margin: 3rem 0">
        <span
          style="
            font-size: 5rem;
            font-weight: bold;
            color: red;
            text-shadow: 2px 2px 2px gray;
          "
          >What</span
        >
        <span style="font-size: 3rem; text-shadow: 2px 2px 2px gray"
          >'s that!?</span
        >
      </p>
      <v-row v-if="culture.length != 0" >
        <v-card
          class="mx-5 my-3"
          max-width="250"
          v-for="(item, index) in culture"
          :key="index"
          style="cursor: pointer"
        >
          <v-img
            class="white--text align-end"
            height="200px"
            :src= item.imageUrl
            @click="gopostdetail(item)"
          >
            <v-card-title>{{ item.culturename }}</v-card-title>
          </v-img>
          <v-card-actions class="d-flex justify-content-center">
            <v-btn
              color="red"
              style="font-weight: bold; width: 50%"
              text
              @click="gopostdetail(item)"
            >
              상세보기
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-row>
      <v-row class="d-flex justify-content-center mb-5" v-if="culture.length == 0">
        <p style="font-size:1.5rem; font-weight:bold;">등록된 결과물이 없습니다.</p>
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
    gopostdetail(item) {
      axios
        .get(this.$baseurl + `/culture/find?culturename=${item.culturename}`)
        .then((res) => {
          this.post = res.data.object[0];
          // console.log(this.post);
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
</style>