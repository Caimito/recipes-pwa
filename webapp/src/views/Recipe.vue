<template>
  <RecipeDisplay :recipe="recipe" />
</template>

<script>
import db from '@/db'
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
    recipe: {}
  }),

  beforeMount () {
    this.loadRecipe()
  },

  methods: {
    loadRecipe () {
      const id = this.$route.params.id

      db.recipes.get(id).then(recipe => {
        this.recipe = recipe
      })
    }
  }

}
</script>
