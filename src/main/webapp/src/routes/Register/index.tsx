import { injectReducer } from '../../store/reducers'
import {PlainRoute} from "react-router"
import {AppStore} from "../../store/store"

declare const global: any

export default (store: AppStore) => ({
  path : 'register',
  getComponent (nextState, cb) {
    global.require.ensure([], (require: any) => {
      const Register = require('./containers/RegisterContainer').default
      const reducer = require('./modules/register').default

      injectReducer(store, { key: 'register', reducer })
      cb(null,Register)
    }, 'register')
  }
} as PlainRoute)
