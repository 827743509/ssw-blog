<template>
  <div>
    <div class="menu-container">
      <div class="nav-logo">
        <img class="logo-img" src="@/assets/logo.png" />
        <span class="logo-text">salt-soda-water</span>
      </div>
      <div class="nav-menu-search">
        <a-menu v-model:selectedKeys="current" mode="horizontal">
          <a-menu-item key="1">
            <router-link to="/">
              <a-icon type="mail" />博客
            </router-link>
          </a-menu-item>
          <a-menu-item key="2">
            <router-link to="/note/list">
              <a-icon type="mail" />笔记
            </router-link>
          </a-menu-item>
          <a-menu-item key="3">
            <router-link to="/about">
              <a-icon type="mail" />关于我
            </router-link>
          </a-menu-item>
        </a-menu>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      current: ['1']
    }
  }
}
</script>

<style scoped>
.menu-container {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  padding: 0 60px;
  line-height: 70px;
  background-color: #fff;
  margin-bottom: 10px;
}

.nav-logo {
  display: flex;
  flex-direction: row;
  align-items: center;
}

.logo-img {
  width: 40px;
  height: 40px;
  margin-right: 10px;
}

.logo-text {
  font-size: 24px;
}

.nav-menu-search {
  display: flex;
  flex-direction: row;
  align-items: center;
  margin-right: 40px;
}

.ant-menu {
  font-size: 16px !important;
}

.ant-menu-horizontal {
  border-bottom: none !important;
}
</style>
