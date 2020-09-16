<template>
  <div class="container">
    <div class="d-flex justify-content-end">
      <v-list>
        <v-list-group no-action prepend-icon="far fa-user-circle">
          <template v-slot:activator>
            <v-list-item-title>Users</v-list-item-title>
          </template>

          <v-list-group no-action sub-group value="true">
            <template v-slot:activator>
              <v-list-item-content>
                <v-list-item-title>Admin</v-list-item-title>
              </v-list-item-content>
            </template>
          </v-list-group>
        </v-list-group>
      </v-list>
    </div>
    <div>
      <p class="text-center" style="margin-top:15rem;">
        <span style="font-size:2rem; font-wegiht:bold; color:red;">SEARCH</span>
        <span style="font-size:2rem; font-wegiht:bold;">ANYTHING</span>
      </p>
      <v-col cols="12" sm="6" class="mx-auto">
        <v-row>
          <v-text-field prepend-icon="fas fa-search" placeholder="검색해주세요!" filled rounded dense></v-text-field>
          <i class="fas fa-microphone ml-2 mt-2" style="font-size:1.6rem;"></i>
        </v-row>
      </v-col>
      <div class="d-flex justify-content-center" style="margin-top:19rem;">
        <button class="btndown" @click="scroll">
          <i class="fas fa-angle-down" style="font-size:2rem;"></i>
        </button>
      </div>
    </div>

    <div class="mt-2">
      <div style="height:50rem;">
        <a href="#heritage" style="font-size:1.5rem;">문화재</a>
        <div class="d-flex justify-content-center" style="margin-top:19rem;">
          <button class="btndown" @click="scroll1">
            <i class="fas fa-angle-down" style="font-size:2rem;"></i>
          </button>
        </div>
      </div>
      <div style="height:50rem;">
        <a href="#location" style="font-size:1.5rem;">지역</a>
        <div class="d-flex justify-content-center" style="margin-top:19rem;">
          <button class="btndown" @click="scroll2">
            <i class="fas fa-angle-down" style="font-size:2rem;"></i>
          </button>
        </div>
      </div>
      <div style="height:50rem;">
        <a href="#comment" style="font-size:1.5rem;">최근 댓글</a>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  created() {
    window.addEventListener("scroll", this.updatescroll);
  },
  methods: {
    updatescroll() {
      this.scrollposition = window.scrollY;
      // console.log(this.scrollposition)
    },
    scroll() {
      // var location = document.querySelector("#heritage").offsetTop;
      window.scrollTo({ top: 740, behavior: "smooth" });
    },
    scroll1() {
      window.scrollTo({ top: 1550, behavior: "smooth" });
    },
    scroll2() {
      window.scrollTo({ top: 2345, behavior: "smooth" });
    },
  },
  data() {
    return {
      scrollposition: 0,
    };
  },
};
</script>

<style scoped>
.btndown:focus {
  outline: none;
}

.btndown:hover {
  color: red;
}

a {
  text-decoration: none;
  color: red !important;
}
</style>