<template>
  <div class="container">
    <form>
      <label for="name">Name</label>
      <input type="text" id="name" v-model="name" />
      <label for="description">Description</label>
      <textarea id="description" rows="3" @input="resize()" ref="textarea" v-model="description"></textarea>
      <label for="ingredients">Ingredients</label>
      <textarea id="ingredients" rows="4" @input="resize()" ref="textarea" v-model="ingredients"></textarea>
      <label for="instructions">Instructions</label>
      <textarea id="instructions" rows="10" @input="resize()" ref="textarea" v-model="instructions"></textarea>
      <button type="submit" @click.prevent="saveRecipe">Save</button>
    </form>
  </div>
</template>

<script>
import { useRecipesStore } from '@/store/recipes'
import { v4 as uuidv4 } from 'uuid'

export default {
  name: 'RecipeNew',
  data: () => ({
    name: '',
    description: '',
    ingredients: '',
    instructions: ''
  }),

  methods: {
    resize () {
      const element = this.$refs.textarea

      element.style.height = '18px'
      element.style.height = element.scrollHeight + 'px'
    },

    saveRecipe () {
      console.log('saveRecipe WITH STORE')
      const recipe = {
        id: uuidv4(),
        name: this.name,
        description: this.description,
        ingredients: this.ingredients.split('\n'),
        instructions: this.instructions.split('\n'),
        created: new Date(),
        synced: false
      }

      const recipesStore = useRecipesStore()
      recipesStore.addRecipe(recipe).then(() => {
        this.$router.push('/recipes')
      })
    }
  }
}
</script>
