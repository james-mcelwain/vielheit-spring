import {PlainRoute} from 'react-router'
import User from '../domain/User'
import CoreLayout from '../layouts/CoreLayout'
import {AppStore} from '../store/store'
import EditorRoute from './Editor'
import Home from './Home'
import LoginRoute from './Login'
import { LOGIN_SUCCESS } from './Login/modules/login'
import ProfileRoute from './Profile'
import RegisterRoute from './Register'

const pubPaths = ['/login', '/register']
const loggedIn = () => sessionStorage.getItem('token')

export const createRoutes: (s: AppStore) => PlainRoute  = (store: AppStore) => ({
  path: '/',
  onEnter: ({ location }, replace) => {
    if (!loggedIn()) {
      if (!pubPaths.includes(location.pathname)) {
        replace('/login')
      }
    } else {
      store.dispatch({
        type: LOGIN_SUCCESS(),
        payload: new User(sessionStorage.getItem('user')),
      })
      if (sessionStorage.getItem('token') && pubPaths.includes(location.pathname)) {
        replace('/')
      }
    }
  },
  component: CoreLayout,
  indexRoute: Home,
  childRoutes: [
    LoginRoute(store),
    RegisterRoute(store),
    EditorRoute(store),
    ProfileRoute(store),
  ],
})

export default createRoutes
