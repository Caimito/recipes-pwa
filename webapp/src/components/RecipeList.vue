<template>
  <div>
    <RecipeToolbar />
    <ul class="recipe-list">
      <li v-for="recipe in recipes" :key="recipe.id">
        <router-link :to="{ name: 'Recipe', params: { id: recipe.id } }">{{ recipe.name }}</router-link>
      </li>
    </ul>
    <RecipeDisplay :recipe="selectedRecipe" />

    {{ recipes }}
  </div>
</template>

<script>
import db from '@/db'
import RecipeDisplay from './RecipeDisplay.vue'
import RecipeToolbar from './RecipeToolbar.vue'

export default {
  name: 'RecipeList',

  components: {
    RecipeDisplay,
    RecipeToolbar
  },

  data: () => ({
    selectedRecipe: {},
    recipes: []
  }),

  mounted () {
    this.loadRecipes()
  },

  methods: {
    loadRecipes () {
      db.recipes.toArray().then(recipes => {
        this.recipes = recipes
      })
    },

    selectRecipe (recipe) {
      console.log(recipe)
      this.selectedRecipe = recipe
    }
  }
}
</script>
