import { createRouter, createWebHashHistory } from 'vue-router'

import Layout from '@/layout'

export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    redirect: '/blog/index',
    hidden: true
  },

  {
    path: '/dashboard',
    redirect: '/blog/index',
    hidden: true
  },

  {
    path: '/blog',
    component: Layout,
    children: [
      {
        path: 'index',
        name: 'BlogManage',
        component: () => import('@/views/blog/index'),
        meta: { title: '博客管理', icon: 'blog' }
      }
    ]
  },

  {
    path: '/music',
    component: Layout,
    children: [
      {
        path: 'index',
        name: 'MusicManage',
        component: () => import('@/views/music/index'),
        meta: { title: '音乐管理', icon: 'music' }
      }
    ]
  },

  { path: '/:pathMatch(.*)*', redirect: '/404', hidden: true }
]

const createAppRouter = () => createRouter({
  history: createWebHashHistory(),
  scrollBehavior: () => ({ top: 0 }),
  routes: constantRoutes
})

const router = createAppRouter()
router.options = { routes: constantRoutes }

export function resetRouter() {
  router.getRoutes().forEach(route => {
    if (route.name) {
      router.removeRoute(route.name)
    }
  })
  constantRoutes.forEach(route => router.addRoute(route))
  router.options = { routes: constantRoutes }
}

export default router
