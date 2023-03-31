import { createApp } from 'vue'
import App from './App.vue'
import './registerServiceWorker'
import { router } from './router'
import { createPinia } from 'pinia'
import syncData from './syncData'

const pinia = createPinia()
const app = createApp(App)

app.use(router)
app.use(pinia)

app.mount('#app')

window.addEventListener('online', syncData)
window.addEventListener('offline', () => {
  // Handle offline mode
})
