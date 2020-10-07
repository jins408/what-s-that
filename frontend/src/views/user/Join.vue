<template>
    <div class="my-auto">
  <div class="joinbg">
    <!-- 웹버전 -->
    <div class="d-none d-sm-block">
    <div class=" join_title_web" >간편 회원가입</div>
    </div>
    <!-- 모바일버전 -->
    <div class="d-block d-sm-none d-md-none">
    <div class=" join_title_mobile" >간편 회원가입</div>
    </div>
    </div>
        <v-container >
            <v-form>
                <v-row>
                    <v-col cols="2"></v-col>
                    <v-col cols="9">
                      <div class="d-flex justify-content-end" style="margin-top: 6rem;">
                      <h6>* 표시는 필수 입력 항목입니다.</h6>
                      </div>
                      <hr class="joinhr">
                        <v-row rows="12" sm="12" class="mb-5" >
                            <v-col cols="12" sm="3">
                             <h5 style="font-weight: bold">* 이메일 </h5> 
                            </v-col>
                          <v-col class="p-0" cols="12" sm="6">  
                                <v-text-field
                                    outlined
                                    v-model="joindata.email"
                                    :disabled="emailcheck ? '' : emailcheck"
                                ></v-text-field>
                          </v-col>
                          <v-col class="d-flex justify-content-end p-0 mt-2" cols="12" sm="2">
                            <v-btn large  outlined color="indigo" @click="checkemail" :disabled="emailcheck ? '' : emailcheck">중복 확인</v-btn>
                          </v-col>
                          </v-row>

                          <v-row rows="12" sm="12" class="mb-5">
                          <v-col cols="12" sm="3">
                             <h5 style="font-weight: bold">* 인증번호 </h5> 
                          </v-col>
                             <v-col class="p-0" cols="12" sm="6">
                                <v-text-field
                                    outlined
                                    v-model="authnum"
                                    required
                                    :disabled="authnumcheck ? '' : authnumcheck"
                                    ></v-text-field>           
                          </v-col>
                          <v-col class="d-flex justify-content-end p-0 mt-2" cols="12" sm="2">
                            <v-btn sm="2" large outlined color="indigo" @click="checkauthnum" :disabled="authnumcheck ? '' : authnumcheck">인증 확인</v-btn>
                          </v-col>
                        </v-row>
                        
                        <v-row rows="12" sm="12" class="mb-5">
                          <v-col cols="12" sm="3">
                            <h5 style="font-weight: bold">* 이름 </h5> 
                          </v-col>
                            <v-col class="p-0" cols="12" sm="6">
                              <v-text-field
                                  outlined
                                  v-model="joindata.username"
                                  required
                                  ></v-text-field>
                            </v-col>
                        </v-row>

                        <v-row rows="12" sm="12" class="mb-5">
                          <v-col cols="12" sm="3">
                            <h5 style="font-weight: bold">* 비밀번호 </h5> 
                          </v-col>
                          <v-col class="p-0" cols="12" sm="6">
                            <v-text-field
                                outlined
                                :rules="passwordrules"
                                v-model="joindata.password"
                                :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                                :type="show1 ? 'text' : 'password'"
                                @click:append="show1 = !show1"
                                required
                                ></v-text-field>
                            </v-col>
                        </v-row>
                      
                          <v-row rows="12" sm="12" class="mb-5">
                            <v-col cols="12" sm="3">
                              <h5 style="font-weight: bold">* 비밀번호확인 </h5> 
                            </v-col>
                               <v-col class="p-0" cols="12" sm="6">
                                <v-text-field
                                    outlined
                                    v-model="joindata.passwordconfirm"
                                    :rules="confirmrules"
                                    :append-icon="show2 ? 'mdi-eye' : 'mdi-eye-off'"
                                    :type="show2 ? 'text' : 'password'"
                                    @click:append="show2 = !show2"
                                ></v-text-field>
                               </v-col>
                               <v-col class="d-flex justify-content-end p-0 mt-2" cols="12" sm="2">
                                <v-btn large outlined color="error" @click="checkrules" v-if="!this.rulecheck" >비밀번호 확인</v-btn>
                               </v-col>
                          </v-row>
                            
                <v-row class="mt-5 mb-5">
                    <v-row rows="9"></v-row>
                    <v-btn large color="primary" dark @click="join" v-if="this.rulecheck">가입하기</v-btn>
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
        .get(this.$baseurl + `/user/emailoverlap/${this.joindata.email}`)
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
          console.log(err)
        })
      },
      checkauthnum(){
        axios
        .get(this.$baseurl + `/user/emailcode/${this.joindata.email}/${this.authnum}`)
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
          Swal.fire({
          position: 'top',
          icon: 'error',
          title: '영문,숫자 포함 8 자리이상이어야 합니다.',
          showConfirmButton: false,
          timer: 1500
        })
          // alert('영문,숫자 포함 8 자리이상이어야 합니다.')
        }else if(this.joindata.password != this.joindata.passwordconfirm){
          this.rulecheck = false
          Swal.fire({
          position: 'top',
          icon: 'error',
          title: '비밀번호와 비밀번호 확인이 다릅니다.',
          showConfirmButton: false,
          timer: 1500
        })
          // alert('비밀번호와 비밀번호 확인이 다릅니다.')
        }else if((this.joindata.password).indexOf(' ') > 0){
          this.rulecheck = false
          Swal.fire({
          position: 'top',
          icon: 'error',
          title: '빈 칸을 넣을 수 없습니다.',
          showConfirmButton: false,
          timer: 1500
        })
          // alert('빈 칸을 넣을 수 없습니다.')
        }
        else{
          this.rulecheck = true
          Swal.fire({
          position: 'top',
          icon: 'success',
          title: '비밀번호 확인이 되었습니다.',
          showConfirmButton: false,
          timer: 1500
        })
          // alert('비밀번호 확인 되었습니다.')
        }
      },
      join(){
        if (this.joindata.email.length == 0 && this.joindata.password.length == 0 && this.joindata.passwordconfirm.length == 0 && this.joindata.username.length == 0 && this.authnum.length == 0){
            this.rulecheck = false
            Swal.fire({
          position: 'top',
          icon: 'error',
          title: '정보를 모두 입력해주세요.',
          showConfirmButton: false,
          timer: 1500
        })
            // alert('정보를 모두 입력해주세요.')
          }
          else if (this.joindata.email.length == 0){
            Swal.fire({
          position: 'top',
          icon: 'error',
          title: '이메일을 입력해주세요.',
          showConfirmButton: false,
          timer: 1500
        })
            // alert('이메일을 입력해주세요.')
          }
          else if (this.joindata.password.length == 0){
            this.rulecheck = false
            Swal.fire({
          position: 'top',
          icon: 'error',
          title: '비밀번호를 입력해주세요.',
          showConfirmButton: false,
          timer: 1500
        })
            // alert('비밀번호을 입력해주세요.')
          }
          else if (this.joindata.passwordconfirm.length == 0){
            this.rulecheck = false
            Swal.fire({
          position: 'top',
          icon: 'error',
          title: '비밀번호 확인을 입력해주세요.',
          showConfirmButton: false,
          timer: 1500
        })
            // alert('비밀번호 확인을 입력해주세요.')
          }
          else if (this.joindata.username.length == 0){
            Swal.fire({
          position: 'top',
          icon: 'error',
          title: '이름을 입력해주세요.',
          showConfirmButton: false,
          timer: 1500
        })
            // alert('이름을 입력해주세요.')
          }
          else if (this.authnum.length == 0){
            Swal.fire({
          position: 'top',
          icon: 'error',
          title: '인증번호를 입력해주세요.',
          showConfirmButton: false,
          timer: 1500
        })
            // alert('인증번호을 입력해주세요.')
          }
          else if (this.joindata.password != this.joindata.passwordconfirm) {
            this.rulecheck = false
            Swal.fire({
          position: 'top',
          icon: 'error',
          title: '비밀번호와 비밀번호 확인이 다릅니다.',
          showConfirmButton: false,
          timer: 1500
        })
            // alert('비밀번호와 비밀번호 확인이 다릅니다.')
          }
          else if (!this.checkemail) {
            Swal.fire({
          position: 'top',
          icon: 'error',
          title: '이메일 중복확인을 해주세요.',
          showConfirmButton: false,
          timer: 1500
        })
            // alert('이메일 중복확인을 해주세요.')
          }
          else if (!this.authnumcheck) {
            Swal.fire({
          position: 'top',
          icon: 'error',
          title: '인증번호 확인을 해주세요.',
          showConfirmButton: false,
          timer: 1500
        })
            // alert('인증번호 확인을 해주세요.')
          }
          else{
            axios
            .post(this.$baseurl + `/user/signup`, this.joindata)
            .then(()=>{
              Swal.fire({
              position: 'top-end',
              icon: 'success',
              title: '회원가입 완료!',
              showConfirmButton: false,
              timer:1500
          })
          setTimeout(()=>{
            scroll(0, 0);
                this.$router.push('/')
          },1500)
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
.joinbg{
    background: url('../../assets/join_bg.jpg') no-repeat;
    width: 100%;
    height: 18rem;
    background-size: cover;
    background-position: center;
}

.join_title_web{
  position: absolute;
  left: 42%;
  top: 11rem;
  color: white;
  font-size: 2.7rem;
  font-weight: bold;
}

.join_title_mobile{
  position: absolute;
  left: 12%;
  top: 11rem;
  color: white;
  font-size: 2.7rem;
  font-weight: bold;
}

.joinhr{
  margin-bottom: 3rem;
  border: 0.5px solid;
}

</style>