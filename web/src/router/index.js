import Vue from 'vue'
import Router from 'vue-router'
import Shell from '@/components/terminal-web'
import DockerAttach from '@/components/docker-attach'
import DockerExec from '@/components/docker-exec'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Shell',
      component: Shell
    },{
      path: '/docker/attach',
      name: 'DockerAttach',
      component: DockerAttach
    },{
      path: '/docker/exec',
      name: 'DockerExec',
      component: DockerExec
    },
  ]
})
