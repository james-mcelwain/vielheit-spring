import {PlainRoute} from 'react-router'
import { injectReducer } from '../../store/reducers'
import {AppStore} from '../../store/store'

declare const require: any

export default function(store: AppStore): PlainRoute {
  return {
    path: 'login',
    getComponent(nextState, cb) {
      require.ensure([], function(require: any) {
        const Login = require('./containers/LoginContainer').default
        const reducer = require('./modules/login').default

        injectReducer(store, {key: 'login', reducer })

        cb(null, Login)
      }, 'login')
    },
  }
}
