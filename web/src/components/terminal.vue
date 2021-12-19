<template>
  <div class="window" ref="contain">
    <div class="header"></div>
    <div id="xterm" class="xterm" ref="xterm"/>
  </div>
</template>

<script>
import 'xterm/css/xterm.css'
import {Terminal} from 'xterm'
import {FitAddon} from 'xterm-addon-fit'
import SockJS from "sockjs-client";

export default {
  name: "terminal",
  props: {
    // 终端信息
    hostInfo: {type: Object},
  },
  watch: {
    'hostInfo.width': {
      handler(value) {
        this.$refs.contain.style.width = null
        this.$refs.contain.style.width = value
        this.fitAddon.fit()
      },
    },
    'hostInfo.height': {
      handler(value) {
        this.$refs.contain.style.height = null
        this.$refs.contain.style.height = value
        this.fitAddon.fit()
      },
    },
  },
  mounted() {
    this.$refs.contain.style.width = '200px'
    this.$refs.contain.style.height = '700px'
    this.initTerm();
  },
  beforeDestroy() {
    this.socket.close();
    this.term.dispose();
  },
  data() {
    return {
      term: null,
      socket: null,
      fitAddon: null,
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
      this.fitAddon = new FitAddon();
      this.fitAddon.activate(term)
      term.loadAddon(this.fitAddon);
      term.open(document.getElementById('xterm'));
      this.fitAddon.fit();
      term.focus();
      this.term = term;
      this.initSocket();
    },
    //初始化websocket
    initSocket() {
      let url = 'http://localhost:8080/stomp/websocketJS';
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

        this.socket.send(JSON.stringify({
          operate: 'connect',
          host: this.hostInfo.host,//IP
          port: this.hostInfo.port,//端口号
          username: this.hostInfo.username,//用户名
          password: this.hostInfo.password,//密码
        }));
        this.term.write('Connecting...\r\n');
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

.header {
  background-color: #E0E0E0;
  border-top-left-radius: 6px;
  border-top-right-radius: 6px;
  padding: 20px;
}

.header::before {
  content: '';
  display: inline-block;
  width: 12px;
  height: 12px;
  border-radius: 50%;
  background-color: #fd6458;
  box-shadow: 20px 0 0 #ffbf2b, 40px 0 0 #24cc3d;
  float: left;
}

.window .xterm {
  margin: 0;
  padding: 0;
  background-color: #F5F5F5;
  border-bottom-left-radius: 6px;
  border-bottom-right-radius: 6px;
}
</style>
