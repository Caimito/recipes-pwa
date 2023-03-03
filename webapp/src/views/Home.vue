<template>
  <div class="container">
    <h1>Recipe App</h1>
    <p>A future place for something awesome.</p>
    <RecipeList />
  </div>
</template>

<script>
import RecipeList from '@/components/RecipeList.vue'

export default {
  name: 'HomeView',
  components: {
    RecipeList
  },

}
</script>
