import request from '@/utils/request'

export function fetchBlogPage(data) {
  return request({
    url: '/blog/getByPage',
    method: 'post',
    data
  })
}

export function fetchBlog(id) {
  return request({
    url: `/blog/get/${id}`,
    method: 'get'
  })
}

export function createBlog(data) {
  return request({
    url: '/blog/save',
    method: 'post',
    data
  })
}

export function updateBlog(data) {
  return request({
    url: '/blog/update',
    method: 'put',
    data
  })
}

export function deleteBlog(id) {
  return request({
    url: `/blog/delete/${id}`,
    method: 'delete'
  })
}
