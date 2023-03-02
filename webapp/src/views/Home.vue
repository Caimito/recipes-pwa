<template>
  <h1>Recipe App</h1>
  <p>A future place for something awesome. Sometimes there is an update</p>
  <p>List:</p>
  <RecipeList />
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
