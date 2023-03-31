import db from './db'
import api from './api'

function syncData () {
  console.log('Syncing data...')

  if (!navigator.onLine) {
    console.log('Not online, aborting sync')
    return
  }

  db.recipes
    .filter(recipe => recipe.synced === false)
    .toArray()
    .then(unsyncedRecipes => {
      if (unsyncedRecipes.length > 0) {
        return api.syncRecipes(unsyncedRecipes)
      }
      return []
    })
    .then(syncedRecipes => {
      const updatePromises = syncedRecipes.map(syncedRecipe => {
        return db.recipes.update(syncedRecipe.localId, {
          id: syncedRecipe.id,
          synced: true
        })
      })
      return Promise.all(updatePromises)
    })
    .catch(error => {
      console.error('Error syncing data:', error)
    })
}

export default syncData
