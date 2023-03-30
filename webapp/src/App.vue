<template>
  <HeaderComponent />
  <p v-if="updateExists">
    An update is available and can be installed by clicking the button below.
    <button @click="refreshApp"> Update </button>
  </p>

  <router-view />
</template>

<script>
import '@/assets/css/global.css'
import update from '@/mixins/update'
import HeaderComponent from './components/HeaderComponent.vue'

export default {
  name: 'RecipeApp',
  components: {
    HeaderComponent
  },
  mixins: [update],

  data: () => ({
    registration: null,
    updateExists: false
  }),

  created () {
    console.log('created')
    document.addEventListener('swUpdated', this.updateAvailable, { once: true })
  },

  methods: {
    updateAvailable (event) {
      console.log('updateAvailable')
      this.registration = event.detail
      this.updateExists = true
    }
  }

}
</script>
