import { defineStore } from 'pinia'
import Dexie from 'dexie'

const db = new Dexie('RecipesDB')
db.version(1).stores({
  recipes: '++id, name, description, ingredients, instructions, created'
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
        const response = await fetch('/api/recipes')
        const recipes = await response.json()
        await db.recipes.clear()
        await db.recipes.bulkAdd(recipes)
        this.recipes = recipes
        console.log('fetchRecipes', recipes)
      } else {
        const recipes = await db.recipes.toArray()
        this.recipes = recipes
      }
    },
    async addRecipe (recipe) {
      if (isOnline()) {
        const response = await fetch('/api/recipes', {
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
        const id = await db.recipes.add(recipe)
        this.recipes.push({ ...recipe, id })
      }
    },
    async updateRecipe (recipe) {
      if (isOnline()) {
        await fetch(`/api/recipes/${recipe.id}`, {
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
        await fetch(`/api/recipes/${id}`, {
          method: 'DELETE'
        })
        await db.recipes.delete(id)
      } else {
        await db.recipes.delete(id)
      }
      this.recipes = this.recipes.filter(recipe => recipe.id !== id)
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
