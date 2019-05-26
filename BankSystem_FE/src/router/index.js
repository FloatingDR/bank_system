import Vue from 'vue'
import Router from 'vue-router'
import login from '@/components/Login'
import footer from '@/components/footer'
import register from '@/components/register'
import change from '@/components/changePassword'
import index from '@/components/common/index'
import aside from '@/components/common/aside'
import header from '@/components/common/header'
import info from '@/components/account/info'
import indexMain from '@/components/common/main'
import change2 from '@/components/account/changPassword'
import saveMoney from '@/components/service/saveMoney'
import transfer from '@/components/service/transfer'
import cancel from '@/components/account/cancel'

Vue.use(Router)

const router = new Router({
  routes: [
    {
      /*登录页*/
      path: '/',
      name: 'Login',
      component: login,
      children: [
        {
          path: '/',
          components: {
            footer: footer,
            register: register,
            changePassword: change
          }
        },
      ]
    },
    {
      /*登录页*/
      path: '/admin',
      name: 'admin',
      component: index,
      children: [
        {
          path: 'cancelCard',
          components: {
            footer: footer,
            aside: aside,
            header: header,
            main: cancel
          }
        },
      ]
    },
    {
      /*主页*/
      path: '/index',
      name: 'index',
      meta: {
        requireAuth: true,
      },
      component: index,
      children: [
        {
          path: '',
          components: {
            footer: footer,
            aside: aside,
            header: header,
            main: indexMain
          }
        }, {
          //这里不能写成/userinfo
          path: 'userinfo',
          components: {
            footer: footer,
            aside: aside,
            header: header,
            main: info
          }
        }, {
          path: 'changePassword',
          components: {
            footer: footer,
            aside: aside,
            header: header,
            main: change2
          }
        },
      ]
    },
    {
      path: '/service',
      name: 'service',
      meta: {
        requireAuth: true,
      },
      component: index,
      children: [
        {
          path: 'save',
          components: {
            footer: footer,
            aside: aside,
            header: header,
            main: saveMoney
          }
        }, {
          path: 'transfer',
          components: {
            footer: footer,
            aside: aside,
            header: header,
            main: transfer
          }
        },
      ]
    }
  ]
})

export default router;
/**
 * to表示即将进入的页面路由，
 * from表示当前导航正要离开的路由
 * next: Function:执行效果依赖 next 方法的调用参数。
 * next(): 进行管道中的下一个钩子。如果全部钩子执行完了，则导航的状态就是 confirmed （确认的）。
 * next(false): 中断当前的导航。如果浏览器的 URL 改变了（可能是用户手动或者浏览器后退按钮），那么 URL 地址会重置到 from 路由对应的地址。
 * next('/') 或者 next({ path: '/' }): 跳转到一个不同的地址。当前的导航被中断，然后进行一个新的导航。
 * next(error): (2.4.0+) 如果传入 next 的参数是一个 Error 实例，则导航会被终止且该错误会被传递给 router.onError() 注册过的回调。
 */
router.beforeEach((to, from, next) => {

  if (to.meta.requireAuth) {  // 判断该路由是否需要登录权限
    const isLogin = sessionStorage.getItem("$token");

    if (isLogin) {  // token存在放行
      next();
    } else {//不存在跳转到登陆页
      next({
        path: '/',
      })
    }
  } else {//如果当前页面不需要登陆，放行
    next();
  }
})
