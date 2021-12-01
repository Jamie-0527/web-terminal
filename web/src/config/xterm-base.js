import { WS } from "@/common/websocket";

const install = function(Vue) {
  const base = {
    //参数&方法 
    WS({ url, openFn, messageFn, errorFn, isInit = false } = {}) {
      return new WS({ url, openFn, messageFn, errorFn, isInit });
    },
  };
  Vue.prototype.base = base;
};

export default {
  install
};