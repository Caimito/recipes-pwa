import { createRouter, createWebHistory } from 'vue-router'
import Home from './views/Home.vue'
import Recipe from './views/Recipe.vue'

const routes = [
  { 
    name: 'Home', 
    path: '/', 
    component: Home 
  },
  { 
    name: 'Recipe', 
    path: '/recipe/:id', 
    component: Recipe, 
    props: true 
  },
]

export const router = createRouter({
  history: createWebHistory(),
  routes, 
})
