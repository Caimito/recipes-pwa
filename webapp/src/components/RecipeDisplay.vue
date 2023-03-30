<template>
  <div>
    <p>{{ recipe }}</p>
    <h1>{{ recipe.name }}</h1>
    <p>{{ recipe.description }}</p>
    <p>{{ recipe.instructions }}</p>
    <p>{{ recipe.ingredients }}</p>
    <p>{{ recipe.created }}</p>
    <p>{{ recipe.synced }}</p>
  </div>
</template>

<script>
export default {
  name: 'RecipeDisplay',
  props: {
    recipe: {
      type: Object,
      required: true
    }
  }
}
</script>
