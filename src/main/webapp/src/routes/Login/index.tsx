import { injectReducer } from '../../store/reducers'
import {PlainRoute} from "react-router"
import {AppStore} from "../../store/store"

export default (store: AppStore) => ({
  path : 'login',
  getComponent (nextState, cb) {
    require.ensure([], (require) => {
      const Login = require('./containers/LoginContainer').default
      const reducer = require('./modules/login').default

      injectReducer(store, { key: 'login', reducer })

      cb(null, Login)
    }, 'login')
  }
} as PlainRoute)
