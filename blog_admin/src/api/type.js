import request from '@/utils/request'

export function fetchTypeList() {
  return request({
    url: '/type/listBack',
    method: 'get'
  })
}
