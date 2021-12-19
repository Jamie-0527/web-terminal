<template>
  <div class="father">
    <VueDragResize
      :w="200"
      :h="700"
      @resizing="handleResizing"
      :resizable="true"
    >
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
        host: '192.168.127.132',
        port: '22',
        username: 'jamie',
        password: 'root',
        height: 700,
        width: 900,
      }
    }
  },
  methods: {
    handleResizing(newRect) {
      this.hostInfo.height = newRect.height
      this.hostInfo.width = newRect.width
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
