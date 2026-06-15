import request from "@/utils/request";
var group_name = "util";
export default {
  getYGOJammingRate(param) {
    // 阅读
    return request({
      url: `/${group_name}/getYGOJammingRate`,
      method: "post",
      data: param
    });
  }
};
