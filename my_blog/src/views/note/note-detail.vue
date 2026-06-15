<template>
  <div>
    <div class="blog-bg">
      <div class="blog-title">{{ note.noteTitle }}</div>
      <div class="blog-item">
        <div class="blog-time">发布时间：{{ note.createdTime }}</div>
      </div>
      <div class="blog-content" v-html="note.noteContent" />
    </div>
  </div>
</template>

<script>
import noteApi from '@/api/note'

export default {
  data() {
    return {
      note: {}
    }
  },
  created() {
    this.getNoteDetail(this.$route.params.id)
  },
  methods: {
    getNoteDetail(noteId) {
      noteApi.getById(noteId).then(res => {
        this.note = res.data
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
