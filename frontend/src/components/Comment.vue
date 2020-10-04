<template>
   <div style="width: 90%; margin: auto; ">
     <div class=" d-flex justify-left mt-5">
          <v-textarea
            label="Comment"
            auto-grow
            outlined
            rows="1"
            row-height="15"
            v-model="commentData.reply"
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

        <v-card 
          v-for="comment in comments"
          :key="comment.regno"
          class="pt-3"
          max-width="100%"
          min-height="3rem"
          outlined
          style="border-top:none; border-right:none; border-left:none;"
        >
          <div class="px-5" style="line-height:3rem;">
            <span>{{comment.username}} : {{comment.reply}}</span>
            <v-btn
              class="float-right px-0"
              text
              small
              color="red accent-4"
              v-if="comment.userno == commentData.userno"
              @click="commentdelete(comment.regno)"
              
            > <v-icon
                >mdi-close-circle-outline</v-icon></v-btn>
            <v-btn
              class="float-right px-0"
              text
              small
              color="deep-purple accent-4"
              v-if="comment.userno == commentData.userno"
              @click="changeupdate(comment.regno)"
            ><v-icon >mdi-pencil-circle-outline</v-icon></v-btn>
            
            <div class="d-flex justify-left" v-if="isupdate">
              <v-textarea
                v-if="comment.regno == cid"
                auto-grow
                outlined
                rows="1"
                row-height="15"
                v-model="updatecomment.reply"
              ></v-textarea>
              <v-btn
                v-if="comment.regno == cid"
                class="mt-2"
                text
                color="success accent-4"
                @click="commentupdate(comment.regno )"
              >수정완료</v-btn>
            </div>
          </div>
        </v-card>
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
    created(){
        console.log(this.$store.state.user.userno)
        // this.commentData.userno=this.$store.state.user.userno
        console.log(this.commentData.userno)
        this.updatecomment.userno=this.$store.state.user.userno
        this.commentList()
        
    },
    methods: {
        createComment(){
            let isLoggedIn = this.$store.state.user.isLoggedIn || "";
            if(!isLoggedIn){
                alert("로그인 후 입력가능 합니다")
            }else{
                var flag = 0;
                if(this.commentData.reply == ""){
                    alert("댓글을 입력해주세요")
                    flag = 1;
                
                }
            }
            if(flag==0){
                axios.post(`${baseURL}/dictionary/comment?postno=`+this.commentData.postno+'&reply='+this.commentData.reply+'&userno='+this.commentData.userno)
                .then((response) =>{
                    console.log(response.data)
                    alert("댓글이 등록되었습니다.")
                    location.reload();
                })
                .catch((error) =>{
                    console.log(error)
                    alert("댓글등록 실패")
                })
            }
        },
        commentList(){
            axios.get(`${baseURL}/dictionary/comment/${this.commentData.postno}`)
            .then((response) =>{
                console.log(response.data.object)
                this.comments = response.data.object
            })
            .catch((error) =>{
                console.log(error)
            })
        },
        commentdelete(regno){
            axios.delete(`${baseURL}/dictionary/comment/${regno}`)
            .then(() =>{
               alert("댓글이 삭제되었습니다")
               location.reload();
            })
            .catch((error) =>{
                console.log(error)
            })
        },
        commentupdate(){
            axios.put(`${baseURL}/dictionary/comment`, this.updatecomment)
            .then(() =>{
                alert("댓글 수정완료!")
                location.reload();
            })
            .catch((error) =>{
                console.log(error)
                alert(error)
            })
        },
        changeupdate(regno){
            this.updatecomment.commentno = regno;
            this.isupdate = !this.isupdate;
            this.cid = regno;
            console.log(this.cid);
        }
    },

    props:{
        commentData : Object
    },

    data: () => {
        return { 
            isupdate: false,
            cid: "",
            comments: [],
            updatecomment: {  
                commentno: "",
                postno: 1,
                reply: "",
                userno: "",
            }
        };
  },

}
</script>

<style>

</style>
