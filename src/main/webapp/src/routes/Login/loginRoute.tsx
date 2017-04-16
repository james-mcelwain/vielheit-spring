import {PlainRoute} from 'react-router'
import { injectReducer } from '../../store/reducers'
import {AppStore} from '../../store/store'

declare const require: any

export default function(store: AppStore): PlainRoute {
  return {
    path: 'login',
    getComponent(nextState, cb) {
      require.ensure([], (require: any) => {
        const Login = require('./containers/LoginContainer').default
        const module = require('./modules/login').default

        injectReducer(store, {key: 'login', reducer: module.getReducer() })
        cb(null, Login)
      }, 'login')
    },
  }
}
