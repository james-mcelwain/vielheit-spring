import { injectReducer } from '../../store/reducers'
import {PlainRoute} from "react-router"
import {AppStore} from "../../store/store"

declare const global: any

export default (store: AppStore) => ({
  path : 'login',
  getComponent (nextState, cb) {
    global.require.ensure([], (require: any) => {
      const Login = require('./containers/LoginContainer').default
      const reducer = require('./modules/login').default

      injectReducer(store, { key: 'login', reducer })

      cb(null, Login)
    }, 'login')
  }
} as PlainRoute)
