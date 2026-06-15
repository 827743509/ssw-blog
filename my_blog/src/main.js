import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import 'ant-design-vue/dist/antd.css'
import Antd from 'ant-design-vue'
import VueAPlayer from 'vue3-aplayer'
import AntIcon from '@/components/AntIcon'
import '@/permission'

createApp(App)
  .component('a-icon', AntIcon)
  .use(router)
  .use(Antd)
  .use(VueAPlayer, {
    defaultCover: 'https://github.com/u3u.png'
  })
  .mount('#app')
