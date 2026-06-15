
<template>
  <div>
    <div class="button-container">
      <div class="button-text">卢本伟牛逼</div>
    </div>
    <div>
      <span class="type-title">分类：</span>
      <span
        :key="item.typeId"
        @click="changeselect($event, item.typeId)"
        class="type-name-select"
        v-for="item in typeList"
      >{{ item.typeName }}</span>
    </div>
    <a-input-search
      @search="changeKeyWowrds()"
      allow-clear
      class="search"
      placeholder="在这里能搜到奇怪的东西"
      style="width: 200px"
      v-model:value="page.params.noteTitle"
    />
    <div class="blog-list-container">
      <a-card
        :key="item.noteId"
        body-style="padding: '18px'"
        class="blog-card"
        v-for="item in List"
      >
        <div class="blog-main">
          <div class="blog-image" v-if="item.noteImage">
            <img :src="item.noteImage" class="blog-cover" />
          </div>
          <router-link
            :class="item.noteImage ? 'image-blog' : 'blog-container'"
            :to="'/note/detail/' + item.noteId"
          >
            <div class="blog-title">{{ item.noteTitle }}</div>
            <div class="blog-bottom">
              <div class="blog-type">
                分类：
                <a-tag
                  :key="subitem.typeId"
                  color="green"
                  v-for="subitem in item.type"
                >{{ subitem.typeName }}</a-tag>
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
        :show-total="(total) => `共 ${total} 条`"
        :total="totalCount"
        @change="pageChange"
        show-quick-jumper
      />
    </div>
  </div>
</template>

<script>
import noteApi from '@/api/note'
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
      current: ['createdTime'],
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
          noteTitle: ''
        },
        partFlag: true
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
      noteApi.getByPage(this.page).then(res => {
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
      document.getElementsByClassName('type-name-selected').forEach(e => {
        e.setAttribute('class', 'type-name-select')
      })
      e.target.setAttribute('class', 'type-name-selected')
      if (id === 0) {
        this.page.currentPage = 1
        this.page.params.typeId = null
      } else {
        this.page.currentPage = 1
        this.page.params.typeId = id
      }

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

.blog-comment {
  margin-bottom: 10px;
  font-size: 14px;
  color: #9c9ea8;
  line-height: 22px;
  max-height: 90px;
  overflow: hidden;
  text-overflow: ellipsis;
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
