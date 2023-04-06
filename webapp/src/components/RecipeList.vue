<template>
  <div>
    <RecipeToolbar />
    <ul class="recipe-list">
      <li v-for="recipe in recipes" :key="recipe.id">
        <router-link :to="{ name: 'Recipe', params: { id: recipe.id } }">{{ recipe.name }}</router-link>
      </li>
    </ul>
    <RecipeDisplay :recipe="selectedRecipe" />
  </div>
</template>

<script>
import { useRecipesStore } from '@/store/recipes'
import RecipeDisplay from './RecipeDisplay.vue'
import RecipeToolbar from './RecipeToolbar.vue'

export default {
  name: 'RecipeList',

  components: {
    RecipeDisplay,
    RecipeToolbar
  },

  data: () => ({
    selectedRecipe: {}
  }),

  computed: {
    recipes () {
      const recipesStore = useRecipesStore()
      return recipesStore.getAllRecipes
    }
  },

  mounted () {
    this.loadRecipes()
  },

  methods: {
    loadRecipes () {
      const recipesStore = useRecipesStore()
      recipesStore.fetchRecipes()
    },

    selectRecipe (recipe) {
      console.log(recipe)
      this.selectedRecipe = recipe
    }
  }
}
</script>
