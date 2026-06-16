import request from '@/utils/request'

export function uploadImage(file) {
  const data = new FormData()
  data.append('file', file)
  return request({
    url: '/upload/uploadImage',
    method: 'post',
    data,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}
