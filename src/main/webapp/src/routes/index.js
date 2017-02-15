// We only need to import the modules necessary for initial render
import CoreLayout from '../layouts/CoreLayout'
import Home from './Home'
import LoginRoute from './Login'
import RegisterRoute from './Register'
import EditorRoute from './Editor'

const pubPaths = ['/login', '/register']
const loggedIn = (location) => (pubPaths.includes(location.pathname) || sessionStorage.getItem('token'))

export const createRoutes = (store) => ({
  path: '/',
  onEnter: ({location}, replace) => {
    if (!loggedIn(location)) {
      replace('/login')
    }
  },
  component: CoreLayout,
  indexRoute: Home,
  childRoutes: [
    LoginRoute(store),
    RegisterRoute(store),
    EditorRoute(store)
  ]
})

export default createRoutes
