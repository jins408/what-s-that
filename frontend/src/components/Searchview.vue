<template>
  <div>
      <div>
          <a class="ml-5" style="font-size:1.5rem; margin:0 text-decoration: none; color: red;">검색 결과</a>
        <div v-for="(item,index) in culture" :key="index">
            <p style="margin-left:3rem;" @click="gopostdetail(item)">{{item}}</p>
        </div>
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