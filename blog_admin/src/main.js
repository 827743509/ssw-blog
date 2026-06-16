import { createApp } from 'vue'

import 'normalize.css/normalize.css'

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import locale from 'element-plus/lib/locale/lang/en'

import '@/styles/index.scss'

import App from './App'
import store from './store'
import router from './router'

import icons from '@/icons'
import '@/permission'

if (process.env.NODE_ENV === 'production') {
  const { mockXHR } = require('../mock')
  mockXHR()
}

createApp(App)
  .use(store)
  .use(router)
  .use(ElementPlus, { locale })
  .use(icons)
  .mount('#app')
