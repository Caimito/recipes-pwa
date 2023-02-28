<template>
  <div>
    <button @click="addRecipe">Add Recipe</button>
    <ul>
      <li v-for="recipe in recipes" :key="recipe.id">
        <router-link :to="{ name: 'Recipe', params: { id: recipe.id } }">{{ recipe.name }}</router-link>

      </li>
    </ul>
    <RecipeDisplay :recipe="selectedRecipe" />
  </div>
</template>

<script>
import RecipeDisplay from './RecipeDisplay.vue'

export default {
  name: 'RecipeList',

  components: {
    RecipeDisplay
  },

  data: () => ({
    selectedRecipe: {},
    recipes: [
      {
        id: 1,
        name: 'Pancakes'
      },
      {
        id: 2,
        name: 'Pizza'
      },
      {
        id: 3,
        name: 'Spaghetti'
      }
    ]
  }),

  methods: {
    addRecipe() {
      this.recipes.push({
        id: this.recipes.length + 1,
        name: 'New Recipe'
      })
    },

    selectRecipe(recipe) {
      console.log(recipe)
      this.selectedRecipe = recipe
    }
  }
}
</script>
