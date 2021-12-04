<template>
  <div class="father">
    <VueDragResize :is-resizable="false">
      <terminal :host-info="hostInfo"/>
    </VueDragResize>
  </div>
</template>

<script>
import terminal from './terminal'
import VueDragResize from 'vue-drag-resize'
export default {
  name: "test",
  components: {
    terminal,
    VueDragResize
  },
  data() {
    return {
      hostInfo: {
        host: '192.168.127.128',//IP
        port: '22',//端口号
        username: 'jamie',//用户名
        password: 'root'//密码
      }
    }
  }
}
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
}
</style>
