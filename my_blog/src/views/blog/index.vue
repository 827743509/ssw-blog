<template>
  <div>
    <div class="blog-bg">
      <div class="blog-title">{{ blog.blogTitle }}</div>
      <div class="blog-item">
        <div class="blog-time">发布时间：{{ blog.createdTime }}</div>
      </div>
      <div class="blog-content" v-html="blog.blogContent" />
    </div>
  </div>
</template>

<script>
import blogApi from '@/api/blog'

export default {
  data() {
    return {
      blog: {}
    }
  },
  watch: {
    '$route.params.id'(blogId) {
      this.getBlogInfo(blogId)
    }
  },
  created() {
    this.getBlogInfo(this.$route.params.id)
  },
  methods: {
    getBlogInfo(blogId) {
      blogApi.readById(blogId).then(res => {
        this.blog = res.data
      })
    }
  }
}
</script>

<style scoped>
.blog-bg {
  background: #fff;
  padding: 40px 25px;
}

.blog-title {
  text-align: center;
  font-size: 24px;
  font-weight: bold;
  line-height: 70px;
}

.blog-item {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  margin-bottom: 30px;
  margin-top: 10px;
}

.blog-content :deep(pre) {
  padding: 14px 16px;
  margin: 16px 0;
  overflow-x: auto;
  color: #e5e7eb;
  white-space: pre;
  background: #1f2937;
  border-radius: 4px;
}

.blog-content :deep(pre code) {
  padding: 0;
  font-family: Consolas, Monaco, "Courier New", monospace;
  font-size: 14px;
  line-height: 1.6;
  color: inherit;
  background: transparent;
}
</style>
