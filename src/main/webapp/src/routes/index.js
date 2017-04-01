import CoreLayout from '../layouts/CoreLayout'
import Home from './Home'
import LoginRoute from './Login'
import RegisterRoute from './Register'
import EditorRoute from './Editor'
import ProfileRoute from './Profile'

const pubPaths = ['/login', '/register']
const loggedIn = (location) => (pubPaths.includes(location.pathname) || sessionStorage.getItem('token'))

export const createRoutes = (store) => ({
  path: '/',
  onEnter: ({location}, replace) => {
    if (!loggedIn(location)) {
      replace('/login')
    } else if (sessionStorage.getItem('token') && pubPaths.includes(location.pathname)) {
      replace('/')
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
