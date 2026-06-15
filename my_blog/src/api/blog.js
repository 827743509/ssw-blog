import request from '@/utils/request'

const groupName = 'blog'

export default {
  getByPage(page) {
    return request({
      url: `/${groupName}/getByPage`,
      method: 'post',
      data: page
    })
  },
  getTimeLine() {
    return request({
      url: `/${groupName}/getTimeLine`,
      method: 'get'
    })
  },
  readById(id) {
    return request({
      url: `/${groupName}/read/${id}`,
      method: 'get'
    })
  }
}
