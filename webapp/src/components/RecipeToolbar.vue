<template>
  <div class="recipe-toolbar">
    <button @click="addRecipe">Add Recipe</button>
  </div>
</template>

<script>
export default {
  name: 'RecipeToolbar',

  methods: {
    addRecipe() {
      this.$emit('addRecipe')
    }
  }
}
</script>
