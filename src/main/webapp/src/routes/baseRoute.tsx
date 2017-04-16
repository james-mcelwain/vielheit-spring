import {PlainRoute} from 'react-router'
import User from '../domain/User'
import CoreLayout from '../layouts/CoreLayout'
import {AppStore} from '../store/store'
import EditorRoute from './Editor/editorRoute'
import Home from 'routes/Home/homeRoute'
import LoginRoute from 'routes/Login/loginRoute'
import Application from 'core/Application'

const pubPaths = ['/login', '/register']
const loggedIn = () => sessionStorage.getItem('token')

export const createRoutes: (s: AppStore) => PlainRoute  = (store: AppStore) => ({
  path: '/',
  onEnter: ({ location }, replace) => {
    const $cover = document.querySelector('#cover')
    if ($cover) {
      setTimeout(() => $cover.remove(), 100)
    }
    if (!loggedIn()) {
      if (!pubPaths.includes(location.pathname)) {
        replace('/login')
      }
    } else {
      store.dispatch(Application.LOGIN.dispatch(new User(sessionStorage.getItem('user'))))
      if (sessionStorage.getItem('token') && pubPaths.includes(location.pathname)) {
        replace('/')
      }
    }
  },
  component: CoreLayout,
  indexRoute: Home,
  childRoutes: [
    EditorRoute(store),
    LoginRoute(store),
  ],
})

export default createRoutes
