<template>
  <div class="rich-text-editor" :class="{ 'is-focused': focused }">
    <div class="editor-toolbar">
      <el-button-group>
        <el-button size="small" title="加粗" @click="format('bold')">B</el-button>
        <el-button size="small" title="斜体" @click="format('italic')">I</el-button>
        <el-button size="small" title="下划线" @click="format('underline')">U</el-button>
      </el-button-group>
      <el-button-group>
        <el-button size="small" title="无序列表" @click="format('insertUnorderedList')">•</el-button>
        <el-button size="small" title="有序列表" @click="format('insertOrderedList')">1.</el-button>
      </el-button-group>
      <el-button size="small" title="插入链接" @click="insertLink">链接</el-button>
      <el-button size="small" title="插入代码块" @click="insertCodeBlock">代码块</el-button>
      <el-button size="small" :loading="uploading" title="上传图片" @click="openFilePicker">图片</el-button>
      <input ref="fileInput" class="file-input" type="file" accept="image/*" @change="handleFileChange">
    </div>
    <div
      ref="editor"
      class="editor-content"
      :style="{ minHeight }"
      contenteditable="true"
      :data-placeholder="placeholder"
      @focus="handleFocus"
      @blur="handleBlur"
      @input="handleInput"
      @paste="handlePaste"
      @keyup="saveRange"
      @mouseup="saveRange"
    ></div>
  </div>
</template>

<script>
import { ElMessage } from 'element-plus'
import { uploadImage } from '@/api/upload'

function cleanHtml(html) {
  const template = document.createElement('template')
  template.innerHTML = html || ''
  template.content.querySelectorAll('script, style, iframe, object, embed').forEach(node => node.remove())
  template.content.querySelectorAll('*').forEach(node => {
    Array.from(node.attributes).forEach(attr => {
      if (/^on/i.test(attr.name) || /^javascript:/i.test(attr.value)) {
        node.removeAttribute(attr.name)
      }
    })
  })
  return template.innerHTML
}

export default {
  name: 'RichTextEditor',
  props: {
    modelValue: {
      type: String,
      default: ''
    },
    placeholder: {
      type: String,
      default: '请输入内容'
    },
    minHeight: {
      type: String,
      default: '260px'
    }
  },
  emits: ['update:modelValue', 'blur'],
  data() {
    return {
      focused: false,
      uploading: false,
      savedRange: null
    }
  },
  watch: {
    modelValue: {
      immediate: true,
      handler(value) {
        this.$nextTick(() => {
          const editor = this.$refs.editor
          if (editor && editor.innerHTML !== (value || '')) {
            editor.innerHTML = value || ''
          }
        })
      }
    }
  },
  methods: {
    handleFocus() {
      this.focused = true
      this.saveRange()
    },
    handleBlur() {
      this.focused = false
      this.saveRange()
      this.$emit('blur')
    },
    handleInput() {
      const html = cleanHtml(this.$refs.editor.innerHTML)
      if (html !== this.$refs.editor.innerHTML) {
        this.$refs.editor.innerHTML = html
      }
      this.$emit('update:modelValue', html)
    },
    saveRange() {
      const selection = window.getSelection()
      if (selection && selection.rangeCount > 0 && this.$refs.editor.contains(selection.anchorNode)) {
        this.savedRange = selection.getRangeAt(0).cloneRange()
      }
    },
    restoreRange() {
      const editor = this.$refs.editor
      editor.focus()
      if (!this.savedRange) return
      const selection = window.getSelection()
      selection.removeAllRanges()
      selection.addRange(this.savedRange)
    },
    format(command, value = null) {
      this.restoreRange()
      document.execCommand(command, false, value)
      this.handleInput()
      this.saveRange()
    },
    insertLink() {
      const url = window.prompt('请输入链接地址')
      if (!url) return
      this.format('createLink', url)
    },
    insertCodeBlock() {
      this.restoreRange()
      const selection = window.getSelection()
      const selectedText = selection ? selection.toString() : ''
      const code = document.createElement('code')
      code.textContent = selectedText || '请输入代码'

      document.execCommand(
        'insertHTML',
        false,
        `<pre>${code.outerHTML}</pre><p><br></p>`
      )
      this.handleInput()
      this.saveRange()
    },
    openFilePicker() {
      this.saveRange()
      this.$refs.fileInput.click()
    },
    async handleFileChange(event) {
      const file = event.target.files && event.target.files[0]
      event.target.value = ''
      if (file) {
        await this.uploadAndInsertImage(file)
      }
    },
    async handlePaste(event) {
      const files = Array.from(event.clipboardData?.items || [])
        .filter(item => item.kind === 'file' && item.type.startsWith('image/'))
        .map(item => item.getAsFile())
        .filter(Boolean)

      if (!files.length) return
      event.preventDefault()
      this.saveRange()
      for (const file of files) {
        await this.uploadAndInsertImage(file)
      }
    },
    async uploadAndInsertImage(file) {
      if (!file.type.startsWith('image/')) {
        ElMessage.error('只能上传图片文件')
        return
      }
      this.uploading = true
      try {
        const res = await uploadImage(file)
        this.insertImage(res.data)
        ElMessage.success('图片上传成功')
      } finally {
        this.uploading = false
      }
    },
    insertImage(url) {
      this.restoreRange()
      document.execCommand('insertHTML', false, `<img src="${url}" alt="" /><p><br></p>`)
      this.handleInput()
      this.saveRange()
    }
  }
}
</script>

<style lang="scss" scoped>
.rich-text-editor {
  width: 100%;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  overflow: hidden;
  background: #fff;
  transition: border-color 0.2s;
}

.rich-text-editor.is-focused {
  border-color: #409eff;
}

.editor-toolbar {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 8px;
  border-bottom: 1px solid #ebeef5;
  background: #f8fafc;
}

.file-input {
  display: none;
}

.editor-content {
  padding: 10px 12px;
  line-height: 1.7;
  outline: none;
  overflow-y: auto;
  word-break: break-word;
}

.editor-content:empty::before {
  content: attr(data-placeholder);
  color: #a8abb2;
}

.editor-content :deep(img) {
  max-width: 100%;
  height: auto;
  display: block;
  margin: 8px 0;
  border-radius: 4px;
}

.editor-content :deep(p) {
  margin: 0 0 8px;
}

.editor-content :deep(pre) {
  padding: 14px 16px;
  margin: 10px 0;
  overflow-x: auto;
  color: #e5e7eb;
  white-space: pre;
  background: #1f2937;
  border-radius: 4px;
}

.editor-content :deep(pre code) {
  padding: 0;
  font-family: Consolas, Monaco, "Courier New", monospace;
  font-size: 14px;
  line-height: 1.6;
  color: inherit;
  background: transparent;
}
</style>
