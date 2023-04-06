<template>
  <div class="container">
    <h1>Recipe App</h1>
    <RecipeList />
    <div>
      <button @click="askForPermission">Ask for Permission</button>
      <button @click="subscribe">Subscribe</button>
      <button @click="unsubscribe">Unsubscribe</button>
      <button @click="sendNotification">Notify</button>
    </div>
  </div>
</template>

<script>
import RecipeList from '@/components/RecipeList.vue'
import { subscribeUser } from '@/notifications'

export default {
  name: 'HomeView',
  components: {
    RecipeList
  },

  methods: {
    askForPermission () {
      Notification.requestPermission().then((permission) => {
        if (permission === 'granted') {
          console.log('Notification permission granted.')
        } else {
          console.error('Notification permission denied.')
        }
      })
    },

    subscribe () {
      console.log('subscribe')
      subscribeUser()
    },

    unsubscribe () {
      console.log('unsubscribe')
      navigator.serviceWorker.ready
        .then(registration => {
          registration.pushManager.getSubscription()
            .then(subscription => {
              console.log('Existing subscription:', subscription)
              if (subscription) {
                subscription.unsubscribe()
              }
            })
        })
    },

    sendNotification () {
      fetch('/recipes/api/notifications/send', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          title: 'Hello',
          message: 'This is a notification'
        })
      })
    }
  }
}
</script>
