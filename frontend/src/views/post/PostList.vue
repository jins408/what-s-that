<template>
  <div>
    <v-col cols="12" sm="8" class="mx-auto">
      <v-row>
        <v-text-field
          prepend-icon="fas fa-search"
          label="검색해주세요!"
          filled
          rounded
        ></v-text-field>
        <i class="fas fa-microphone ml-2 mt-2" style="font-size: 1.6rem"></i>
      </v-row>
    </v-col>
    <div class="container">
        <v-col class="pt-5">
        <v-row cols="12" sm="12">
            <v-card
            class="mx-5 my-3"
            max-width="250"
            style="cursor: pointer"
            >
            <v-img
                class="white--text align-end"
                height="200px"
                src="https://cdn.vuetifyjs.com/images/cards/docks.jpg"
            >
                <div class="d-flex justify-content-end">
                <v-card-title>불국사</v-card-title>
                </div>
            </v-img>
            <v-card-actions class="d-flex justify-content-center">
                <v-btn color="red" style="font-weight:bold; width:50%" text > 상세보기 </v-btn>
                <v-btn color="red" style="font-weight:bold; width:50%" text > 찜 </v-btn>
            </v-card-actions>
            </v-card>


            <v-card
            class="mx-5 my-3"
            max-width="250"
            style="cursor: pointer"
            >
            <v-img
                class="white--text align-end"
                height="200px"
                src="https://cdn.vuetifyjs.com/images/cards/docks.jpg"
            >
                <div class="d-flex justify-content-end">
                <v-card-title>불국사</v-card-title>
                </div>
            </v-img>
            <v-card-actions class="d-flex justify-content-center">
                <v-btn color="red" style="font-weight:bold; width:50%" text > 상세보기 </v-btn>
                <v-btn color="red" style="font-weight:bold; width:50%" text > 찜 </v-btn>
            </v-card-actions>
            </v-card><v-card
            class="mx-5 my-3"
            max-width="250"
            style="cursor: pointer"
            >
            <v-img
                class="white--text align-end"
                height="200px"
                src="https://cdn.vuetifyjs.com/images/cards/docks.jpg"
            >
                <div class="d-flex justify-content-end">
                <v-card-title>불국사</v-card-title>
                </div>
            </v-img>
            <v-card-actions class="d-flex justify-content-center">
                <v-btn color="red" style="font-weight:bold; width:50%" text > 상세보기 </v-btn>
                <v-btn color="red" style="font-weight:bold; width:50%" text > 찜 </v-btn>
            </v-card-actions>
            </v-card><v-card
            class="mx-5 my-3"
            max-width="250"
            style="cursor: pointer"
            >
            <v-img
                class="white--text align-end"
                height="200px"
                src="https://cdn.vuetifyjs.com/images/cards/docks.jpg"
            >
                <div class="d-flex justify-content-end">
                <v-card-title>불국사</v-card-title>
                </div>
            </v-img>
            <v-card-actions class="d-flex justify-content-center">
                <v-btn color="red" style="font-weight:bold; width:50%" text > 상세보기 </v-btn>
                <v-btn color="red" style="font-weight:bold; width:50%" text > 찜 </v-btn>
            </v-card-actions>
            </v-card><v-card
            class="mx-5 my-3"
            max-width="250"
            style="cursor: pointer"
            >
            <v-img
                class="white--text align-end"
                height="200px"
                src="https://cdn.vuetifyjs.com/images/cards/docks.jpg"
            >
                <div class="d-flex justify-content-end">
                <v-card-title>불국사</v-card-title>
                </div>
            </v-img>
            <v-card-actions class="d-flex justify-content-center">
                <v-btn color="red" style="font-weight:bold; width:50%" text > 상세보기 </v-btn>
                <v-btn color="red" style="font-weight:bold; width:50%" text > 찜 </v-btn>
            </v-card-actions>
            </v-card>
        </v-row>
        </v-col>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    // search() {
    //   if (this.culturename == "") {
    //     this.searchok = false;
    //   } else {
    //     axios
    //       .get(`${baseURL}/dictionary/culture/search/${this.culturename}`)
    //       .then((res) => {
    //         if (this.searchok == false) {
    //           this.scroll();
    //         } else {
    //           this.gosearchview();
    //         }
    //         this.searchok = true;
    //         this.culture = res.data.object;
    //         // console.log(this.culture)
    //       })
    //       .catch((err) => {
    //         console.log(err);
    //       });
    //   }
    // },
    // gopostdetail(item) {
    //   axios
    //     .get(`${baseURL}/dictionary/culture/${item}`)
    //     .then((res) => {
    //       this.post = res.data.object;
    //       console.log(this.post);
    //       this.$router.push({
    //         name: "PostDetail",
    //         params: { ID: this.post.postno },
    //       });
    //     })
    //     .catch((err) => {
    //       console.log(err);
    //     });
    // },
  },
};
</script>

<style>
</style>