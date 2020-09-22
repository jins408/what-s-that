<template>
   <v-tabs
      v-model="tab"
      background-color="deep-purple accent-4"
      dark
      :vertical="vertical"
    >
      
      <v-tab
        v-for="i in tabs"
        :key="i"
        :href="`#tab-${i}`"
      >
        Tab {{ i }}
      </v-tab>

      <v-tab-item
        v-for="i in tabs"
        :key="i"
        :value="'tab-' + i"
      >
 
          <v-card-text>{{ text }}</v-card-text>
        
      </v-tab-item>
    </v-tabs>

</template>

<script>
export default {
     data () {
      return {
        tab: null,
        text: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.',
        vertical: true,
        right: false,
        tabs: 3,
      }
    },

}
</script>

<style>

</style>