<template>
  <div class="window">
    <div class="header"></div>
    <div id="terminal" class="terminal"></div>
  </div>
</template>
<!--
  仅供学习参考
-->

<script>
import 'xterm/css/xterm.css'
import { Terminal } from 'xterm'
import { FitAddon } from 'xterm-addon-fit'
import { AttachAddon } from 'xterm-addon-attach'
import SockJS from "sockjs-client";

export default {
  name: "docker-exec",
  data() {
    return {
      containerId: this.$route.query.containerId,
      term: null,
      socket: null,
    }
  },
  mounted() {
    this.initTerm()
  },
  beforeDestroy() {
    this.socket.close()
    this.term.dispose()
  },
  methods: {
    //初始化Xterm
    initTerm() {
      /*
      * 此处访问连接的由来请看以下两个链接中的文档
      * https://docs.docker.com/engine/api/v1.41/#operation/ContainerAttachWebsocket
      * https://docs.docker.com/engine/reference/commandline/dockerd/#daemon-socket-option
      * 注意：记得开启目标主机的防火墙端口
      * */
      let url = 'http://localhost:8080/stomp/websocketJS?width=100&height=50&ip=192.168.127.128&containerId=2543842e5824b8b4a094575475a4cb339ae5c22d57a68559ce73f6a1568f8610';
      const term = new Terminal({
        cursorBlink: true, // 光标闪烁
        cursorStyle: "block", // 光标样式 null | 'block' | 'underline' | 'bar'
        scrollback: 800, //回滚
        tabStopWidth: 8, //制表宽度
        screenKeys: true,
        fontFamily: "Consolas",
        fontSize: 22,
        fontWeightBold: "bold",
        theme: {
          foreground: "#24cc3d"
        },
      });
      const fitAddon = new FitAddon();
      const socket = new SockJS(url)
      const attachAddon = new AttachAddon(socket)
      term.loadAddon(fitAddon);
      term.loadAddon(attachAddon);
      term.open(document.getElementById('terminal'));
      fitAddon.fit();
      term.focus();
      // 窗口尺寸变化时，终端尺寸自适应
      window.onresize = function() {
        fitAddon.fit()
      }
      this.term = term;
      this.socket = socket;
      this.initSocket();
    },
    //初始化websocket
    initSocket() {
      this.socketOnOpen();
      this.socketOnMessage();
      this.socketOnClose();
      this.socketOnError();
    },
    socketOnOpen() {
      this.socket.onopen = () => {
        this.term.write('Connecting...\r');
        this.socket.send('\n');
      }
    },
    // 回显字符
    socketOnMessage() {
      let _this = this;
      this.socket.onmessage = (evt) => {
        let data = evt.data;
        _this.term.write(data);
      }
    },
    socketOnClose() {
      this.socket.onclose = () => {
        // console.log('close socket')
      }
    },
    socketOnError() {
      let _this = this;
      this.socket.onerror = (error) => {
        //连接失败回调
        _this.term.write('Error: ' + error + '\r\n');
      }
    },
  }
}
</script>
<style scoped>
* {
  margin: 0;
  padding: 0;
}
.window {
  width: 1000px;
  margin: 0 auto;
}

.header {
  background-color: #E0E0E0;
  border-top-left-radius: 6px;
  border-top-right-radius: 6px;
  padding: 10px;
}

.header::before {
  content: '';
  display: inline-block;
  width: 12px;
  height: 12px;
  border-radius: 50%;
  background-color: #fd6458;
  box-shadow: 20px 0 0 #ffbf2b, 40px 0 0 #24cc3d;
  margin-left: -950px;
}

.window .terminal {
  width: 100%;
  height: 100%;
  margin: 0;
  padding: 0;
  background-color: #F5F5F5;
  border-bottom-left-radius: 6px;
  border-bottom-right-radius: 6px;
}
</style>
