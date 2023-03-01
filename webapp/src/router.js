import { createRouter, createWebHistory } from 'vue-router'
import HomeView from './views/Home.vue'
import Recipe from './views/Recipe.vue'

const routes = [
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
]

export const router = createRouter({
  history: createWebHistory(),
  routes, 
})
