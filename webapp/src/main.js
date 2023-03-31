import { createApp } from 'vue'
import App from './App.vue'
import './registerServiceWorker'
import { router } from './router'
import { createPinia } from 'pinia'
import { useRecipesStore } from '@/store/recipes'

const pinia = createPinia()
const app = createApp(App)

app.use(router)
app.use(pinia)

app.mount('#app')

window.addEventListener('online', () => {
  console.log('online')
  const recipesStore = useRecipesStore()
  recipesStore.syncRecipes()
})

window.addEventListener('offline', () => {
  // Handle offline mode
})
