import { injectReducer } from '../../store/reducers'
import {PlainRoute} from "react-router"
import {AppStore} from "../../store/store"

export default (store: AppStore) => ({
  path : 'register',
  getComponent (nextState, cb) {
    require.ensure([], (require) => {
      const Register = require('./containers/RegisterContainer').default
      const reducer = require('./modules/register').default

      injectReducer(store, { key: 'register', reducer })
      cb(null,Register)
    }, 'register')
  }
} as PlainRoute)
