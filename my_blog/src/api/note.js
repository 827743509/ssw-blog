import request from "@/utils/request";
var group_name = "note";
export default {
  getByPage(page) {
    // 前台查询
    return request({
      url: `/${group_name}/getByPage`,
      method: "post",
      data: page
    });
  },
  getById(id) {
    // 根据id阅读
    return request({
      url: `/${group_name}/get/${id}`,
      method: "get"
    });
  }
};
