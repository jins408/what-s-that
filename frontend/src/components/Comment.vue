<template>
   <div style="width: 90%; margin: auto; ">
     <div class=" d-flex justify-left mt-5">
          <v-textarea
            label="Comment"
            auto-grow
            outlined
            rows="1"
            row-height="15"
            v-model="comment"
          ></v-textarea>
          <v-btn
            class="ma-2 mt-0"
            outlined
            medium
            fab
            color="indigo"
            style="border:none;"
            @click="createComment"
          >
            <v-icon medium>mdi-comment</v-icon>
          </v-btn>
        </div>
    </div>
</template>

<script>
import axios from "axios";
import { mapState } from "vuex";

const baseURL = "http://localhost:8080";

export default {
    computed: {
        ...mapState({
            userno: state => state.user.userno,
            isLoggedIn: state => state.user.isLoggedIn,
        })
    },
    methods: {
        createComment(){
            let isLoggedIn = this.$store.state.user.isLoggedIn || "";
            if(!isLoggedIn){
                alert("로그인 후 입력가능 합니다")
            }else{
                var flag = 0;
                if(this.comment == ""){
                    alert("댓글을 입력해주세요")
                    flag = 1;
                } 
            }
            if(flag==0){
                axios.post(`${baseURL}/dictionary/comment`, this.comment,{
                    headers: {
                        Authorization: this.$store.state.auth.token
                    }
                })
                .then((response) =>{
                    console.log(response.data)
                    alert("댓글이 등록되었습니다.")
                })
                .catch((error) =>{
                    console.log(error)
                    alert("댓글등록 실패")
                })
            }
        }
    },

    data: () => {
        return { 
            comment: {
                reply:"",
                userno:this.$store.state.user.userno,
                postno:1
            },
            comments: [],
        };
  },

}
</script>

<style>

</style>