const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  publicPath: '/recipes',
  transpileDependencies: true,
  devServer: {
    port: 9090,
    proxy: {
      '^/recipes/api/': {
        target: 'http://localhost:8080',
        headers: { host: 'localhost:8080' },
        changeOrigin: true,
        secure: false,
        logLevel: 'debug',
        onProxyReq: (proxyReq, req, res) => {
          console.log(`[PROXY] ${req.method} ${req.url} => ${proxyReq.getHeader('host')}${proxyReq.path}`)
        },
        pathRewrite: {
          '^/recipes/api': '' // Remove '/recipes' from the request path
        }
      }
    }
  },
  pwa: {
    workboxOptions: {
      runtimeCaching: [
        {
          urlPattern: new RegExp(`^${process.env.VUE_APP_API_URL}`),
          handler: 'NetworkFirst',
          options: {
            networkTimeoutSeconds: 10,
            cacheName: 'api-cache',
            cacheableResponse: {
              statuses: [0, 200]
            }
          }
        },
        {
          urlPattern: /\.(?:png|jpg|jpeg|svg)$/,
          handler: 'CacheFirst',
          options: {
            cacheName: 'images-cache',
            expiration: {
              maxEntries: 100,
              maxAgeSeconds: 30 * 24 * 60 * 60 // 30 Days
            }
          }
        },
        {
          urlPattern: /\.(?:js|css)$/,
          handler: 'StaleWhileRevalidate',
          options: {
            cacheName: 'static-assets-cache'
          }
        }
      ]
    }
  }
})
