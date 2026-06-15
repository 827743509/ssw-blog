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

</style>
