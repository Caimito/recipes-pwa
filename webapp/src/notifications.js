// TODO: this prints the question right away, but it should be asked when the user clicks on a button
// Notification.requestPermission().then((permission) => {
//   if (permission === 'granted') {
//     console.log('Notification permission granted.')
//   } else {
//     console.error('Notification permission denied.')
//   }
// })

const vapidPublicKey = 'BPl11OKqblKvjFJUIgpjN0edr9Z5Zdab0-n9CoBu0mrcl3MGQ-UkeypWA0VXDJTAsHi7SfJE5Ps27T58oQbw0_8='

export function subscribeUser () {
  console.log('subscribeUser')
  navigator.serviceWorker.ready
    .then(registration => {
      console.log('navigator.serviceWorker.ready')
      return registration.pushManager.getSubscription()
    })
    .then(existingSubscription => {
      console.log('Existing subscription:', existingSubscription)

      if (!existingSubscription) {
        const subscribeOptions = {
          userVisibleOnly: true,
          applicationServerKey: urlBase64ToUint8Array(vapidPublicKey)
        }

        return navigator.serviceWorker.ready
          .then(registration => {
            return registration.pushManager.subscribe(subscribeOptions)
          })
          .then(subscription => {
            console.log('Push subscription:', subscription)
            // Send subscription object to your backend server
            return fetch('/recipes/api/notifications/subscribe', {
              method: 'POST',
              headers: {
                'Content-Type': 'application/json'
              },
              body: JSON.stringify(subscription)
            })
          })
          .then(response => {
            if (!response.ok) {
              throw new Error('Failed to subscribe for push notifications')
            }
          })
          .catch(error => {
            console.error('Error while subscribing for push notifications:', error)
          })
      }
    })
    .catch(error => {
      console.error('Error while subscribing for push notifications:', error)
    })
}

function urlBase64ToUint8Array (base64String) {
  const padding = '='.repeat((4 - (base64String.length % 4)) % 4)
  const base64 = (base64String + padding).replace(/-/g, '+').replace(/_/g, '/')
  const rawData = window.atob(base64)
  const outputArray = new Uint8Array(rawData.length)

  for (let i = 0; i < rawData.length; ++i) {
    outputArray[i] = rawData.charCodeAt(i)
  }

  return outputArray
}
