<template>
  <p v-if="updateExists">
    An update is available
    <button @click="refreshApp"> Update </button>
  </p>

  <router-view />
</template>

<script>
import update from '@/mixins/update'

export default {
  name: 'RecipeApp',
  components: {
  },
  mixins: [update],

  data: () => ({
    registration: null,
    updateExists: false
  }),

  created() {
    document.addEventListener('swUpdated', this.updateAvailable, { once: true })
  },

  methods: {
    updateAvailable(event) {
      this.registration = event.detail
      this.updateExists = true
    }
  },

}
</script>
