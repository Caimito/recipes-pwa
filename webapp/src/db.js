import Dexie from 'dexie'

const db = new Dexie('recipesApp')

db.version(1).stores({
  recipes: '++id, name, ingredients, instructions, created'
})

export default db
