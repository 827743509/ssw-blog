<template>
  <div class="manage-page">
    <div class="toolbar">
      <el-form :inline="true" :model="query" class="query-form">
        <el-form-item label="标题">
          <el-input v-model="query.blogTitle" clearable placeholder="请输入博客标题" @keyup.enter="handleSearch" />
        </el-form-item>
        <el-form-item label="分类">
          <el-select v-model="query.typeId" clearable placeholder="全部分类">
            <el-option v-for="item in typeOptions" :key="item.typeId" :label="item.typeName" :value="item.typeId" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
      <el-button type="primary" @click="openCreate">新增博客</el-button>
    </div>

    <el-table v-loading="loading" :data="list" border class="data-table">
      <el-table-column label="封面" width="96">
        <template #default="{ row }">
          <el-image v-if="row.blogImage" class="cover" :src="row.blogImage" fit="cover" />
          <span v-else class="empty-text">暂无</span>
        </template>
      </el-table-column>
      <el-table-column prop="blogTitle" label="标题" min-width="220" show-overflow-tooltip />
      <el-table-column prop="typeName" label="分类" min-width="160" show-overflow-tooltip />
      <el-table-column prop="createdTime" label="创建时间" width="180" />
      <el-table-column prop="updateTime" label="更新时间" width="180" />
      <el-table-column label="操作" width="180" fixed="right">
        <template #default="{ row }">
          <el-button type="primary" link @click="openEdit(row)">编辑</el-button>
          <el-button type="danger" link @click="handleDelete(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="pagination">
      <el-pagination
        v-model:current-page="page.currentPage"
        v-model:page-size="page.pageSize"
        :page-sizes="[10, 20, 50]"
        layout="total, sizes, prev, pager, next, jumper"
        :total="page.totalCount"
        @size-change="fetchList"
        @current-change="fetchList"
      />
    </div>

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="720px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
        <el-form-item label="标题" prop="blogTitle">
          <el-input v-model="form.blogTitle" placeholder="请输入博客标题" />
        </el-form-item>
        <el-form-item label="封面图片" prop="blogImage">
          <div class="upload-field">
            <el-upload
              class="cover-uploader"
              :show-file-list="false"
              :http-request="handleCoverUpload"
              :before-upload="beforeCoverUpload"
            >
              <img v-if="form.blogImage" :src="form.blogImage" class="cover-preview" />
              <div v-else class="cover-placeholder">
                <span>上传封面</span>
              </div>
            </el-upload>
            <el-input v-model="form.blogImage" placeholder="上传后自动填充，也可手动粘贴图片 URL" />
          </div>
        </el-form-item>
        <el-form-item label="分类">
          <el-select v-model="form.typeIds" multiple clearable placeholder="请选择分类">
            <el-option v-for="item in typeOptions" :key="item.typeId" :label="item.typeName" :value="item.typeId" />
          </el-select>
        </el-form-item>
        <el-form-item label="内容" prop="blogContent">
          <el-input v-model="form.blogContent" type="textarea" :rows="10" placeholder="请输入博客内容" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" :loading="submitLoading" @click="handleSubmit">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { ElMessage, ElMessageBox } from 'element-plus'
import { createBlog, deleteBlog, fetchBlog, fetchBlogPage, updateBlog } from '@/api/blog'
import { fetchTypeList } from '@/api/type'
import { uploadImage } from '@/api/upload'

const defaultForm = () => ({
  blogId: '',
  blogTitle: '',
  blogImage: '',
  blogContent: '',
  typeIds: []
})

