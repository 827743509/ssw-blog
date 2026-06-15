"use strict";
const path = require("path");
const CompressionPlugin = require("compression-webpack-plugin");
function resolve(dir) {
  return path.join(__dirname, dir);
}

const name = "盐汽水的博客"; // page title
const port = 9002; // dev port

// All configuration item explanations can be find in https://cli.vuejs.org/config/
module.exports = {
  publicPath: "/",
  outputDir: "dist",
  assetsDir: "static",
  lintOnSave: false,
  productionSourceMap: false,
  devServer: {
    port: port,
    open: true,
    client: {
      overlay: {
        warnings: false,
        errors: true,
        runtimeErrors: false
      }
    },
    proxy: {
      [process.env.VUE_APP_BASE_API]: {
        target: "http://127.0.0.1:9000",
        changeOrigin: true,
        pathRewrite: {
          ["^" + process.env.VUE_APP_BASE_API]: ""
        }
      }
    }
  },
  configureWebpack: {
    // provide the app's title in webpack's name field, so that
    // it can be accessed in index.html to inject the correct title.
    name: name,
    resolve: {
      alias: {
        "@": resolve("src")
      }
    },
    plugins: [
      new CompressionPlugin({
        // gzip压缩配置
        test: /\.js$|\.html$|\.css/, // 匹配文件名
        threshold: 10240, // 对超过10kb的数据进行压缩
        deleteOriginalAssets: false // 是否删除原文件
      })
    ]
  },
  chainWebpack(config) {
    config.module
      .rule("js")
      .exclude.add(/[\\/]node_modules[\\/]@babel[\\/]runtime[\\/]/)
      .end();
  }
};
