<template>
  <h1>Recipe App</h1>
  <p>A future place for something awesome. New version</p>
  <p>List:</p>
  <RecipeList />
</template>

<script>
import RecipeList from '@/components/RecipeList.vue'

export default {
  name: 'RecipeApp',
  components: {
    RecipeList
  }
}
</script>
