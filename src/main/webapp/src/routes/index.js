import CoreLayout from '../layouts/CoreLayout'
import Home from './Home'
import LoginRoute from './Login'
import RegisterRoute from './Register'
import EditorRoute from './Editor'
import ProfileRoute from './Profile'
import { LOGIN_SUCCESS } from './Login/modules/login'
import User from '../domain/User'

const pubPaths = ['/login', '/register']
const loggedIn = (location) => sessionStorage.getItem('token')

export const createRoutes = (store) => ({
  path: '/',
  onEnter: ({ location }, replace) => {
    if (!loggedIn(location)) {
      if(!pubPaths.includes(location.pathname)) {
        replace('/login')
      }
    } else {
      store.dispatch({
        type: LOGIN_SUCCESS,
        payload: new User(JSON.parse(sessionStorage.getItem('user')))
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
    ProfileRoute(store)
  ]
})

export default createRoutes
