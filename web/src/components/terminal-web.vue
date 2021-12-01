<template>
  <div>
    <terminal :host-info="hostInfo"></terminal>
  </div>
</template>

<script>
import terminal from './terminal'
export default {
  name: "test",
  components: {terminal},
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
