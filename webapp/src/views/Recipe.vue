<template>
  <div class="container">
    <RecipeDisplay v-if="recipe" :recipe="recipe" />
  </div>
</template>

<script>
import { useRecipesStore } from '@/store/recipes'
import RecipeDisplay from '@/components/RecipeDisplay.vue'

export default {
  name: 'RecipePage',
  components: {
    RecipeDisplay
  },

  props: {
    id: {
      type: Number,
      required: true
    }
  },

  computed: {
    recipe () {
      const recipesStore = useRecipesStore()
      return recipesStore.getRecipeById(this.id)
    }
  },

  beforeMount () {
    const recipesStore = useRecipesStore()
    recipesStore.fetchRecipes()
  }

}
</script>
