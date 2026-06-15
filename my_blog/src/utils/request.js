import axios from 'axios'
import { message } from 'ant-design-vue'

const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API,
  timeout: 5000
})

service.interceptors.request.use(
  config => config,
  error => Promise.reject(error)
)

service.interceptors.response.use(
  response => {
    const res = response.data
    if (res.code && res.code !== 20000) {
      message.error(res.msg)
      return Promise.reject(new Error(res.msg || 'Error'))
    }
    return res
  },
  error => {
    console.log('err' + error)
    return Promise.reject(error)
  }
)

export default service
