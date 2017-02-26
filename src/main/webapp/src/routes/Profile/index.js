import { injectReducer } from '../../store/reducers'

export default (store) => ({
  path : 'profile',
  getComponent (nextState, cb) {
    require.ensure([], (require) => {
      const Register = require('./containers/ProfileContainer').default
      const profile = require('./modules/profile').default

      injectReducer(store, { key: 'profile',  profile })
      cb(null,Register)
    }, 'profile')
  }
})
