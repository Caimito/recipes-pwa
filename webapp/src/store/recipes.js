import { defineStore } from 'pinia'
import Dexie from 'dexie'

const db = new Dexie('RecipesDB')
db.version(1).stores({
  recipes: '++id, name, description, ingredients, instructions, created, synced'
})

function isOnline () {
  return window.navigator.onLine
}

export const useRecipesStore = defineStore({
  id: 'recipes',
  state: () => ({
    recipes: []
  }),
  actions: {
    async fetchRecipes () {
      if (isOnline()) {
        const response = await fetch('/recipes/api/recipes')
        const recipes = await response.json()
        await db.recipes.clear()
        await db.recipes.bulkAdd(recipes)
        this.recipes = recipes
        console.log('fetchRecipes', recipes)

        // Sync unsynced recipes after fetching from the API
        await this.syncRecipes()
      } else {
        console.log('fetchRecipes offline')
        const recipes = await db.recipes.toArray()
        this.recipes = recipes
      }
    },
    async addRecipe (recipe) {
      console.log('addRecipe', recipe)
      if (isOnline()) {
        console.log('addRecipe online')
        const response = await fetch('/recipes/api/recipes', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(recipe)
        })
        const newRecipe = await response.json()
        await db.recipes.put(newRecipe)
        this.recipes.push(newRecipe)
      } else {
        console.log('addRecipe offline')
        const id = await db.recipes.add(recipe)
        this.recipes.push({ ...recipe, id })
      }
    },
    async updateRecipe (recipe) {
      if (isOnline()) {
        await fetch(`/recipes/api/recipes/${recipe.id}`, {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(recipe)
        })
        await db.recipes.put(recipe)
      } else {
        await db.recipes.put(recipe)
      }
    },
    async deleteRecipe (id) {
      if (isOnline()) {
        await fetch(`/recipes/api/recipes/${id}`, {
          method: 'DELETE'
        })
        await db.recipes.delete(id)
      } else {
        await db.recipes.delete(id)
      }
      this.recipes = this.recipes.filter(recipe => recipe.id !== id)
    },
    async syncRecipes () {
      console.log('syncRecipes')
      if (isOnline()) {
        console.log('syncRecipes online')
        // Get unsynced recipes from Dexie
        const unsyncedRecipes = await db.recipes
          .filter(recipe => recipe.synced === false)
          .toArray()

        // Sync each unsynced recipe
        for (const recipe of unsyncedRecipes) {
          // Send the recipe to the API
          const response = await fetch('/recipes/api/recipes', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json'
            },
            body: JSON.stringify(recipe)
          })
          const syncedRecipe = await response.json()

          // Update the local storage with the synced recipe
          await db.recipes.put({ ...syncedRecipe, synced: true })

          // Update the state in the store
          const index = this.recipes.findIndex((r) => r.id === recipe.id)
          if (index !== -1) {
            this.recipes.splice(index, 1, syncedRecipe)
          }
        }
      }
    }
  },
  getters: {
    getRecipeById: (state) => (id) => {
      return state.recipes.find(recipe => recipe.id === id)
    },
    getAllRecipes: (state) => {
      console.log('getAllRecipes', state.recipes)
      return state.recipes
    }
  }
})