export default {
  name: 'BlogManage',
  data() {
    return {
      loading: false,
      submitLoading: false,
      dialogVisible: false,
      list: [],
      typeOptions: [],
      query: {
        blogTitle: '',
        typeId: ''
      },
      page: {
        currentPage: 1,
        pageSize: 10,
        totalCount: 0
      },
      form: defaultForm(),
      rules: {
        blogTitle: [{ required: true, message: '请输入博客标题', trigger: 'blur' }],
        blogContent: [{ required: true, message: '请输入博客内容', trigger: 'blur' }]
      }
    }
  },
  computed: {
    dialogTitle() {
      return this.form.blogId ? '编辑博客' : '新增博客'
    }
  },
  created() {
    this.fetchTypes()
    this.fetchList()
  },
  methods: {
    async fetchTypes() {
      const res = await fetchTypeList()
      this.typeOptions = res.data || []
    },
    async fetchList() {
      this.loading = true
      try {
        const res = await fetchBlogPage({
          currentPage: this.page.currentPage,
          pageSize: this.page.pageSize,
          params: {
            blogTitle: this.query.blogTitle,
            typeId: this.query.typeId
          }
        })
        const data = res.data || {}
        this.list = data.list || []
        this.page.totalCount = data.totalCount || 0
      } catch (error) {
        this.list = []
        this.page.totalCount = 0
        ElMessage.error('博客列表加载超时，请确认后端服务和数据库连接状态')
      } finally {
        this.loading = false
      }
    },
    handleSearch() {
      this.page.currentPage = 1
      this.fetchList()
    },
    handleReset() {
      this.query = { blogTitle: '', typeId: '' }
      this.handleSearch()
    },
    openCreate() {
      this.form = defaultForm()
      this.dialogVisible = true
      this.$nextTick(() => this.$refs.formRef && this.$refs.formRef.clearValidate())
    },
    async openEdit(row) {
      const res = await fetchBlog(row.blogId)
      this.form = { ...defaultForm(), ...(res.data || {}) }
      this.dialogVisible = true
      this.$nextTick(() => this.$refs.formRef && this.$refs.formRef.clearValidate())
    },
    beforeCoverUpload(file) {
      const isImage = file.type.startsWith('image/')
      if (!isImage) {
        ElMessage.error('只能上传图片文件')
      }
      return isImage
    },
    async handleCoverUpload(options) {
      const res = await uploadImage(options.file)
      this.form.blogImage = res.data
      ElMessage.success('封面上传成功')
    },
    handleSubmit() {
      this.$refs.formRef.validate(async valid => {
        if (!valid) return
        this.submitLoading = true
        try {
          if (this.form.blogId) {
            await updateBlog(this.form)
            ElMessage.success('博客更新成功')
          } else {
            await createBlog(this.form)
            ElMessage.success('博客新增成功')
          }
          this.dialogVisible = false
          this.fetchList()
        } finally {
          this.submitLoading = false
        }
      })
    },
    async handleDelete(row) {
      await ElMessageBox.confirm(`确认删除「${row.blogTitle}」吗？`, '删除确认', { type: 'warning' })
      await deleteBlog(row.blogId)
      ElMessage.success('删除成功')
      this.fetchList()
    }
  }
}
</script>

<style lang="scss" scoped>
.manage-page {
  padding: 20px;
}

.toolbar {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  gap: 16px;
  padding: 18px 20px 0;
  margin-bottom: 16px;
  border: 1px solid #ebeef5;
  border-radius: 8px;
  background: #fff;
}

.query-form {
  flex: 1;
}

.data-table {
  width: 100%;
}

.cover {
  width: 56px;
  height: 40px;
  border-radius: 4px;
  background: #f5f7fa;
}

.upload-field {
  display: grid;
  grid-template-columns: 140px 1fr;
  gap: 12px;
  width: 100%;
}

.cover-uploader {
  width: 140px;
}

.cover-preview,
.cover-placeholder {
  width: 140px;
  height: 84px;
  border-radius: 6px;
}

.cover-preview {
  display: block;
  object-fit: cover;
}

.cover-placeholder {
  display: flex;
  align-items: center;
  justify-content: center;
  border: 1px dashed #c0c4cc;
  color: #909399;
  background: #fafafa;
  cursor: pointer;
}

.empty-text {
  color: #c0c4cc;
}

.pagination {
  display: flex;
  justify-content: flex-end;
  padding: 18px 0 0;
}
</style>
