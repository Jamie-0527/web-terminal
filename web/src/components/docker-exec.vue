<template>
  <div class="father">
    <vue-drag-resize :is-resizable="false">
      <div class="window">
        <div class="header"></div>
        <div id="terminal" class="terminal"/>
      </div>
    </vue-drag-resize>
  </div>
</template>
<!--
  仅供学习参考
  以 exec -it 的方式进入容器
-->

<script>
import 'xterm/css/xterm.css'
import { Terminal } from 'xterm'
import { FitAddon } from 'xterm-addon-fit'
import SockJS from "sockjs-client";
import VueDragResize from 'vue-drag-resize'

export default {
  name: "docker-exec",
  components: {
    VueDragResize
  },
  mounted() {
    this.initTerm();
  },
  beforeDestroy() {
    this.socket.close();
    this.term.dispose();
  },
  data() {
    return {
      containerId: this.$route.query.containerId,
      term: null,
      socket: null,
    }
  },
  methods: {
    //初始化Xterm
    initTerm() {
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
      term.loadAddon(fitAddon);
      term.open(document.getElementById('terminal'));
      fitAddon.fit();
      term.focus();
      // 窗口尺寸变化时，终端尺寸自适应
      window.onresize = function () {
        fitAddon.fit()
      }
      this.term = term;
      this.initSocket();
    },
    //初始化websocket
    initSocket() {
      let url = `http://localhost:8080/stomp/websocketJS?containerId=${this.containerId}`;
      if (window.WebSocket) {
        // 创建WebSocket对象
        this.socket = new SockJS(url)
      } else {
        this.term.write('Error: WebSocket Not Supported\r\n');//否则报错
        return;
      }
      this.socketOnOpen();
      this.socketOnMessage();
      this.socketOnClose();
      this.socketOnError();
    },
    socketOnOpen() {
      this.socket.onopen = () => {

        this.term.write('Connecting...\r\n');
        this.socket.send(JSON.stringify({operate: 'connect'}));
        // 监听键盘输入
        this.term.onData((data) => {
          //发送指令
          this.socket.send(JSON.stringify({"operate": "command", "command": data}));
        });
      }
    },
    // 回显字符
    socketOnMessage() {
      let _this = this;
      this.socket.onmessage = (evt) => {
        let data = evt.data.toString();
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
