<template>
  <div>
    <a-card title="音乐欣赏" :head-style="headStyle">
      <div>
        <aplayer v-if="music.src" :music="music" :list="musicList" :show-lrc="true" />
      </div>
    </a-card>
  </div>
</template>

<script>
import musicApi from '@/api/music'
export default {
  data() {
    return {
      headStyle: {
        fontSize: '18px',
        fontWeight: 'bold',
        lineHeight: '15px',
        borderLeft: '5px solid #409eff'
      },
      music: {},
      musicList: []
    }
  },
  async created() {
    await this.getMusicList()
  },
  methods: {
    async getMusicList() {
      await musicApi.getList().then(res => {
        this.musicList = res.data.map(item => ({
          title: item.name,
          artist: item.artist,
          src: item.url,
          pic: item.cover,
          lrc: item.lrc
        }))
        this.music = this.musicList[0] || {}
      })
    }
  }
}
</script>

<style>

</style>
