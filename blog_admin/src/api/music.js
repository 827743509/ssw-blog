import request from '@/utils/request'

export function fetchMusicPage(data) {
  return request({
    url: '/music/getByPage',
    method: 'post',
    data
  })
}

export function fetchMusic(id) {
  return request({
    url: `/music/get/${id}`,
    method: 'get'
  })
}

export function createMusic(data) {
  return request({
    url: '/music/save',
    method: 'post',
    data
  })
}

export function updateMusic(data) {
  return request({
    url: '/music/update',
    method: 'put',
    data
  })
}

export function deleteMusic(id) {
  return request({
    url: `/music/delete/${id}`,
    method: 'delete'
  })
}

export function enableMusic(id) {
  return request({
    url: `/music/enable/${id}`,
    method: 'put'
  })
}

export function disableMusic(id) {
  return request({
    url: `/music/disable/${id}`,
    method: 'put'
  })
}
