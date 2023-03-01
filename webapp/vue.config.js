const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  publicPath: '/recipes',
  transpileDependencies: true,
  pwa: {
    workboxOptions: {
        skipWaiting: true
    }
}
})
