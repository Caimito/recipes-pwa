import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/Home.vue'
import Recipe from '@/views/Recipe.vue'
import RecipeNew from '@/views/RecipeNew.vue'

const routes = [
  {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: HomeView
  },
  { 
    name: 'Home', 
    path: '/', 
    component: HomeView 
  },
  { 
    name: 'Recipe', 
    path: '/recipe/:id', 
    component: Recipe, 
    props: true 
  },
  {
    name: 'NewRecipe',
    path: '/recipe/new',
    component: RecipeNew,
  }
]

export const router = createRouter({
  history: createWebHistory(),
  routes, 
})
