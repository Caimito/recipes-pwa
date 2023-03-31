function handleErrors (response) {
  if (!response.ok) {
    throw new Error(response.statusText)
  }
  return response
}

export default {
  syncRecipes (recipes) {
    return fetch('/recipes/sync', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ recipes })
    })
      .then(handleErrors)
      .then((response) => response.json())
      .catch((error) => {
        console.error('Error syncing recipes:', error)
      })
  }
}
