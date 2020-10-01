<template>
  <div class="container">
      <div>
          <p class="text-center" style="margin:3rem 0;">
                <span style="font-size:5rem; font-weight:bold; color:red; text-shadow: 2px 2px 2px gray;">What</span>
                <span style="font-size:3rem; text-shadow: 2px 2px 2px gray;">'s that!?</span>
              </p>
              <div class="d-flex justify-content-between">
                <v-card
                    class="mx-5"
                    max-width="300"
                    v-for="(item,index) in culture" :key="index" style="cursor:pointer;" @click="gopostdetail(item)"
                >
                    <v-img
                    class="white--text align-end"
                    height="200px"
                    src="https://cdn.vuetifyjs.com/images/cards/docks.jpg"
                    >
                    <v-card-title>{{item}}</v-card-title>
                    </v-img>
                </v-card>

              </div>
                <!-- <span style="font-size:2rem; color:gray;">{{index+1}}. </span>
                <span style="font-size:2rem; color:gray; margin-left:3rem;" >{{item}}</span> -->
      </div>
  </div>
</template>

<script>
import axios from "axios";

const baseURL = "http://localhost:8080";

export default {
    props:{
        culture: Array,
    },
    data () {
        return {
            post : [],
        }
    },
    methods:{
        gopostdetail(item){
            axios.get(`${baseURL}/dictionary/culture/${item}`)
            .then((res)=>{
            this.post = res.data.object
            console.log(this.post)
            this.$router.push({
                name: 'PostDetail',
                params: { ID: this.post.postno }
            })
            })
            .catch((err)=>{
            console.log(err)
            })
        }
    }

}
</script>

<style>

</style>