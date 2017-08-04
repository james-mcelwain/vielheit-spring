import {PlainRoute} from 'react-router'
import { injectReducer } from '../../store/reducers'
import {AppStore} from '../../store/store'

declare const require: any

export default (store: AppStore) => ({
  path : 'register',
  getComponent(nextState, cb) {
      require.ensure([], function(require: any) {
      const Register = require('./containers/RegisterContainer').default
      const reducer = require('./modules/register').default

      injectReducer(store, { key: 'register', reducer })
      cb(null, Register)
    }, 'register')
  },
} as PlainRoute)
