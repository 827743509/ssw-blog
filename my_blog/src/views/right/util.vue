<template>
  <div>
    <a-card :head-style="headStyle" title="小工具">
      <div class="link-container">
        <router-link to="/util/YGOJammingRate">
          <a-icon type="mail" />ygo卡手率计算
        </router-link>
      </div>
    </a-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      headStyle: {
        fontSize: '18px',
        fontWeight: 'bold',
        lineHeight: '15px',
        borderLeft: '5px solid #409eff'
      }
    }
  },
  created() {},
  methods: {}
}
</script>

<style scoped>
.link-container {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
}

.link-container a {
  font-size: 24px;
  min-width: 60px;
  margin-right: 15px;
}
</style>
