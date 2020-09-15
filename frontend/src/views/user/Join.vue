<template>
    <div class="my-auto">
        <v-container >
            <v-form>
                <p class="text-center" style="font-size:1.3rem; font-weight:bold;">회원가입</p>
                <v-row>
                    <v-col cols="3"></v-col>
                    <v-col cols="6">
                        <v-row>
                            <v-text-field
                                label="E-mail"
                                required
                                ></v-text-field>
                                <v-btn class="mt-5 ml-2" small outlined color="indigo">중복 확인</v-btn>
                        </v-row>
                        
                        <v-row>
                            <v-text-field
                                label="Password"
                                v-model="password"
                                :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                                :type="show1 ? 'text' : 'password'"
                                @click:append="show1 = !show1"
                                required
                                ></v-text-field>
                        </v-row>
                        
                            <v-form ref="form">
                                <v-row>

                                <v-text-field
                                    v-model="passwordconfirm"
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
                    <v-btn outlined color="indigo">가입하기</v-btn>
                </v-row>
                    </v-col>
                </v-row>
            </v-form>
        </v-container>
    </div>
</template>

<script>
  export default {
    data () {
      return {
        show1: false,
        show2: false,
        password:"",
        passwordconfirm:"",
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

        if (this.password) {
          const rule =
            v => (!!v && v) === this.password ||
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
