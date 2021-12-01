import Vue from 'vue'
import Router from 'vue-router'
import Shell from '@/components/terminal-web'
import Docker from '@/components/terminal-docker'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Shell',
      component: Shell
    },{
      path: '/docker',
      name: 'Docker',
      component: Docker
    },
  ]
})
