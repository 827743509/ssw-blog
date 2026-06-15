<template>
  <div class="background">
    <a-form :label-col="{ span: 5 }" :wrapper-col="{ span: 12 }">
      <a-form-item label="卡组数量">
        <a-select style="width: 120px" v-model="param.dickCount">
          <a-select-option :key="i" v-for="i in dickCountOptions">{{ i }}</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="卡组单卡启动数量">
        <a-select style="width: 120px" v-model="param.singleCard">
          <a-select-option :key="i" v-for="i in cardOptions">{{ i }}</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="卡组双卡启动A(不包含单卡)">
        <a-select style="width: 120px" v-model="param.doubleCardOne">
          <a-select-option :key="i" v-for="i in cardOptions">{{ i }}</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="卡组双卡启动B(不包含单卡)">
        <a-select style="width: 120px" v-model="param.doubleCardTwo">
          <a-select-option :key="i" v-for="i in cardOptions">{{ i }}</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="不卡手概率">
        <div style="color:red">{{ result }}</div>
      </a-form-item>
      <a-form-item label="点一下">
        <a-button @click="submit" html-type="submit" type="primary">计算</a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<script>
import utilApi from '@/api/util'
export default {
  data() {
    return {
      result: '0%',
      dickCountOptions: [],
      param: {
        dickCount: 40,
        singleCard: 0,
        doubleCardOne: 0,
        doubleCardTwo: 0
      }
    }
  },
  computed: {
    cardOptions() {
      const cardOptions = []
      for (let i = 0; i <= this.param.dickCount; i++) {
        cardOptions.push(i)
      }
      return cardOptions
    }
  },
  created() {
    for (let i = 40; i <= 60; i++) {
      this.dickCountOptions.push(i)
    }
  },
  methods: {
    submit() {
      utilApi.getYGOJammingRate(this.param).then(res => {
        this.result = res.data
      })
    }
  }
}
</script>

<style scoped>
.background {
  background-image: url('http://image.aimeiliyalove.xyz/background.jpg');
  background-size: cover;
}
</style>
