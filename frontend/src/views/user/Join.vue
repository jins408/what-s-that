<template>
    <div class="my-auto">
        <v-container >
            <v-form>
                <p class="text-center" style="font-size:1.3rem; font-weight:bold;">간편 회원가입</p>
                <v-row>
                    <v-col cols="3"></v-col>
                    <v-col cols="6">
                        <v-row>
                            <div style="width:46%">
                                <v-text-field
                                    label="E-mail"
                                    required
                                    v-model="joindata.email"
                                    ></v-text-field>
                            </div>
                                    <v-btn class="mt-5 ml-2" style="padding:0 0.5rem;" small outlined color="indigo" @click="checkemail">중복 확인</v-btn>

                            <div style="width:25%">
                                <v-text-field
                                    class="ml-2"
                                    label="인증번호"
                                    v-model="authnum"
                                    required
                                    ></v-text-field>
                                
                            </div>
                                    <v-btn sm="2" class="mt-5 ml-2" style="padding:0 0.5rem;" small outlined color="indigo" @click="checkauthnum">인증 확인</v-btn>
                        </v-row>
                        
                        <v-row>
                            <v-text-field
                                label="Password"
                                v-model="joindata.password"
                                :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                                :type="show1 ? 'text' : 'password'"
                                @click:append="show1 = !show1"
                                required
                                ></v-text-field>
                        </v-row>
                        
                            <v-form ref="form">
                                <v-row>

                                <v-text-field
                                    v-model="joindata.passwordconfirm"
                                    :rules="rules"
                                    label="Password Confirm"
                                    :append-icon="show2 ? 'mdi-eye' : 'mdi-eye-off'"
                                    :type="show2 ? 'text' : 'password'"
                                    @click:append="show2 = !show2"
                                ></v-text-field>
                                </v-row>
                            </v-form>
                <v-row class="mt-5">
                    <v-row rows="9"></v-row>
                    <v-btn outlined color="indigo" @click="join">가입하기</v-btn>
                </v-row>
                    </v-col>
                </v-row>
            </v-form>
        </v-container>
    </div>
</template>

<script>
import axios from "axios";

const baseURL = "http://localhost:8080";

export default {
    methods: {
      checkemail(){
        axios
        .get(`${baseURL}/dictionary/user/emailoverlap/${this.joindata.email}`)
        .then(()=>{
          alert(`${this.joindata.email} 확인되었습니다.`)
        })
        .catch((err)=>{
          alert(`${this.joindata.email}이미 사용중인 이메일입니다.`)
          console.log(err)
        })
      },
      checkauthnum(){
        axios
        .get(`${baseURL}/dictionary/user/emailcode/${this.joindata.email}/${this.authnum}`)
        .then(()=>{
          alert(`${this.authnum} 확인되었습니다.`)
        })
        .catch((err)=>{
          alert('정확히 입력해주세요.')
          console.log(err)
        })
      },
      join(){
        axios
        .post(`${baseURL}/dictionary/user/signup`, this.joindata)
        .then(()=>{
          alert('회원가입 성공!')
          this.$router.push('/')
        })
        .catch((err)=>{
          console.log(err)
        })
      }
    },
    data () {
      return {
        show1: false,
        show2: false,
        joindata:{
          email:"",
          password:"",
          passwordconfirm:"",
        },
        authnum:"",
        allowSpaces: false,
      }
    },
    computed: {
      rules () {
        const rules = []

        if (!this.allowSpaces) {
          const rule =
            v => (v || '').indexOf(' ') < 0 ||
              '빈 칸을 넣을 수 없습니다.'

          rules.push(rule)
        }

        if (this.joindata.password) {
          const rule =
            v => (!!v && v) === this.joindata.password ||
              'Password가 일치하지 않습니다.'

          rules.push(rule)
        }

        return rules
      },
    },
  }
</script>

<style>
</style>