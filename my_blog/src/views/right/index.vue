<template>
  <div>
    <div class="my-container">
      <my-back />
      <music />
      <my-link />
      <my-util />
    </div>
  </div>
</template>

<script>
import MyBack from './my-back'
import Music from './music'
import MyLink from './link'
import MyUtil from './util'
export default {
  components: {
    MyBack,
    Music,
    MyLink,
    MyUtil
  }
}
</script>

<style scoped>
.my-container {
  width: 350px;
  padding: 0 10px;
}
</style>
