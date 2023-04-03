<template>
  <div class="container">
    <h1>Recipe App</h1>
    <div>
      <button @click="subscribe">Subscribe</button>
      <button @click="unsubscribe">Unsubscribe</button>
      <button @click="sendNotification">Notify</button>
    </div>
    <p>A future place for something real awesome.</p>
    <ul>
      <li>API</li>
    </ul>
    <RecipeList />
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
