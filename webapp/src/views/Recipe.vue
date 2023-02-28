<template>
  <RecipeDisplay :recipe="recipe" />
</template>

<script>
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

  data: () => ({
    recipe: {},
    recipes: [
      {
        id: 1,
        name: 'Pancakes',
        description: 'A delicious breakfast food'
      },
      {
        id: 2,
        name: 'Pizza',
        description: 'A delicious lunch food'
      },
      {
        id: 3,
        name: 'Spaghetti',
        description: 'A delicious dinner food'
      }
    ]
  }),

  beforeMount() {
    this.loadRecipe()
  },

  methods: {
    loadRecipe() {
      const id = this.$route.params.id

      this.recipe = this.recipes.find(recipe => recipe.id == id)
    }
  }

}
</script>
