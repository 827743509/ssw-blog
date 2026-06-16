<template>
  <div class="manage-page">
    <div class="toolbar">
      <el-form :inline="true" :model="query" class="query-form">
        <el-form-item label="歌曲">
          <el-input v-model="query.name" clearable placeholder="请输入歌曲名" @keyup.enter="handleSearch" />
        </el-form-item>
        <el-form-item label="歌手">
          <el-input v-model="query.artist" clearable placeholder="请输入歌手" @keyup.enter="handleSearch" />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="query.enable" clearable placeholder="全部状态">
            <el-option label="启用" :value="1" />
            <el-option label="停用" :value="0" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
      <el-button type="primary" @click="openCreate">新增音乐</el-button>
    </div>

    <el-table v-loading="loading" :data="list" border class="data-table">
      <el-table-column label="封面" width="96">
        <template #default="{ row }">
          <el-image v-if="row.cover" class="cover" :src="row.cover" fit="cover" />
          <span v-else class="empty-text">暂无</span>
        </template>
      </el-table-column>
      <el-table-column prop="name" label="歌曲" min-width="180" show-overflow-tooltip />
      <el-table-column prop="artist" label="歌手" min-width="160" show-overflow-tooltip />
      <el-table-column prop="url" label="播放地址" min-width="240" show-overflow-tooltip />
      <el-table-column label="状态" width="100">
        <template #default="{ row }">
          <el-tag :type="row.enable === 1 ? 'success' : 'info'">{{ row.enable === 1 ? '启用' : '停用' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createdTime" label="创建时间" width="180" />
      <el-table-column label="操作" width="230" fixed="right">
        <template #default="{ row }">
          <el-button type="primary" link @click="openEdit(row)">编辑</el-button>
          <el-button :type="row.enable === 1 ? 'warning' : 'success'" link @click="handleToggle(row)">
            {{ row.enable === 1 ? '停用' : '启用' }}
          </el-button>
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

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="680px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
        <el-form-item label="歌曲" prop="name">
          <el-input v-model="form.name" placeholder="请输入歌曲名" />
        </el-form-item>
        <el-form-item label="歌手" prop="artist">
          <el-input v-model="form.artist" placeholder="请输入歌手" />
        </el-form-item>
        <el-form-item label="播放地址" prop="url">
          <el-input v-model="form.url" placeholder="请输入播放地址" />
        </el-form-item>
        <el-form-item label="封面地址">
          <el-input v-model="form.cover" placeholder="请输入封面图片 URL" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.enable">
            <el-radio :label="1">启用</el-radio>
            <el-radio :label="0">停用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="歌词">
          <el-input v-model="form.lrc" type="textarea" :rows="7" placeholder="请输入歌词" />
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
import { createMusic, deleteMusic, disableMusic, enableMusic, fetchMusic, fetchMusicPage, updateMusic } from '@/api/music'

const defaultForm = () => ({
  id: '',
  name: '',
  artist: '',
  url: '',
  cover: '',
  lrc: '',
  enable: 1
})

export default {
  name: 'MusicManage',
  data() {
    return {
      loading: false,
      submitLoading: false,
      dialogVisible: false,
      list: [],
      query: {
        name: '',
        artist: '',
        enable: ''
      },
      page: {
        currentPage: 1,
        pageSize: 10,
        totalCount: 0
      },
      form: defaultForm(),
      rules: {
        name: [{ required: true, message: '请输入歌曲名', trigger: 'blur' }],
        artist: [{ required: true, message: '请输入歌手', trigger: 'blur' }],
        url: [{ required: true, message: '请输入播放地址', trigger: 'blur' }]
      }
    }
  },
  computed: {
    dialogTitle() {
      return this.form.id ? '编辑音乐' : '新增音乐'
    }
  },
  created() {
    this.fetchList()
  },
  methods: {
    async fetchList() {
      this.loading = true
      try {
        const res = await fetchMusicPage({
          currentPage: this.page.currentPage,
          pageSize: this.page.pageSize,
          params: {
            name: this.query.name,
            artist: this.query.artist,
            enable: this.query.enable
          },
          sortColumn: 'createdTime',
          sortMethod: 'desc'
        })
        const data = res.data || {}
        this.list = data.list || []
        this.page.totalCount = data.totalCount || 0
      } finally {
        this.loading = false
      }
    },
    handleSearch() {
      this.page.currentPage = 1
      this.fetchList()
    },
    handleReset() {
      this.query = { name: '', artist: '', enable: '' }
      this.handleSearch()
    },
    openCreate() {
      this.form = defaultForm()
      this.dialogVisible = true
      this.$nextTick(() => this.$refs.formRef && this.$refs.formRef.clearValidate())
    },
    async openEdit(row) {
      const res = await fetchMusic(row.id)
      this.form = { ...defaultForm(), ...row, ...(res.data || {}) }
      this.dialogVisible = true
      this.$nextTick(() => this.$refs.formRef && this.$refs.formRef.clearValidate())
    },
    handleSubmit() {
      this.$refs.formRef.validate(async valid => {
        if (!valid) return
        this.submitLoading = true
        try {
          if (this.form.id) {
            await updateMusic(this.form)
            ElMessage.success('音乐更新成功')
          } else {
            await createMusic(this.form)
            ElMessage.success('音乐新增成功')
          }
          this.dialogVisible = false
          this.fetchList()
        } finally {
          this.submitLoading = false
        }
      })
    },
    async handleToggle(row) {
      if (row.enable === 1) {
        await disableMusic(row.id)
        ElMessage.success('已停用')
      } else {
        await enableMusic(row.id)
        ElMessage.success('已启用')
      }
      this.fetchList()
    },
    async handleDelete(row) {
      await ElMessageBox.confirm(`确认删除「${row.name}」吗？`, '删除确认', { type: 'warning' })
      await deleteMusic(row.id)
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
  height: 56px;
  border-radius: 4px;
  background: #f5f7fa;
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
