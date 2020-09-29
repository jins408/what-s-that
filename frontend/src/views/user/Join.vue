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
                                    :disabled="emailcheck ? '' : emailcheck"
                                    ></v-text-field>
                            </div>
                                    <v-btn class="mt-5 ml-2" style="padding:0 0.5rem;" small outlined color="indigo" @click="checkemail" :disabled="emailcheck ? '' : emailcheck">중복 확인</v-btn>

                            <div style="width:25%">
                                <v-text-field
                                    class="ml-2"
                                    label="인증번호"
                                    v-model="authnum"
                                    required
                                    :disabled="authnumcheck ? '' : authnumcheck"
                                    ></v-text-field>
                                
                            </div>
                                    <v-btn sm="2" class="mt-5 ml-2" style="padding:0 0.5rem;" small outlined color="indigo" @click="checkauthnum" :disabled="authnumcheck ? '' : authnumcheck">인증 확인</v-btn>
                        </v-row>
                        
                        <v-row>
                            <v-text-field
                                label="Username"
                                v-model="joindata.username"
                                required
                                ></v-text-field>
                        </v-row>

                        <v-row>
                            <v-text-field
                                label="Password"
                                :rules="passwordrules"
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
                                    :rules="confirmrules"
                                    label="Password Confirm"
                                    :append-icon="show2 ? 'mdi-eye' : 'mdi-eye-off'"
                                    :type="show2 ? 'text' : 'password'"
                                    @click:append="show2 = !show2"
                                ></v-text-field>
                                </v-row>
                            </v-form>
                <v-row class="mt-5">
                    <v-row rows="9"></v-row>
                    <v-btn outlined color="indigo" @click="checkrules" v-if="!this.rulecheck" >비밀번호 확인</v-btn>
                    <v-btn outlined color="indigo" @click="join" v-if="this.rulecheck">가입하기</v-btn>
                </v-row>
                    </v-col>
                </v-row>
            </v-form>
        </v-container>
    </div>
</template>

<script>
import axios from "axios";
import Swal from "sweetalert2";
import PV from "password-validator";

const baseURL = "http://localhost:8080";

export default {
  created(){
    this.passwordSchema
      .is()
      .min(8)
      .is()
      .max(100)
      .has()
      .digits()
      .has()
      .letters();
  },
    methods: {
      checkemail(){
        axios
        .get(`${baseURL}/dictionary/user/emailoverlap/${this.joindata.email}`)
        .then((res)=>{
          if(res.data.message == "success"){
            this.emailcheck = true
            // console.log(this.emailcheck)
            Swal.fire({
              width: 450,
              height: 250,
              position: 'top-end',
              icon: 'success',
              text: `<${this.joindata.email}> 확인되었습니다.`,
              showConfirmButton: false,
              timer: 1500
            })
            // alert(`${this.joindata.email} 확인되었습니다.`)
          }
          else if(res.data.message=="fail"){
            Swal.fire({
              width: 450,
              height: 250,
              position: 'top-end',
              icon: 'error',
              text: `<${this.joindata.email}>은(는) 이미 사용중인 이메일입니다.`,
              showConfirmButton: false,
              timer: 1500
            })
            // alert(`${this.joindata.email}이미 사용중인 이메일입니다.`)

          }
        })
        .catch((err)=>{
           alert(`오류`)
          console.log(err.data)
        })
      },
      checkauthnum(){
        axios
        .get(`${baseURL}/dictionary/user/emailcode/${this.joindata.email}/${this.authnum}`)
        .then(()=>{
          this.authnumcheck = true
          // console.log(this.authnumcheck)
          Swal.fire({
              width: 400,
              height: 200,
              position: 'top-end',
              icon: 'success',
              text: `<${this.authnum}> 확인되었습니다.`,
              showConfirmButton: false,
              timer: 1500
            })
          // alert(`${this.authnum} 확인되었습니다.`)
        })
        .catch((err)=>{
          Swal.fire({
              width: 400,
              height: 200,
              position: 'top-end',
              icon: 'error',
              text: `정확히 입력해주세요.`,
              showConfirmButton: false,
              timer: 1500
            })
          // alert('정확히 입력해주세요.')
          console.log(err)
        })
      },
      checkrules(){
        if (!this.passwordSchema.validate(this.joindata.password)){
          this.rulecheck = false
          alert('영문,숫자 포함 8 자리이상이어야 합니다.')
        }else if(this.joindata.password != this.joindata.passwordconfirm){
          this.rulecheck = false
          alert('비밀번호와 비밀번호 확인이 다릅니다.')
        }else if((this.joindata.password).indexOf(' ') > 0){
          this.rulecheck = false
          alert('빈 칸을 넣을 수 없습니다.')
        }
        else{
          this.rulecheck = true
          alert('비밀번호 확인 되었습니다.')
        }
      },
      join(){
        if (this.joindata.email.length == 0 && this.joindata.password.length == 0 && this.joindata.passwordconfirm.length == 0 && this.joindata.username.length == 0 && this.authnum.length == 0){
            this.rulecheck = false
            alert('정보를 모두 입력해주세요.')
          }
          else if (this.joindata.email.length == 0){
            alert('이메일을 입력해주세요.')
          }
          else if (this.joindata.password.length == 0){
            this.rulecheck = false
            alert('비밀번호을 입력해주세요.')
          }
          else if (this.joindata.passwordconfirm.length == 0){
            this.rulecheck = false
            alert('비밀번호 확인을 입력해주세요.')
          }
          else if (this.joindata.username.length == 0){
            alert('이름을 입력해주세요.')
          }
          else if (this.authnum.length == 0){
            alert('인증번호을 입력해주세요.')
          }
          else if (this.joindata.password != this.joindata.passwordconfirm) {
            this.rulecheck = false
            alert('비밀번호와 비밀번호 확인이 다릅니다.')
          }
          else if (!this.checkemail) {
            alert('이메일 중복확인을 해주세요.')
          }
          else if (!this.authnumcheck) {
            alert('인증번호 확인을 해주세요.')
          }
          else{
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
          username:"",
        },
        authnum:"",
        allowSpaces: false,
        passwordSchema: new PV(),
        authnumcheck:false,
        emailcheck:false,
        rulecheck:false,
      }
    },
    computed: {
      confirmrules () {
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
      passwordrules(){
        const rules = []

        if (!this.allowSpaces) {
          const rule =
            v => (v || '').indexOf(' ') < 0 ||
              '빈 칸을 넣을 수 없습니다.'

          rules.push(rule)
        }
        
        if (!this.passwordSchema.validate(this.joindata.password)) {
          const rule = '영문,숫자 포함 8 자리이상이어야 합니다.'

          rules.push(rule)
        }
        return rules
      }
    },
  }
</script>

<style>
</style>