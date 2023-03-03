<template>
  <div class="recipe-toolbar">
    <router-link to="/recipe/new" custom v-slot="{ navigate }">
      <button @click="navigate" role="link">
        New Recipe
      </button>
    </router-link>
  </div>
</template>
