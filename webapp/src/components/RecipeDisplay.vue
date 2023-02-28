<template>
  <div>
    <h1>{{ recipe.name }}</h1>
    <p>{{ recipe.description }}</p>
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
