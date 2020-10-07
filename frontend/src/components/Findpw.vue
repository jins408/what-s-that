<template>
  <v-dialog  max-width="300px" v-model="show" >
    <v-card>
    <v-card-title>비밀번호 찾기</v-card-title>
    <v-card-text class="pb-0">
        <v-text-field label="Email*" required v-model="email"></v-text-field>
    </v-card-text>
    <v-divider></v-divider>
    <v-card-actions class="d-flex justify-end">
        <v-btn depressed color="primary" @click="findPW">완료</v-btn>
        <v-btn depressed color="error" @click.stop="show=false">취소</v-btn>
    </v-card-actions>
    </v-card>
    </v-dialog>
</template>

<script>
import axios from 'axios'

export default {
    computed: {
        show: {
            get () {
                return this.value
            },
            set (value) {
                this.$emit('input', value)
            }
        }
    },
    props: {
        value: Boolean
    },
    methods:{
        findPW(){
            axios.get(this.$baseurl + `/user/findpwd/${this.email}/`)
            .then(() =>{
                alert("이메일로 임시 비밀번호가 발송 되었습니다")
                this.show = false
            })
            .catch(() =>{
                alert("비밀번호 찾기 실패")
            })
        }
    },
    data: () =>{
        return{
            dialog: false,
            email: "",

        }
    }
}

</script>