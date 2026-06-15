<template>
  <component :is="iconComponent" v-if="iconComponent" />
</template>

<script>
import {
  EyeOutlined,
  MailOutlined
} from '@ant-design/icons-vue'

const icons = {
  eye: EyeOutlined,
  mail: MailOutlined
}

export default {
  name: 'AntIcon',
  props: {
    type: {
      type: String,
      required: true
    }
  },
  computed: {
    iconComponent() {
      return icons[this.type]
    }
  }
}
</script>
