<template>
  <div>
    <div class="button-container">
      <div class="button-text">{{ name }}</div>
      <div class="button-menu">
        <a-menu v-model:selectedKeys="current" mode="horizontal" @click="changeSort">
          <a-menu-item key="created_time">最新</a-menu-item>
        </a-menu>
      </div>
    </div>
    <div>
      <span class="type-title">分类：</span>
      <span
        v-for="item in typeList"
        :key="item.typeId"
        class="type-name-select"
        @click="changeselect($event, item.typeId)"
      >{{ item.typeName }}</span>
    </div>
    <a-input-search
      v-model:value="page.params.blogTitle"
      allow-clear
      class="search"
      placeholder="在这里能搜到奇怪的东西"
      style="width: 200px"
      @search="changeKeyWowrds"
    />
    <div class="blog-list-container">
      <a-card v-for="item in List" :key="item.blogId" :body-style="blogBodyStyle" class="blog-card">
        <div class="blog-main">
          <div v-if="item.blogImage" class="blog-image">
            <img :src="item.blogImage" class="blog-cover" />
          </div>
          <router-link
            :class="item.blogImage ? 'image-blog' : 'blog-container'"
            :to="'/info/' + item.blogId"
          >
            <div class="blog-title">{{ item.blogTitle }}</div>
            <div class="blog-bottom">
              <div class="blog-type">
                分类：
                <a-tag v-for="subitem in item.type" :key="subitem.typeId" color="green">
                  {{ subitem.typeName }}
                </a-tag>
              </div>

              <div class="blog-meta">
                <div class="blog-time">{{ item.createdTime }}</div>
              </div>
            </div>
          </router-link>
        </div>
      </a-card>
    </div>
    <div class="blog-pagination">
      <a-pagination
        :default-current="1"
        :show-total="total => `共 ${total} 条`"
        :total="totalCount"
        show-quick-jumper
        @change="pageChange"
      />
    </div>
  </div>
</template>

<script>
import blogApi from '@/api/blog'
import typeApi from '@/api/type'

export default {
  props: {
    name: {
      type: String,
      default: '博客'
    }
  },
  data() {
    return {
      current: ['created_time'],
      blogBodyStyle: {
        padding: '18px'
      },
      typeList: [],
      List: [],
      totalCount: 0,
      page: {
        currentPage: 1,
        pageSize: 10,
        sortColumn: 'created_time',
        sortMethod: 'desc',
        params: {
          typeId: null,
          blogTitle: ''
        }
      }
    }
  },
  created() {
    typeApi.getList().then(res => {
      this.typeList = res.data
      this.typeList.unshift({
        typeId: 0,
        typeName: '全部'
      })
    })
    this.getByPage()
  },
  methods: {
    changeKeyWowrds() {
      this.page.currentPage = 1
      this.getByPage()
    },
    pageChange(pageNumber) {
      this.page.currentPage = pageNumber
      this.getByPage()
    },
    getByPage() {
      blogApi.getByPage(this.page).then(res => {
        this.List = res.data.list
        this.totalCount = res.data.totalCount
      })
    },
    changeSort(e) {
      this.page.sortColumn = e.key
      this.page.sortMethod = 'desc'
      this.getByPage()
    },
    changeselect(e, id) {
      document.getElementsByClassName('type-name-selected').forEach(item => {
        item.setAttribute('class', 'type-name-select')
      })
      e.target.setAttribute('class', 'type-name-selected')
      this.page.currentPage = 1
      this.page.params.typeId = id === 0 ? null : id
      this.getByPage()
    }
  }
}
</script>

<style scoped>
.search {
  padding: 5px 0px;
}
.type-title {
  font-size: 20px;
}
.type-name-selected {
  padding: 0px 10px;
  border-radius: 30px;
  background: #889;
  border: 1px solid cyan;
}
.type-name-select {
  padding: 0px 10px;
  border-radius: 30px;
  border: 1px solid cyan;
}
.button-container {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  padding: 0 5px;
  align-items: center;
  line-height: 70px;
}

.button-text {
  font-size: 20px;
}

.ant-menu-horizontal {
  border-bottom: none !important;
}

.ant-menu {
  background: none !important;
}

a {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  color: #3e4149;
}

.blog-container {
  width: 100%;
  min-height: 130px;
}

.image-blog {
  width: 550px;
  min-height: 130px;
}

a:hover {
  color: #3e4149;
}

.blog-title {
  font-size: 16px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  margin-bottom: 10px;
}

.blog-main {
  display: flex;
  flex-direction: row;
  align-items: center;
}

.blog-bottom {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
}

.blog-meta {
  font-size: 12px;
  color: #9c9ea8;
}

.blog-image {
  width: 230px;
  margin-right: 20px;
}

.blog-cover {
  width: 100%;
  border-radius: 5px;
}

.blog-card {
  margin-bottom: 15px;
  border-radius: 5px;
}
</style>
